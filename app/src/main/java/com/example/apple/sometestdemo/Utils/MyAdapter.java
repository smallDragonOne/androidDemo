package com.example.apple.sometestdemo.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by apple on 16/6/24.
 * 自定义适配器
 */
public abstract class MyAdapter<T> extends BaseAdapter {

    /// 数据
    protected List<T> mDatas;

    /// 上下文
    private Context context;

    /// 自定义布局
    protected int mLayoutId;
    public MyAdapter(Context context,List<T> beans , int mlayoutId ){
        this.mDatas = beans;
        this.mLayoutId = mlayoutId;
        this.context = context;
    }

    public MyAdapter(){

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context,convertView,parent,mLayoutId,position);
        convertToView(viewHolder,getItem(position));
        return viewHolder.getConverView();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     * @param holder 单元格内容
     * @param bean   数据源
     */
    protected abstract void convertToView(ViewHolder holder,T bean);

}
