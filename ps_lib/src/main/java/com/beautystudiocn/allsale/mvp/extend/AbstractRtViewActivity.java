package com.beautystudiocn.allsale.mvp.extend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.beautystudiocn.rxnetworklib.network.bean.ApiException;

import com.beautystudiocn.allsale.mvp.network.AbstractNetworkActivity;
import com.beautystudiocn.allsale.mvp.network.IReloadActionView;
import com.beautystudiocn.allsale.mvp.network.NetworkPresenter;
import com.beautystudiocn.allsale.R;
import com.beautystudiocn.allsale.util.ToastUtil;
import com.beautystudiocn.allsale.util.UIUtil;
import com.beautystudiocn.allsale.widget.dialog.ProgressHUD;
import com.beautystudiocn.allsale.widget.reloadview.PageTips;
import com.beautystudiocn.allsale.widget.reloadview.ReloadTipsView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <br> ClassName:   AbstractRtViewActivity
 * <br> Description: activity view层基类
 */
public abstract class AbstractRtViewActivity<T extends NetworkPresenter>
        extends AbstractNetworkActivity<T> implements ReloadTipsView.LoadTipsListener, IReloadActionView {
    /*请求失败*/
    public static final int TASK_REQUEST_FAIL = 100;
    /**
     * 无数据
     */
    public static final int TASK_REQUEST_NO_DATA = 101;
    /**
     * 接口请求无网络
     */
    public static final int TASK_REQUEST_NOT_NET = 103;
    private LinearLayout mLayoutMain;
    private View mMainContent;
    private RelativeLayout mRlTitle;

    private LinearLayout mIvLeft;
    private ImageView mIvRight;
    private Button mBtnRight;
    private TextView mTvTitle;

    /***todo标题***/
    public String mTitleName = "";

    protected View mRtvReload;

    /***提示内容***/
    private PageTips mPageTips;

    /***是否显示情感图***/
    protected boolean mIsShowReload = false;

    /***是否打开重新加载***/
    private boolean mIsOpenReload;
    /***标题的横线***/
    private View mBaseline;
    /**
     * 是否在父类，自动使用ButterKnife绑定控件
     */
    protected boolean isAutoBindView = true;
    /***ButterKnife绑定控件***/
    protected Unbinder mUnbinder;
    protected ProgressHUD mProgressHUD;
    protected String mPageId = this.getClass().getSimpleName();
    private ImageView mIvTwoRight;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutMain = new LinearLayout(this);
        mLayoutMain.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        mLayoutMain.setOrientation(LinearLayout.VERTICAL);
        setContentView(mLayoutMain);

        if (getCurrentPresenter() != null) {
            getCurrentPresenter().setReloadView(this);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //增加title、baseLine
        mRlTitle = (RelativeLayout) getLayoutInflater().inflate(R.layout.lib_layout_base_activity_title, getMainLayout(), false);
        getMainLayout().addView(mRlTitle, -1);
        //mBaseline = mRlTitle.findViewById(R.id.view_baseline);
        //增加内容
        mMainContent = getLayoutInflater().inflate(layoutResID, null);
        getMainLayout().addView(mMainContent, new ViewGroup.LayoutParams(-1, -1));

        init();

        if (isAutoBindView) {
            mUnbinder = ButterKnife.bind(this);
        }
    }

    public LinearLayout getMainLayout() {
        return mLayoutMain;
    }

    /**
     * <br> Description: 增加布局和Header
     * <br> Author:      wujianghua
     * <br> Date:        2017/8/17 22:02
     *
     * @param layoutResID int
     * @param headerResID int
     */
    public void setContentView(@LayoutRes int layoutResID, @LayoutRes int headerResID) {
        mRlTitle = (RelativeLayout) getLayoutInflater()
                .inflate(headerResID, getMainLayout(), false);
        getMainLayout().addView(mRlTitle, -1);

        //增加内容
        mMainContent = getLayoutInflater().inflate(layoutResID, null);
        getMainLayout().addView(mMainContent, new ViewGroup.LayoutParams(-1, -1));

        if (isAutoBindView) {
            mUnbinder = ButterKnife.bind(this);
        }
    }


    /**
     * <br> Description: 初始化HeaderView
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:02
     */
    private void init() {
        //备注：xml中标签名已按新规范修改，自定义headerView时需注意
        mIvLeft = (LinearLayout) mRlTitle.findViewById(R.id.iv_left);
        mTvTitle = (TextView) mRlTitle.findViewById(R.id.tv_title);
        mBtnRight = (Button) mRlTitle.findViewById(R.id.btn_right);
        mIvRight = (ImageView) mRlTitle.findViewById(R.id.iv_right);
        mIvTwoRight = (ImageView) mRlTitle.findViewById(R.id.iv_right_two);
        mIvLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                clickBack(arg0);
            }
        });
    }

    public ImageView getRightTwoImageView() {
        return mIvTwoRight;
    }

    /**
     * <br> Description: 设置标题栏
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:04
     *
     * @param titleResId int
     */
    protected void setTitleBar(int titleResId) {
        if (mTvTitle != null) {
            mTvTitle.setText(getString(titleResId));
        }
        mTitleName = getString(titleResId);
    }

    public String getTitleBar() {
        if (!TextUtils.isEmpty(mTitleName)) {
            return mTitleName;
        }
        return getClass().getSimpleName();
    }

    /**
     * <br> Description: 设置标题栏
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:05
     *
     * @param title String
     */
    protected void setTitleBar(String title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
        mTitleName = title;
    }

    /**
     * <br> Description: 设置标题栏 标题文本颜色
     * <br> Author:      wujianghua
     * <br> Date:        2018/1/15 17:30
     */
    protected void setTitleTxtColor(int color) {
        if (mTvTitle != null) {
            mTvTitle.setTextColor(color);
        }
    }

    /**
     * <br> Description: 获取Activity标题栏
     * <br> Author:      wujianghua
     * <br> Date:        2017/8/8 10:56
     */
    protected RelativeLayout getLayRTitle() {
        return mRlTitle;
    }

    /**
     * <br> Description: 隐藏标题
     * <br> Author:     wujianghua
     * <br> Date:        2017/9/29 15:45
     */
    protected void hideTitle() {
        UIUtil.setVisibility(mRlTitle, false);
    }

    /**
     * <br> Description: 显示标题（没横杆的）
     * <br> Author:     wujianghua
     * <br> Date:        2017/9/29 15:45
     */
    protected void showNoLineTitle() {
        UIUtil.setVisibility(mRlTitle, true);
        UIUtil.setVisibility(mBaseline, false);
    }

    /**
     * 隐藏标题栏（移除掉）
     */
    protected void goneTitle() {
        getMainLayout().removeView(mRlTitle);
        mTitleName = "";
        if (isAutoBindView) {
            mUnbinder = ButterKnife.bind(this);
        }
    }

    /**
     * <br> Description: 隐藏左上角后退键
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:02
     */
    protected void goneBackButton() {
        if (mIvLeft != null) {
            mIvLeft.setVisibility(View.GONE);
        }
    }

    /**
     * <br> Description: 获取左上角后退键
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:03
     *
     * @return ImageView imageView
     */
    protected LinearLayout getLeftButton() {
        return mIvLeft;
    }

    /**
     * <br> Description: 获取右上角按钮
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:04
     *
     * @return Button button
     */
    protected Button getRightButton() {
        mBtnRight.setVisibility(View.VISIBLE);
        return mBtnRight;
    }

    /**
     * <br> Description: 获取右上角图片
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:04
     *
     * @return ImageView imageView
     */
    protected ImageView getRightImageView() {
        return mIvRight;
    }

    /**
     * 点击后退
     */
    public void clickBack(View view) {
        boolean sHowKeyboard = isSHowKeyboard(this, view);
        if (sHowKeyboard == true) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(AbstractRtViewActivity.this
                    .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        finish();
    }

    /**
     * 判断软键盘是否弹出
     */
    public static boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    @Override
    public void clickReloadData() {
        if (getCurrentPresenter() != null) {
            getCurrentPresenter().retryRequest();
        }
    }

    /**
     * <br> Description: 初始化情感图ReloadTipsView
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:17
     */
    protected View initRtvReload() {
        //情感图
        ReloadTipsView rtvReload = new ReloadTipsView(this);
        rtvReload.setVisibility(View.GONE);
        rtvReload.setOnReloadDataListener(this);
        return rtvReload;
    }

    public View getReloadView() {
        return mRtvReload;
    }

    @Override
    public void displaySuccess(String taskId, Object result) {
        super.displaySuccess(taskId, result);
        if (getCurrentPresenter() != null) {
            setVisibilityReloadView(View.GONE, getCurrentPresenter().getRetryReqTaskId(), taskId,
                    TASK_REQUEST_FAIL);
        }
    }

    @Override
    public void displayRequestFailure(String taskId, ApiException e) {
        super.displayRequestFailure(taskId, e);
        if (getCurrentPresenter() != null) {
            setVisibilityReloadView(View.VISIBLE, getCurrentPresenter().getRetryReqTaskId(), taskId,
                    TASK_REQUEST_FAIL);
        }
    }

    @Override
    public void displayNetworkError(String taskId, ApiException e) {
        super.displayNetworkError(taskId, e);
        if (getCurrentPresenter() != null) {
            setVisibilityReloadView(View.VISIBLE, getCurrentPresenter().getRetryReqTaskId(), taskId,
                    TASK_REQUEST_FAIL);
        }
    }

    @Override
    public void displayRequestNotNet(String taskId, ApiException e) {
        super.displayRequestNotNet(taskId, e);
        if (getCurrentPresenter() != null) {
            setVisibilityReloadView(View.VISIBLE, getCurrentPresenter().getRetryReqTaskId(), taskId,
                    TASK_REQUEST_NOT_NET);
        }
    }

    @Override
    public void onStartToRetry() {
        if (mRtvReload == null) {
            mRtvReload = initRtvReload();
            /*if (mRtvReload != null) {
                getMainLayout().addView(mRtvReload);
            }*/
        }
        this.mIsOpenReload = true;
    }

    /**
     * <br> Description: 设置提示内容
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:18
     *
     * @param pageTips PageTips
     */
    protected void setEmptyTips(PageTips pageTips) {
        mPageTips = pageTips;
    }

    /**
     * <br> Description: 显示、隐藏情感图
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:18
     *
     * @param visibility    是否可见
     * @param currentTaskId 指定重新加载的任务
     * @param taskId        当前请求任务
     * @param resultType    类型 (TASK_REQUEST_FAIL、TASK_REQUEST_NOT_NET、TASK_REQUEST_NO_DATA)
     */
    public void setVisibilityReloadView(int visibility, String currentTaskId,
                                        String taskId, int resultType) {
        if (mRtvReload == null || !(mRtvReload instanceof ReloadTipsView)) {
            return;
        }

        ReloadTipsView reloadTipsView = (ReloadTipsView) mRtvReload;

        if (!mIsOpenReload || !currentTaskId.equals(taskId) || visibility != View.VISIBLE) {
            showMainContentLayout();
            reloadTipsView.setVisibility(View.GONE);
            return;
        }

        hideMainContentLayout();
        reloadTipsView.setVisibility(visibility);
        switch (resultType) {
            case TASK_REQUEST_NOT_NET:
                reloadTipsView.showNoNetworkTips();
                break;
            case TASK_REQUEST_NO_DATA:
                if (mPageTips != null) {
                    reloadTipsView.showCustomEmptyTips(mPageTips);
                } else {
                    reloadTipsView.showEmptyTips();
                }
                break;
            default:
                reloadTipsView.showFailureTips();
                break;
        }
    }

    /**
     * <br> Description: 隐藏主布局
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:23
     */
    protected void hideMainContentLayout() {
        mIsShowReload = true;
        if (mMainContent != null) {
            mMainContent.setVisibility(View.GONE);
        }
    }

    /**
     * <br> Description: 显示主布局
     * <br> Author:      wujianghua
     * <br> Date:        2017/5/24 16:23
     */
    protected void showMainContentLayout() {
        mIsShowReload = false;
        if (mMainContent != null) {
            mMainContent.setVisibility(View.VISIBLE);
        }
    }

    public void showLoading() {
        showLoading("");
    }

    @Override
    public void showLoading(String tips) {
        if (tips == null || isFinishing()) {
            return;
        }

        if (mProgressHUD == null) {
            mProgressHUD = ProgressHUD.show(this, tips);
        } else {
            mProgressHUD.setMessage(tips);
            if (!mProgressHUD.isShowing()) {
                mProgressHUD.show();
            }
        }
    }

    @Override
    public void dismissLoading() {
        if (mProgressHUD != null && !isFinishing()) {
            mProgressHUD.dismiss();
            mProgressHUD = null;
        }
    }

    @Override
    public void showToast(String info) {
        if (!TextUtils.isEmpty(info)) {
            ToastUtil.showToast(info, getMainLayout());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToastUtil.setDefaultRootView(mLayoutMain);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mProgressHUD != null) {
            mProgressHUD.dismiss();
            mProgressHUD = null;
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }
}
