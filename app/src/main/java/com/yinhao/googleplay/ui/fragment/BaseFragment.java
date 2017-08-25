package com.yinhao.googleplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yinhao.googleplay.ui.view.LoadingPage;
import com.yinhao.googleplay.util.UIUtils;

/**
 * Created by yinhao on 2017/7/22.
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment {

    private LoadingPage mLoadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TextView textView = new TextView(UIUtils.getContext());
//        textView.setText(getClass().getSimpleName());
        mLoadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }

            @Override
            public View onCreateSuccessView() {
                //防止因为名字相同而栈溢出
                return BaseFragment.this.onCreateSuccessView();
            }
        };
        return mLoadingPage;
    }

    /**
     * 加载数据
     */
    public void loadData() {
        if (mLoadingPage != null) {
            mLoadingPage.loadData();
        }
    }

    /**
     * 加载成功的布局，必须由子类来实现
     *
     * @return
     */
    public abstract View onCreateSuccessView();

    /**
     * 加载网络数据，必须由子类实现
     *
     * @return
     */
    public abstract LoadingPage.ResultState onLoad();
}
