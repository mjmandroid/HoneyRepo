package com.beautystudiocn.allsale.picture.compress.compressor;

import android.net.Uri;

import java.io.ByteArrayOutputStream;

/**
 * <br> ClassName:   IBitmapCompressor
 * <br> Description: 图片压缩模块接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/5/23 16:49
 */

public interface IBitmapCompressor {
    /**
     *<br> Description: 设置压缩参数
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/5/24 13:52
     * @param maxSize     最大压缩大小
     * @param minWidth    最小压缩长度
     */
    void setCompressLimitSize(int maxSize, int minWidth);

    /**
     *<br> Description: 图片压缩
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/5/24 13:53
     * @param uri      图片资源
     * @return         输出流
     */
    ByteArrayOutputStream compress(Uri uri);
}
