package com.example.wangzeyu.wzyzhongjie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangzeyu.wzyzhongjie.bean.Home;
import com.example.wangzeyu.wzyzhongjie.constants.RestConstants;
import com.example.wangzeyu.wzyzhongjie.http.OkHttpManager;
import com.example.wangzeyu.wzyzhongjie.http.OnHttpCallback;
import com.example.wangzeyu.wzyzhongjie.viewholder.select.BuyHomeViewHolder;
import com.example.wangzeyu.wzyzhongjie.viewholder.select.SelectUseInfo;
import com.example.wangzeyu.wzyzhongjie.viewholder.select.SellHomeViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SelectActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int CHOOSE_PHOTO = 2;
    private TextView tv0;
    private LinearLayout ll_select_content;
    private NavigationView navigationView;
    private Button choose_from_Album;
    private ImageView picture;

    private SellHomeViewHolder mSellHomeViewHolder;
    private BuyHomeViewHolder mBuyHomeViewHolder;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        ll_select_content = (LinearLayout) findViewById(R.id.ll_select_content);
        mBuyHomeViewHolder = new BuyHomeViewHolder(ll_select_content);
        mBuyHomeViewHolder.init();
        removeOtherView();
        ll_select_content.addView(mBuyHomeViewHolder.getView());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        tv0 = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView0);
        tv0.setText(username);

        //String path = "http://172.16.64.203:8080/数据库查询/SelectHome";
        String path = RestConstants.API_HOST + "数据库查询/SelectHome";
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
                    for (int j=0;j<sa[i].length;j+=17) {
                        System.out.println(sa[i][j]+"++++"+sa[i][j+16]);
                        Home home = new Home(username,sa[i][j],sa[i][j+16]);
                        list.add(home);
                    }
                }

                mBuyHomeViewHolder.notifySetDataChanged(list);

                if("读取成功".equals(s[1])){
                    Toast.makeText(SelectActivity.this,s[1], Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(SelectActivity.this, "读取失败，请重新读取!", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.buyHome) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FrameLayout frameLayout = null;

        if (id == R.id.buyHome) {
            removeOtherView();
            ll_select_content.addView(mBuyHomeViewHolder.getView());
        } else if (id == R.id.sellHome) {
            removeOtherView();
            if (null == mSellHomeViewHolder) {
                mSellHomeViewHolder = new SellHomeViewHolder(ll_select_content);
                mSellHomeViewHolder.init();
            }
            ll_select_content.addView(mSellHomeViewHolder.getView());
            Button submitbutton = (Button) findViewById(R.id.submitbutton);
            submitbutton.setOnClickListener(new View.OnClickListener() {
                EditText homeID = (EditText)findViewById(R.id.homeID);
                EditText huxing = (EditText) findViewById(R.id.huxing);
                EditText mianji = (EditText) findViewById(R.id.mianji);
                EditText chaoxiang = (EditText) findViewById(R.id.chaoxiang);
                EditText louchen = (EditText) findViewById(R.id.louchen);
                EditText xinjiuchengdu = (EditText) findViewById(R.id.xinjiuchengdu);
                EditText luohu = (EditText) findViewById(R.id.luohu);
                EditText man2nian = (EditText) findViewById(R.id.man2nian);
                EditText fandai = (EditText) findViewById(R.id.fandai);
                EditText shuangping = (EditText) findViewById(R.id.shuangping);
                EditText xuewei = (EditText) findViewById(R.id.xuewei);
                EditText qianyue = (EditText) findViewById(R.id.qianyue);
                EditText hongben = (EditText) findViewById(R.id.hongben);
                EditText jipodu = (EditText) findViewById(R.id.jipodu);
                EditText chanquanren = (EditText) findViewById(R.id.chanquanren);
                EditText huanfan = (EditText) findViewById(R.id.huanfan);
                EditText price = (EditText) findViewById(R.id.price);
                EditText minprice = (EditText) findViewById(R.id.minprice);
                public void onClick(View v) {
                    String homeIDt = homeID.getText().toString().trim();
                    String huxingt = huxing.getText().toString().trim();
                    String mianjit = mianji.getText().toString().trim();
                    String chaoxiangt = chaoxiang.getText().toString().trim();
                    String louchent = louchen.getText().toString().trim();
                    String xinjiuchengdut = xinjiuchengdu.getText().toString().trim();
                    String luohut = luohu.getText().toString().trim();
                    String man2niant = man2nian.getText().toString().trim();
                    String fandait = fandai.getText().toString().trim();
                    String shuangpingt = shuangping.getText().toString().trim();
                    String xueweit = xuewei.getText().toString().trim();
                    String qianyuet = qianyue.getText().toString().trim();
                    String hongbent = hongben.getText().toString().trim();
                    String jipodut = jipodu.getText().toString().trim();
                    String chanquanrent = chanquanren.getText().toString().trim();
                    String huanfant = huanfan.getText().toString().trim();
                    String pricet = price.getText().toString().trim();
                    String minpricet = minprice.getText().toString().trim();
                    if(!(homeIDt.isEmpty())&&!(huxingt.isEmpty())&&!(mianjit.isEmpty())&&!(chaoxiangt.isEmpty())&&
                            !(louchent.isEmpty())&&!(xinjiuchengdut.isEmpty())&&!(luohut.isEmpty())&&!(man2niant.isEmpty())&&
                            !(fandait.isEmpty())&&!(shuangpingt.isEmpty())&&!(xueweit.isEmpty())&&!(qianyuet.isEmpty())&&
                            !(hongbent.isEmpty())&&!(jipodut.isEmpty())&&!(chanquanrent.isEmpty())&&!(huanfant.isEmpty())
                            &&!(pricet.isEmpty())&&!(minpricet.isEmpty())){
                        String path = RestConstants.API_HOST + "数据库查询/HomeDengji";
                        //String path = "http://192.168.43.191:8080/数据库查询/HomeDengji";
                        Map<String, String> params = new HashMap<>();
                        Intent intent = getIntent();
                        String ID = intent.getStringExtra("ID");
                        params.put("ID", ID);
                        params.put("homeIDt", homeIDt);
                        params.put("huxingt", huxingt);
                        params.put("mianjit", mianjit);
                        params.put("louchent", louchent);
                        params.put("xinjiuchengdut", xinjiuchengdut);
                        params.put("luohut", luohut);
                        params.put("man2niant", man2niant);
                        params.put("fandait", fandait);
                        params.put("shuangpingt", shuangpingt);
                        params.put("xueweit", xueweit);
                        params.put("qianyuet", qianyuet);
                        params.put("hongbent", hongbent);
                        params.put("jipodut", jipodut);
                        params.put("chanquanrent", chanquanrent);
                        params.put("chaoxiangt", chaoxiangt);
                        params.put("huanfant", huanfant);
                        params.put("pricet", pricet);
                        params.put("minpricet", minpricet);
                        OkHttpManager.getInstance().post(path, params, new OnHttpCallback() {
                            @Override
                            public void onSuccess(String str) {
                                //String a[] = str.split("@");
                                Toast.makeText(SelectActivity.this,str, Toast.LENGTH_LONG).show();
                                if("登记成功".equals(str)){
                                    Intent intent = new Intent(SelectActivity.this,SelectActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(SelectActivity.this, "登记失败!", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Toast.makeText(SelectActivity.this, "登记失败!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }
            });
        } else if (id == R.id.zhongjiemamage) {
            removeOtherView();
            Intent intent = getIntent();
            final String ID = intent.getStringExtra("ID");
            String path = RestConstants.API_HOST + "数据库查询/ManageSelect";
            //String path = "http://192.168.43.191:8080/数据库查询/HomeDengji";
            Map<String, String> params = new HashMap<>();
            params.put("ID", ID);
            OkHttpManager.getInstance().post(path, params, new OnHttpCallback() {
                @Override
                public void onSuccess(String str) {
                    System.out.println(str);
                    //String a[] = str.split("@");
                    if(("您好！欢迎您管理员"+ID).equals(str)){
                        Toast.makeText(SelectActivity.this,str, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SelectActivity.this,ZhongJieGuanLiActivity.class);
                        intent.putExtra("ID",ID);
                        startActivity(intent);
                    }else{
                        Toast.makeText(SelectActivity.this, "非管理员，请勿点击", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    Toast.makeText(SelectActivity.this, "登记失败!", Toast.LENGTH_LONG).show();
                }
            });


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void removeOtherView() {
        int itemViewCount = ll_select_content.getChildCount();
        if (itemViewCount > 1) {
            for (int i = 1; i < itemViewCount; i++)
                ll_select_content.removeViewAt(i);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */




}
