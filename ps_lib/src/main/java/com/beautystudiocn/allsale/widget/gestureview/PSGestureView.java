package com.beautystudiocn.allsale.widget.gestureview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <br> ClassName:   PSGestureView
 * <br> Description:  绘制手势
 * <br>
 * <br> Author:      wuheng
 * <br> Date:        2017/9/30 10:00
 */
public class PSGestureView extends FrameLayout implements IPSGestureStatus {
    /***视图上的点***/
    private List<PSGesturePointView> mPointViews = null;
    /***选中的点***/
    private ArrayList<PSPoint> mSelectPoints = null;
    /***事件点的位置***/
    PSPoint mEventPoint;
    /***画线***/
    private Paint mLinePaint = null;
    /***按下***/
    private boolean isTouching = false;
    /***是否可绘制状态 ***/
    private boolean canDraw = true;
    /***手势的配置***/
    private PSGestureConfig mConfig = null;
    /***手势管理类***/
    private PSGestureManager mGestureManger = null;

    public PSGestureView(Context context) {
        super(context);
        initView();
    }

    public PSGestureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PSGestureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * <br> Description: 设置手势的回调对象
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 15:37
     */
    public void setGestureLinstener(IPSGestureChangeLinstener mGestureLinstener) {
        mGestureManger.setmGestureLinstener(mGestureLinstener, mConfig, this);
    }

    /**
     * <br> Description: 返回手势的管理对象
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 17:52
     */
    public PSGestureManager getGestureManager() {
        return mGestureManger;
    }

    /**
     * <br> Description: 显示正常
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 17:25
     */
    @Override
    public void showGestureNormalStatus() {
        for (PSPoint point : mSelectPoints) {
            mPointViews.get(point.getIndex()).showGestureNormalStatus();
        }
    }

    /**
     * <br> Description: 显示选中的状态
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 17:26
     */
    @Override
    public void showGesturePressedStatus() {
        for (PSPoint point : mSelectPoints) {
            mPointViews.get(point.getIndex()).showGesturePressedStatus();
        }
    }

    /**
     * <br> Description: 显示错误状态
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 17:26
     */
    @Override
    public void showGestureErrorStatus() {
        for (PSPoint point : mSelectPoints) {
            mPointViews.get(point.getIndex()).showGestureErrorStatus();
        }
    }

    /**
     * <br> Description: 修改pointItemView中的配置
     * <br> Author:      kevinwu
     * <br> Date:        2017/10/13 下午9:48
     */
    public void refreshView() {
        if (mSelectPoints != null) {
            mSelectPoints.clear();
        }
        if (mPointViews != null) {
            mPointViews.clear();
        }
        this.removeAllViews();
        initArraySource();
        for (PSGesturePointView pointView : mPointViews) {
            pointView.setPointConfig(getGestureConfig().getPointViewConfig());
            pointView.showGestureNormalStatus();
        }
    }

    /**
     * <br> Description: 获取手势配置信息
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 17:13
     */
    public PSGestureConfig getGestureConfig() {
        if (mConfig == null) {
            mConfig = new PSGestureConfig();
        }
        return mConfig;
    }

    /**
     * <br> Description: 初始化视图
     * <br> Author:      wuheng
     * <br> Date:        2017/10/9 15:29
     */
    private void initView() {
        setClipChildren(false);
        mPointViews = new ArrayList<>();
        mEventPoint = new PSPoint();
        mSelectPoints = new ArrayList<>();
        initArraySource();
        mGestureManger = new PSGestureManager();
    }

    /**
     * <br> Description: 初始化资源
     * <br> Author:      wuheng
     * <br> Date:        2017/10/9 15:33
     */
    private void initArraySource() {
        PSGesturePointView pointView = null;
        for (int index = 0; index < 9; index++) {
            pointView = new PSGesturePointView(getContext());
            pointView.setDrawText(false);
//            pointView.setRenderPic(R.drawable.locus_round_click);
            pointView.getCenterPoint().setIndex(index);
            mPointViews.add(pointView);
            this.addView(pointView, getLayoutParams(index));
            pointView.showGestureNormalStatus();
        }
    }

    /**
     * <br> Description: params
     * <br> Author:      wuheng
     * <br> Date:        2017/10/10 10:17
     */
    private LayoutParams getLayoutParams(int index) {

        /***圆宽***/
        int diameter = getGestureConfig().getDiameter();
        /***margin***/
        int circleMargin = getGestureConfig().getMargin();
        /***每排只显示3个***/
        int columnNum = getGestureConfig().getColumnNum();
        LayoutParams params = new LayoutParams(diameter, diameter);
        int marginLeft, marginTop;

        /***当前第几排***/
        int currentRow = index / columnNum;
        int currentRowIndex = index % columnNum;

        marginTop = currentRow == 0 ?
                0 : circleMargin * (currentRow + 1) + diameter * currentRow;
        marginLeft = currentRowIndex == 0 ?
                0 : circleMargin * currentRowIndex + diameter * currentRowIndex;

        params.setMargins(marginLeft, marginTop, 0, 0);
        return params;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (getGestureConfig().getAutoResize()) {
            //是否按比例缩放
            this.excuteAutoSize();
        }
    }

