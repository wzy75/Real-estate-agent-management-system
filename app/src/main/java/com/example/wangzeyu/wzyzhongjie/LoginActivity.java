package com.example.wangzeyu.wzyzhongjie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangzeyu.wzyzhongjie.constants.RestConstants;
import com.example.wangzeyu.wzyzhongjie.http.OkHttpManager;
import com.example.wangzeyu.wzyzhongjie.http.OnHttpCallback;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity{
    private EditText loginID;
    private EditText loginPassword;
    private Button login;
    private TextView zhuCeText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginID = (EditText) findViewById(R.id.loginID);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        login = (Button) findViewById(R.id.loginButton);
        zhuCeText = (TextView) findViewById(R.id.zhuce);
        zhuCeText.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,zhuCeActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String ID = loginID.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                String path = RestConstants.API_HOST + "数据库查询/login";
                //String path = "http://172.16.64.203:8080/数据库查询/login";
                //String path = "http://172.16.65.148:8080/数据库查询/login";
                //String path = "http://192.168.43.191:8080/数据库查询/login";
                Map<String, String> params = new HashMap<>();
                params.put("ID", ID);
                params.put("passWord", password);
                OkHttpManager.getInstance().post(path, params, new OnHttpCallback() {
                    @Override
                    public void onSuccess(String str) {
                        String a[] = str.split("@");
                        if("登录成功".equals(a[1])){
                            Toast.makeText(LoginActivity.this,a[1], Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this,SelectActivity.class);
                            intent.putExtra("username",a[0]);
                            intent.putExtra("ID",ID);
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginActivity.this, "登录失败，请重新登录!", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "登录失败，请重新登录!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}