package com.zr.domebar.adapter;

import android.graphics.Picture;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zr.domebar.R;

import com.zr.domebar.bean.Picture.ShowapiResBodyBean.PagebeanBean.Pic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S01 on 2017/5/8.
 */

public class PicAdapter extends BaseAdapter{
    private List<Pic> data = new ArrayList<>();
    private ViewHolder holder;
    public PicAdapter(List<Pic> data){
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(parent.getContext())
                .load(data.get(position).getList().get(0).getBig())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.img_pic);
       holder.tv_title.setText(data.get(position).getTitle());

        return convertView;
    }
    class ViewHolder{
        ImageView img_pic;
        TextView tv_title;

        public ViewHolder(View view) {
            img_pic = (ImageView) view.findViewById(R.id.img_news);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
    //下拉刷新
    public void setNewData(List<Pic> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
    //加载更多
    public void setMoreData(List<Pic> newData){

        data.addAll(newData);
        notifyDataSetChanged();
    }
}
