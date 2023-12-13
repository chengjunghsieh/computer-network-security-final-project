package com.example.ralph.helloworld;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by ralph on 2017/6/4.
 */

public class FloatingWindow extends Service {

    private WindowManager windowManager;
    private LinearLayout linearLayout;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams parameters;
        LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        parameters = new WindowManager.LayoutParams( 110, 180,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT );

        // -------
        for(int i=0; i<10; i++) {
            linearLayout = new LinearLayout(this);
            linearLayout.setBackgroundColor(Color.argb(128, 255, 0, 0));
            linearLayout.setLayoutParams(llParameters);
            parameters.x = -540+100*i;
            parameters.y = 275;
            windowManager.addView(linearLayout, parameters);
        }




//        linearLayout = new LinearLayout(this);
//        linearLayout.setBackgroundColor(Color.argb(128, 255, 0, 0));
//        linearLayout.setLayoutParams(llParameters);
//        parameters.x = -540;
//        parameters.y = 275;
//        windowManager.addView(linearLayout, parameters);
//
//        // ------
//        linearLayout = new LinearLayout(this);
//        linearLayout.setBackgroundColor(Color.argb(128, 255, 0, 0));
//        linearLayout.setLayoutParams( llParameters );
//        parameters.x = 170;
//        parameters.y = 275;
//        windowManager.addView(linearLayout, parameters);
//
//        // -------
//        linearLayout = new LinearLayout(this);
//        linearLayout.setBackgroundColor(Color.argb(128, 255, 0, 0));
//        linearLayout.setLayoutParams(llParameters);
//        parameters.x = 270;
//        parameters.y = 275;
//        windowManager.addView(linearLayout, parameters);

    }
}
