package com.example.wangzeyu.wzyzhongjie;

import android.content.Intent;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wangzeyu.wzyzhongjie.constants.RestConstants;
import com.example.wangzeyu.wzyzhongjie.http.OkHttpManager;
import com.example.wangzeyu.wzyzhongjie.http.OnHttpCallback;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class zhuCeActivity extends AppCompatActivity {
    private EditText ID;
    private EditText name;
    private EditText password;
    private EditText password2;
    private EditText phoneNum;
    private EditText IDCard;
    private Button zhuceButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        ID = (EditText) findViewById(R.id.ID);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        IDCard = (EditText) findViewById(R.id.IDCard);
        zhuceButton = (Button) findViewById(R.id.zhucebutton);
        zhuceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String IDtext = ID.getText().toString().trim();
                final String nametext = name.getText().toString().trim();
                String passwordtext = password.getText().toString().trim();
                String password2text = password2.getText().toString().trim();
                String phoneNumtext = phoneNum.getText().toString().trim();
                String IDCardtext = IDCard.getText().toString().trim();
                if (IDtext.isEmpty()) {
                    Toast.makeText(zhuCeActivity.this, "ID不能为空!", Toast.LENGTH_LONG).show();
                    return;
                } else if (nametext.isEmpty()) {
                    Toast.makeText(zhuCeActivity.this, "姓名不能为空!", Toast.LENGTH_LONG).show();
                    return;
                } else if (passwordtext.isEmpty()) {
                    Toast.makeText(zhuCeActivity.this, "密码不能为空!", Toast.LENGTH_LONG).show();
                    return;
                } else if (password2text.isEmpty()) {
                    Toast.makeText(zhuCeActivity.this, "确认密码不能为空!", Toast.LENGTH_LONG).show();
                    return;
                } else if (!(passwordtext.equals(password2text))) {
                    Toast.makeText(zhuCeActivity.this, "两次密码不一致!", Toast.LENGTH_LONG).show();
                    return;
                } else if (phoneNumtext.isEmpty()) {
                    Toast.makeText(zhuCeActivity.this, "电话号码不能为空!", Toast.LENGTH_LONG).show();
                    return;
                } else if (IDCardtext.isEmpty()) {
                    Toast.makeText(zhuCeActivity.this, "身份证不能为空!", Toast.LENGTH_LONG).show();
                    return;
                }
                String path = RestConstants.API_HOST +  "数据库查询/RegiStration";
                //String path = "http://172.16.88.222:8080/数据库查询/RegiStration";
                Map<String, String> params = new HashMap<>();
                params.put("ID", IDtext);
                params.put("passWord", passwordtext);
                params.put("userName", nametext);
                params.put("tel", phoneNumtext);
                params.put("iDCard", IDCardtext);
                OkHttpManager.getInstance().post(path, params, new OnHttpCallback() {
                    @Override
                    public void onSuccess(String str) {
                        Toast.makeText(zhuCeActivity.this,str, Toast.LENGTH_LONG).show();
                        if("注册成功".equals(str)){
                            Intent intent = new Intent(zhuCeActivity.this,SelectActivity.class);
                            intent.putExtra("username",nametext);
                            intent.putExtra("ID",IDtext);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(zhuCeActivity.this, "注册失败，请重新注册!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
