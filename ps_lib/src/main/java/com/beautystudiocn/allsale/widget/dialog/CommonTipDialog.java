package com.beautystudiocn.allsale.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.beautystudiocn.allsale.R;


/**
 * <br> ClassName:   CommonTipDialog
 * <br> Description: 带有确定与取消按钮的弹窗
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/8/21 16:12
 */
public class CommonTipDialog extends AlertDialog {
    private boolean confirmFlag;
    private boolean cancelFlag;
    private Context mContext;
    private int mTitleColor;
    private int mContentColor;
    private int mCancelColor;
    private int mConfirmColor;
    private int mCancelDrawableId;
    private int mSingleCancelDrawableId;
    private int mConfirmDrawableId;
    private int mSingleConfirmDrawableId;
    private static int mStyleId = R.style.lib_common_tip_dialog_theme;

    /**
     *<br> Description: 配置全局弹窗样式
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/10/31 16:53
     */
    public static void configStyle(@StyleRes int id) {
        mStyleId = id;
    }

    public CommonTipDialog(Context context) {
        this(context,mStyleId);
    }

    public CommonTipDialog(Context context, @StyleRes int id) {
        super(context,id);
        mContext = context;
        getStyleTypeValue(id);
    }

    private void getStyleTypeValue(@StyleRes int id) {
        TypedArray array = mContext.obtainStyledAttributes(id,R.styleable.lib_common_dialog_attrs);
        mTitleColor = array.getColor(R.styleable.lib_common_dialog_attrs_lib_dialog_title_text_color, 0);
        mContentColor = array.getColor(R.styleable.lib_common_dialog_attrs_lib_dialog_content_text_color, 0);
        mCancelColor = array.getColor(R.styleable.lib_common_dialog_attrs_lib_dialog_cancel_button_text_color, 0);
        mConfirmColor = array.getColor(R.styleable.lib_common_dialog_attrs_lib_dialog_confirm_button_text_color, 0);
        mCancelDrawableId = array.getResourceId(R.styleable.lib_common_dialog_attrs_lib_dialog_cancel_button_bg, 0);
        mSingleCancelDrawableId = array.getResourceId(R.styleable.lib_common_dialog_attrs_lib_dialog_single_cancel_button_bg, 0);
        mConfirmDrawableId = array.getResourceId(R.styleable.lib_common_dialog_attrs_lib_dialog_confirm_button_bg, 0);
        mSingleConfirmDrawableId = array.getResourceId(R.styleable.lib_common_dialog_attrs_lib_dialog_single_confirm_button_bg, 0);
        array.recycle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_dialog_tips_layout);
    }

    /**
     *<br> Description: 判断dialog是否有效
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/10/31 16:53
     */
    protected boolean checkIsActive() {
        if (!(mContext instanceof Activity) || (((Activity) mContext).isFinishing())) {
            return false;
        }
        return true;
    }

    /**
     *<br> Description: 初始化
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 17:20
     * @param isCanCancel
     *                  是否可取消
     * @return
     */
    public CommonTipDialog init(boolean isCanCancel) {
        return init(isCanCancel,isCanCancel);
    }

    /**
     *<br> Description: 初始化
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 17:20
     * @param isCanCancel
     *                  是否可取消
     * @param isCanCancelOnTouchOutSide
     *                  是否可根据点击外部取消
     * @return
     */
    public CommonTipDialog init(boolean isCanCancel,boolean isCanCancelOnTouchOutSide) {
        if (!checkIsActive()) {
            return this;
        }
        setCancelable(isCanCancel);
        setCanceledOnTouchOutside(isCanCancelOnTouchOutSide);
        show();
        setOnClickListener(null);
        return this;
    }

    /**
     *<br> Description: 设置标题
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 17:21
     * @param title
     *                  标题
     * @return
     */
    public CommonTipDialog setDialogTitle(CharSequence title) {
        setDialogTitle(title,null);
        return this;
    }

    /**
     *<br> Description: 设置标题
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 17:21
     * @param title
     *                  标题
     * @param tf
     *                  样式
     * @return
     */
    public CommonTipDialog setDialogTitle(CharSequence title, Typeface tf) {
        if (!checkIsActive()) {
            return this;
        }
        if (TextUtils.isEmpty(title)) {
            return this;
        }
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        if (mTitleColor != 0) {
            txtTitle.setTextColor(mTitleColor);
        } else {
            txtTitle.setTextColor(0XFF212121);
        }
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(title);
        if (tf != null) {
            txtTitle.setTypeface(tf);
        }
        return this;
    }

    /**
     *<br> Description: 设置内容
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 17:21
     * @param content
     *                  内容
     * @return
     */
    public CommonTipDialog setContent(CharSequence content) {
        setContent(content, Gravity.CENTER);
        return this;
    }

    /**
     *<br> Description: 设置内容
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 17:58
     * @param content
     *                  内容
     * @param gravity
     *                  内容布局方向
     * @return
     */
    public CommonTipDialog setContent(CharSequence content, int gravity) {
        if (!checkIsActive()) {
            return this;
        }
        if (TextUtils.isEmpty(content)) {
            return this;
        }
        TextView txtMessage = (TextView) findViewById(R.id.txtMessage);
        txtMessage.setGravity(gravity);
        if (mContentColor != 0) {
            txtMessage.setTextColor(mContentColor);
        } else {
            txtMessage.setTextColor(0xFF212121);
        }
        txtMessage.setText(content);
        txtMessage.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     *<br> Description: 设置是否不再提醒
     *<br> Author:      yexiaochuan
     *<br> Date:        2018/1/8 11:48
     * @param key
     *                  当前弹窗标记位
     * @return
     */
    public CommonTipDialog setIgnoreTip(final String key) {
        return setIgnoreTip(key,"");
    }

    public CommonTipDialog setIgnoreTip(final String key, CharSequence content) {
        if (!checkIsActive()) {
            return this;
        }

        if (TextUtils.isEmpty(key)) {
            return this;
        }

        CheckBox cbTip = (CheckBox) findViewById(R.id.cb_tip);
        cbTip.setVisibility(View.VISIBLE);
        cbTip.setTextColor(0xFFCCCCCC);
        if (!TextUtils.isEmpty(content)) {
            cbTip.setText(content);
        }
        cbTip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("SharedPreference.xml", Context.MODE_PRIVATE).edit();
                    editor.putBoolean(key, true);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("SharedPreference.xml", Context.MODE_PRIVATE).edit();
                    editor.putBoolean(key, false);
                    editor.apply();
                }
            }
        });
        return this;
    }

    /**
     *<br> Description: 设置确定按钮
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 17:58
     * @param text
     *                  按钮文本
     * @return
     */
    public CommonTipDialog setConfirm(CharSequence text) {
        setConfirm(text,0);
        return this;
    }

    /**
     *<br> Description: 设置确定按钮,倒计时
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 17:59
     * @param text
     *                  按钮文本
     * @param time
     *                  倒计时时间
     * @return
     */
    public CommonTipDialog setConfirm(CharSequence text, int time) {
        if (!checkIsActive()) {
            return this;
        }
        if (TextUtils.isEmpty(text)) {
            confirmFlag = false;
            return this;
        }

        confirmFlag = true;
        TextView confirmBtn = (TextView) findViewById(R.id.btnOk);
        confirmBtn.setVisibility(View.VISIBLE);
        if (mConfirmColor != 0) {
            confirmBtn.setTextColor(mConfirmColor);
        } else {
            confirmBtn.setTextColor(0xFFFFC61A);
        }
        confirmBtn.setText(text);

        if (time > 0) {
            setCutDownFunction(confirmBtn, text, time);
        }

        setBtnBackground();
        return this;
    }

    /**
     *<br> Description: 设置倒计时功能
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 17:59
     */
    private CommonTipDialog setCutDownFunction(final TextView confirmBtn, final CharSequence text, int time) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                Bundle bundle = message.getData();
                if (bundle != null) {
                    int count = bundle.getInt("time",0);
                    if (count > 0) {
                        confirmBtn.setText(text + "(" + count + ")");
                        count--;
                        Message msg = new Message();
                        Bundle data = new Bundle();
                        data.putInt("time",count);
                        msg.setData(data);
                        sendMessageDelayed(msg,1000);
                    } else {
                        confirmBtn.setText(text);
                        confirmBtn.setEnabled(true);
                    }
                }
            }
        };

        confirmBtn.setEnabled(false);
        confirmBtn.setText(text + "(" + time + ")");
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("time",time - 1);
        message.setData(bundle);
        handler.sendMessageDelayed(message,1000);
        return this;
    }

    /**
     *<br> Description: 设置取消按钮
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 17:59
     * @param text
     *                  按钮文本
     * @return
     */
    public CommonTipDialog setCancel(CharSequence text) {
        if (!checkIsActive()) {
            return this;
        }
        if (TextUtils.isEmpty(text)) {
            cancelFlag = false;
            return this;
        }

        cancelFlag = true;
        TextView cancelBtn = (TextView) findViewById(R.id.btnCancel);
        cancelBtn.setText(text);
        cancelBtn.setVisibility(View.VISIBLE);
        if (mCancelColor != 0) {
            cancelBtn.setTextColor(mCancelColor);
        } else {
            cancelBtn.setTextColor(0xFF212121);
        }
        setBtnBackground();
        return this;
    }

    /**
     *<br> Description: 设置按钮背景，适配窗口圆角
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 18:00
     */
    private void setBtnBackground() {
        findViewById(R.id.dialog_lly).setVisibility(View.VISIBLE);
        findViewById(R.id.line1).setVisibility(View.VISIBLE);

        if (cancelFlag && confirmFlag) {
            setCancelButtonBg();
            setConfirmButtonBg();
            return;
        }

        if (cancelFlag) {
            if (mSingleCancelDrawableId != 0) {
                findViewById(R.id.btnCancel).setBackgroundResource(mSingleCancelDrawableId);
            } else {
                setCancelButtonBg();
            }
        }

        if (confirmFlag) {
            if (mSingleConfirmDrawableId != 0) {
                findViewById(R.id.btnOk).setBackgroundResource(mSingleConfirmDrawableId);
            } else {
                setConfirmButtonBg();
            }
        }
    }

    private void setConfirmButtonBg() {
        if (mConfirmDrawableId != 0) {
            findViewById(R.id.btnOk).setBackgroundResource(mConfirmDrawableId);
        } else {
            findViewById(R.id.btnOk).setBackgroundResource(R.drawable.lib_bg_bottom_right_rounded_box_selector);
        }
    }

    private void setCancelButtonBg() {
        if (mCancelDrawableId != 0) {
            findViewById(R.id.btnCancel).setBackgroundResource(mCancelDrawableId);
        } else {
            findViewById(R.id.btnCancel).setBackgroundResource(R.drawable.lib_bg_bottom_left_rounded_box_selector);
        }
    }

    /**
     *<br> Description: 设置按钮点击事件
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/21 18:00
     */
    public CommonTipDialog setOnClickListener(final OnConfirmListener listener) {
        if (!checkIsActive()) {
            return this;
        }
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.clickCancel();
                }
            }
        });

        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.clickConfirm();
                }
            }
        });
        return this;
    }

    public interface OnConfirmListener {
        void clickConfirm();

        void clickCancel();
    }

    /**
     * <br> ClassName:   DialogUtil
     * <br> Description: OnConfirmListener简单实例
     * <br>
     * <br> Author:      谢文良
     * <br> Date:        2017/8/22 10:09
     */
    public static class SimpleOnConfirmListener implements OnConfirmListener {

        @Override
        public void clickConfirm() {

        }

        @Override
        public void clickCancel() {

        }
    }
}
