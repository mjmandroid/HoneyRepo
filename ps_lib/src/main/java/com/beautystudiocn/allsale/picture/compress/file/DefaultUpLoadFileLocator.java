package com.beautystudiocn.allsale.picture.compress.file;

import android.content.Context;

import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.util.StorageUtils;
import com.beautystudiocn.allsale.util.StorageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <br> ClassName:   DefaultUpLoadFileLocator
 * <br> Description: todo(这里用一句话描述这个类的作用)   
 * <br>  
 * <br> Author:      yexiaochuan                             
 * <br> Date:        2017/5/23 16:49
 */

public class DefaultUpLoadFileLocator implements IUpLoadFileLocator {
    private final Context mContext;

    public DefaultUpLoadFileLocator(Context context) {
        mContext = context;
    }

    @Override
    public String saveCompress2File(ByteArrayOutputStream out) {
        String BaseDir = StorageUtils.getCacheDirectory(mContext).getAbsolutePath();
        // 创建目录
        File cache = new File(BaseDir, "/compress");
        if (!cache.exists()) {
            cache.mkdirs();
        }
        FileOutputStream fileStream = null;
        File target = new File(cache.getAbsolutePath(),+System.currentTimeMillis() + ".jpg");
        try {
            fileStream = new FileOutputStream(target);
            out.writeTo(fileStream);
            LoggerManager.d("compress", "saveCompress2File path : " + target.getAbsolutePath());
            return target.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ByteArrayOutputStream getBitmapBytes(String path) {
        LoggerManager.d("compress", "bitmap path : " + path);
        ByteArrayOutputStream baos = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(path));
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            LoggerManager.d("compress", "ByteArrayOutputStream size : " + baos.size() / 1024 + "Kb");
            return baos;
        } catch (IOException e) {
            if (baos != null) {
                try {
                    baos.flush();
                    baos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
