package com.yinhao.googleplay.ui.holder;

import android.view.View;

/**
 * Created by yinhao on 2017/8/24.
 */

public abstract class BaseHolder<T> {

    private View mRootView;
    private T data;

    public BaseHolder() {
        mRootView = initView();
        //3，打tag标记
        mRootView.setTag(this);
    }

    //1，加载布局文件
    //2，初始化控件，findViewById
    public abstract View initView();

    //返回item布局对象
    public View getRootView() {
        return mRootView;
    }

    //设置当前item的数据
    public void setData(T data) {
        this.data = data;
        refreshView(data);
    }

    //获取当前item的数据
    public T getData() {
        return data;
    }

    //4，根据数据来刷新界面
    public abstract void refreshView(T data);
}
