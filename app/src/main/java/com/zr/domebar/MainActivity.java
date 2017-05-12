package com.zr.domebar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zr.domebar.ui.fragment.JokeFragment;
import com.zr.domebar.ui.fragment.NewsFragment;
import com.zr.domebar.ui.fragment.PersonFragment;
import com.zr.domebar.ui.fragment.PicFragment;
import com.zr.domebar.ui.widget.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation;
    @BindView(R.id.container)
    LinearLayout container;
    private NewsFragment newsFragment;
    private JokeFragment jokeFragment;
    private PicFragment picFragment;
    private PersonFragment personFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        //
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        //
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        newsFragment = new NewsFragment();
        fragmentTransaction.replace(R.id.content,newsFragment);
        setTitle(R.string.news);
        fragmentTransaction.commit();
    }

    private BottomNavigationViewEx.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationViewEx.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bnv_news:
                    newsFragment = new NewsFragment();
                    setTitle(R.string.news);
                    replace(newsFragment);
                    return true;
                case R.id.bnv_joke:
                    jokeFragment = new JokeFragment();
                    setTitle(R.string.joke);
                    replace(jokeFragment);
                    return true;
                case R.id.bnv_pic:
                    picFragment = new PicFragment();
                    setTitle(R.string.pic);
                    replace(picFragment);
                    return true;
                case R.id.bnv_person:
                    personFragment = new PersonFragment();
                    setTitle(R.string.person);
                    replace(personFragment);
                    return true;
            }
            return false;
        }

    };
    private void replace(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content,fragment);
        fragmentTransaction.commit();

    }

}
