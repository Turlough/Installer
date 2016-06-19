package com.example.turlough.installer.download;

import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * Created by turlough on 19/06/16.
 */

public class Zip {

    private final File outputDirectory = new File("/sdcard/Avego/FTM/content/incoming/");


    public File extract(Uri uri) throws IOException {

        File source = new File(uri.getPath());
        String name = source.getName();
        File outfile = new File(outputDirectory, name);
        outputDirectory.mkdirs();

        InputStream is;
        ZipInputStream zis;
        FileOutputStream fout = null;

        String filename;
        is = new FileInputStream(source);
        zis = new ZipInputStream(new BufferedInputStream(is));
        ZipEntry ze;
        byte[] buffer = new byte[1024];
        int count;

        while ((ze = zis.getNextEntry()) != null) {
            filename = ze.getName();

            if (ze.isDirectory()) {
                File fmd = new File(outfile, filename);
                fmd.mkdirs();
                continue;
            }

            fout = new FileOutputStream(outputDirectory + filename);

            while ((count = zis.read(buffer)) != -1) {
                fout.write(buffer, 0, count);
            }

            fout.close();
            zis.closeEntry();
        }

        zis.close();

        return outfile;
    }


}
