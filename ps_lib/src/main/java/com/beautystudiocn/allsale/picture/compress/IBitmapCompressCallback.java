package com.beautystudiocn.allsale.picture.compress;

import android.net.Uri;

/**
 * <br> ClassName:   IBitmapCompressCallback
 * <br> Description: 图片异步压缩接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/5/23 16:48
 */

public interface IBitmapCompressCallback {
    /**
     *<br> Description: 图片压缩中
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/5/24 14:16
     * @param progress    压缩进度
     * @param uri         图片资源地址
     */
    void onCompressing(int progress, Uri uri);

    /**
     *<br> Description: 图片压缩结束
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/5/24 14:17
     * @param filePath    文件地址
     * @param uri         图片资源地址
     */
    void onFinish(String filePath, Uri uri);

    /**
     *<br> Description: 图片压缩失败
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/5/24 14:18
     * @param errorCode    错误代码
     * @param uri          图片资源地址
     */
    void onFailed(int errorCode, Uri uri);
}
