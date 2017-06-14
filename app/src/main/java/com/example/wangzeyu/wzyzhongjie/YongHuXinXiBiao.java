package com.example.wangzeyu.wzyzhongjie;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wangzeyu.wzyzhongjie.bean.Home;
import com.example.wangzeyu.wzyzhongjie.constants.RestConstants;
import com.example.wangzeyu.wzyzhongjie.http.OkHttpManager;
import com.example.wangzeyu.wzyzhongjie.http.OnHttpCallback;
import com.example.wangzeyu.wzyzhongjie.viewholder.select.BuyHomeViewHolder;
import com.example.wangzeyu.wzyzhongjie.viewholder.select.SelectUseInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YongHuXinXiBiao extends AppCompatActivity {
    private LinearLayout ll_select_content;
    private SelectUseInfo mBuyHomeViewHolder;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yong_hu_xin_xi_biao);
        ll_select_content = (LinearLayout) findViewById(R.id.ll_select_content);
        mBuyHomeViewHolder = new SelectUseInfo(ll_select_content);
        mBuyHomeViewHolder.init();
        ll_select_content.addView(mBuyHomeViewHolder.getView());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        String path = RestConstants.API_HOST + "数据库查询/SelectBeforeUpdate";
        //String path = "http://192.168.43.191:8080/数据库查询/SelectHome";
        //String path = "http://172.16.88.222:8080/数据库查询/RegiStration";
        Map<String, String> params = new HashMap<>();
        params.put("1", "1");
        OkHttpManager.getInstance().post(path, params, new OnHttpCallback() {
            @Override
            public void onSuccess(String str) {
                String s[] = str.split("%");
                String ss[] = s[0].split("#");
                String sa[][] = new String[ss.length][];
                for(int i=0;i<ss.length;i++){
                    sa[i] = ss[i].split("@");
                }
                //int i = 0;
                List<Home> list = new ArrayList<Home>();
                for (int i=0;i<sa.length;i++){
                    for (int j=0;j<sa[i].length;j+=5) {
                        Home home = new Home(sa[i][j],sa[i][j],"http://img.lanrentuku.com/img/allimg/1702/14876697235728.jpg");
                        list.add(home);
                    }
                }

                mBuyHomeViewHolder.notifySetDataChanged(list);

                if("查询成功".equals(s[1])){
                    Toast.makeText(YongHuXinXiBiao.this,s[1], Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(YongHuXinXiBiao.this,s[1], Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(YongHuXinXiBiao.this, "读取失败，请重新读取!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
