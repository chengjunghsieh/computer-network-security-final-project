package com.example.ralph.helloworld;

/**
 * Created by user on 6/11/17.
 */
import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import static android.view.accessibility.AccessibilityEvent.TYPE_VIEW_CLICKED;

public class MyAccessibilityService extends AccessibilityService{

    private static final String TAG = MyAccessibilityService.class.getSimpleName();
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, "ACC::onAccessibilityEvent: " + event.getEventType());
        //TYPE_WINDOW_STATE_CHANGED == 32
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event
                .getEventType()) {
            Log.i(TAG, "window state changed");
            AccessibilityNodeInfo nodeInfo = event.getSource();
//            Log.wtf(TAG, "ACC::onAccessibilityEvent: nodeInfo=" + nodeInfo);
            if (nodeInfo == null) {
                Log.i(TAG, "ACC:NULL");
                return;
            }
           // Log.wtf(TAG, "ACC: Open Camera");
            List<AccessibilityNodeInfo> list =
                    nodeInfo.findAccessibilityNodeInfosByViewId("com.example.ralph.helloworld:id/btn");

            for (AccessibilityNodeInfo node : list) {
 //               Log.i(TAG, "ACC::onAccessibilityEvent: button1 " + node);
                   node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
           // Log.wtf("Camera", "ACC: Take Photo");
            List<AccessibilityNodeInfo> list2 = nodeInfo.
                    findAccessibilityNodeInfosByViewId("com.android.camera2:id/shutter_button");

            for (AccessibilityNodeInfo node : list2) {
                //                Log.i("Camera", "ACC: Camera shutter button" + node);
                //            AccessibilityNodeInfo node = list2.get(0);
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }


        }
        else if (AccessibilityEvent.TYPE_VIEW_CLICKED == event
                .getEventType()) {
            Log.wtf(TAG, "ACC:"+event.getContentDescription());
            AccessibilityNodeInfo nodeInfo = event.getSource();
            if(nodeInfo == null) {
                Log.i(TAG, "ACC:NULL");
                return;
            }
            Log.wtf("Camera", "ACC: Take photo Done" + nodeInfo.getText());

            AccessibilityNodeInfo parent =  nodeInfo.getParent();

            if(parent == null ) return;
            Log.wtf("Camera", "ACC: Take photo Done" + parent);

        }
        else if(AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ==
                event.getEventType()) {
            Log.wtf(TAG, "ACC: WINDOW CONTENT CHANGED");
            AccessibilityNodeInfo nodeInfo = event.getSource();
            if(nodeInfo == null) {
                Log.i(TAG, "ACC:NULL");
                return;
            }
        }
        else if(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED == event.getEventType()) {
            Log.wtf(TAG, "ACC: view text changed");
            AccessibilityNodeInfo nodeInfo = event.getSource();
            Log.wtf(TAG, "ACC: EditText: " + nodeInfo.getText());
        }
        else if(AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED == event.getEventType()) {
            Log.wtf(TAG, "ACC: VIEW CONTEXT CLICKED");
//            AccessibilityNodeInfo nodeInfo = event.getSource();
            Log.wtf(TAG, "ACC: POSITION X: " + event.getScrollX());
            Log.wtf(TAG, "ACC: POSITION Y: " + event.getScrollY());
        }
    }

    @Override
    public void onServiceConnected() {
        Log.i(TAG, "ACC::onServiceConnected: ");
    }

    @Override
    public void onInterrupt() {
        Log.i(TAG, "Service Interrupt.");
    }
}
