package com.example.apple.sometestdemo.Utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.PolicySpi;

/**
 * Created by apple on 16/6/24.
 */
public class ViewHolder {

    /// 存放界面控件
    private SparseArray<View> mviews;
    /// 单元格位置
    private  int mposition;
    /// 复用单元格
    private  View mConverView;


    /**
     *
     * @param context  上下文
     * @param parent   父类布局
     * @param layoutId 单元格布局
     * @param mposition 当前位置
     */
    public  ViewHolder(Context context, ViewGroup parent , int layoutId , int mposition){
        this.mposition = mposition;
        this.mviews = new SparseArray<>();
        this.mConverView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConverView.setTag(this);
    }

    /**
     * 获取ViewHolder实例
     * @param context
     * @param convertView 复用单元格
     * @param parent      父类布局
     * @param layoutId    自定义单元格显示
     * @param position    位置
     * @return
     */
    public static ViewHolder get(Context context , View convertView , ViewGroup parent ,int layoutId , int position){
        if (convertView == null){
            return new ViewHolder(context , parent, layoutId,position);
        }
        else {
            ViewHolder holder = (ViewHolder)convertView.getTag();
            holder.mposition = position;
            return holder;
        }
    }

    /**
     * 存储控件
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mviews.get(viewId);
        if (view == null){
            view = mConverView.findViewById(viewId);
            mviews.put(viewId , view);
        }
        return (T)view;
    }


    /**
     *
     * @return 获取位置
     */
    public int getMposition(){
        return mposition;
    }

    /**
     * 设置文字
     * @param viewId
     * @param string
     * @return
     */
    public ViewHolder setText(int viewId , String string){
        TextView tv = getView(viewId);
        tv.setText(string);
        return this;
    }

    /**
     * 设置图片
     * @param viewId
     * @param resource
     * @return
     */
    public ViewHolder setImage(int viewId , int resource){
        ImageView img = getView(viewId);
        img.setImageResource(resource);
        return this;
    }

    /**
     * 添加事件
     * @param viewId
     * @param listener
     * @return
     */
    public ViewHolder setClick(int viewId , final View.OnClickListener listener){
        View view  = getView(viewId);
        view.setOnClickListener(listener);
        return this;

    }

    /**
     * 添加网络图片
     * @param viewId
     * @param imgUrl
     * @return
     */
    public ViewHolder setNetImage(int viewId , String imgUrl){
        ImageView img = getView(viewId);
        /// 加载网络图片
        return this;
    }


    /**
     * 获取复用单元格
     * @return
     */
    public View getConverView(){
        return mConverView;
    }
}
