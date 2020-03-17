package com.beautystudiocn.allsale.widget.guideview;

/**
 * <br> Description: 引导图状态
 * <br> Author:      KevinWu
 * <br> Date:        2018/4/23 11:33
 */
public interface IPSGuideShowState {
    void show();

    void hide();

    void destory();

    /**
     * <br> Description:刷新界面
     * <br> Author:      KevinWu
     * <br> Date:        2018/4/28 11:40
     */
    void refreshView();

}
