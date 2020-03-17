package com.beautystudiocn.allsale.autoupdate.product;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.beautystudiocn.allsale.R;
import com.beautystudiocn.allsale.autoupdate.product.custom.CustomUpdateProduct;
import com.beautystudiocn.allsale.autoupdate.product.custom.CustomUpdateProduct;


/**
 * <br> ClassName:   AbstractAutoUpdateProduct
 * <br> Description: 自动更新产品基类，
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:59
 */
public abstract class AbstractAutoUpdateProduct<T> {
    protected Context mContext;
    private boolean mIsShowNotification;
    private boolean mIsShowUpdateDialog;
    private boolean mIsForceUpdate;
    private boolean mIsSilentCheck;
    private boolean mIsShowCancelBtn;
    private String mUpdateUrl;
    private CharSequence mUpdateMessage;
    private CharSequence mUpdateTitle;
    private int mUpdateVersionCode;
    private String mUpdateVersionName;
    private int mCurrentVersionCode;
    private int mNotifySmallIcon;
    private Bitmap mNotifyLargeIcon;
    private IUpdateError mErrorCall;
    private View.OnClickListener mConfirmListener;
    private View.OnClickListener mCancelListener;

    private String mDownLoadPath;

    /**
     * <br> Description: 初始化
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/6/8 18:00
     *
     * @param context 上下文
     */
    public AbstractAutoUpdateProduct(final Context context) {
        mContext = context.getApplicationContext();
        mConfirmListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        mCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        mErrorCall = new IUpdateError() {
            @Override
            public void onFailed(int error) {
                switch (error) {
                    case CustomUpdateProduct.ERROR_IS_NEWEST:
                        Toast.makeText(mContext, mContext.getString(R.string.version_is_new), Toast.LENGTH_SHORT).show();
                        break;
                    case CustomUpdateProduct.ERROR_IS_UPDATING:
                        Toast.makeText(mContext, mContext.getString(R.string.software_is_update), Toast.LENGTH_SHORT).show();
                        break;
                    case CustomUpdateProduct.ERROR_HTTP_ERROR:
                        //  Toast.makeText(mContext,"下载失败，网络请求失败", Toast.LENGTH_SHORT).show();
                        Toast.makeText(mContext, mContext.getString(R.string.rtv_not_net) + "," + mContext.getString(R.string.connect_error), Toast.LENGTH_SHORT).show();
                        break;
                    case CustomUpdateProduct.ERROR_FILE_CREATE_ERROR:
                        // Toast.makeText(mContext,"文件创建失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        // Toast.makeText(mContext,"文件创建失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        Toast.makeText(mContext, mContext.getString(R.string.rtv_not_net) + "," + mContext.getString(R.string.connect_error), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
        mDownLoadPath = Environment.getExternalStorageDirectory() + "/allSale/download";
        mIsShowNotification = true;
        mIsShowUpdateDialog = true;
    }

    /**
     * <br> Description: 自动更新产品构建
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/6/8 18:01
     *
     * @return 返回自动更新产品功能
     */
    protected abstract T build();

    /**
     * <br> Description: 清除所有回调引用,防止内存泄漏
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/7/20 9:52
     */
    protected void destroyAllCall() {
        setErrorCall(null);
        setConfirmListener(null);
        setCancelListener(null);
    }

    public boolean isShowUpdateDialog() {
        return mIsShowUpdateDialog;
    }

    void setIsShowUpdateDialog(boolean mIsShowUpdateDialog) {
        this.mIsShowUpdateDialog = mIsShowUpdateDialog;
    }

    public boolean isForceUpdate() {
        return mIsForceUpdate;
    }

    void setIsForceUpdate(boolean mIsForceUpdate) {
        this.mIsForceUpdate = mIsForceUpdate;
    }

    protected boolean isSilentCheck() {
        return mIsSilentCheck;
    }

    void setIsSilentCheck(boolean mIsSilentCheck) {
        this.mIsSilentCheck = mIsSilentCheck;
    }

    public String getUpdateUrl() {
        return mUpdateUrl;
    }

    void setUpdateUrl(String mUpdateUrl) {
        this.mUpdateUrl = mUpdateUrl;
    }

    public CharSequence getUpdateMessage() {
        return mUpdateMessage;
    }

    void setUpdateMessage(CharSequence mUpdateMessage) {
        this.mUpdateMessage = mUpdateMessage;
    }

    protected int getUpdateVersionCode() {
        return mUpdateVersionCode;
    }

    public void setUpdateVersionCode(int mUpdateVersionCode) {
        this.mUpdateVersionCode = mUpdateVersionCode;
    }

    protected String getUpdateVersionName() {
        return mUpdateVersionName;
    }

    void setUpdateVersionName(String mUpdateVersionName) {
        this.mUpdateVersionName = mUpdateVersionName;
    }

    protected int getCurrentVersionCode() {
        return mCurrentVersionCode;
    }

    void setCurrentVersionCode(int mCurrentVersionCode) {
        this.mCurrentVersionCode = mCurrentVersionCode;
    }

    public View.OnClickListener getConfirmListener() {
        return mConfirmListener;
    }

    void setConfirmListener(View.OnClickListener mConfirmListener) {
        this.mConfirmListener = mConfirmListener;
    }

    public View.OnClickListener getCancelListener() {
        return mCancelListener;
    }

    void setCancelListener(View.OnClickListener mCancelListener) {
        this.mCancelListener = mCancelListener;
    }

    public String getDownLoadPath() {
        return mDownLoadPath;
    }

    void setDownLoadPath(String mDownLoadPath) {
        this.mDownLoadPath = mDownLoadPath;
    }

    protected boolean isShowNotification() {
        return mIsShowNotification;
    }

    void setIsShowNotification(boolean mIsShowNotification) {
        this.mIsShowNotification = mIsShowNotification;
    }

    public boolean isShowCancelBtn() {
        return mIsShowCancelBtn;
    }

    void setShowCancelBtn(boolean showCancelBtn) {
        mIsShowCancelBtn = showCancelBtn;
    }

    protected IUpdateError getErrorCall() {
        return mErrorCall;
    }

    void setErrorCall(IUpdateError mErrorCall) {
        this.mErrorCall = mErrorCall;
    }

    public CharSequence getUpdateTitle() {
        return mUpdateTitle;
    }

    public void setUpdateTitle(CharSequence updateTitle) {
        this.mUpdateTitle = updateTitle;
    }

    public int getNotifySmallIcon() {
        return mNotifySmallIcon;
    }

    public void setNotifySmallIcon(int mNotifySmallIcon) {
        this.mNotifySmallIcon = mNotifySmallIcon;
    }

    public Bitmap getNotifyLargeIcon() {
        return mNotifyLargeIcon;
    }

    public void setNotifyLargeIcon(Bitmap mNotifyLargeIcon) {
        this.mNotifyLargeIcon = mNotifyLargeIcon;
    }

    public abstract void install(String apkFile);

    public interface IUpdateError {
        void onFailed(int error);
    }
}
