package com.streaming.better.honey.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beautystudiocn.allsale.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.tools.ScreenUtils;

public class DialogUtils {

    /**
     * <br> Description: 显示首页收藏弹窗  TODO 这个弹窗待重构
     * <br> Author:      wujianghua
     * <br> Date:        2018/11/2 19:41
     */
    /*public static void showCollectionDialog(final Context mActivity, String storeName, String imageUrl, final String shopId) {
        final Dialog dialog = new Dialog(mActivity, R.style.MyDialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        int width = ScreenUtils.getScreenWidth(mActivity);
        int height = ScreenUtils.getScreenHeight(mActivity);
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        View view = View.inflate(mActivity, R.layout.home_collection_layout, null);
        dialog.setContentView(view, layoutParams);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialog.show();
        TextView tvTip = view.findViewById(R.id.tv_concern_store_success);
        TextView tvEnter = view.findViewById(R.id.tv_enter_store);
        TextView tvSearch = view.findViewById(R.id.tv_goon_search);
        ImageView tvStorePic = view.findViewById(R.id.iv_store_pic);
        tvTip.setText(String.format(mActivity.getString(R.string.collection_store_success), storeName));
        RequestOptions mOptions = new RequestOptions()
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .fitCenter();
        Glide.with(mActivity).load(imageUrl).apply(mOptions).into(tvStorePic);
        tvStorePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(mActivity, ShopDetailActivity.class);
                intent.putExtra("shopId", shopId);
                mActivity.startActivity(intent);
            }
        });
        tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(mActivity, ShopDetailActivity.class);
                intent.putExtra("shopId", shopId);
                mActivity.startActivity(intent);
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void showPop(final Activity activity, View dropDownView) {
        View popView = LayoutInflater.from(MyApplication.getInstance().mContext).inflate(R.layout.pop_classify_more, null);
        final PopupWindow popupWindow = new PopupWindow(popView, DensityUtil.dip2px(MyApplication.getInstance().mContext, 110), DensityUtil.dip2px(MyApplication.getInstance().mContext, 150));
        ColorDrawable dw = new ColorDrawable(0x00000000);
        setPopWindowBackgroundAlpha(0.8f,activity);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.pop_anim_more);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setPopWindowBackgroundAlpha(1f,activity);
            }
        });
        popupWindow.showAsDropDown(dropDownView,20,30, Gravity.TOP| Gravity.RIGHT);
        LinearLayout ll_pop_more = popView.findViewById(R.id.ll_pop_more);
        RelativeLayout rl_more_message = popView.findViewById(R.id.rl_more_message);
        RelativeLayout rl_main_page = popView.findViewById(R.id.rl_main_page);
        RelativeLayout rl_wish_list = popView.findViewById(R.id.rl_wish_list);

        //消息
        rl_more_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ConversationListActivity.class);
                activity.startActivity(intent);
                activity.finish();
                popupWindow.dismiss();
            }
        });

        //主页
        rl_main_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpConfig.getInstance().putInt("id", 1);
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
                popupWindow.dismiss();
            }
        });

        //心愿单
        rl_wish_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, WishListActivity.class);
                activity.startActivity(intent);
                activity.finish();
                popupWindow.dismiss();
            }
        });
    }

    private static void setPopWindowBackgroundAlpha(float bgAlpha,Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //[0.0-1.0]
        if (bgAlpha == 1) {
            //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug,而我遇到的是半透明无效，采用了该方案
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            //此行代码主要是解决在华为手机上半透明效果无效的bug
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        // activity.getWindow().setAttributes(lp);
        activity.getWindow().setAttributes(lp);
    }
*/
}
