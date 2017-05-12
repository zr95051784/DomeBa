package com.zr.domebar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zr.domebar.R;
import com.zr.domebar.bean.ResultJoke.ResultBean.Joke;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S01 on 2017/5/6.
 */

public class JokeAdapter extends BaseAdapter {
    private List<Joke> data = new ArrayList<>();
    private ViewHolder holder;

    public JokeAdapter(List<Joke> data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView ==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joke,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_joke.setText(data.get(position).getContent());
        return convertView;
    }
    class ViewHolder{
        TextView tv_joke;

        public  ViewHolder(View view) {
            tv_joke = (TextView) view.findViewById(R.id.tv_joke);
        }
    }
    //下拉刷新
    public void setNewData(List<Joke> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
    //加载更多
    public void setMoreData(List<Joke> newData){

        data.addAll(newData);
        notifyDataSetChanged();
    }
}
