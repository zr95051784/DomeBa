package com.zr.domebar.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zr.domebar.R;
import com.zr.domebar.adapter.JokeAdapter;
import com.zr.domebar.adapter.NewsAdapter;
import com.zr.domebar.bean.News;
import com.zr.domebar.bean.ResultJoke;
import com.zr.domebar.common.Common;
import com.zr.domebar.common.ServerConfig;
import com.zr.domebar.ui.activity.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by S01 on 2017/5/6.
 */

public class NewsFragment extends Fragment {
    public static final int TYPE_REFRESH = 0x01;
    public static final int TYPE_LOADMORE = 0x02;

    @BindView(R.id.prf_listView)
    PullToRefreshListView prfListView;
    private View rootView;
    private Unbinder unbinder;
    private int page = 1;
    private List<News> data = new ArrayList<>();
    private NewsAdapter newsAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_listview, null);
        unbinder = ButterKnife.bind(this, rootView);

        initData();
        initView();
        return rootView;
    }

    private void initView() {
        newsAdapter = new NewsAdapter(data);
        prfListView.setAdapter(newsAdapter);
        prfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("news",data.get(i));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        prfListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                //下载刷新
                getAsyncData(1,TYPE_REFRESH);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                //加载更多
                getAsyncData(page++,TYPE_LOADMORE);
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
    private void initData() {
        page = 1;
        getAsyncData(page, TYPE_REFRESH);

    }

    //获取异步请求的数据
    private void getAsyncData(int page, final int type) {
        //请求数据
        OkHttpUtils.post()
                .url(ServerConfig.BASE_URL)
                .addParams("key", Common.API_NEWS_KEY)
                .addParams("type","top")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(),"数据请求失败",Toast.LENGTH_SHORT).show();
                        prfListView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        prfListView.onRefreshComplete();
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
                        data.addAll(JSONArray.parseArray(jsonArray.toJSONString(),News.class));
                        Log.i("NEWS","数据大小"+data.size());
                        newsAdapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
