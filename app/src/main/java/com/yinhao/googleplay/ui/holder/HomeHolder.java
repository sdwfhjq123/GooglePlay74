package com.yinhao.googleplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.yinhao.googleplay.R;
import com.yinhao.googleplay.util.UIUtils;

/**
 * Created by yinhao on 2017/8/24.
 * 首页Holder
 */

public class HomeHolder extends BaseHolder<String> {

    private TextView tvContent;

    @Override
    public View initView() {
        //加载布局
        View view = UIUtils.inflate(R.layout.list_item_home);
        //初始化控件
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        return view;
    }

    @Override
    public void refreshView(String data) {
        tvContent.setText(data);
    }
}
