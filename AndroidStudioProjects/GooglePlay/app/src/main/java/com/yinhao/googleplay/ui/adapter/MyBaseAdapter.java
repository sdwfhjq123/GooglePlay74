package com.yinhao.googleplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by yinhao on 2017/7/23.
 */

public class MyBaseAdapter<T> extends BaseAdapter {

    private ArrayList<T> data;

    public MyBaseAdapter(ArrayList<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
