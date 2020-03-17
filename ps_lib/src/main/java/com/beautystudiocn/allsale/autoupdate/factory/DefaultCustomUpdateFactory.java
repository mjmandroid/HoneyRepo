package com.beautystudiocn.allsale.autoupdate.factory;

import android.content.Context;

import com.beautystudiocn.allsale.autoupdate.product.UpdateBuilder;
import com.beautystudiocn.allsale.autoupdate.product.custom.CustomUpdateProduct;
import com.beautystudiocn.allsale.autoupdate.product.UpdateBuilder;
import com.beautystudiocn.allsale.autoupdate.product.custom.CustomUpdateProduct;
import com.beautystudiocn.allsale.autoupdate.product.custom.ICustomUpdate;


/**
 * <br> ClassName:   DefaultCustomUpdateFactory
 * <br> Description: 默认自动更新工厂类
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:46
 */
public class DefaultCustomUpdateFactory implements IUpdateFactory {
    private final Context mContext;

    /**
     *<br> Description: 初始化
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/6/8 17:47
     * @param context
     *                  上下文
     */
    public DefaultCustomUpdateFactory(Context context) {
        mContext = context;
    }

    @Override
    public UpdateBuilder<ICustomUpdate> createUpdateProduct() {
        return new UpdateBuilder(new CustomUpdateProduct(mContext));
    }
}
