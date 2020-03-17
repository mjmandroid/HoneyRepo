package com.beautystudiocn.allsale.widget.guideview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.beautystudiocn.allsale.log.LoggerManager;
import com.beautystudiocn.allsale.log.LoggerManager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <br> ClassName:   GuideView
 * <br> Description: 引导图
 * <br>
 * <br> Author:      KevinWu
 * <br> Date:        2018/4/20 15:24
 */
public class PSGuideView extends RelativeLayout implements IPSGuideShowState {

    /*** 目标View ***/
    private View mTargetView = null;
    /*** 显示的引导View ***/
    private View mHintView = null;
    /*** 引导 ***/
    private SoftReference<PSGuideImageView> mMaskBitmap;
    private PSGuideImageView mPSGuideImageView = null;
    /*** 默认只显示一次 ***/
    private boolean isShowOnce = true;

    /*** 点击其它位置退出 ***/
    private boolean isTouchOutExit = true;
    /*** 是否已显示 ***/
    private boolean isShow;

    /*** 依赖的父View ***/
    private ViewGroup mParentView = null;

    /*** 高亮是否可点击 ***/
    private boolean isHighlightClickable;
    /*** key ***/
    private String mKey;
    /*** 版本号 ***/
    private String mVersionName;
    /*** 配置文件 ***/
    private PSGuideConfig mConfig = null;
    /*** 显示的位置，相对targetView ***/
    private Direction mDirection = Direction.BOTTOM;
    /*** 位置 ***/
    private int mPosition;
    /*** 渲染偏移的长度 ***/
    private int mTmpRenderDeviation = 3;

    public PSGuideView(@NonNull Context context) {
        super(context);
        this.initView();
    }

