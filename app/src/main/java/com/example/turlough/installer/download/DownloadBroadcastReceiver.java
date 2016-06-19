package com.example.turlough.installer.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.example.turlough.installer.Installer;

public class DownloadBroadcastReceiver extends BroadcastReceiver {

    private long enqueue;
    private DownloadManager dm;

    public DownloadBroadcastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);

            dm =(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            Cursor c = dm.query(query);
            if (c.moveToFirst()) {
                int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {

                    String uriString = c
                            .getString(c
                                    .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    Installer.getInstance().downloadCompleted(Uri.parse(uriString));
                }
            }
        }
    }

}
