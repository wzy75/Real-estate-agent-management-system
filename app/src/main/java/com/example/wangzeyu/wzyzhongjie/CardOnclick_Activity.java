package com.example.wangzeyu.wzyzhongjie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangzeyu.wzyzhongjie.constants.RestConstants;
import com.example.wangzeyu.wzyzhongjie.http.OkHttpManager;
import com.example.wangzeyu.wzyzhongjie.http.OnHttpCallback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CardOnclick_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_onclick_);
        Intent intent = getIntent();
        final String ID = intent.getStringExtra("ID");
        final String useID = intent.getStringExtra("useID");
        String path = RestConstants.API_HOST + "数据库查询/InCardHome";
        //String path = "http://192.168.43.191:8080/数据库查询/SelectHome";
        //String path = "http://172.16.88.222:8080/数据库查询/RegiStration";
        Map<String, String> params = new HashMap<>();
        params.put("ID", ID);
        OkHttpManager.getInstance().post(path, params, new OnHttpCallback() {
            @Override
            public void onSuccess(String str) {
                System.out.println(str);
                String s[] = str.split("%");
                String ss[] = s[0].split("@");
                //int i = 0;
                if("读取成功".equals(s[1])){
                    ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                    Picasso.with(CardOnclick_Activity.this).load(ss[16]).into(imageView);
                    Toast.makeText(CardOnclick_Activity.this,s[1], Toast.LENGTH_LONG).show();
                    TextView textView1 = (TextView) findViewById(R.id.textView20);
                    TextView textView2 = (TextView) findViewById(R.id.textView21);
                    TextView textView3 = (TextView) findViewById(R.id.textView22);
                    TextView textView4 = (TextView) findViewById(R.id.textView23);
                    TextView textView5 = (TextView) findViewById(R.id.textView24);
                    TextView textView6 = (TextView) findViewById(R.id.textView25);
                    TextView textView7 = (TextView) findViewById(R.id.textView26);
                    TextView textView8 = (TextView) findViewById(R.id.textView27);
                    TextView textView9 = (TextView) findViewById(R.id.textView29);
                    TextView textView10 = (TextView) findViewById(R.id.textView28);
                    TextView textView11 = (TextView) findViewById(R.id.textView30);
                    textView1.setText(ss[0]);
                    textView2.setText(ss[1]);
                    textView3.setText(ss[3]);
                    textView4.setText(ss[2]);
                    textView5.setText(ss[4]);
                    textView6.setText(ss[5]);
                    textView7.setText(ss[6]);
                    textView8.setText(ss[7]);
                    textView9.setText(ss[10]);
                    textView10.setText(ss[11]);
                    textView11.setText(ss[15]);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(CardOnclick_Activity.this, "读取失败，请重新读取!", Toast.LENGTH_LONG).show();
            }
        });
        Button button = (Button) findViewById(R.id.buybutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = RestConstants.API_HOST + "数据库查询/ConectionNo";
                //String path = "http://192.168.43.191:8080/数据库查询/SelectHome";
                //String path = "http://172.16.88.222:8080/数据库查询/RegiStration";
                Map<String, String> params = new HashMap<>();
                params.put("ID", ID);
                params.put("useID", useID);
                OkHttpManager.getInstance().post(path, params, new OnHttpCallback() {
                    @Override
                    public void onSuccess(String str) {
                        //int i = 0;
                        if("购买登记成功，请等待客服与您联系!".equals(str)){
                            Toast.makeText(CardOnclick_Activity.this,str, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(CardOnclick_Activity.this, "购买登记失败", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}
