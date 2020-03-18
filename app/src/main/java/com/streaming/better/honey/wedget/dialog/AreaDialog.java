package com.streaming.better.honey.wedget.dialog;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.streaming.better.honey.R;
import com.streaming.better.honey.entity.CountryBean;
import com.streaming.better.honey.wedget.wheelview.OnWheelChangedListener;
import com.streaming.better.honey.wedget.wheelview.WheelView;
import com.streaming.better.honey.wedget.wheelview.adapter.ArrayWheelAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作用:
 * Created by 78 on 2017/6/26.
 */

@SuppressLint("ValidFragment")
public class AreaDialog extends DialogFragment implements OnWheelChangedListener, View.OnClickListener {
    private View mContainer;
    private View btnOk;
    private View btnCancel;
    private WheelView diretion;
    private String[] diracitons;
    private String currentDiraction;
    private int mCurrentId;
    private String mCountryCode;
    private Map<String, Integer> countryData = new HashMap<>();
    private Map<String, String> countryData1 = new HashMap<>();
    private AreaDialog.OnDialogClickListener mListener;
    private String[] country;


    public AreaDialog(List<CountryBean.DataBean> children) {
        diracitons = new String[children.size()];
        for (int i = 0; i < children.size(); i++) {
            diracitons[i] = children.get(i).getName();
            countryData.put(children.get(i).getName(), children.get(i).getId());
            countryData1.put(children.get(i).getName(), children.get(i).getCode());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去掉dialog的标题，需要在setContentView()之前
        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getDialog().getWindow();
        //去掉dialog默认的padding
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的位置在底部
        lp.gravity = Gravity.BOTTOM;
        //设置dialog的动画
        lp.windowAnimations = R.style.dialog_animation;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable());
        mContainer = inflater.inflate(R.layout.dialog_direction_select, null);
        initView();
        initEvent();
        initData();
        return mContainer;
    }

    private void initData() {
        updateFirstDiraction();
    }

    private void initView() {
        diretion = (WheelView) mContainer.findViewById(R.id.diretion);
        btnOk = mContainer.findViewById(R.id.btn_ok);
        btnCancel = mContainer.findViewById(R.id.btn_cancel);

    }

    private void initEvent() {
        diretion.addChangingListener(this);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    public void setOnDialogClickListener(AreaDialog.OnDialogClickListener listener) {
        this.mListener = listener;
    }

    private void updateFirstDiraction() {
        if (diracitons != null) {
            ArrayWheelAdapter adapter = new ArrayWheelAdapter(getContext(), diracitons);
            diretion.setViewAdapter(adapter);
            currentDiraction = diracitons[0];
            mCurrentId = countryData.get(currentDiraction);
            mCountryCode = countryData1.get(currentDiraction);
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == diretion) {
            currentDiraction = diracitons[newValue];
            mCurrentId = countryData.get(currentDiraction);
            mCountryCode = countryData1.get(currentDiraction);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                mListener.onBtnClick(currentDiraction, mCurrentId, mCountryCode);
                dismiss();
                break;

            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    public interface OnDialogClickListener {

        void onBtnClick(String diraction, int id, String code);
    }
}
