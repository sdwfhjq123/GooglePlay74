package com.yinhao.googleplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.yinhao.googleplay.ui.holder.BaseHolder;
import com.yinhao.googleplay.ui.holder.MoreHolder;
import com.yinhao.googleplay.util.UIUtils;

import java.util.ArrayList;

/**
 * Created by yinhao on 2017/7/23.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_MORE = 1;

    private ArrayList<T> data;

    public MyBaseAdapter(ArrayList<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size() + 1;//增加加载更多的布局的数量
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回布局类型的个数
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //返回是当前位置需要展示的布局类型
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {//最后一个
            return TYPE_MORE;
        } else {
            return getInnerType();
        }
    }

    //返回具体的类型
    public int getInnerType() {
        return TYPE_NORMAL;//默认就是普通，（分类的子类可以重写此方法添加别的类型）
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        if (convertView == null) {
            //1，加载布局文件
            //2，初始化控件，findViewById
            //3，打tag标记

            //判断是否是加载更多类型
            if (getItemViewType(position) == TYPE_MORE) {
                //加载更多
                holder = new MoreHolder(hasMore());
            } else {
                holder = getHolder();//子类返回具体的对象
            }

        } else {
            holder = (BaseHolder) convertView.getTag();
        }

        //4，根据数据来刷新界面
        if (getItemViewType(position) != TYPE_MORE) {
            holder.setData(getItem(position));
        } else {
            //加载更多的布局
            //一旦加载布局显示出来，就开始加载更多
            MoreHolder moreHolder = (MoreHolder) holder;
            //只有在更多数据的状态下，才加载更多数据
            if (moreHolder.getData() == MoreHolder.STATE_MORE_MORE) {
                loadMore(moreHolder);
            }
        }
        return holder.getRootView();
    }

    /**
     * 子类重写此方法来判断是否加载更多
     *
     * @return
     */
    public boolean hasMore() {
        return true;//默认都是有更多数据的
    }

    private boolean isLoadMore = false;//标记是否加载更多

    /**
     * 加载更多的数据
     */
    public void loadMore(final MoreHolder holder) {
        if (!isLoadMore) {
            isLoadMore = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final ArrayList<T> moreData = onLoadMore();
                    UIUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null) {
                                //每一页有20条数据,如果<20,就认为到最后一页了
                                if (moreData.size() < 20) {
                                    holder.setData(MoreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                                } else {
                                    //加载更多的数据
                                    holder.setData(MoreHolder.STATE_MORE_MORE);
                                }
                                //将更多的数据追加到集合中
                                data.addAll(moreData);
                                //刷新界面
                                MyBaseAdapter.this.notifyDataSetChanged();
                            } else {
                                //加载失败的布局
                                holder.setData(MoreHolder.STATE_MORE_ERROR);
                            }
                            isLoadMore = false;
                        }
                    });
                }
            }).start();
        }
    }

    /**
     * 返回当前页面的Holder对象，必须子类实现
     *
     * @return
     */
    public abstract BaseHolder<T> getHolder();

    /**
     * 加载更多数据，必须由子类实现
     */
    public abstract ArrayList<T> onLoadMore();
}
