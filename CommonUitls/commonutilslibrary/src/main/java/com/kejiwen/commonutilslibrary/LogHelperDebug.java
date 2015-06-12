package com.kejiwen.commonutilslibrary;

public class LogHelperDebug {

    private static final boolean DEBUG_LOG = true;

    public static void d(String subTag, String msg) {
        if (DEBUG_LOG)
            LogHelper.d(subTag, msg);
    }

    public static void i(String subTag, String msg) {
        if (DEBUG_LOG)
            LogHelper.i(subTag, msg);
    }

    public static void w(String subTag, String msg) {
        if (DEBUG_LOG)
            LogHelper.w(subTag, msg);
    }

    public static void w(String subTag, String msg, Throwable e) {
        if (DEBUG_LOG)
            LogHelper.w(subTag, msg, e);
    }

    public static void e(String subTag, String msg) {
        if (DEBUG_LOG)
            LogHelper.e(subTag, msg);
    }

    public static void e(String subTag, String msg, Throwable e) {
        if (DEBUG_LOG)
            LogHelper.e(subTag, msg, e);
    }

}
