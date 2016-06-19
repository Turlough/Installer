package com.example.turlough.installer;

import java.io.File;

/**
 * Created by turlough on 19/06/16.
 */

public interface PackageProgressListener {
    void downloadCompleted(File file);
    void downloadFailed(Exception e);
}
