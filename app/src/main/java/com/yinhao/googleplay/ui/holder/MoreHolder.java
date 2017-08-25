package com.yinhao.googleplay.ui.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yinhao.googleplay.R;
import com.yinhao.googleplay.util.UIUtils;

/**
 * Created by yinhao on 2017/8/24.
 */

public class MoreHolder extends BaseHolder<Integer> {

    /**
     * 加载更多的几种状态
     * 1，可以加载更多
     * 2，加载更多失败
     * 3，没有更多数据
     */
    public static final int STATE_MORE_MORE = 1;
    public static final int STATE_MORE_ERROR = 2;
    public static final int STATE_MORE_NONE = 3;
    private LinearLayout llLoadMore;
    private TextView tvLoadError;

    /**
     * 如果有更多数据，状态为more_more，将此状态传递给父类，父类会同时刷新数据
     *
     * @param hasMore
     */
    public MoreHolder(boolean hasMore) {
        setData(hasMore ? STATE_MORE_MORE : STATE_MORE_NONE);
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
        llLoadMore = (LinearLayout) view.findViewById(R.id.ll_load_more);
        tvLoadError = (TextView) view.findViewById(R.id.tv_load_error);
        return view;
    }

    @Override
    public void refreshView(Integer data) {
        switch (data) {
            case STATE_MORE_MORE://加载更多
                llLoadMore.setVisibility(View.VISIBLE);
                tvLoadError.setVisibility(View.GONE);
                break;
            case STATE_MORE_ERROR://加载失败
                llLoadMore.setVisibility(View.GONE);
                tvLoadError.setVisibility(View.GONE);
                break;
            case STATE_MORE_NONE://加载为空
                llLoadMore.setVisibility(View.GONE);
                tvLoadError.setVisibility(View.VISIBLE);
                break;
        }
    }
}
