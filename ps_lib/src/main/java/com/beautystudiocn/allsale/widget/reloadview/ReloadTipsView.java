package com.beautystudiocn.allsale.widget.reloadview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beautystudiocn.allsale.R;

import static com.beautystudiocn.allsale.R.id.layLMain;

/**
 * <br> ClassName:   ReloadTipsView                        
 * <br> Description: 情感图
 * <br>  
 * <br> Author:      wujun                             
 * <br> Date:        2018/1/19 18:59                     
 */
public class ReloadTipsView extends RelativeLayout {

    private Context mContext;
    private LinearLayout mLayLMain;
    private View mView;
    private ImageView mSpinnerImageView;
    private TextView mTvTips;
    /*** 扩展字段 ***/
    private TextView mTvTips2;
    /*** 重试***/
    private TextView mTvRetryDesc1;
    /***扩展字段***/
    private TextView mTvTipsDesc2;
    private ImageView mImgLogo;
    private boolean mIsReload = true;
    private View mChildView;
    /*** 没有相关记录的图标ID***/
    private int mEmptyDrableResId ;
    /*** 失败图标ID***/
    private int mFailureDrableResId ;
    /*** 没有网络 ***/
    private int mNoNetWorkDrableResId ;

    /*** 没有相关记录 文字描述***/
    private String mEmptyTips;
    /*** 失败文字描述 ***/
    private String mFailureTips;

    /*** 无网络文字描述 ***/
    private String mNoNetWorkTips;
    /*** 重试文字描述 ***/
    private String mTips1Desc;

    /*** 自定义加载View***/
    private View mCustomLoadingView;

    /*** 提示语 背景 间距***/
    private int mTips1TextColor;
    private int mTips1TextSize;
    private int mTips1Bg;
    /*** topMargin***/
    private int mTips1Magin;

    /*** 扩展 背景 间距***/
    private int mTips2TextColor;
    private int mTips2TextSize;
    private int mTips2Bg;
    /*** topMargin***/
    private int mTips2Magin;
    /*** 重试加载 背景 间距***/
    private int mTipsRetryTextColor;
    private int mTipsRetryTextSize;
    private int mTipsRetry1Bg;
    /*** topMargin***/
    private int mTipsRetry1Magin;

    /*** 扩展 背景 间距***/
    private int mTips2DescTextColor;
    private int mTips2DescTextSize;
    private int mTips2Desc1Bg;
    /*** topMargin***/
    private int mTips2Desc1Magin;

    /*** AnimationDrable***/

    private Drawable mLoadBg;

    /*** 是否定义加载view**/
    private boolean mCustomLoading = false;

    private int mLoadViewResId;

    public ReloadTipsView(Context context) {
        this(context,null);
    }


    public ReloadTipsView(Context context, AttributeSet attrs) {
        this(context,attrs,R.attr.lib_reload_style);
    }

