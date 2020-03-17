package com.beautystudiocn.rxnetworklib.network.callback;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by wujianghua on 2017/12/29.
 */

public interface IRequest<T> {

    T url(String url);

    T setReadTimeout(long readTimeOut);

    T setWriteTimeOut(long writeTimeOut);

    T setConnectTimeout(long connectTimeout);

    T setCallback(AbstractCallback callback);

    T setRequestManager(IRequestManager iRequestManager);

    T setReloadAction(ISetReloadAction iSetReloadAction);

    T addParams(Map<String, String> params);

    T addParams2(Map<String, Object> params);

    T addFiles(Map<String, List<String>> fileMap);

    T setIsFormRequest(boolean isFrom);

    void build();
}
