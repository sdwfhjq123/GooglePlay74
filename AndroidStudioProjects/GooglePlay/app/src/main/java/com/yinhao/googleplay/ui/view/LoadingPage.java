package com.yinhao.googleplay.ui.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.yinhao.googleplay.R;
import com.yinhao.googleplay.util.UIUtils;

/**
 * hahaha
 * Created by yinhao on 2017/7/22.
 * 根据当前状态来显示不同页面的自定义控件
 * <p>
 * 未加载---加载中---加载失败---数据为空---加载成功
 */

public abstract class LoadingPage extends FrameLayout {

    private static final int STATE_LOAD_UNDO = 1;//未加载
    private static final int STATE_LOAD_LOADING = 2;//加载中
    private static final int STATE_LOAD_ERROR = 3;//加载失败
    private static final int STATE_LOAD_EMPTY = 4;//数据为空
    private static final int STATE_LOAD_SUCCESS = 5;//加载成功

    private int mCurrentState = STATE_LOAD_UNDO;//当前状态的初始化
    private View mLoadingPage;
    private View mErrorPage;
    private View mEmptyPage;
    private View mSuccessPage;

    public LoadingPage(@NonNull Context context) {
        super(context);
        initView();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //初始化加载中的布局
        if (mLoadingPage == null) {
            mLoadingPage = UIUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);//将加载中的布局添加给帧布局
        }

        if (mErrorPage == null) {
            mErrorPage = UIUtils.inflate(R.layout.page_error);
            addView(mErrorPage);
        }


        if (mEmptyPage == null) {
            mEmptyPage = UIUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage);
        }

        showRightPage();
    }

    /**
     * 根据当前状态显示正确的page
     */
    private void showRightPage() {

        mLoadingPage.setVisibility((mCurrentState == STATE_LOAD_UNDO || mCurrentState == STATE_LOAD_LOADING) ?
                View.VISIBLE : View.GONE);
        mErrorPage.setVisibility(mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        mEmptyPage.setVisibility(mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);

        //当成功布局为空，并且当前状态为成功时，才初始化调用成功的布局
        if (mSuccessPage == null && mCurrentState == STATE_LOAD_SUCCESS) {
            mSuccessPage = onCreateSuccessView();
            if (mSuccessPage != null) {//防止添加一个空的view
                addView(mSuccessPage);
            }
        }

        if (mSuccessPage != null) {
            mSuccessPage.setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 开始加载数据
     */
    public void loadData() {

        if (mCurrentState != STATE_LOAD_LOADING) {//如果当前没有加载，就开始加载数据
            mCurrentState = STATE_LOAD_LOADING;
            new Thread() {
                @Override
                public void run() {
                    final ResultState resultState = onLoad();
                    //运行在主线程(更新ui)
                    UIUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState != null) {
                                mCurrentState = resultState.getState();//网络加载完后，更新网络状态
                                //根据最新状态来刷新页面
                                showRightPage();
                            }
                        }
                    });
                }
            }.start();
        }
    }

    /**
     * 加载数据网络，返回值表示请求网络结束后的状态
     *
     * @return
     */
    public abstract ResultState onLoad();

    /**
     * 加载成功后显示的布局，必须由调用者实现
     *
     * @return
     */
    public abstract View onCreateSuccessView();

    /**
     * 定义一个枚举
     */
    public enum ResultState {
        STATE_SUCCESS(STATE_LOAD_SUCCESS), STATE_ERROE(STATE_LOAD_ERROR), STATE_EMPTY(STATE_LOAD_EMPTY);

        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }

    }
}
