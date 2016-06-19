package com.example.turlough.installer;

import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;

import com.example.turlough.installer.download.DownloadBroadcastReceiver;
import com.example.turlough.installer.download.Zip;

import java.io.File;
import java.io.IOException;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by turlough on 19/06/16.
 */
public class Installer {

    private static Installer ourInstance = new Installer();
    PackageProgressListener listener;
    long enqueue;
    boolean success = false;
    DownloadBroadcastReceiver receiver;

    public static Installer getInstance() {

        return ourInstance;
    }

    private Installer() {
        receiver = new DownloadBroadcastReceiver();
    }

    public void subscribe(Context context, PackageProgressListener listener) {
        this.listener = listener;
        IntentFilter intent = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(receiver, intent);

    }

    public void unsubscribe(Context context) {
        this.listener = null;
        context.unregisterReceiver(receiver);
    }

    public void downloadCompleted(Uri uri) {

        try {
            File extracted = new Zip().extract(uri);
            if(listener != null)
                listener.downloadCompleted(extracted);
            success = true;
        } catch (IOException e) {
            downloadFailed(e);
        }

    }

    public void downloadFailed(Exception e) {
        if(listener != null)
            listener.downloadFailed(e);
        success = false;
    }

    public void download(Context context, Uri uri){
        success = false;
        DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        enqueue = dm.enqueue(request);
    }

    public boolean isSuccess() {

        return success;
    }
}