    /**
     * <br> Description: 调整布局
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/26 19:16
     */
    private void excuteAutoSize() {
        int mViewGroupWidth = getMeasuredWidth();  //当前ViewGroup的总宽度
        int mViewGroupHeight = getMeasuredHeight(); //当前ViewGroup的总高度
        int xOffset = 0;   // 水平偏移量
        int yOffset = 0;   // 垂直偏移量
        // 以最小的为准
        // 纵屏
        if (mViewGroupWidth > mViewGroupHeight) {
            //水平居中
            xOffset = (mViewGroupWidth - mViewGroupHeight) / 2;
        } else {
            yOffset = (mViewGroupHeight - mViewGroupWidth) / 2;
        }
        // 临时宽度
        int widthTmp = mViewGroupWidth > mViewGroupHeight ? mViewGroupHeight : mViewGroupWidth;

        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            View childView = getChildAt(index);
            LayoutParams params = (LayoutParams) childView.getLayoutParams();
            int diameter = widthTmp / 8 * 2;
            int marginLeft, marginTop;
            /*** margin 为圆直径的一半长 ***/
            int circleMargin = diameter / 2;
            /***每排只显示3个***/
            int columnNum = getGestureConfig().getColumnNum();
            /***当前第几排***/
            int currentRow = index / columnNum;
            int currentRowIndex = index % columnNum;

            marginTop = currentRow == 0 ?
                    0 : circleMargin * currentRow + diameter * currentRow;
            marginLeft = currentRowIndex == 0 ?
                    0 : circleMargin * currentRowIndex + diameter * currentRowIndex;
            params.width = diameter;
            params.height = diameter;
            params.setMargins(marginLeft + xOffset, marginTop+yOffset, 0, 0);
            childView.setLayoutParams(params);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.drawLines(canvas);
    }

    /**
     * <br> Description: 绘制线
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 11:14
     */
    private void drawLines(Canvas canvas) {
        if (mSelectPoints.isEmpty()) {
            return;
        }
        if (mLinePaint == null) {
            mLinePaint = new Paint();
            mLinePaint.setStrokeWidth(getGestureConfig().getDrawLineWidth());
            mLinePaint.setAntiAlias(true);
        }

        //通过状态和选择的大小去判定，画线的颜色
        mLinePaint.setColor(canDraw || mSelectPoints.size() >= getGestureConfig().getPwdMiniLength()
                ? getGestureConfig().getDrawLineColor()
                : getGestureConfig().getDrawLineErrorColor());

        for (PSPoint point : mSelectPoints) {
            int index = mSelectPoints.indexOf(point);
            if (index != 0) {
                //过滤第一个点
                PSPoint beginPoint = mSelectPoints.get(index - 1);
                PSPoint endPoint = mSelectPoints.get(index);
                //画一条线
                canvas.drawLine(beginPoint.getX(), beginPoint.getY(),
                        endPoint.getX(), endPoint.getY(),
                        mLinePaint);
            }
        }
        if (isTouching || canDraw) {
            PSPoint lastPoint = mSelectPoints.get(mSelectPoints.size() - 1); // 最后一个点
            //画一条线
            canvas.drawLine(lastPoint.getX(), lastPoint.getY(),
                    mEventPoint.getX(), mEventPoint.getY(),
                    mLinePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!canDraw) return true;
        mEventPoint.set((int) event.getX(), (int) event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart();
                break;
            case MotionEvent.ACTION_MOVE:
                touchChange();
                break;
            case MotionEvent.ACTION_UP:
                touchFinish();
                break;
            case MotionEvent.ACTION_CANCEL:
                touchFinish();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * <br> Description: 开始绘制
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 16:23
     */
    private void touchStart() {
        isTouching = true;
        pointInCircle(mEventPoint);
        mGestureManger.onGestureViewStart(this);
    }

    /**
     * <br> Description: 绘制中
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 16:24
     */
    private void touchChange() {
        pointInCircle(mEventPoint);
        invalidate();
        mGestureManger.onGestureViewChange(this, getCurrentPwd());
    }

    /**
     * <br> Description: 结束绘制
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 16:24
     */
    private void touchFinish() {
        isTouching = false;
        if (mSelectPoints.size() == 0) return;
        setEnabled(false);
        invalidate();
        delayDisappear();
        mGestureManger.onGestureViewFinish(this, getCurrentPwd());
    }

    /**
     * <br> Description: 返回当前选中的数字
     * <br> Author:      KevinWu
     * <br> Date:        2017/11/7 16:46
     */
    private String getCurrentPwd() {
        StringBuilder sb = new StringBuilder();
        int lastIndex = mSelectPoints.size() - 1;
        for (PSPoint point : mSelectPoints) {
            sb.append(point.getIndex());
            if (lastIndex != mSelectPoints.indexOf(point)) {
                //判断当前的点，不是最后一个
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * <br> Description: 延时消失
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 15:50
     */
    private void delayDisappear() {
        canDraw = false;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                showGestureNormalStatus();
                mSelectPoints.clear();
                setEnabled(true);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        canDraw = true;
                    }
                }, 100);
            }
        }, getGestureConfig().getLineDisppearDelayTime());
    }

    /**
     * <br> Description: 判断点是否在圆内，如果在就添加到mSelectPoints中
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 11:53
     */
    private boolean pointInCircle(PSPoint point) {
        for (PSGesturePointView pointView : mPointViews) {
            if (PSGesturePointView.isInSmallCircle(point, pointView)) {
//                KLog.d("zll", "在圆内 ：" + mPointViews.indexOf(pointView));
                if (!selectPointsContain(pointView.getCenterPoint())) {
                    mSelectPoints.add(pointView.getCenterPoint());
                    pointView.showGesturePressedStatus();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * <br> Description: 判断数组中是否存在相应的选中点
     * <br> Author:      wuheng
     * <br> Date:        2017/10/13 14:45
     */
    private boolean selectPointsContain(PSPoint point) {
        boolean isExist = false;
        for (PSPoint tmpPoint : mSelectPoints) {
            if (PSPoint.equalsValue(point, tmpPoint)) {
                isExist = true;
            }
        }
        return isExist;
    }

}
