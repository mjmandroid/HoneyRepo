package com.streaming.better.honey.wedget.autoupdate.factory;


import com.streaming.better.honey.wedget.autoupdate.product.UpdateBuilder;

/**
 * <br> ClassName:   IUpdateFactory
 * <br> Description: 工厂构建接口
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:48
 */
public interface IUpdateFactory {

  /**
   *<br> Description: 创建自动更新产品类
   *<br> Author:      yexiaochuan
   *<br> Date:        2017/6/8 17:48
   * @return
   *                  自动更新产品类配置构建类
   */
  UpdateBuilder createUpdateProduct();
}
