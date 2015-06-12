package com.kejiwen.commonutilslibrary;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.UUID;

public class SystemInfoUtils {

    public static String mLc = "";
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public static int getVersionCode(Context ctx) {
        int versionCode = 0;
        try {
            versionCode = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), versionCode).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;

    }

    public static String getVersionName(Context ctx) {
        String versionName = "";
        try {
            versionName = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;

    }

    public static String getApkLc(Context ctx) {
        if (TextUtils.isEmpty(mLc)) {
            AssetManager am = ctx.getAssets();
            try {
                InputStream is = am.open("lc.txt");
                mLc = FileHelper.readTxtFile(is);
                LogHelperDebug.d("LC", "lc: " + mLc);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mLc;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extract the decimal positive integer from specified string.
     *
     * @param str The string to extract.
     * @return
     */
    public static int extractPositiveInteger(String str, int defValue) {
        final int N = str.length();
        int index = 0;

        // Search the first digit character
        while (index < N) {
            char curCh = str.charAt(index);
            if (curCh >= '0' && curCh <= '9') {
                int start = index;
                // Search the first non-digit character
                index++;
                while (index < N) {
                    curCh = str.charAt(index);
                    if (curCh >= '0' && curCh <= '9') {
                        index++;
                    } else {
                        break;
                    }
                }
                String numberStr = str.substring(start, index);
                return Integer.parseInt(numberStr);
            }
            index++;
        }
        return defValue;
    }

    /**
     * Get system memory info in KB.
     *
     * @return An array with two elements: the first one is available memory in
     * KB; the second one is total memory in KB.
     */
    public static int[] getSystemMemory() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("/proc/meminfo");
            BufferedReader reader = new BufferedReader(fileReader);
            int memAvail = 0;
            int memTotal = 0;
            String line = null;
            int matchCount = 0;
            while ((line = reader.readLine()) != null) {
                if (line.contains("MemTotal")) {
                    matchCount++;
                    memTotal = extractPositiveInteger(line, 0);
                } else if (line.contains("MemFree")) {
                    matchCount++;
                    memAvail += extractPositiveInteger(line, 0);
                } else if (line.contains("Cached")) {
                    matchCount++;
                    memAvail += extractPositiveInteger(line, 0);
                }
                if (matchCount == 3) {
                    break;
                }
            }
            if (memAvail > 0 && memTotal > 0) {
                return new int[]{
                        memAvail, memTotal
                };
            }
        } catch (java.io.FileNotFoundException e) {
            // ignore the exception
        } catch (java.io.IOException e) {
            // ignore the exception
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new int[]{
                0, 0
        };
    }

    public synchronized static String getDeviceId(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String getTopPackageName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return componentInfo.getPackageName();
    }

    public static boolean isRoot() {
        boolean root = false;
        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }
        } catch (Exception e) {
        }
        return root;
    }
}
