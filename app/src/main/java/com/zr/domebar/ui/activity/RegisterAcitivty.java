package com.zr.domebar.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zr.domebar.R;
import com.zr.domebar.bean.User;
import com.zr.domebar.common.Common;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by S01 on 2017/5/11.
 */

public class RegisterAcitivty extends AppCompatActivity {

    private EditText et_re_name,et_re_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        findViews();
        Bmob.initialize(this, Common.BMOB_ID);
    }

    private void findViews() {
        et_re_name = (EditText) findViewById(R.id.et_re_name);
        et_re_pwd  = (EditText) findViewById(R.id.et_re_pwd);
    }
    public void onRegister (View view){
        switch (view.getId()){
            case R.id.btn_register:
                Register();
                break;
        }
    }

    private void Register() {
        final String registerName = et_re_name.getText().toString();
        final String registerPwd = et_re_pwd.getText().toString();
        BmobUser user = new BmobUser();
        user.setUsername(registerName);
        user.setPassword(registerPwd);
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                if (registerName.equals("") || registerPwd.equals("")){
                    Toast.makeText(RegisterAcitivty.this,"账户和密码不能为空",Toast.LENGTH_SHORT).show();
                }else if (registerName.length()<7 ||    registerPwd.length()<7){
                    Toast.makeText(RegisterAcitivty.this,"账户和密码不能小于6位数",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegisterAcitivty.this,"注册成功",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(RegisterAcitivty.this,"注册失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
