package com.yinhao.googleplay.ui.fragment;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yinhao.googleplay.R;
import com.yinhao.googleplay.ui.adapter.MyBaseAdapter;
import com.yinhao.googleplay.ui.holder.BaseHolder;
import com.yinhao.googleplay.ui.holder.HomeHolder;
import com.yinhao.googleplay.ui.view.LoadingPage;
import com.yinhao.googleplay.util.UIUtils;

import java.util.ArrayList;

/**
 * Created by yinhao on 2017/7/22.
 * 主页
 */

public class HomeFragment extends BaseFragment {

    private ArrayList<String> data;

    /**
     * 运行在主线程，数据加载成功会回调此方法
     *
     * @return
     */
    @Override
    public View onCreateSuccessView() {
//        TextView textView = new TextView(UIUtils.getContext());
//        textView.setText("加载成功");
//        textView.setTextColor(Color.BLACK);
        ListView listView = new ListView(UIUtils.getContext());
        listView.setAdapter(new HomeAdapter(data));
        return listView;
    }

    /**
     * 运行在子线程，可以直接执行网络耗时操作
     *
     * @return
     */
    @Override
    public LoadingPage.ResultState onLoad() {
        //请求网络
        data = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            data.add("测试数据" + i);
        }

        return LoadingPage.ResultState.STATE_SUCCESS;
    }

    class HomeAdapter extends MyBaseAdapter<String> {


        public HomeAdapter(ArrayList<String> data) {
            super(data);
        }

        @Override
        public BaseHolder<String> getHolder() {
            return new HomeHolder();
        }

        @Override
        public ArrayList<String> onLoadMore() {
            ArrayList<String> moreData = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                moreData.add("测试更多数据:" + i);
            }
            SystemClock.sleep(2000);
            return moreData;
        }
    }

}
