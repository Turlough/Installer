package com.example.turlough.installer.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.turlough.installer.Installer;
import com.example.turlough.installer.PackageProgressListener;
import com.example.turlough.installer.R;

import java.io.File;

public class MainActivity extends AppCompatActivity implements PackageProgressListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

                    Installer.getInstance().download(this, Uri.parse("http://www.vogella.de/img/lars/LarsVogelArticle7.png"));
                }
        );
    }

    @Override
    protected void onResume(){
        super.onResume();
        Installer.getInstance().subscribe(this, this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        Installer.getInstance().unsubscribe(this);
    }

    @Override
    public void downloadCompleted(File file) {
        toast(file.getName());
    }

    @Override
    public void downloadFailed(Exception e) {
        toast(e.getMessage());
    }

    void toast(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
