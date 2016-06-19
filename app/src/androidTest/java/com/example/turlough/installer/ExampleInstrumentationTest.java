package com.example.turlough.installer;

import android.content.Context;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.example.turlough.installer.download.DownloadBroadcastReceiver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentationTest {

    Installer installer = Installer.getInstance();
    Context context;
    DownloadBroadcastReceiver receiver;

    @Before
    public void setUp(){
        context = InstrumentationRegistry.getTargetContext();
//        receiver = new DownloadBroadcastReceiver();
//        IntentFilter intent = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//        context.registerReceiver(receiver, intent);
    }

    @After
    public void tearDown(){
//        context.unregisterReceiver(receiver);
    }

    @Test
    public void useAppContext() throws Exception {
        assertEquals("com.example.turlough.installer", context.getPackageName());
    }

    @Test
    public void testDownload() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        installer.subscribe(context, new PackageProgressListener() {

            @Override
            public void downloadCompleted(File file) {
                assertTrue("The extracted file does not exist", file.exists());
                latch.countDown();
            }

            @Override
            public void downloadFailed(Exception e) {
                latch.countDown();
            }
        });
        //installer.download(context, Uri.parse("http://www.vogella.de/img/lars/LarsVogelArticle7.png"));
        installer.download(context, Uri.parse("http://localhost/1.zip"));
        latch.await(2, TimeUnit.SECONDS);

        assertTrue("Installer reports failure", installer.isSuccess());

        installer.unsubscribe(context);
    }
}