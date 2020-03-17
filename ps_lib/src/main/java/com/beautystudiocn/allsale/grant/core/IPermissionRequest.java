package com.beautystudiocn.allsale.grant.core;



import java.util.List;

import com.beautystudiocn.allsale.grant.OnPermissionCallback;
import com.beautystudiocn.allsale.grant.PermissionResult;
import io.reactivex.Observable;

/**
 * <br> ClassName:   IPermissionRequest
 * <br> Description: 权限请求的接口
 * <br>
 * <br> Author:      wujun
 * <br> Date:        2017/9/12 10:26
 */
public interface IPermissionRequest {

    /**
     *<br> Description: 添加一个请求的权限
     *<br> Author:      wujun
     *<br> Date:        2017/9/12 10:26
     */
    IPermissionRequest addPermission(String permission);

    /**
     *<br> Description: 添加多个请求的权限
     *<br> Author:      wujun
     *<br> Date:        2017/9/12 10:26
     */
    IPermissionRequest addPermission(String... permissions);

    /**
     *<br> Description: 设置CallBack
     *<br> Author:      wujun
     *<br> Date:        2017/9/12 10:46
     */
    IPermissionRequest addPermissionCallBack(OnPermissionCallback callback);

   /**
    *<br> Description: 进行请求，并监听结果
    *<br> Author:      wujun
    *<br> Date:        2017/9/12 10:26
    */
    void request(OnPermissionCallback callback);

    /**
     *<br> Description: 进行请求
     *<br> Author:      wujun
     *<br> Date:        2017/9/12 10:26
     */
    void request();

   /**
    *<br> Description: 进行请求，并将请求结果用Observable发射
    *<br> Author:      wujun
    *<br> Date:        2017/9/12 10:27
    */
    Observable<List<PermissionResult>> observable();
}
