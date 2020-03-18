 
 [参考地址](http://note.youdao.com/noteshare?id=7f67ce6cef901a45626021f9772508be&sub=F920FE136C234440B33C409FDDEB1A32)
 
 
## Android 沉浸式状态栏的封装

##### 注意：
> 1) 此工具类，仅支持4.4以上的版本状态栏颜色的修改
> 2) 修改状态栏的字体颜色支持miui,flyme和android原生版本6.0以上

#### 关键方法

```java        
/**
 * <br> Description:    设置系统状态栏的颜色
 * <br> Author:      wuheng
 * <br> Date:        2017/9/1 16:55
 *
 * @param activity
 * @param color     颜色
 * @param dark      是否将状态栏颜色置灰
 */
public static void setStatusBarColor(Activity activity, int color, boolean dark)

```

#### 使用场景
一般情况下，是将沉浸栏的设置封装在父Activity中，便于子类的根据具体情况，是否修改相应的颜色。
#### 设计思路
强制的将rootView调整到屏幕大小，然后再针对以下几种状态对界面的显示高度进行调整：
- 系统版本低于4.4时，不处理;
- 系统是否包含虚拟导航键;
- 当前导航键是否显示;
- 当前界面是否有显示键盘;
#### 效果图如下
1. 状态栏背景设置为蓝色，字体默认白色

![](http://note.youdao.com/yws/public/resource/08c7900fdd826ac279b99751c73126aa/xmlnote/DB70C687BE32403984E0BB058B80C3E8/7673)

2. 状态栏背景设置为红色，字体颜色设为灰色

![](http://note.youdao.com/yws/public/resource/08c7900fdd826ac279b99751c73126aa/xmlnote/5E50D99896604DCFA9673F97E928DF94/7674)

###### 具体封装代码如下：
```java
/**
 * <br> Description:
 * <br> Author:      wuheng
 * <br> Date:        2017/9/4 10:27
 *
 * @param color Color值,如Color.WHITE
 * @param dark  字体颜色是否设置为灰色  true 为设置灰， false 为白
 */
public void setStatusBarColor(int color, boolean dark) {
    SystemBarTool.setStatusBarColor(this, color, dark);
}
/**
 * <br> Description:
 * <br> Author:      wuheng
 * <br> Date:        2017/9/4 10:28
 *
 * @param colorHex 16进制的颜色 如"#99ffffff",为白色，前两位是透明度，后6位颜色值
 * @param dark     字体颜色是否设置为灰色  true 为设置灰， false 为白
 */
public void setStatusBarColor(String colorHex, boolean dark) {
    setStatusBarColor(Color.parseColor(colorHex), dark);
}
/**
 * <br> Description: 在super.onCreate调用
 * <br> Author:      wuheng
 * <br> Date:        2017/9/4 11:58
 */
public void setFitWindow(boolean fitWindow) {
    this.fitWindow = fitWindow;
}

public boolean isFitWindow() {
    return fitWindow;
}

/**
 * <br> Description: 监视处理 navigation 高度事件
 * <br> Author:      wuheng
 * <br> Date:        2017/9/4 9:25
 */
private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = () -> {
    if (NavigationBarTool.hasNavigationBar(BaseActivity.this)) {
        changeRootViewFitWindow();
    } else {
        //不存在导航键的情况
        Log.e("zll", "不存在 虚拟导航键");
    }
};


/**
 * <br> Description: 调整rootView,以适应屏幕大小
 * <br> Author:      wuheng
 * <br> Date:        2017/9/4 14:01
 */
protected void changeRootViewFitWindow() {
    int navigationHeight = NavigationBarTool.checkDeviceHasNavigationBar(getWindowManager()) ?
            NavigationBarTool.getNavigationBarHeight(BaseActivity.this)
            : 0;
//        Log.e("zll", "changeRootViewFitWindow   :   " + navigationHeight);
    FrameLayout rootView = (FrameLayout) getMainLayout().getParent();
    rootView.setPadding(rootView.getPaddingLeft(),
            isFitWindow() ? 0 : NavigationBarTool.getStatusBarHeight(BaseActivity.this),
            rootView.getPaddingRight(),
            KeyBroadUtil.isKeyBroadShow() ? KeyBroadUtil.getKeyBroadKeyHeight() : navigationHeight);
}

/***监控键盘的状态***/
private KeyBroadUtil.IKeyBoardVisibleListener keyBoardVisibleListener = (boolean visible, int keyboardHeight) -> {

    int navigationHeight = NavigationBarTool.getNavigationBarHeight(BaseActivity.this);
    FrameLayout rootView = (FrameLayout) getMainLayout().getParent();
    rootView.setPadding(rootView.getPaddingLeft(),
            rootView.getPaddingTop(),
            rootView.getPaddingRight(),
            visible ? keyboardHeight : navigationHeight);
    onSoftKeyBoardVisible(visible, keyboardHeight);
};


@Override
public void onSoftKeyBoardVisible(boolean visible, int keyboardHeight) {

}
```
##### 监视键盘的工具类

```java

package widget.kevin.com.samplewidget.systembar.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * <br> ClassName:   KeyBroadUtil
 * <br> Description: 监视键盘的高度
 * <br>
 * <br> Author:      wuheng
 * <br> Date:        2017/8/7 14:23
 */

public class KeyBroadUtil {

    public interface IKeyBoardVisibleListener {
        void onSoftKeyBoardVisible(boolean visible, int keyboardHeight);
    }

    private static boolean isVisiableForLast = false;
    private static int keyBroadKeyHeight = 0;
    /***监控键盘的高度***/
    private static IKeyBoardVisibleListener keyBoardVisibleListener = null;

    public static boolean isKeyBroadShow() {
        return isVisiableForLast;
    }

    /**
     * <br> Description: 返回键盘的高度
     * <br> Author:      wuheng
     * <br> Date:        2017/9/4 14:37
     */
    public static int getKeyBroadKeyHeight() {
        return keyBroadKeyHeight;
    }

    /**
     * <br> Description: todo(监视键盘的高度变化)
     * <br> Author:      wuheng
     * <br> Date:        2017/8/4 16:22
     *
     * @param activity activity
     * @param listener 事件
     */
    public static void addSoftKeyBoardVisibleListener(Activity activity, final IKeyBoardVisibleListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        keyBoardVisibleListener = listener;
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕的高度
                int displayHeight = rect.bottom;//- rect.top;  // 顶部状态栏的高度
                //获得屏幕整体的高度
                int height = decorView.getHeight();
                //获得键盘高度
                keyBroadKeyHeight = height - displayHeight;
                boolean visible = (double) displayHeight / height < 0.8;
                if (visible != isVisiableForLast) {
                    listener.onSoftKeyBoardVisible(visible, keyBroadKeyHeight);
                }
                isVisiableForLast = visible;
            }
        });
    }

    /**
     * <br> Description: todo(解绑)
     * <br> Author:      wuheng
     * <br> Date:        2017/8/7 14:25
     */
    public static void removeSofeKeyBoardVisibleListener() {
        keyBoardVisibleListener = null;
    }
}

```