    public PSGuideView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initView();
    }

    public PSGuideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView();
    }

    /**
     * <br> Description: 初始化View
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/20 15:35
     */
    @SuppressLint("ResourceType")
    private void initView() {
        setLayoutParams(new LayoutParams(-1, -1));
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTouchOutExit) {
                    destory();
                }
            }
        });
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setClipChildren(false);
    }

    /**
     * <br> Description: 初始配置文件
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/28 19:08
     */
    public PSGuideConfig getConfig() {
        if (mConfig == null) {
            mConfig = new PSGuideConfig();
        }
        return mConfig;
    }

    public void setConfig(PSGuideConfig mConfig) {
        this.mConfig = mConfig;
    }

    /**
     * <br> Description: 目标视图
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/20 16:38
     */
    public void setTargetView(@NonNull View targetView) {
        this.mTargetView = targetView;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    /**
     * <br> Description: 引导视图
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/20 16:38
     */
    public void setHintView(@NonNull View hintView) {
        this.mHintView = hintView;
        mHintView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                LoggerManager.d("zllll", "mHintView new (" + left + "," + top + "," + right + "," + bottom + ")");
                LoggerManager.d("zllll", "mHintView old (" + oldLeft + "," + oldTop + "," + oldRight + "," + oldBottom + ")");

            }
        });
    }

    /**
     * <br> Description: 依赖的ViewGroup
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/24 18:07
     */
    public void setParentView(@NonNull ViewGroup parentView) {
        if (parentView == null) {
            return;
        }
        this.mParentView = parentView;
    }

    /**
     * <br> Description: 设置位置
     * <br> Author:      KevinWu
     * <br> Date:        2018/6/1 10:05
     */
    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    /**
     * <br> Description: 是否只显示一次
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/20 16:40
     */
    public void setShowOnce(boolean isShowOnce) {
        this.isShowOnce = isShowOnce;
    }

    /**
     * <br> Description: 返回显示次数
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/20 16:42
     */
    public boolean isShowOnce() {
        return this.isShowOnce;
    }

    private void initGuideImage() {
        if (this.mPSGuideImageView == null) {
            this.mPSGuideImageView = new PSGuideImageView(getContext());
            this.mPSGuideImageView.setConfing(getConfig());
            this.mPSGuideImageView.setId(mPosition);
            mPSGuideImageView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (isHighlightClickable) {
                        return false;
                    } else {
                        return true;
                    }
                }
            });
            mMaskBitmap = new SoftReference<>(mPSGuideImageView);
        }
    }


    /**
     * <br> Description: 显示
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/20 18:27
     */
    @Override
    public void show() {
        setBackgroundColor(getConfig().getBackgroundColor());
        if (mParentView == null) {
            ((FrameLayout) ((Activity) getContext()).getWindow().getDecorView()).addView(this);
            if (mTargetView != null) {
                //设置targetView的回调
                mTargetView.addOnLayoutChangeListener(mOnLayoutChangeListener);
                initGuideImage();
                iniGuideImageLocation(mTargetView);
                initHintView();   // 文字描述
            }

        } else {
            mParentView.addView(this);
        }
        isShow = true;
    }

    private OnLayoutChangeListener mOnLayoutChangeListener = new OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            LoggerManager.d("zll", "mTargetView new (" + left + "," + top + "," + right + "," + bottom + ")");
            LoggerManager.d("zll", "mTargetView old (" + oldLeft + "," + oldTop + "," + oldRight + "," + oldBottom + ")");

            LoggerManager.e("onGlobalLayout", "  " + countGlobal++);
            initGuideImage();
            iniGuideImageLocation(v);
            initHintView();   // 文字描述
        }
    };

    /**
     * <br> Description: 延时弹出，默认1秒
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/23 16:51
     */
    public void show(long delayMillis) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                show();
            }
        }, delayMillis > 0 ? delayMillis : 300);
    }

    /**
     * <br> Description: 隐藏
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/23 10:06
     */
    @Override
    public void hide() {
        if (mParentView == null) {
            ((FrameLayout) ((Activity) getContext()).getWindow().getDecorView()).removeView(this);
        } else {
            mParentView.removeView(this);
        }
        isShow = false;
    }

    @Override
    public void destory() {
        hide();
        if (mTargetView != null) {
            mTargetView.destroyDrawingCache();//释放缓存占用的资源
            mTargetView.setDrawingCacheEnabled(false);
            mTargetView.removeOnLayoutChangeListener(mOnLayoutChangeListener);
        }
        if (mMaskBitmap != null && mMaskBitmap.get() != null) {
            mMaskBitmap.get().destory();
            mMaskBitmap = null;
        }
    }

    @Override
    public void refreshView() {
        invalidate();
    }

    /**
     * <br> Description: 是否已显示
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/24 9:42
     */
    public boolean isShow() {
        return isShow;
    }

    private int countGlobal = 1;


    /**
     * <br> Description: 初始化图片
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/23 14:13
     */
    private void iniGuideImageLocation(View targetView) {
        if (targetView != null) {
            targetView.setDrawingCacheEnabled(true);//设置能否缓存图片信息（drawing cache）
            targetView.buildDrawingCache();
            Bitmap bitmap = targetView.getDrawingCache();
            int renderWidth = getConfig().getHighLightWidth();
            int measuredWidth = targetView.getMeasuredWidth();
            int meausredHeight = targetView.getMeasuredHeight();
            LayoutParams params = new LayoutParams(measuredWidth + renderWidth * mTmpRenderDeviation * 2,
                    meausredHeight + renderWidth * mTmpRenderDeviation * 2);
            int location[] = new int[2];
            targetView.getLocationOnScreen(location);
            int tmpRender = renderWidth * mTmpRenderDeviation; // 可视的渲染宽度
            params.setMargins(location[0] - tmpRender, location[1] - tmpRender, 0, 0);

            mPSGuideImageView.layout(location[0] - tmpRender, location[1] - tmpRender,
                    location[0] + measuredWidth + tmpRender, location[1] + meausredHeight + tmpRender);

            if (this.contain(this, mPSGuideImageView)) {
                this.removeView(mPSGuideImageView);
            }
            this.addView(mPSGuideImageView, params);
            mPSGuideImageView.setImageBitmap(bitmap);
            mPSGuideImageView.refreshView();
        }
    }

    /**
     * <br> Description: 初始化
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/23 16:53
     */
    private void initHintView() {
        if (mDirection == Direction.LEFT) {
            showHintViewLeft();
        } else if (mDirection == Direction.TOP) {
            showHintViewTop();
        } else if (mDirection == Direction.RIGHT) {
            showHintViewRight();
        } else if (mDirection == Direction.BOTTOM) {
            showHintViewBottom();
        }
    }

    /**
     * <br> Description: 显示在左边
     * <br> Author:      KevinWu
     * <br> Date:        2018/6/1 9:57
     */
    private void showHintViewLeft() {
        if (mTargetView != null && mPSGuideImageView != null) {
            int location[] = new int[2];
            mTargetView.getLocationOnScreen(location);
            final int top = location[1];
            LayoutParams params = new LayoutParams(-2, -2);
            if (this.contain(this, mHintView)) {
                this.removeView(mHintView);
            }
            params.setMargins(0, -top, 0, 0);  //将hintView显示在屏幕外
            this.addView(mHintView, params);
            //调整位置，刚才targetView上面
            mHintView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                        mHintView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mHintView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    int renderWidth = getConfig().getHighLightWidth() * mTmpRenderDeviation;
                    // 调整
                    ((RelativeLayout.LayoutParams) mHintView.getLayoutParams())
                            .setMargins(mTargetView.getLeft() - mHintView.getMeasuredWidth() - renderWidth,
                                    top, 0, 0);
                    requestLayout();
                }
            });
        }
    }

    /**
     * <br> Description: 显示在上边
     * <br> Author:      KevinWu
     * <br> Date:        2018/6/1 9:57
     */
    private void showHintViewTop() {
        if (mTargetView != null && mPSGuideImageView != null) {
            int location[] = new int[2];
            mTargetView.getLocationOnScreen(location);
            final int renderWidth = getConfig().getHighLightWidth() * mTmpRenderDeviation;
            // targetView 与顶部的距离减去渲染的宽度
            final int top = location[1] - renderWidth;
            LayoutParams params = new LayoutParams(-1, -2);
            params.setMargins(0, top, 0, 0);
            if (this.contain(this, mHintView)) {
                this.removeView(mHintView);
            }
            this.addView(mHintView, params);
            mHintView.layout(0, top, 0, 0);
            invalidate();
            //调整位置，刚才targetView上面
            mHintView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                        mHintView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mHintView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    // 调整
                    ((RelativeLayout.LayoutParams) mHintView.getLayoutParams())
                            .setMargins(0, mHintView.getTop() - mHintView.getMeasuredHeight(),
                                    0, 0);
                    requestLayout();
                }
            });
        }
    }

    /**
     * <br> Description: 显示在右边
     * <br> Author:      KevinWu
     * <br> Date:        2018/6/1 9:58
     */
    private void showHintViewRight() {
        if (mTargetView != null && mPSGuideImageView != null) {
            int location[] = new int[2];
            mTargetView.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];
            LayoutParams params = new LayoutParams(-2, -2);
            if (this.contain(this, mHintView)) {
                this.removeView(mHintView);
            }
            this.addView(mHintView, params);
            int renderWidth = getConfig().getHighLightWidth() * mTmpRenderDeviation;
            params.setMargins(left + mTargetView.getMeasuredWidth() + renderWidth, top, 0, 0);

            mHintView.layout(location[0] + mTargetView.getMeasuredWidth(),
                    location[1],
                    0, 0);
            invalidate();
        }
    }

    /**
     * <br> Description: 显示在下边
     * <br> Author:      KevinWu
     * <br> Date:        2018/6/1 9:57
     */
    private void showHintViewBottom() {
        if (mTargetView != null && mPSGuideImageView != null) {
            int location[] = new int[2];
            mTargetView.getLocationOnScreen(location);
            int left = 0;// =location[0];
            int top = location[1];
            LayoutParams params = new LayoutParams(-1, -1);
            if (this.contain(this, mHintView)) {
                this.removeView(mHintView);
            }
            this.addView(mHintView, params);
            int renderWidth = getConfig().getHighLightWidth() * mTmpRenderDeviation;
            params.setMargins(left, top + mTargetView.getMeasuredHeight() + renderWidth, 0, 0);

            mHintView.layout(left,
                    location[1] + mTargetView.getMeasuredHeight() + renderWidth,
                    location[0] + mHintView.getMeasuredWidth(),
                    location[1] + mHintView.getMeasuredHeight());
        }
    }


    /**
     * <br> Description:直接子View中是否包含
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/23 15:33
     */
    private boolean contain(ViewGroup parentView, View view) {
        if (parentView == null || view == null) {
            return false;
        }
        int index = 0;
        int size = parentView.getChildCount();
        for (; index < size; index++) {
            if (this.getChildAt(index) == view) {
                return true;
            }
        }
        return false;
    }

    private OnGuideClickLinstener mGuideClickLinstener = null;

    public void setGuideClickLinstener(final OnGuideClickLinstener listener) {
        if (listener == null) {
            return;
        }
        this.mGuideClickLinstener = listener;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTouchOutExit) {
                    destory();
                }
                mGuideClickLinstener.onGuideViewClick(v);
            }
        });
    }

    /**
     * <br> Description: 设置打开过蒙版状态
     * <br> Author:      longluliu
     * <br> Date:        2018/4/19 14:08
     *
     * @param isShow 是否显示过
     */
    private void setOpenState(boolean isShow) {
        if (!TextUtils.isEmpty(mKey)) {
            PSGuideShareVersionUtil.getInstance(mVersionName).setValue(mKey, isShow);
        }
    }

    /**
     * <br> Description: 是否打开过蒙版
     * <br> Author:      longluliu
     * <br> Date:        2018/4/19 14:09
     *
     * @return
     */
    private boolean getOpenState() {
        return PSGuideShareVersionUtil.getInstance(mVersionName).getValue(mKey, false);
    }

    /**
     * <br> Description: 获取屏幕宽高
     * <br> Author:      KevinWu
     * <br> Date:        2018/6/13 17:18
     */
    private int[] getScreenSize() {
        int screen[] = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        screen[0] = dm.widthPixels;
        screen[1] = dm.heightPixels;
        return screen;
    }

    /**
     * <br> ClassName:   GuideView
     * <br> Description: 事件回调
     * <br>
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/24 21:25
     */
    public interface OnGuideClickLinstener {
        void onGuideViewClick(View view);
    }

    public static class Builder {
        private Context mContext = null;
        /*** 版本号 ***/
        private String versionName = null;
        /*** 是否是测试模式 ***/
        private boolean isDebug;
        private List<PSGuideView> mSourceView = null;
        /*** 点击touchout 退出  ***/
        private boolean isTouchOutExit = true;
        /*** 当前显示index ***/
        private int mCurrentIndex;
        /*** 显示依赖的父View ***/
        private ViewGroup mParentView;
        /*** 高亮是否可点击 ***/
        private boolean isHighlightClickable = true;

        /*** 配置对象 ***/
        private PSGuideConfig mConfig = null;

        /**
         * <br> Description:
         * <br> Author:      KevinWu
         * <br> Date:        2018/4/20 16:28
         *
         * @param mContext
         * @param versionName 版本号，用于控制下次是否再显示
         */
        public Builder(@NonNull Context mContext, String versionName) {
            init(mContext, versionName, null);
        }

        public Builder(@NonNull Context mContext, @NonNull String versionName, @Nullable ViewGroup parentView) {
            init(mContext, versionName, parentView);
        }

        /**
         * <br> Description: 初始化
         * <br> Author:      KevinWu
         * <br> Date:        2018/4/23 16:47
         */
        private void init(@NonNull Context mContext, String versionName, ViewGroup parentView) {
            this.mContext = mContext;
            this.versionName = versionName;
            this.mSourceView = new ArrayList<>(4);
            if (parentView != null) {
                this.mParentView = parentView;
            }
        }


        /**
         * <br> Description: 依赖的ViewGroup
         * <br> Author:      KevinWu
         * <br> Date:        2018/4/24 18:07
         */
        public Builder setParentView(@NonNull ViewGroup parentView) {
            if (parentView != null) {
                this.mParentView = parentView;
            }
            return this;
        }

        public Builder setTouchOutExit(boolean isTouchOutExit) {
            this.isTouchOutExit = isTouchOutExit;
            return this;
        }

        /**
         * <br> Description: 高亮是否可点击
         * <br> Author:      longluliu
         * <br> Date:        2018/4/25 16:33
         */
        public Builder setHighlightClickable(boolean isHighlightClickable) {
            this.isHighlightClickable = isHighlightClickable;
            return this;
        }

        /**
         * <br> Description: 设置蒙版背景色
         * <br> Author:      longluliu
         * <br> Date:        2018/4/25 16:33
         */
        public Builder setBackgroundColor(int bgColor) {
            getConfig().setBackgroundColor(bgColor);
            return this;
        }

        /**
         * <br> Description: 设置配置属性
         * <br> Author:      KevinWu
         * <br> Date:        2018/5/14 14:15
         */
        public Builder setConfig(PSGuideConfig config) {
            if (this.mConfig != null) {
                config.setBackgroundColor(this.mConfig.getBackgroundColor());
            }
            this.mConfig = config;
            return this;
        }

        /*** 获取属性 ***/
        private PSGuideConfig getConfig() {
            return mConfig;
        }

        /**
         * <br> Description: 是否是测试模式
         * <br> Author:      KevinWu
         * <br> Date:        2018/4/20 16:19
         */
        public Builder setDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        /**
         * <br> Description: 添加引导View
         * <br> Author:      KevinWu
         * <br> Date:        2018/4/20 16:30
         *
         * @param targetView 要显示引导的目标视图
         * @param hintView   蒙板上显示的视图
         * @return
         */
        public Builder addGuideView(@NonNull View targetView, @NonNull View hintView, String key) {
            return addGuideView(targetView, hintView, Direction.BOTTOM, null, key);
        }

        /**
         * <br> Description: 添加引导View
         * <br> Author:      KevinWu
         * <br> Date:        2018/4/20 16:30
         *
         * @param targetView 要显示引导的目标视图
         * @param hintView   蒙板上显示的视图
         * @return
         */
        public Builder addGuideView(@NonNull View targetView, @NonNull View hintView, @NonNull Direction direction, String key) {
            return addGuideView(targetView, hintView, direction, null, key);
        }

        /**
         * <br> Description: 添加引导View
         * <br> Author:      KevinWu
         * <br> Date:        2018/4/20 16:30
         *
         * @param targetView 要显示引导的目标视图
         * @param hintView   蒙板上显示的视图
         * @return
         */
        public Builder addGuideView(@NonNull View targetView, @NonNull View hintView,
                                    Direction direction, OnGuideClickLinstener clickLinstener,
                                    String key) {
            PSGuideView psGuideView = new PSGuideView(this.mContext);
            psGuideView.setBackgroundColor(Color.WHITE);
            psGuideView.setTargetView(targetView);
            psGuideView.setHintView(hintView);
            psGuideView.setParentView(mParentView);
            psGuideView.setPosition(mSourceView.size());
            if (clickLinstener != null) {
                psGuideView.setGuideClickLinstener(clickLinstener);
            }
            psGuideView.isTouchOutExit = isTouchOutExit;
            psGuideView.isHighlightClickable = isHighlightClickable;
            psGuideView.mKey = key;
            psGuideView.mVersionName = versionName;
            if (direction != null) {
                psGuideView.setDirection(direction);
            }
            mSourceView.add(psGuideView);
            return this;
        }


        /******************         写到这多个叠加时的bug          **********************/
        /**
         * <br> Description: 显示
         * <br> Author:      KevinWu
         * <br> Date:        2018/4/23 10:07
         */
        public Builder show() {
            if (mCurrentIndex >= mSourceView.size()) {
                return this;
            }
            PSGuideView psGuideView = mSourceView.get(mCurrentIndex);
            psGuideView.setConfig(getConfig());
            psGuideView.refreshView();
            if (mSourceView.isEmpty() || psGuideView == null) {
                return this;
            }
            if (!isDebug) {
                if (psGuideView.getOpenState()) {
                    return this;
                }
            }
            if (!psGuideView.isShow()) {
                psGuideView.setOpenState(true);
                psGuideView.show();
            }
            return this;
        }

        /**
         * <br> Description: 显示下一个
         * <br> Author:      KevinWu
         * <br> Date:        2018/4/24 9:44
         */
        public void showNext() {
            destoryCurrent();
            mCurrentIndex++;
            if (mSourceView.size() == mCurrentIndex && mCurrentIndex >= 1) {
                // 判断是否是最后一个，如果是就释放
                mCurrentIndex--;
                destory();
                return;
            }
            show();
        }

        /**
         * <br> Description: 删除当前层
         * <br> Author:      longluliu
         * <br> Date:        2018/4/28 15:27
         */
        public void destoryCurrent() {
            if (mSourceView != null && mCurrentIndex < mSourceView.size()) {
                mSourceView.get(mCurrentIndex).destory();
            }
        }

        public void destory() {
            mSourceView.get(mCurrentIndex).destory();
            mSourceView.clear();
            mCurrentIndex = 0;
        }
    }

    /**
     * <br> ClassName:   GuideView
     * <br> Description: 定义GuideView相对于targetView的方位，共4种。不设置则默认在targetView下方
     * 在下方时，做优先级处理  如果所在的方向空间不足时，则显示在对应的方向（如上对应下，左对应右，反之亦然）
     * <br>
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/23 17:08
     */
    public enum Direction {
        LEFT, TOP, RIGHT, BOTTOM
    }

}
