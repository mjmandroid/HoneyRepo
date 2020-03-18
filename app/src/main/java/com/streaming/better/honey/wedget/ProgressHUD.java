package com.streaming.better.honey.wedget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;



import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.streaming.better.honey.R;
import com.streaming.better.honey.base.App;


/**
 * loading
 *
 * @author longluliu
 * @ClassName: ProgressHUD
 * @Description: TODO
 * @date 2014-7-22 上午10:57:35
 */
public class ProgressHUD extends Dialog {
  private Context contexts;

  public ProgressHUD(Context context) {
    super(context);
    contexts = context;
  }

    public ProgressHUD(Context context, int theme) {
        super(context, theme);
        contexts = context;
    }

    @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    //startAnimation(ContextCompat.getDrawable(contexts, R.mipmap.gif_loading));
  }

  public void setMessage(CharSequence message) {
    if (message != null && message.length() > 0) {
      findViewById(R.id.message).setVisibility(View.VISIBLE);
      TextView txt = (TextView) findViewById(R.id.message);
      txt.setText(message);
      txt.invalidate();
    }
  }

  public static ProgressHUD show(Context context, int message) {
    String msg = context.getString(message);
    return show(context, msg);
  }

  public static ProgressHUD show(Context context, String message) {
    return show(context, message, true, true, null);
  }

  public static ProgressHUD show(Context context, String message, boolean cancelable) {
    return show(context, message, true, cancelable, null);
  }

  private static ProgressHUD show(Context context, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) {
    ProgressHUD dialoghud = new ProgressHUD(context, R.style.Lib_ProgressHUD);
    dialoghud.setTitle("");
    View view = LayoutInflater.from(context).inflate(R.layout.lib_view_progress_hud, null);
    ImageView imageView = view.findViewById(R.id.spinnerImageView);
    Glide.with(App.getInstance().getApplicationContext())
            .asGif()
            .apply(new RequestOptions()
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
            .load(R.mipmap.gif_loading)
            .into(imageView);
    //setAlphaBg(view, 255);
    dialoghud.setContentView(view);
    if (message == null || message.length() == 0) {
      dialoghud.findViewById(R.id.message).setVisibility(View.GONE);
    } else {
      TextView txt = (TextView) dialoghud.findViewById(R.id.message);
      txt.setText(message);
    }
    dialoghud.setOnCancelListener(cancelListener);
    if (dialoghud.getWindow() != null && dialoghud.getWindow().getAttributes() != null) {
      dialoghud.getWindow().getAttributes().gravity = Gravity.CENTER;
      WindowManager.LayoutParams lp = dialoghud.getWindow().getAttributes();
      lp.dimAmount = 0f;
      dialoghud.getWindow().setAttributes(lp);
    }
    dialoghud.setCancelable(cancelable);
    dialoghud.setCanceledOnTouchOutside(cancelable);
    dialoghud.show();
    return dialoghud;
  }


  private void startAnimation(Drawable imageDrawable) {
    // Set Drawable
    ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
    if (imageView != null) {
      imageView.setImageDrawable(imageDrawable);
      //            mUseIntrinsicAnimation = (imageDrawable instanceof AnimationDrawable);
      if (imageDrawable instanceof AnimationDrawable) {
        AnimationDrawable spinner = (AnimationDrawable) imageDrawable;
        spinner.start();
      }
    }
  }


  /**
   * <br> Description: 设置View背景透明度
   * <br> Author:      wujianghua
   * <br> Date:        2017/7/26 10:08
   *
   * @param view  要设置透明度的控件
   * @param alpha 透明度程度0-255
   */
  public static void setAlphaBg(View view, int alpha) {
    if (view != null && view.getBackground() != null) {
      /*0-255*/
      view.getBackground().setAlpha(alpha);
    }
  }

}
