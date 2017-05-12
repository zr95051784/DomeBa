package com.zr.domebar.ui.fragment;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zr.domebar.R;
import com.zr.domebar.adapter.JokeAdapter;
import com.zr.domebar.bean.ResultJoke;
import com.zr.domebar.bean.ResultJoke.ResultBean.Joke;
import com.zr.domebar.common.Common;
import com.zr.domebar.common.Constant;
import com.zr.domebar.common.ServerConfig;
import com.zr.domebar.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by S01 on 2017/5/6.
 */

public class JokeFragment extends Fragment {
    public static final int TYPE_REFRESH = 0x01;
    public static final int TYPE_LOADMORE = 0x02;
    @BindView(R.id.prf_listView)
    PullToRefreshListView prfListView;
    private View rootView;
    private Unbinder unbinder;

    private int page = 1;
    private List<Joke> data = new ArrayList<>();
    private JokeAdapter jokeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_listview, null);
        unbinder = ButterKnife.bind(this, rootView);


        initView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        prfListView.setMode(PullToRefreshBase.Mode.BOTH);
        jokeAdapter = new JokeAdapter(data);
        prfListView.setAdapter(jokeAdapter);
        prfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(), data.get(position - 1).getContent(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), ((Joke) jokeAdapter.getItem(position - 1)).getContent(), Toast.LENGTH_SHORT).show();
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

    private void initData() {
        page = 1;
        getAsyncData(page, TYPE_REFRESH);

    }

    //获取异步请求的数据
    private void getAsyncData(int page, final int type) {
        //请求数据
        OkHttpUtils
                .get()
                .url(ServerConfig.JUHE_JOKE_URL)
                .addParams("sort", "desc")
                .addParams("page", String.valueOf(page))
                .addParams("pagesize", Constant.PAGE_SIZE)
                .addParams("time", TimeUtils.getTime())
                .addParams("key", Common.HUHE_JOKE_KEY)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                        prfListView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        prfListView.onRefreshComplete();
                        //解析数据
                        ResultJoke resultJoke = JSON.parseObject(response, ResultJoke.class);
                        switch (type) {
                            case TYPE_REFRESH:
                                jokeAdapter.setNewData(resultJoke.getResult().getData());
                                break;
                            case TYPE_LOADMORE:
                                jokeAdapter.setMoreData(resultJoke.getResult().getData());
                                break;
                        }
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
