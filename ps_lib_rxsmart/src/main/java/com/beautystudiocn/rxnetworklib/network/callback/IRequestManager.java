package com.beautystudiocn.rxnetworklib.network.callback;
/**
 *<br> Description: 网络请求管理
 *<br> Author:      wujianghua
 *<br> Date:        2018/10/23 11:56
 */
public interface IRequestManager<T> {

    void addCalls(T d);

    void removeAllCalls();

    void removeCall(T d);

}
