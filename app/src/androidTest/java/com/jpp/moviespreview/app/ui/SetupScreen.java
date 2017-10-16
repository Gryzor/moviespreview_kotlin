package com.jpp.moviespreview.app.ui;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by jpp on 10/16/17.
 */

public class SetupScreen {

    public static void setupScreen(final Activity activity) {
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }
}
