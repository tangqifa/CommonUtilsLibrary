package com.kejiwen.commonutilslibrary;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Utility to obtain the internal and external storage info.
 */
public class StorageUtils {

    /**
     * Get the internal storage directory (ROM)
     */
    public static String getInternalStorageDirectory() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * Get the available internal storage size (ROM).
     */
    public static long getInternalStorageAvailableSize() {
        StatFs stat = new StatFs(getInternalStorageDirectory());
        return (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
    }

    /**
     * Get the total internal storage size (ROM).
     */
    public static long getInternalStorageTotalSize() {
        StatFs stat = new StatFs(getInternalStorageDirectory());
        return (long) stat.getBlockSize() * (long) stat.getBlockCount();
    }

    public static int getInertalStorageUsedPercent() {
        long total = getInternalStorageTotalSize();
        long free = getInternalStorageAvailableSize();
        return (int) ((total - free) * 100l / total);
    }

    public static int getInertalStorageFreedPercent() {
        long total = getInternalStorageTotalSize();
        long free = getInternalStorageAvailableSize();
        return (int) (free * 100l / total);
    }

    public static int getExternalStorageUsedPercent() {
        if (StorageUtils.externalStorageAvailable()) {
            long total = getExternalStorageTotalSize();
            long free = getExternalStorageAvailableSize();
            return (int) ((total - free) * 100l / total);
        } else {
            return 0;
        }
    }

    public static int getExternalStorageFreedPercent() {
        if (StorageUtils.externalStorageAvailable()) {
            long total = getExternalStorageTotalSize();
            long free = getExternalStorageAvailableSize();
            return (int) (free * 100l / total);
        } else {
            return 0;
        }
    }

    /**
     * Check if the external storage exists (SD Card)
     *
     * @return true if the external storage exists, otherwise false
     */
    public static boolean externalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * Get the external storage directory (SD Card)
     */
    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * Get the absolute path of a relative path on the external storage (SD card)
     */
    public static String getExternalStorageSubDirectory(String relativePath) {
        return new File(Environment.getExternalStorageDirectory(), relativePath).getAbsolutePath();
    }

    /**
     * Get the available external storage size (SD Card)
     *
     * @return Return the available external storage size in bytes if possible, otherwise -1
     */
    public static long getExternalStorageAvailableSize() {
        try {
            if (externalStorageAvailable()) {
                StatFs stat = new StatFs(getExternalStorageDirectory());
                return (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
            }
        } catch (Exception e) {
            // ignore
        }
        return -1;
    }

    /**
     * Get the total external storage size (SD Card)
     *
     * @return Return the total external storage size in bytes if possible, otherwise -1
     */
    public static long getExternalStorageTotalSize() {
        try {
            if (externalStorageAvailable()) {
                StatFs stat = new StatFs(getExternalStorageDirectory());
                long temp = (long) stat.getBlockSize() * (long) stat.getBlockCount();
                return temp == 0 ? -1 : temp;
            }
        } catch (Exception e) {
            // ignore
        }
        return -1;
    }

    // TODO: The following path is not standard
    private static final String EXTRA_SDCARD_PATH = getExternalStorageDirectory() + "/external_sd";

    /**
     * Check if the extra SD card available
     */
    public static boolean extraSDCardAvailable() {
        return new File(EXTRA_SDCARD_PATH).exists();
    }

    /**
     * Get the available extra SD card size.
     *
     * @return Return the available extra SD card in bytes if possible, otherwise -1
     */
    public static long getExtraSDCardAvailableSize() {
        if (externalStorageAvailable()) {
            StatFs stat = new StatFs(EXTRA_SDCARD_PATH);
            return (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        } else {
            return -1;
        }
    }

    /**
     * Get the total extra SD card size.
     *
     * @return Return the total extra SD card size in bytes if possible, otherwise -1
     */
    public static long getExtraSDCardTotalSize() {
        if (externalStorageAvailable()) {
            StatFs stat = new StatFs(EXTRA_SDCARD_PATH);
            return (long) stat.getBlockSize() * (long) stat.getBlockCount();
        } else {
            return -1;
        }
    }
}
