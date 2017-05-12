package com.zr.domebar.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zr.domebar.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by S01 on 2017/5/6.
 */

public class PersonFragment extends Fragment {
    private View rootView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_listview,null);
        unbinder = ButterKnife.bind(this,rootView);

        initData();
        initView();
        return rootView;
    }

    private void initView() {

    }

    private void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
