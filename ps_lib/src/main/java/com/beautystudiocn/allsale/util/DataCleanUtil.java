package com.beautystudiocn.allsale.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * <br> ClassName:   DataCleanUtil
 * <br> Description:本应用数据清除管理器(主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录)
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2017/7/20 9:19
 */
public class DataCleanUtil {

    /**
     * <br> Description: 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:20
     *
     * @param context 上下文
     */
    private static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * <br> Description: 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:21
     *
     * @param context 上下文
     */
    private static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * <br> Description: 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:21
     *
     * @param context 上下文
     */
    private static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * <br> Description: 按名字清除本应用数据库
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:22
     *
     * @param context 上下文
     * @param dbName  要清除的数据库名
     */
    private static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * <br> Description: 清除/data/data/com.xxx.xxx/files下的内容
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:23
     *
     * @param context 上下文
     */
    private static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * <br> Description: 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:23
     *
     * @param context 上下文
     */
    private static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * <br> Description: 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:24
     *
     * @param filePath 要清除的文件路径
     */
    private static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * <br> Description: 清除本应用所有的数据（不包括数据库和sharedpreference数据）
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:25
     *
     * @param filepath 需要清除的文件路径
     */
    public static void cleanApplicationData(String... filepath) {
        cleanInternalCache(AppUtil.getContext());
        cleanExternalCache(AppUtil.getContext());
        cleanFiles(AppUtil.getContext());
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * <br> Description: 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:28
     *
     * @param dir 要删除的文件
     * @return 返回是否删除成功，true代表删除成功，false代表删除失败
     */
    private static boolean deleteFilesByDirectory(File dir) {
        if (dir == null) {
            return false;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null && children.length > 0) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteFilesByDirectory(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * <br> Description: 获取缓存大小
     * <br> Author:      longluliu
     * <br> Date:        2015/05/06 11:27
     *
     * @return 返回缓存大小
     */

    public static String getTotalCacheSize() {
        long cacheSize = getFolderSize(AppUtil.getContext().getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(AppUtil.getContext().getExternalCacheDir());
            //cacheSize += getFolderSize(new File(cachePath));
        }
        return getFormatSize(cacheSize);
    }


    /**
     * <br> Description: 获取文件大小
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:30
     *
     * @param file 文件对象
     * @return 返回文件字节大小
     */
    private static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if (fileList != null && fileList.length > 0) {
                for (int i = 0; i < fileList.length; i++) {
                    // 如果下面还有文件
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * <br> Description: 删除指定目录下文件及目录
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:34
     *
     * @param filePath       目录路径
     * @param deleteThisPath 要删除的目录文件路径
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * <br> Description: 格式化文件大小单位
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 9:36
     *
     * @param size 文件字节大小
     * @return 返回带单位的文件大小（byte,KB,MB,GB,TB）
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

}