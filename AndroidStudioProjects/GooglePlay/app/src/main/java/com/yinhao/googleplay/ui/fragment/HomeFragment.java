package com.yinhao.googleplay.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yinhao.googleplay.R;
import com.yinhao.googleplay.ui.adapter.MyBaseAdapter;
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
        for (int i = 0; i < 20; i++) {
            data.add("测试数据" + i);
        }

        return LoadingPage.ResultState.STATE_SUCCESS;
    }

    class HomeAdapter extends MyBaseAdapter<String> {


        public HomeAdapter(ArrayList<String> data) {
            super(data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                //1，加载布局文件
                convertView = UIUtils.inflate(R.layout.list_item_home);
                holder = new ViewHolder();
                //2，findViewById
                holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                //3，打标记
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String content = getItem(position);
            holder.tvContent.setText(content);
            return convertView;
        }
    }

    class ViewHolder {
        public TextView tvContent;
    }
}
