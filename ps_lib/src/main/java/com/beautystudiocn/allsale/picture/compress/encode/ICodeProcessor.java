package com.beautystudiocn.allsale.picture.compress.encode;

import java.io.ByteArrayOutputStream;

/**
 * <br> ClassName:   ICodeProcessor
 * <br> Description: 数据流格式化模块接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/5/23 16:49
 */

public interface ICodeProcessor {
    /**
     *<br> Description: 为数据流编码
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/5/24 9:37
     * @param baos    需要编码的输出流
     * @return
     */
    String encryptCode(ByteArrayOutputStream baos);
}