    public ReloadTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.lib_reload_tips,
                defStyleAttr,R.style.lib_reload_tips_theme);
        if (ta != null) {
            mEmptyDrableResId = ta.getResourceId(R.styleable.lib_reload_tips_lib_reload_empty,0);
            mFailureDrableResId = ta.getResourceId(R.styleable.lib_reload_tips_lib_reload_failure,0);
            mNoNetWorkDrableResId = ta.getResourceId(R.styleable.lib_reload_tips_lib_reload_no_network,0);
            mEmptyTips = ta.getString(R.styleable.lib_reload_tips_lib_reload_empty_tips);
            mFailureTips = ta.getString(R.styleable.lib_reload_tips_lib_reload_failure_tips);
            mNoNetWorkTips = ta.getString(R.styleable.lib_reload_tips_lib_reload_nonetwork_tips);
            mTips1Desc = ta.getString(R.styleable.lib_reload_tips_lib_reload_retry_text);
            /*** 设置间距***/
            mTips1Magin = ta.getDimensionPixelSize(R.styleable.lib_reload_tips_lib_reload_tips1_margin,0);
            mTips2Magin = ta.getDimensionPixelSize(R.styleable.lib_reload_tips_lib_reload_tips2_margin,0);
            mTipsRetry1Magin = ta.getDimensionPixelSize(R.styleable.lib_reload_tips_lib_reload_retry_margin,0);
            mTips2Desc1Magin = ta.getDimensionPixelSize(R.styleable.lib_reload_tips_lib_reload_tips2_desc_margin,0);
            /*** 设置背景***/
            mTips1Bg = ta.getResourceId(R.styleable.lib_reload_tips_lib_reload_tips1_bg,0);
            mTips2Bg = ta.getResourceId(R.styleable.lib_reload_tips_lib_reload_tips2_bg,0);
            mTipsRetry1Bg = ta.getResourceId(R.styleable.lib_reload_tips_lib_reload_retry_bg,0);
            mTips2Desc1Bg = ta.getResourceId(R.styleable.lib_reload_tips_lib_reload_tips2_desc_bg,0);
            /*** 设置颜色***/
            mTips1TextColor = ta.getColor(R.styleable.lib_reload_tips_lib_reload_tips1_textcolor,0);
            mTips2TextColor = ta.getColor(R.styleable.lib_reload_tips_lib_reload_tips2_textcolor,0);
            mTipsRetryTextColor = ta.getColor(R.styleable.lib_reload_tips_lib_reload_retry_textcolor,0);
            mTips2DescTextColor = ta.getColor(R.styleable.lib_reload_tips_lib_reload_tips2_desc_textcolor,0);
          /*** 设置字体大小***/
            mTips1TextSize = ta.getDimensionPixelSize(R.styleable.lib_reload_tips_lib_reload_tips1_textsize,0);
            mTips2TextSize = ta.getDimensionPixelSize(R.styleable.lib_reload_tips_lib_reload_tips2_textsize,0);
            mTipsRetryTextSize = ta.getDimensionPixelSize(R.styleable.lib_reload_tips_lib_reload_retry_textsize,0);
            mTips2DescTextSize = ta.getDimensionPixelSize(R.styleable.lib_reload_tips_lib_reload_tips2_desc_textsize,0);
            mLoadBg = ta.getDrawable(R.styleable.lib_reload_tips_lib_reload_loading_bg);
            mLoadViewResId = ta.getResourceId(R.styleable.lib_reload_tips_lib_reload_customloading_view,0);
            ta.recycle();
        }
        init(context);
    }

    private void init(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.lib_view_load_tips_layout, this, true);
        mLayLMain = (LinearLayout) mView.findViewById(layLMain);
        mImgLogo = (ImageView) mView.findViewById(R.id.imgLogo);
        mTvTips = (TextView) mView.findViewById(R.id.tvTips);
        mTvTips2 = (TextView) mView.findViewById(R.id.tvTips2);
        mTvRetryDesc1 = (TextView) mView.findViewById(R.id.tvTipsDesc1);
        mTvTipsDesc2 = (TextView) mView.findViewById(R.id.tvTipsDesc2);
        mSpinnerImageView = (ImageView) mView.findViewById(R.id.spinnerImageView);
        if (mEmptyDrableResId == 0) {
            mEmptyDrableResId = R.drawable.empty_express_image;
        }

        if (mFailureDrableResId == 0) {
            mFailureDrableResId = R.drawable.nonetwork_express_image;
        }

        if (mNoNetWorkDrableResId == 0 ) {
            mNoNetWorkDrableResId = R.drawable.nonetwork_express_image;
        }

        if (mTips1Desc != null) {
            mTvRetryDesc1.setText(mTips1Desc);
        }

        if (mLoadViewResId != 0 && mLoadViewResId != -1) {
            mCustomLoadingView = LayoutInflater.from(mContext).inflate(mLoadViewResId,null);
           setCustomLoadingView(mCustomLoadingView);

        }
        if (mTips1Magin != 0) {
            setVeiwMargin(mTvTips,0,mTips1Magin,0,0);
        }
        if (mTips2Magin != 0) {
            setVeiwMargin(mTvTips2,0,mTips2Magin,0,0);
        }
        if (mTipsRetry1Magin != 0) {
            setVeiwMargin(mTvRetryDesc1,0,mTipsRetry1Magin,0,0);
        }
        if (mTips2Desc1Magin !=0) {
            setVeiwMargin(mTvTipsDesc2,0,mTips2Desc1Magin,0,0);
        }

        setTextSizeAndColor(mTvTips,mTips1TextColor,mTips1TextSize);
        setTextSizeAndColor(mTvTips2,mTips2TextColor,mTips2TextSize);
        setTextSizeAndColor(mTvRetryDesc1,mTipsRetryTextColor,mTipsRetryTextSize);
        setTextSizeAndColor(mTvTipsDesc2,mTips2DescTextColor,mTips2DescTextSize);
        setViewBackGroundResourece(mTvTips,mTips1Bg);
        setViewBackGroundResourece(mTvTips2,mTips2Bg);
        setViewBackGroundResourece(mTvRetryDesc1,mTipsRetry1Bg);
        setViewBackGroundResourece(mTvTipsDesc2,mTips2Desc1Bg);
        if (mLoadBg != null) {
            mSpinnerImageView.setBackgroundDrawable(mLoadBg);
        }
    }


    /**
     *<br> Description: 设置背景
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 19:01
     * @param bgColor    bgColor
     */
    public void setViewBackgroundColor(int bgColor) {
        mView.setBackgroundColor(getResources().getColor(bgColor));
    }

    /**
     *<br> Description: 没有相关记录
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:36
     */
    public void showEmptyTips() {
        showTips();
        if (TextUtils.isEmpty(mEmptyTips)) {
            mTvTips.setText(R.string.lib_common_not_data);
        } else {
            mTvTips.setText(mEmptyTips);
        }

        mTvTips2.setVisibility(View.GONE);
        mImgLogo.setImageResource(mEmptyDrableResId);
    }

    /**
     *<br> Description: 没有相关记录
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:36
     * @param drawable    没有记录的图标标记
     */
    public void showEmptyTips(int drawable) {
        showTips();
        if (TextUtils.isEmpty(mEmptyTips)) {
            mTvTips.setText(R.string.lib_common_not_data);
        } else {
            mTvTips.setText(mEmptyTips);
        }
        mTvTips2.setVisibility(View.GONE);
        if (drawable > 0) {
            mImgLogo.setImageResource(drawable);
        } else {
            mImgLogo.setImageResource(mEmptyDrableResId);
        }
    }


    /**
     *<br> Description: 请求失败
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:36
     */
    public void showFailureTips() {
        showTips();
        if (TextUtils.isEmpty(mFailureTips)) {
            mTvTips.setText(R.string.lib_common_service_tips);
        } else {
            mTvTips.setText(mFailureTips);
        }
        mTvTips2.setVisibility(View.GONE);
        mImgLogo.setImageResource(mFailureDrableResId);

    }

    /**
     *<br> Description: 无网络
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:37
     */
    public void showNoNetworkTips() {
        showTips();
        if (TextUtils.isEmpty(mNoNetWorkTips)) {
            mTvTips.setText(R.string.lib_common_not_net);
        } else {
            mTvTips.setText(mNoNetWorkTips);
        }
        mTvTips2.setVisibility(View.GONE);
        int num = (int) (Math.random() * 2 + 1);
        if (num == 1) {
            mImgLogo.setImageResource(mNoNetWorkDrableResId);
        } else {
            mImgLogo.setImageResource(mNoNetWorkDrableResId);
        }
    }

   /**
    *<br> Description: 提示内容
    *<br> Author:      wujun
    *<br> Date:        2018/1/19 19:11
    */
    private void showTips(PageTips pageTips) {
        if (pageTips == null) {
            return;
        }
        if (!TextUtils.isEmpty(pageTips.getTips())) {
            mTvTips.setText(pageTips.getTips());
            mTvTips.setVisibility(View.VISIBLE);
        } else {
            mTvTips.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(pageTips.getTips2())) {
            mTvTips2.setText(pageTips.getTips2());
            mTvTips2.setVisibility(View.VISIBLE);
        } else {
            mTvTips2.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(pageTips.getTipsDesc1())) {
            mTvRetryDesc1.setText(pageTips.getTipsDesc1());
            mTvRetryDesc1.setVisibility(View.VISIBLE);
        } else {
            mTvRetryDesc1.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(pageTips.getTipsDesc2())) {
            mTvTipsDesc2.setVisibility(View.VISIBLE);
            mTvTipsDesc2.setText(pageTips.getTipsDesc2());
        } else {
            mTvTipsDesc2.setVisibility(View.GONE);
        }

        if (pageTips.getIconResid() > 0) {
            mImgLogo.setVisibility(View.VISIBLE);
            mImgLogo.setImageResource(pageTips.getIconResid());
        } else {
            mImgLogo.setVisibility(View.GONE);
        }

        if (pageTips.getBgResid() > 0) {
            mView.setBackgroundResource(pageTips.getBgResid());
        }
    }

    private void showTips() {
        mView.setVisibility(View.VISIBLE);
        if (mCustomLoading && mCustomLoadingView != null) {
            mCustomLoadingView.setVisibility(GONE);
        } else {
            mSpinnerImageView.setVisibility(View.GONE);
        }
        mTvRetryDesc1.setVisibility(View.VISIBLE);
        mTvTipsDesc2.setVisibility(View.VISIBLE);
        mTvTips.setVisibility(View.VISIBLE);
        if (mChildView != null) {
            mLayLMain.removeView(mChildView);
        }
    }

    /**
     *<br> Description: 设置自定义加载中View
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:37
     */
    public  void setCustomLoadingView(View view) {
        if (view == null) {
            return;
        }
        if (mCustomLoadingView != null) {
            mLayLMain.removeView(mCustomLoadingView);
        }
        mSpinnerImageView.setVisibility(GONE);
        mCustomLoadingView = view;
        mCustomLoading = true;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)mSpinnerImageView.getLayoutParams();
        mLayLMain.addView(view,params);
        mCustomLoadingView.setVisibility(GONE);
    }
    /**
     * 设置重新加载监听
     *
     * @param listener void
     * @author longluliu
     * @date 2014-4-2 下午4:32:58
     */

    public void setOnReloadDataListener(final LoadTipsListener listener) {
        if (listener != null && mLayLMain != null) {
            mLayLMain.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mTvRetryDesc1.getVisibility() == View.VISIBLE && mIsReload) {
                        showProgressBar();
                        listener.clickReloadData();
                    }
                }
            });
        }
    }

    /**
     * 显示load
     * void
     *
     * @author longluliu
     * @date 2014-4-2 下午4:33:48
     */
    public void showProgressBar() {
        mView.setVisibility(View.VISIBLE);
        if (mCustomLoading && mCustomLoadingView != null) {
            mCustomLoadingView.setVisibility(VISIBLE);
        } else {
            mSpinnerImageView.setVisibility(View.VISIBLE);
            AnimationDrawable spinner = (AnimationDrawable) mSpinnerImageView.getBackground();
            spinner.start();
        }
        mTvRetryDesc1.setVisibility(View.GONE);
        mTvTipsDesc2.setVisibility(View.GONE);
        mTvTips.setVisibility(View.GONE);
        mTvTips2.setVisibility(View.GONE);
        if (mChildView != null) {
            mLayLMain.removeView(mChildView);
        }
    }

    public void showCustomEmptyTips(PageTips pageTips) {
        mView.setVisibility(View.VISIBLE);
        if (mCustomLoading &&  mCustomLoadingView != null) {
            mCustomLoadingView.setVisibility(GONE);
        } else {
            mSpinnerImageView.setVisibility(View.GONE);
        }
        mTvTips.setVisibility(View.VISIBLE);
        if (pageTips == null) {
            showEmptyTips();
        } else {
            if (mChildView != null) {
                mLayLMain.removeView(mChildView);
            }
            mIsReload = pageTips.isReload();
            if (pageTips.getChildView() != null) {
                mChildView = pageTips.getChildView();
                mLayLMain.addView(mChildView);
            } else {
                if (mChildView != null) {
                    mLayLMain.removeView(mChildView);
                }
            }
            showTips(pageTips);
        }
    }

    /**
     * 是否显示
     *
     * @return boolean
     * @author longluliu
     * @date 2014-4-11 下午7:40:47
     */
    public boolean isShowReload() {
        if (mView.getVisibility() == View.VISIBLE && mTvRetryDesc1.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

    /**
     * 是否显示
     *
     * @return boolean
     * @author longluliu
     * @date 2014-4-11 下午7:40:47
     */
    public boolean isShowLoad() {
        if (mCustomLoading) {
            if (mView.getVisibility() == View.VISIBLE && mCustomLoadingView != null
                    && mCustomLoadingView.getVisibility() == View.VISIBLE ) {
                return true;
            }
        } else {
            if (mView.getVisibility() == View.VISIBLE && mSpinnerImageView.getVisibility() == View.VISIBLE) {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏
     * void
     *
     * @author longluliu
     * @date 2014-4-2 下午4:33:48
     */
    public void goneView() {
        mView.setVisibility(View.GONE);
    }

    public interface LoadTipsListener {
        public void clickReloadData();
    }


    /**
     *<br> Description: 设置LoadingAniamtionDralbe
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:40
     * @param drawable    Animationdrable
     */
    public void setLoadingDrable(Drawable drawable) {
        if (drawable instanceof AnimationDrawable) {
            mSpinnerImageView.setBackgroundDrawable(drawable);
        }
    }

    /**
     *<br> Description: 设置没有相关记录文字
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:41
     * @param mEmptyTips    文字描述
     */
    public void setEmptyTips(String mEmptyTips) {
        this.mEmptyTips = mEmptyTips;
    }

    /**
     *<br> Description: 设置失败文字描述
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:41
     * @param mFailureTips    失败文字描述
     */
    public void setFailureTips(String mFailureTips) {
        this.mFailureTips = mFailureTips;
    }

    /**
     *<br> Description: 设置失无网络文字描述
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:41
     * @param mNoNetWorkTips    失无网络描述
     */
    public void setNoNetWorkTips(String mNoNetWorkTips) {
        this.mNoNetWorkTips = mNoNetWorkTips;
    }

    /**
     *<br> Description: 设置无相关记录textviw的颜色字体大小
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param textColorResId     textColorResId
     * @param textsize textsize
     */
    public void setTips1TextAndColor(int textColorResId,int textsize ) {
        setTextSizeAndColor(mTvTips,textColorResId,textsize);
    }

    /**
     *<br> Description: 扩展 的颜色字体大小
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param textColorResId     textColorResId
     * @param textsize textsize
     */
    public void setTips2Text(int textColorResId,int textsize) {

        setTextSizeAndColor(mTvTips2,textColorResId,textsize);
    }

    /**
     *<br> Description: 设置重试加载textview的颜色字体大小
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param textColorResId    textColorResId
     * @param textsize textsize
     */
    public void setTipsRetryColorSize(int textColorResId,int textsize) {

        setTextSizeAndColor(mTvRetryDesc1,textColorResId,textsize);
    }

    /**
     *<br> Description: 扩展颜色字体大小
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param textColorResId    textColorResId
     * @param textsize textsize
     */
    public void setTips2DescText(int textColorResId,int textsize) {

        setTextSizeAndColor(mTvTipsDesc2,textColorResId,textsize);
    }


    /**
     *<br> Description: 设置Tips1 没有相关记录间距
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param left      left
     * @param top top
     * @param right right
     * @param bottom bottom
     */
    public void setTip1Magin(int left,int top,int right,int bottom) {
        setVeiwMargin(mTvTips,left,top,right,bottom);
    }

    /**
     *<br> Description: 设置Tips2 扩展字段间距
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param left      left
     * @param top top
     * @param right right
     * @param bottom bottom
     */
    public void setTip2Magin(int left,int top,int right,int bottom) {
        setVeiwMargin(mTvTips2,left,top,right,bottom);
    }

    /**
     *<br> Description: 设置Tip1Desc 重试相关文字间距
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param left      left
     * @param top top
     * @param right right
     * @param bottom bottom
     */
    public void setTipsRetryMagin(int left,int top,int right,int bottom) {
        setVeiwMargin(mTvRetryDesc1,left,top,right,bottom);
    }

    /**
     *<br> Description: 设置ip2Desc 扩展字段间距
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param left      left
     * @param top top
     * @param right right
     * @param bottom bottom
     */
    public void setTip2DescMagin(int left,int top,int right,int bottom) {
        setVeiwMargin(mTvTipsDesc2,left,top,right,bottom);
    }

    /**
     *<br> Description: 设置文本
     *<br> Author:      wujun
     *<br> Date:        2018/1/20 9:47
     * @param text    text
     */
    public void setTip2Text(String text) {
        mTvTips2.setText(text);
    }
     /**
      *<br> Description: 设置文本
      *<br> Author:      wujun
      *<br> Date:        2018/1/20 9:47
      * @param text    text
      */
    public void setTip2DescText(String text) {
        mTvTipsDesc2.setText(text);
    }

    /**
     *<br> Description: 设置Tips1 没有相关记录背景
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param resId resId or colorResId
     */
    public void setTip1BackGroundRes(int resId) {
        setViewBackGroundResourece(mTvTips,resId);
    }

    /**
     *<br> Description: 设置Tips1 扩展字段背景
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param resId resId or colorResId
     */
    public void setTip2BackGroundRes(int resId) {
        setViewBackGroundResourece(mTvTips2,resId);
    }

    /**
     *<br> Description: 设置TTip1Desc 重试背景
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param  resId or colorResId
     */
    public void setTipsRetryBackGroundRes( int resId) {
        setViewBackGroundResourece(mTvRetryDesc1,resId);
    }

    /**
     *<br> Description: 设置Tip2Desc 扩展字段背景
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:43
     * @param resId or colorResId
     */
    public void setTip2DescBackGroundRes(int resId) {
        setViewBackGroundResourece(mTvTipsDesc2,resId);
    }

    private void  setViewBackGroundResourece(View view, int drawable) {
        if (drawable != 0) {
            view.setBackgroundResource(drawable);
        }
    }

    /**
     *<br> Description: 设置重试的文字
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:50
     * @param text    text
     */
    public void setTipsRetryText(String text) {
        if (!TextUtils.isEmpty(text)) {
            mTvRetryDesc1.setText(text);
        }
    }

    /**
     *<br> Description: 设置间距
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:50
     * @param tvTips    view
     * @param left     left
     * @param top top
     * @param right right
     * @param bottom bottom
     */
    private void setVeiwMargin(View tvTips, int left, int top, int right, int bottom) {

          LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvTips.getLayoutParams();
          params.setMargins(left,top,right,bottom);
          tvTips.setLayoutParams(params);


    }

    /**
     *<br> Description: 设置颜色、字体大小
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:49
     * @param tvTip             View
     * @param textColorResId  textColorResId
     * @param textsize textsize
     */
    private void setTextSizeAndColor(TextView tvTip, int textColorResId, int textsize) {
        if (textColorResId != 0) {
            tvTip.setTextColor(textColorResId);
        }
        if (textsize != 0) {
            tvTip.setTextSize(TypedValue.COMPLEX_UNIT_PX,textsize);
        }

    }

    /**
     *<br> Description: 设置无数据图片
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:47
     * @param emptyDrableResId    资源Id
     */
    public void setEmptyDrableResId(int emptyDrableResId) {
        if (emptyDrableResId != 0) {
            this.mEmptyDrableResId = emptyDrableResId;
        }
    }

    /**
     *<br> Description: 设置失败图片
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:47
     * @param failureDrableResId    failureDrableResId
     */
    public void setFailureDrableResId(int failureDrableResId) {
        if (failureDrableResId != 0) {
            this.mFailureDrableResId = failureDrableResId;
        }
    }

    /**
     *<br> Description: 设置无网络图片
     *<br> Author:      wujun
     *<br> Date:        2018/1/19 18:47
     * @param noNetWorkDrableResId     noNetWorkDrableResId
     */
    public void setNoNetWorkDrableResId(int noNetWorkDrableResId) {
        if (noNetWorkDrableResId != 0) {
            this.mNoNetWorkDrableResId = noNetWorkDrableResId;
        }
    }


}
