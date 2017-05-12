package com.zr.domebar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.zr.domebar.common.Common;
import com.zr.domebar.ui.activity.RegisterAcitivty;

import java.util.HashMap;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;


/**
 * Created by S01 on 2017/5/9.
 */

public class LoginActivty extends AppCompatActivity implements PlatformActionListener {
    private Intent intent;
    private ImageView login_photo;
    private TextView tv_register,tv_forgetpassword;
    private Button btn_login;
    private EditText login_name,login_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Bmob.initialize(this, Common.BMOB_ID);
        findView();

    }

    private void findView() {
        login_photo = (ImageView) findViewById(R.id.login_photo);
        login_name = (EditText) findViewById(R.id.login_name);
        login_password = (EditText) findViewById(R.id.login_password);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_forgetpassword = (TextView) findViewById(R.id.tv_forgetpassword);

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Login();
                break;
            case R.id.tv_register:
                Register();
                break;
            case R.id.ibtn_sina:
                loginBysina();
                break;
            case R.id.ibtn_wechat:
                loginByWechat();
                break;
            case R.id.ibtn_qq:
                loginByQQ();
                break;
        }
    }


    private void Register() {
        intent = new Intent(LoginActivty.this, RegisterAcitivty.class);
        startActivity(intent);
    }

    private void Login() {
        String name = login_name.getText().toString();
        String pwd = login_password.getText().toString();
        BmobUser user = new BmobUser();
        user.setUsername(name);
        user.setPassword(pwd);
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                intent = new Intent(LoginActivty.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivty.this,"登录成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(LoginActivty.this,"账户密码错误",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loginByQQ() {
        //获取授权平台的对象
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        //设置授权监听器
        qq.setPlatformActionListener(this);
        //authhorize与showUser单独调用一个即可
        qq.authorize();//单独授权，OnComplete返回的hashmap是空的
         qq.showUser(null);//授权并获取用户信息
        //移除授权
        // qq.removeAccount(true);
    }

    private void loginByWechat() {
        //获取授权平台的对象
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        //设置授权监听器
        wechat.setPlatformActionListener(this);
        //authhorize与showUser单独调用一个即可
        wechat.authorize();//单独授权，OnComplete返回的hashmap是空的
        // wechat.showUser(null);//授权并获取用户信息
        //移除授权
        // wechat.removeAccount(true);
    }

    private void loginBysina() {
        //获取授权平台的对象
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        //设置授权监听器
        weibo.setPlatformActionListener(this);
        //authhorize与showUser单独调用一个即可
        weibo.authorize();//单独授权，OnComplete返回的hashmap是空的
        // weibo.showUser(null);//授权并获取用户信息
        //移除授权
        // weibo.removeAccount(true);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Looper.prepare();
        //输出所有授权信息
        PlatformDb userDB = platform.getDb();

        String result = platform.getDb().exportData();
        Log.i("TAG",result);
        Toast.makeText(LoginActivty.this,result,Toast.LENGTH_SHORT).show();
        //TODO 根据获取的信息完成当前系统的注册功能，如果已经授权过则直接登录
        String userId = userDB.getUserId();
        String icon = userDB.getUserIcon();
        String token = userDB.getToken();
        String nickname = userDB.getUserName();

        //TODO
        Looper.loop();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(LoginActivty.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(LoginActivty.this,"授权取消",Toast.LENGTH_SHORT).show();

    }

}
