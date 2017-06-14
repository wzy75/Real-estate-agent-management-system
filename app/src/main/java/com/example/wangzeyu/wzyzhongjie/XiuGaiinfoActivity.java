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

public class XiuGaiinfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gaiinfo);
        Intent intent = getIntent();
        final String useID = intent.getStringExtra("useID");
        TextView ID = (TextView) findViewById(R.id.xiugaiID);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText phone = (EditText) findViewById(R.id.phone);
        final EditText IDCard = (EditText) findViewById(R.id.IDCard);
        ID.setText(useID);
        System.out.println(useID);
        String path = RestConstants.API_HOST + "数据库查询/SelectBeforeUpdate";
        //String path = "http://172.16.88.222:8080/数据库查询/RegiStration";
        Map<String, String> params = new HashMap<>();
        params.put("ID", useID);
        OkHttpManager.getInstance().post(path, params, new OnHttpCallback() {
            @Override
            public void onSuccess(String str) {
                String s[] = str.split("%");
                String ss[] = s[0].split("@");

                if("查询成功".equals(s[1])){
                    password.setText(ss[1]);
                    name.setText(ss[2]);
                    phone.setText(ss[3]);
                    IDCard.setText(ss[4]);
                    Toast.makeText(XiuGaiinfoActivity.this,s[1], Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(XiuGaiinfoActivity.this, "注册失败，请重新注册!", Toast.LENGTH_LONG).show();
            }
        });
        Button button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordt = password.getText().toString().trim();
                String namet = name.getText().toString().trim();
                String phonet = phone.getText().toString().trim();
                String IDCardt = IDCard.getText().toString().trim();
                String path = RestConstants.API_HOST + "数据库查询/UpdateueserDate";
                //String path = "http://172.16.88.222:8080/数据库查询/RegiStration";
                Map<String, String> params = new HashMap<>();
                params.put("ID", useID);
                params.put("password",passwordt);
                params.put("name", namet);
                params.put("phone", phonet);
                params.put("IDCard", IDCardt);
                OkHttpManager.getInstance().post(path, params, new OnHttpCallback() {
                    @Override
                    public void onSuccess(String str) {
                        if("更新数据成功".equals(str)){
                            Toast.makeText(XiuGaiinfoActivity.this,str, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(XiuGaiinfoActivity.this,str, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(XiuGaiinfoActivity.this, "注册失败，请重新注册!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
