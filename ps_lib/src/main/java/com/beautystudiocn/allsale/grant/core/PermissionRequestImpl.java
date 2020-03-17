package com.beautystudiocn.allsale.grant.core;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.beautystudiocn.allsale.grant.OnPermissionCallback;
import com.beautystudiocn.allsale.grant.PermissionResult;
import com.beautystudiocn.allsale.log.LoggerManager;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.beautystudiocn.allsale.grant.PermissionResult.Type.NO_ASK;


/**
 * <br> ClassName:   PermissionRequestImpl
 * <br> Description: 权限申请实现类
 * <br>
 * <br> Author:      wujun
 * <br> Date:        2017/9/12 10:35
 */
public class PermissionRequestImpl  implements IPermissionRequest {

    private final Activity mActivity;
    private final LinkedList<String> mPermissions = new LinkedList<>();
    private OnPermissionCallback mCallBack;

    PermissionRequestImpl(WeakReference<Activity> weakReference) {
        mActivity = weakReference.get();
    }

    @Override
    public IPermissionRequest addPermission(String permission) {
        mPermissions.add(permission);
        return this;
    }

    @Override
    public IPermissionRequest addPermission(String... permissions) {
        mPermissions.addAll(Arrays.asList(permissions));
        return this;
    }

    @Override
    public IPermissionRequest addPermissionCallBack(OnPermissionCallback observer) {
        this.mCallBack = observer;
        return this;
    }

    @Override
    public void request() {
      onRequest();
    }

    @Override
    public void request(OnPermissionCallback callback) {
        this.mCallBack = callback;
         request();
    }

    /**
     *<br> Description: 开始请求
     *<br> Author:      wujun
     *<br> Date:        2017/9/12 10:56
     */
    private void onRequest() {
        if (mCallBack == null) {
            throw new NullPointerException("OnPermissionCallback == null");
        }
        if (observable() == null) {
            return;
       }

        observable().subscribe(new Consumer<List<PermissionResult>>() {
            @Override
            public void accept(@NonNull List<PermissionResult> permissionResults) {
                LinkedList<String> mRefusedList = new LinkedList<>();
                LinkedList<String> mNoAskList = new LinkedList<>();
                LinkedList<String> mAllowList = new LinkedList<>();
                //
                for (PermissionResult result : permissionResults) {
                    switch (result.getType()) {

                        case GRANTED:
                            mAllowList.add(result.getName());
                            break;
                        case DENIED:
                            mRefusedList.add(result.getName());
                            break;

                        case NO_ASK:
                            mNoAskList.add(result.getName());
                            break;
                    }
                }
                doRequestComplete(mRefusedList, mNoAskList, mAllowList);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable)  {
                LoggerManager.d(throwable.getMessage());
            }
        });

    }

    /**
     *<br> Description: 处理权限申请结束
     *<br> Author:      wujun
     *<br> Date:        2017/9/12 18:25
     * @param mRefusedList    拒绝的权限
     * @param mNoAskList  不再询问的权限
     * @param mAllowList  允许的权限
     */
    private void doRequestComplete(LinkedList<String> mRefusedList, LinkedList<String> mNoAskList,
                                   LinkedList<String> mAllowList) {
        // 全部允许
        if (mAllowList.size() == mPermissions.size()) {
            mCallBack.onRequestAllow(mAllowList.toArray(new String[mAllowList.size()]));
            return;
        }
        //不允许或者不再询问
        if ((!mRefusedList.isEmpty() && !mNoAskList.isEmpty()) || mRefusedList.size() > 0) {
            mCallBack.onRequestRefuse(mRefusedList
                    .toArray(new String[mRefusedList.size()]));
            return;
        }
        if (mNoAskList.size() > 0) {
            mCallBack.onRequestNoAsk(mNoAskList
                    .toArray(new String[mNoAskList.size()]));
        }
    }

    @Override
    public Observable<List<PermissionResult>> observable() {
        int size = mPermissions.size();
        if (size == 0) {
            throw new IllegalStateException("need addPermission()");
        }
        if (mActivity == null || mActivity.isFinishing()) {
            return null;
        }

        return new RxPermissions(mActivity)
                .requestEach(mPermissions
                        .toArray(new String[mPermissions.size()]))
                .map(new Function<Permission, PermissionResult>() {
                    @Override
                    public PermissionResult apply(@NonNull Permission permission) throws Exception {
                        if (isGranted(permission)) {
                            return new PermissionResult(permission.name, PermissionResult.Type.GRANTED);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            return new PermissionResult(permission.name, PermissionResult.Type.DENIED);
                        } else {
                            return new PermissionResult(permission.name, NO_ASK);
                        }
                    }
                })
                .buffer(size);
    }


    /**
     *<br> Description: 权限是否通过，兼容targetSdkVersion < Android M,
     *<br> Author:      wujun
     *<br> Date:        2017/9/1 10:06
     */
    private boolean  isGranted(Permission permission) {
        int targetVersion = getTargetVersion();
        if (targetVersion < Build.VERSION_CODES.M) {
            return  PermissionChecker.checkSelfPermission(mActivity, permission.name)
                    == PermissionChecker.PERMISSION_GRANTED;
        } else {
            return  permission.granted;
        }
    }

    /**
     *<br> Description: 获取targetViersion
     *<br> Author:      wujun
     *<br> Date:        2017/9/1 10:50
     */
    private int getTargetVersion() {
        try {
            final PackageInfo info = mActivity.getPackageManager().getPackageInfo(
                    mActivity.getPackageName(), 0);
            return    info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  -1;
    }
}
