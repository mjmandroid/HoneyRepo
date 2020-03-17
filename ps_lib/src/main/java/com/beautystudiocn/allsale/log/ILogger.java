package com.beautystudiocn.allsale.log;

/**
 * <br> ClassName:   ILogger
 * <br> Description: 日志接口
 * <br>
 * <br> Author:      hehaodong
 * <br> Date:        2017-05-31 16:06
 */
public interface ILogger {

    /**
     *<br> Description:打印详细信息，自定义Tag方法
     *<br> Author:      hehaodong
     *<br> Date:        2017-05-31 16:16
     * @param tag 标签
     * @param msg 日志信息
     */
    void v(String tag, String msg);

    /**
     *<br> Description: 打印详细信息，自定义Tag方法，还有异常信息
     *<br> Author:      hehaodong
     *<br> Date:        2017-05-31 16:19
     * @param tag 标签
     * @param msg 日志信息
     * @param tr 异常信息
     */
    void v(String tag, String msg, Throwable tr);

    /**
     *<br> Description: 打印debug信息，自定义Tag方法
     *<br> Author:      hehaodong
     *<br> Date:        2017-05-31 16:20
     * @param tag 标签
     * @param msg 日志信息
     */
    void d(String tag, String msg);

    /**
     *<br> Description: 打印debug信息，自定义Tag方法，还有异常信息
     *<br> Author:      hehaodong
     *<br> Date:        2017-05-31 16:21
     * @param tag 标签
     * @param msg 日志信息
     * @param tr 异常信息
     */
    void d(String tag, String msg, Throwable tr);

    /**
     *<br> Description: 打印信息，自定义Tag方法
     *<br> Author:      hehaodong
     *<br> Date:        2017-05-31 16:21
     * @param tag 标签
     * @param msg 日志信息
     */
    void i(String tag, String msg);

    /**
     *<br> Description: 打印信息，自定义Tag方法，还有异常信息
     *<br> Author:      hehaodong
     *<br> Date:        2017-05-31 16:21
     * @param tag 标签
     * @param msg 日志信息
     * @param tr 异常信息
     */
    void i(String tag, String msg, Throwable tr);

    /**
     *<br> Description: 打印警告信息，自定义Tag方法
     *<br> Author:      hehaodong
     *<br> Date:        2017-05-31 16:21
     * @param tag 标签
     * @param msg 日志信息
     */
    void w(String tag, String msg);

    /**
     *<br> Description: 打印警告信息，自定义Tag方法，还有异常信息
     *<br> Author:      hehaodong
     *<br> Date:        2017-05-31 16:21
     * @param tag 标签
     * @param msg 日志信息
     * @param tr 异常信息
     */
    void w(String tag, String msg, Throwable tr);

    /**
     *<br> Description: 打印错误信息，自定义Tag方法
     *<br> Author:      hehaodong
     *<br> Date:        2017-05-31 16:21
     * @param tag 标签
     * @param msg 日志信息
     */
    void e(String tag, String msg);

    /**
     *<br> Description: 打印错误信息，自定义Tag方法，还有异常信息
     *<br> Author:      hehaodong
     *<br> Date:        2017-05-31 16:21
     * @param tag 标签
     * @param msg 日志信息
     * @param tr 异常信息
     */
    void e(String tag, String msg, Throwable tr);

    /**
     *<br> Description: JSON格式，打印信息，自定义tag
     *<br> Author:      hehaodong
     *<br> Date:        2017-05-31 17:31
     * @param tag 标签
     * @param msg 日志信息
     */
    void json(String tag, String msg);

}
