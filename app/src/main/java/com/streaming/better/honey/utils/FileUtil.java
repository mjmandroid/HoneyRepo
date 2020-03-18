package com.streaming.better.honey.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * <br> ClassName:   FileUtil
 * <br> Description: 文件操作类
 * <br>
 * <br> Author:      FileUtil
 * <br> Date:        2015/01/09 14:05
 */
public class FileUtil {
    public static final String DOWNLOAD_APK_URL = Environment.getExternalStorageDirectory() + "/allSale_user/download";
    /**
     * <br> Description: 将对象序列化为本地文件
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 10:10
     *
     * @param obj      要序列化的对象
     * @param fileName 文件路径
     */
    public static void serializeFile(Context context, Object obj, String fileName) {
        FileOutputStream outStream = null;
        ObjectOutputStream objStream = null;
        try {
            outStream = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            objStream = new ObjectOutputStream(outStream);
            objStream.writeObject(obj);
            objStream.close();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objStream != null) {
                    objStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <br> Description: 从本地文件中加载序列化过的对象
     * <br> Author:      wujianghua
     * <br> Date:        2014/10/14 17:57
     * * @param fileName 本地文件路径
     *
     * @return 返回序列化过的对象
     */
    public static Object loadSerializeFile(Context context, String fileName) {
        FileInputStream inputStream = null;
        ObjectInputStream objStream = null;
        try {
            if (!existFile(context, fileName)) {
                return null;
            }
            inputStream = context.openFileInput(fileName);
            objStream = new ObjectInputStream(inputStream);
            Object obj = objStream.readObject();
            return obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objStream != null) {
                    objStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * <br> Description: 是否存在序列化的文件
     * <br> Author:      wujianghua
     * <br> Date:        2014/10/14 17:58
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 是否存在 true代表存在，false代表不存在
     */
    private static boolean existFile(Context context, String fileName) {
        String[] files = context.fileList();
        for (String file : files) {
            if (fileName.toLowerCase().equals(file.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br> Description: 获取SDCard的目录路径功能
     * 使用前需申请sdCard权限
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 11:11
     *
     * @return SDCard的目录路径
     */
    public static String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir != null ? sdcardDir.toString() : "";
    }


    /**
     * <br> Description: 删除文件
     * <br> Author:      wujianghua
     * <br> Date:        2015/04/21 21:39
     *
     * @param file 要删除的文件file对象
     * @return 是否删除成功，true代表删除成功，false代表删除失败
     */
    public static boolean deleteFoder(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                if (files != null) {
                    for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                        deleteFoder(files[i]); // 把每个文件 用这个方法进行迭代
                    }
                }
            }
            boolean isSuccess = file.delete();
            if (!isSuccess) {
                return false;
            }
        }
        return true;
    }

    /**
     * <br> Description: 递归创建文件夹
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 11:23
     *
     * @param file 要创建的文件对象
     * @return 返回文件路径，创建失败返回""
     */
    public static String createFile(File file) {
        try {
            if (file.getParentFile().exists()) {
                file.createNewFile();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * <br> Description: 递归创建文件夹
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/20 11:23
     *
     * @param dirPath 要创建的文件路径
     * @return 返回文件路径
     */
    public static String createDir(String dirPath) {
        try {
            File file = new File(dirPath);
            if(file.exists()){
                return dirPath;
            }
            if (file.getParentFile().exists()) {
                file.mkdir();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirPath;
    }


    // 生成文件
    public static File createNewFile(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }
}
