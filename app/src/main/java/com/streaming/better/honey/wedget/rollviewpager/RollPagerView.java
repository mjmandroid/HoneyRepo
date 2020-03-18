package com.streaming.better.honey.wedget.rollviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.streaming.better.honey.R;
import com.streaming.better.honey.utils.DensityUtil;
import com.streaming.better.honey.wedget.scrollView.RCAttrs;
import com.streaming.better.honey.wedget.scrollView.RCHelper;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 支持轮播和提示的的viewpager
 */
public class RollPagerView extends RelativeLayout implements OnPageChangeListener, RCAttrs {

	private ViewPager mViewPager;
	private PagerAdapter mAdapter;
	private OnItemClickListener mOnItemClickListener;
    private GestureDetector mGestureDetector;

	private long mRecentTouchTime;
	//播放延迟
	private int delay;
	
	//hint位置
	private int gravity;
	
	//hint颜色
	private int color;
	
	//hint透明度
	private int alpha;

	private int paddingLeft;
	private int paddingTop;
	private int paddingRight;
	private int paddingBottom;

	private View mHintView;
	private Timer timer;


	private RCHelper mRCHelper;


	//--- 公开接口 ----------------------------------------------------------------------------------

	public void setClipBackground(boolean clipBackground) {
		mRCHelper.mClipBackground = clipBackground;
		invalidate();
	}

	public void setRoundAsCircle(boolean roundAsCircle) {
		mRCHelper.mRoundAsCircle = roundAsCircle;
		invalidate();
	}

	public void setRadius(int radius) {
		for (int i = 0; i < mRCHelper.radii.length; i++) {
			mRCHelper.radii[i] = radius;
		}
		invalidate();
	}

	public void setTopLeftRadius(int topLeftRadius) {
		mRCHelper.radii[0] = topLeftRadius;
		mRCHelper.radii[1] = topLeftRadius;
		invalidate();
	}

	public void setTopRightRadius(int topRightRadius) {
		mRCHelper.radii[2] = topRightRadius;
		mRCHelper.radii[3] = topRightRadius;
		invalidate();
	}

	public void setBottomLeftRadius(int bottomLeftRadius) {
		mRCHelper.radii[6] = bottomLeftRadius;
		mRCHelper.radii[7] = bottomLeftRadius;
		invalidate();
	}

	public void setBottomRightRadius(int bottomRightRadius) {
		mRCHelper.radii[4] = bottomRightRadius;
		mRCHelper.radii[5] = bottomRightRadius;
		invalidate();
	}

	public void setStrokeWidth(int strokeWidth) {
		mRCHelper.mStrokeWidth = strokeWidth;
		invalidate();
	}

	public void setStrokeColor(int strokeColor) {
		mRCHelper.mStrokeColor = strokeColor;
		invalidate();
	}

	@Override
	public void invalidate() {
		if (null != mRCHelper)
			mRCHelper.refreshRegion(this);
		super.invalidate();
	}

	public boolean isClipBackground() {
		return mRCHelper.mClipBackground;
	}

	public boolean isRoundAsCircle() {
		return mRCHelper.mRoundAsCircle;
	}

	public float getTopLeftRadius() {
		return mRCHelper.radii[0];
	}

	public float getTopRightRadius() {
		return mRCHelper.radii[2];
	}

	public float getBottomLeftRadius() {
		return mRCHelper.radii[4];
	}

	public float getBottomRightRadius() {
		return mRCHelper.radii[6];
	}

	public int getStrokeWidth() {
		return mRCHelper.mStrokeWidth;
	}

	public int getStrokeColor() {
		return mRCHelper.mStrokeColor;
	}


