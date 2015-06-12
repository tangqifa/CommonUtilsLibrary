package com.kejiwen.commonutilslibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {
    static Handler mHandler;

    public static void showMidToast(final Context context, final String msg) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.post(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(context.getApplicationContext(), msg,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
            }
        });
    }
}
