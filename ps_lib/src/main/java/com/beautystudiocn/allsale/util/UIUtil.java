package com.beautystudiocn.allsale.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * <br> ClassName:   UIUtil
 * <br> Description: UI工具类
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2017/8/1 10:57
 */
public class UIUtil { 

    /**
     * <br> Description: dp转px数值
     * <br> Author:      wujianghua
     * <br> Date:        2017/8/2 11:26
     *
     * @param dp        要转换的dp值
     * @return 返回转换后的px值
     */
    public static int dp2px(float dp) {
        float scale = AppUtil.getContext().getResources()
                .getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * <br> Description: 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * <br> Author:      longluliu
     * <br> Date:        2014/08/02 15:39
     *
     * @param pxValue 要转换的px数值
     * @return 返回转换后的dp数值
     */
    public static int px2dp(float pxValue) {
        float scale = AppUtil.getContext().getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * <br> Description: dp转px
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/26 9:58
     *
     * @param context 上下文
     * @param value   要转的dp数值
     * @return 返回转换后的px数值
     */
    public static int getDp(Context context, float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics());
    }

    /**
     * <br> Description: sp转px数值
     * <br> Author:      wujianghua
     * <br> Date:        2017/8/2 11:27
     *
     * @param resources resources
     * @param sp        要转换的sp值
     * @return 返回转换后的px值
     */
    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }



    /**
     * <br> Description: 设置控件背景
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/26 10:18
     *
     * @param view     要设置背景的控件
     * @param drawable 背景drawable图片
     */
    @SuppressLint("NewApi")
    public static void setBackground(View view, Drawable drawable) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        }
    }

    /**
     * <br> Description: 设置控件的宽高
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/26 9:55
     *
     * @param view 要设置宽高的控件
     * @param w    要设置的宽度
     * @param h    要设置的高度
     */
    public static void setHW(View view, int w, int h) {
        if (view != null) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            lp.height = h;
            lp.width = w;
            view.setLayoutParams(lp);
        }
    }

    /**
     * <br> Description: 设置点击事件
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/26 10:06
     *
     * @param view            要设置的控件
     * @param onClickListener 点击回调接口
     */
    public static void setOnClickListener(View view, View.OnClickListener onClickListener) {
        if (view != null) {
            view.setOnClickListener(onClickListener);
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

    /**
     * <br> Description: 设置TextView内容
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/26 10:13
     *
     * @param textView textview
     * @param mText    要填充的内容
     */
    public static void setText(TextView textView, CharSequence mText) {
        if (textView != null) {
            textView.setText(mText);
        }
    }

    /**
     * <br> Description: 设置EditText光标移动到文本框末尾
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/26 10:14
     *
     * @param editTex edittext控件
     */
    public static void setSelection(EditText editTex) {
        if (editTex != null && !TextUtils.isEmpty(editTex.getText().toString())) {
            editTex.setSelection(editTex.getText().length());
        }
    }

    /**
     * <br> Description: 设置EditText是否可编辑状态
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/26 10:14
     *
     * @param editText     edittext控件
     * @param isEdistState ture代表可编辑，false代表不可编辑
     */
    public static void setEditState(EditText editText, boolean isEdistState) {
        if (editText != null) {
            editText.setFocusable(isEdistState);
            editText.setFocusableInTouchMode(isEdistState);
            editText.requestFocus();
        }
    }

    /**
     * <br> Description: 设置TextView图标
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/26 10:15
     *
     * @param textView     textview控件
     * @param context      上下文
     * @param drawb_left   左边图标
     * @param drawb_top    上边图标
     * @param drawb_right  右边图标
     * @param drawb_bottom 下边图标
     */
    public static void setCompoundDrawables(TextView textView, Context context, int drawb_left, int drawb_top, int drawb_right, int drawb_bottom) {
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        if (textView != null) {
            textView.setCompoundDrawables(getTestDrawable(context, drawb_left), getTestDrawable(context, drawb_top), getTestDrawable(context, drawb_right), getTestDrawable(context, drawb_bottom)); //设置图标
        }
    }

    public static Drawable getTestDrawable(Context context, int drawbId) {
        Drawable drawable = null;
        if (drawbId != -1) {
            drawable = ContextCompat.getDrawable(context, drawbId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        return drawable;
    }

    /**
     * <br> Description: 设置图片
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/26 10:17
     *
     * @param view       imageview控件
     * @param drawableId 要设置的图片id
     */
    public static void setImageResource(ImageView view, int drawableId) {
        if (view != null) {
            view.setImageResource(drawableId);
        }
    }

    /**
     * <br> Description: 设置图片
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/26 10:17
     *
     * @param view     imageview控件
     * @param drawable 要设置的图片drawable对象
     */
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        if (view != null) {
            view.setImageDrawable(drawable);
        }
    }

    /**
     * <br> Description: 是否显示View
     * <br> Author:      wujianghua
     * <br> Date:        2017/7/26 9:55
     *
     * @param view   要显示或隐藏的控件
     * @param isShow 是否要显示，true代表显示，false代表隐藏
     */
    public static void setVisibility(View view, boolean isShow) {
        if (view != null) {
            view.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * <br> Description: 设置控制输入框小数位数
     * <br> Author:      longluliu
     * <br> modify:      yexiaochuan
     * <br> Date:        2015/07/27 21:44
     *
     * @param edtText 输入框edittext
     * @param length  小数位数
     */
    public static void limitDecimalDigits(EditText edtText, final int length) {
        edtText.setFilters(new InputFilter[]{new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // 空字符串处理
                if ("".equals(source.toString())) {
                    return null;
                }

                //小数点位置是否在两位小数限制范围内
                if (".".equals(source.toString()) && dstart < dest.length() - length) {
                    return "";
                }

                //输入位置为非小数位
                int dotPos = dest.toString().indexOf(".");
                if (dotPos == -1 || dstart <= dotPos) {
                    return null;
                }

                //输入位置为小数位
                String dValue = dest.toString();
                String[] splitArray = dValue.split("\\.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - length;
                    if (diff > 0 && end - diff >= 0 && source.length() >= (end - diff)) {
                        return source.subSequence(start, end - diff);
                    }
                }
                return null;
            }
        }});
    }
    /**
     * <br> Description: 显示软键盘
     * <br> Author:      qiuruifeng
     * <br> Date:        2017/8/22 15:44
     *
     * @param view edittext
     */
    public static void showSoftInput(final View view) {
        if (view == null || view.getContext() == null) return;
        final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }, 200);
    }

    /**
     * <br> Description: 隐藏软键盘
     * <br> Author:      qiuruifeng
     * <br> Date:        2017/8/22 15:43
     *
     * @param view edittext
     */
    public static void hideSoftInput(View view) {
        if (view == null || view.getContext() == null) return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * <br> Description: 限制数值小数点
     * <br> Author:      yangyinglong
     * <br> Date:        2017/10/31 9:33
     *
     * @param editText editText
     */
    public static void limitEditText(EditText editText, final int length) {
        editText.addTextChangedListener(new TextWatcher() {
            int index = 0;
            boolean needDelete = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                index = 0;
                String editText = s.toString();
                if (!".".equals(editText)) {
                    String[] split = editText.split("\\.");
                    if (split.length >= 2) {
                        if (split[1].length() > length) {
                            index = start;
                            needDelete = true;
                        }
                    }
                    if (split[0].length() > 10) {
                        index = start;
                        needDelete = true;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (needDelete) {
                    needDelete = false;
                    s.replace(index, index + 1, "");
                }
            }
        });
    }

}