	//--- Selector 支持 ----------------------------------------------------------------------------

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		mRCHelper.drawableStateChanged(this);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mRCHelper.onSizeChanged(this, w, h);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		canvas.saveLayer(mRCHelper.mLayer, null, Canvas.ALL_SAVE_FLAG);
		super.dispatchDraw(canvas);
		mRCHelper.onClipDraw(canvas);
		canvas.restore();
	}

	@Override
	public void draw(Canvas canvas) {
		if (mRCHelper.mClipBackground) {
			canvas.save();
			canvas.clipPath(mRCHelper.mClipPath);
			super.draw(canvas);
			canvas.restore();
		} else {
			super.draw(canvas);
		}
	}

	public interface HintViewDelegate{
        void setCurrentPosition(int position, HintView hintView);
        void initView(int length, int gravity, HintView hintView);
    }

    private HintViewDelegate mHintViewDelegate = new HintViewDelegate() {
        @Override
        public void setCurrentPosition(int position,HintView hintView) {
            if(hintView!=null)
                hintView.setCurrent(position);
        }

        @Override
        public void initView(int length, int gravity,HintView hintView) {
            if (hintView!=null){
         	   hintView.initView(length,gravity);
			}
        }
    };


	public RollPagerView(Context context){
		this(context,null);
	}

	public RollPagerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public RollPagerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(attrs);
		mRCHelper = new RCHelper();
		mRCHelper.initAttrs(context, attrs);
	}

	/**
	 * 读取提示形式  和   提示位置   和    播放延迟
	 * @param attrs
	 */
	private void initView(AttributeSet attrs){
		if(mViewPager!=null){
			removeView(mViewPager);
		}

		TypedArray type = getContext().obtainStyledAttributes(attrs, R.styleable.RollViewPager);
		gravity = type.getInteger(R.styleable.RollViewPager_rollviewpager_hint_gravity, 1);
		delay = type.getInt(R.styleable.RollViewPager_rollviewpager_play_delay, 0);
		color = type.getColor(R.styleable.RollViewPager_rollviewpager_hint_color, Color.BLACK);
		alpha = type.getInt(R.styleable.RollViewPager_rollviewpager_hint_alpha, 0);
		paddingLeft = (int) type.getDimension(R.styleable.RollViewPager_rollviewpager_hint_paddingLeft, 0);
		paddingRight = (int) type.getDimension(R.styleable.RollViewPager_rollviewpager_hint_paddingRight, 0);
		paddingTop = (int) type.getDimension(R.styleable.RollViewPager_rollviewpager_hint_paddingTop, 0);
		paddingBottom = (int) type.getDimension(R.styleable.RollViewPager_rollviewpager_hint_paddingBottom, DensityUtil.dip2px(getContext(),4));

		mViewPager = new ViewPager(getContext());
		mViewPager.setId(R.id.viewpager_inner);
		mViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(mViewPager);
		type.recycle();
//		initHint(new ColorPointHintView(getContext(),Color.parseColor("#E3AC42"),Color.parseColor("#88ffffff")));
        //手势处理
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (mOnItemClickListener!=null){
                    if (mAdapter instanceof LoopPagerAdapter){//原谅我写了这么丑的代码
                        mOnItemClickListener.onItemClick(mViewPager.getCurrentItem()%((LoopPagerAdapter) mAdapter).getRealCount());
                    }else {
                        mOnItemClickListener.onItemClick(mViewPager.getCurrentItem());
                    }
                }
                return super.onSingleTapUp(e);
            }
        });
	}

    private final static class TimeTaskHandler extends Handler{
        private WeakReference<RollPagerView> mRollPagerViewWeakReference;

        public TimeTaskHandler(RollPagerView rollPagerView) {
            this.mRollPagerViewWeakReference = new WeakReference<>(rollPagerView);
        }

        @Override
        public void handleMessage(Message msg) {
            RollPagerView rollPagerView = mRollPagerViewWeakReference.get();
            int cur = rollPagerView.getViewPager().getCurrentItem()+1;
            if(cur>=rollPagerView.mAdapter.getCount()){
                cur=0;
            }
            rollPagerView.getViewPager().setCurrentItem(cur);
            rollPagerView.mHintViewDelegate.setCurrentPosition(cur, (HintView) rollPagerView.mHintView);

			if (((LoopPagerAdapter)rollPagerView.mAdapter).getRealCount() <= 1){
				rollPagerView.removeHintView();
				rollPagerView.stopPlay();
			}
        }
    }
    private TimeTaskHandler mHandler = new TimeTaskHandler(this);

    private static class WeakTimerTask extends TimerTask{
        private WeakReference<RollPagerView> mRollPagerViewWeakReference;

        public WeakTimerTask(RollPagerView mRollPagerView) {
            this.mRollPagerViewWeakReference = new WeakReference<>(mRollPagerView);
        }

        @Override
        public void run() {
            RollPagerView rollPagerView = mRollPagerViewWeakReference.get();
            if (rollPagerView!=null){
                if(rollPagerView.isShown() && System.currentTimeMillis() - rollPagerView.mRecentTouchTime > rollPagerView.delay){
                    rollPagerView.mHandler.sendEmptyMessage(0);
                }
            }else{
                cancel();
            }
        }
    }

	/**
	 * 开始播放
	 * 仅当view正在显示 且 触摸等待时间过后 播放
	 */
	private void startPlay() {
		if (delay <= 0 || mAdapter == null || ((LoopPagerAdapter)mAdapter).getRealCount() <= 1) {
			return;
		}
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		//用一个timer定时设置当前项为下一项
		timer.schedule(new WeakTimerTask(this), delay, delay);
	}

    private void stopPlay(){
        if (timer!=null){
            timer.cancel();
            timer = null;
        }
    }

    public void setHintViewDelegate(HintViewDelegate delegate){
        this.mHintViewDelegate = delegate;
    }


	private void initHint(HintView hintview){
		if(mHintView!=null){
			removeView(mHintView);
		}
		if(hintview == null||!(hintview instanceof HintView)){
			return;
		}
		//如果只有一个页面，就不初始化hint
		if(((LoopPagerAdapter)mAdapter).getRealCount() > 1){
			mHintView = (View) hintview;
			loadHintView();
		}
	}

	/**
	 * 加载hintview的容器
	 */
	private void loadHintView(){
		addView(mHintView);
		mHintView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		((View) mHintView).setLayoutParams(lp);

		GradientDrawable gd = new GradientDrawable();
		gd.setColor(color);
		gd.setAlpha(alpha);
		mHintView.setBackgroundDrawable(gd);

        mHintViewDelegate.initView(mAdapter == null ? 0 : mAdapter.getCount(), gravity, (HintView) mHintView);
	}


	/**
	 * 设置viewager滑动动画持续时间
	 * @param during
	 */
	public void setAnimationDurtion(final int during){
		try {
			// viePager平移动画事件
			Field mField = ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);
			Scroller mScroller = new Scroller(getContext(),
					// 动画效果与ViewPager的一致
                    new Interpolator() {
                        public float getInterpolation(float t) {
                            t -= 1.0f;
                            return t * t * t * t * t + 1.0f;
                        }
                    }) {

                @Override
                public void startScroll(int startX, int startY, int dx,
                                        int dy, int duration) {
                    // 如果手工滚动,则加速滚动
                    if (System.currentTimeMillis() - mRecentTouchTime > delay) {
                        duration = during;
                    } else {
                        duration /= 2;
                    }
                    super.startScroll(startX, startY, dx, dy, duration);
                }

				@Override
				public void startScroll(int startX, int startY, int dx,
						int dy) {
					super.startScroll(startX, startY, dx, dy,during);
				}
			};
			mField.set(mViewPager, mScroller);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

    public void setPlayDelay(int delay){
        this.delay = delay;
        startPlay();
    }


    public void pause(){
        stopPlay();
    }

    public void resume(){
        startPlay();
    }

    public boolean isPlaying(){
        return timer!=null;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

	/**
	 * 设置提示view的位置
	 *
	 */
	public void setHintPadding(int left,int top,int right,int bottom){
		paddingLeft = left;
		paddingTop = top;
		paddingRight = right;
		paddingBottom = bottom;
		mHintView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
	}

	/**
	 * 设置提示view的透明度
	 * @param alpha 0为全透明  255为实心
	 */
	public void setHintAlpha(int alpha){
		this.alpha = alpha;
		initHint((HintView)mHintView);
	}

	/**
	 * 支持自定义hintview
	 * 只需new一个实现HintView的View传进来
	 * 会自动将你的view添加到本View里面。重新设置LayoutParams。
	 * @param hintview
	 */
	public void setHintView(HintView hintview){
		if (mHintView != null) {
			removeView(mHintView);
		}
		this.mHintView = (View) hintview;
		if (hintview!=null && hintview instanceof View){
			initHint(hintview);
		}
	}

	public void removeHintView(){
		if(mHintView != null){
			removeView(mHintView);
		}
	}

	/**
	 * 取真正的Viewpager
	 * @return
	 */
	public ViewPager getViewPager() {
		return mViewPager;
	}

	/**
	 * 设置Adapter
	 * @param adapter
	 */
	public void setAdapter(PagerAdapter adapter){
		adapter.registerDataSetObserver(new JPagerObserver());
		mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
		mAdapter = adapter;
		dataSetChanged();
    }

	/**
	 * 用来实现adapter的notifyDataSetChanged通知HintView变化
	 */
	private class JPagerObserver extends DataSetObserver {
		@Override
		public void onChanged() {
			dataSetChanged();
		}

		@Override
		public void onInvalidated() {
			dataSetChanged();
		}
	}

	private void dataSetChanged(){
		if(mHintView!=null) {
			mHintViewDelegate.initView(mAdapter.getCount(), gravity, (HintView) mHintView);
			mHintViewDelegate.setCurrentPosition(mViewPager.getCurrentItem(), (HintView) mHintView);
		}
        startPlay();
    }

	/**
	 * 为了实现触摸时和过后一定时间内不滑动,这里拦截
	 * @param ev
	 * @return
	 */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//		mRecentTouchTime = System.currentTimeMillis();
//        mGestureDetector.onTouchEvent(ev);
		int action = ev.getAction();
		if (action == MotionEvent.ACTION_DOWN && !mRCHelper.mAreaRegion.contains((int) ev.getX(), (int) ev.getY())) {
			return false;
		}
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP) {
			refreshDrawableState();
		} else if (action == MotionEvent.ACTION_CANCEL) {
			setPressed(false);
			refreshDrawableState();
		}
        return super.dispatchTouchEvent(ev);
    }

	/**
	 * 0：什么都没做
	 * 1：开始滑动
	 * 2：滑动结束
	 * @param arg0
	 */
    @Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0){
			case ViewPager.SCROLL_STATE_IDLE:
				//无动作、初始状态
				break;
			case ViewPager.SCROLL_STATE_DRAGGING:
				//点击、滑屏
				stopPlay();
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				//释放
				startPlay();
				break;
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int arg0) {
        mHintViewDelegate.setCurrentPosition(arg0, (HintView) mHintView);
	}

}
