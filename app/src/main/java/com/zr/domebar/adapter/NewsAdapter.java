package com.zr.domebar.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zr.domebar.R;
import com.zr.domebar.bean.News;
import com.zr.domebar.bean.ResultJoke;

import java.util.List;


/**
 * Created by S01 on 2017/4/28.
 */

public class NewsAdapter extends BaseAdapter {
    private List<News> data;
    private ViewHolder holder;

    public NewsAdapter(List<News> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        Log.i("adapter","数据大小"+data.size());
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.i("adapter",data.get(i).getTitle());
        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Glide.with(viewGroup.getContext())
                .load(data.get(i).getThumbnail_pic_s())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.img_news);
        holder.tv_title.setText(data.get(i).getTitle());
        return view;
    }
    class ViewHolder{
        ImageView img_news;
        TextView tv_title;

        public ViewHolder(View view) {
            img_news = (ImageView) view.findViewById(R.id.img_news);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
    //下拉刷新
    public void setNewData(List<News> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
    //加载更多
    public void setMoreData(List<News> newData){

        data.addAll(newData);
        notifyDataSetChanged();
    }
}
