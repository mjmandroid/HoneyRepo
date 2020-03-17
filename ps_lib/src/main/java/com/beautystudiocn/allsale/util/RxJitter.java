package com.beautystudiocn.allsale.util;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * <br> ClassName:   Jitter
 * <br> Description: 抖动事件处理
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:        2017/9/19 11:40
 */

public class RxJitter {
    private static Map<Object, RxJitter> mJitters = new HashMap<>();
    private Observer<? super Object> mObserver;
    private IEventObserver mEventObserver;
    private Disposable mDisposable;

    /**
     * <br> Description: 绑定事件源(在事件源发生期间，绑定key必须相同)
     * <br> Author:      wujianghua
     * <br> Date:        2017/9/19 12:31
     *
     * @param key key
     * @return Jitter
     */
    public static RxJitter bind(Object key) {
        return bind(key, 300, TimeUnit.MILLISECONDS);
    }

    /**
     * <br> Description: 绑定事件源(在事件源发生期间，绑定key必须相同)
     * <br> Author:      wujianghua
     * <br> Date:        2017/9/19 12:31
     *
     * @param key            key
     * @param windowDuration time to wait before emitting another item after emitting the last item
     * @param unit           the unit of time of {@code windowDuration}
     * @return Jitter
     */
    public static RxJitter bind(Object key, long windowDuration, TimeUnit unit) {
        if (!mJitters.containsKey(key)) {
            mJitters.put(key, new RxJitter(windowDuration, unit));
        }
        return mJitters.get(key);
    }

    /**
     * <br> Description: 构造函数
     * <br> Author:      wujianghua
     * <br> Date:        2017/9/19 12:33
     *
     * @param windowDuration time to wait before emitting another item after emitting the last item
     * @param unit           the unit of time of {@code windowDuration}
     */
    private RxJitter(long windowDuration, TimeUnit unit) {
        mDisposable = new Observable<Object>() {
            @Override
            protected void subscribeActual(Observer<? super Object> observer) {
                mObserver = observer;
            }
        }.throttleFirst(windowDuration, unit).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (mEventObserver != null) {
                    mEventObserver.onClickEvent(o);
                }
            }
        });
    }

    /**
     * <br> Description: 产生的事件
     * <br> Author:      wujianghua
     * <br> Date:        2017/9/19 12:33
     *
     * @param o 事件
     */
    public void bufferEvent(Object o) {
        mObserver.onNext(o);
    }

    /**
     * <br> Description: 解绑
     * <br> Author:      wujianghua
     * <br> Date:        2017/9/19 17:10
     */
    public static void unBind() {
        for (Map.Entry<Object, RxJitter> item : mJitters.entrySet()) {
            item.getValue().mDisposable.dispose();
            item.getValue().subscribe(null);
        }
        mJitters.clear();
    }

    /**
     * <br> ClassName:   IEventObserver
     * <br> Description: 事件监听器
     * <br>
     * <br> Author:      wujianghua
     * <br> Date:        2017/9/21 9:04
     */
    public interface IEventObserver {
        /**
         * <br> Description: 接受事件
         * <br> Author:      wujianghua
         * <br> Date:        2017/9/21 9:04
         *
         * @param o 事件
         */
        void onClickEvent(Object o);
    }

    /**
     * <br> Description: 设置事件监听器
     * <br> Author:      wujianghua
     * <br> Date:        2017/9/21 9:04
     *
     * @param mEventObserver IEventObserver
     * @return Jitter
     */
    public RxJitter subscribe(IEventObserver mEventObserver) {
        this.mEventObserver = mEventObserver;
        return this;
    }
}
