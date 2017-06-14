package com.example.wangzeyu.wzyzhongjie.viewholder.select;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wangzeyu.wzyzhongjie.CardOnclick_Activity;
import com.example.wangzeyu.wzyzhongjie.LoginActivity;
import com.example.wangzeyu.wzyzhongjie.R;
import com.example.wangzeyu.wzyzhongjie.SelectActivity;
import com.example.wangzeyu.wzyzhongjie.adapter.BuyHomeAdapter;
import com.example.wangzeyu.wzyzhongjie.bean.Home;
import com.example.wangzeyu.wzyzhongjie.callback.OnBuyHomeItemClickListener;
import com.example.wangzeyu.wzyzhongjie.constants.RestConstants;
import com.example.wangzeyu.wzyzhongjie.http.OkHttpManager;
import com.example.wangzeyu.wzyzhongjie.http.OnHttpCallback;
import com.example.wangzeyu.wzyzhongjie.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangzeyu on 2017/6/12.
 */

public class BuyHomeViewHolder extends BaseViewHolder implements OnBuyHomeItemClickListener {

    private RecyclerView rv_select_buy_content;
    private List<Home> mBuyHomeList;
    private BuyHomeAdapter mBuyHomeAdapter;

    public BuyHomeViewHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    protected void initView() {
        setContentViewByParent(R.layout.inner_select_buy_home);
        rv_select_buy_content = (RecyclerView) findViewById(R.id.rv_select_buy_content);
        rv_select_buy_content.setLayoutManager(
                new GridLayoutManager(super.mContext, 2, GridLayoutManager.VERTICAL, false));
//        rv_select_buy_content.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initData() {
        mBuyHomeList = new ArrayList<>();
        mBuyHomeAdapter = new BuyHomeAdapter(mBuyHomeList);
        rv_select_buy_content.setAdapter(mBuyHomeAdapter);
    }

    @Override
    protected void initListener() {
        mBuyHomeAdapter.setOnBuyHomeItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, Home home, int position) {
        //处理点击事件
        String ID=home.getID();
        String useID = home.getUseIDID();
        Intent intent = new Intent(mContext,CardOnclick_Activity.class);
        intent.putExtra("ID",ID);
        intent.putExtra("useID",useID);
        Toast.makeText(mContext,"欢迎选中"+ID, Toast.LENGTH_LONG).show();
        mContext.startActivity(intent);
    }

    public void notifySetDataChanged(List<Home> homeList){
        mBuyHomeList.clear();
        if (null != homeList){
            mBuyHomeList.addAll(homeList);
        }
        mBuyHomeAdapter.notifyDataSetChanged();
    }


}
