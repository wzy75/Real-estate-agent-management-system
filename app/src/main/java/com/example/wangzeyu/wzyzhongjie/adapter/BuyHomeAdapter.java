package com.example.wangzeyu.wzyzhongjie.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangzeyu.wzyzhongjie.R;
import com.example.wangzeyu.wzyzhongjie.bean.Home;
import com.example.wangzeyu.wzyzhongjie.callback.OnBuyHomeItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wangzeyu on 2017/6/12.
 */
public class BuyHomeAdapter extends RecyclerView.Adapter<BuyHomeAdapter.ViewHolder> {
    private Context mContext;
    private List<Home> mHomeList;
    private OnBuyHomeItemClickListener mOnBuyHomeItemClickListener;

    public BuyHomeAdapter(List<Home> homeList) {
        mHomeList = homeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.content_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BuyHomeAdapter.ViewHolder holder, final int position) {
        final Home home = mHomeList.get(position);
        holder.setData(home);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnBuyHomeItemClickListener) {
                    mOnBuyHomeItemClickListener.onItemClick(v, home, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == mHomeList ? 0 : mHomeList.size();
    }

    public void setOnBuyHomeItemClickListener(OnBuyHomeItemClickListener listener) {
        this.mOnBuyHomeItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView homeImage;
        TextView homeId;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            homeImage = (ImageView) view.findViewById(R.id.home_iamge);
            homeId = (TextView) view.findViewById(R.id.home_name);
        }

        public void setData(Home holder) {
            homeId.setText(holder.getID());
            String imageID = holder.getImageID();
            if (!TextUtils.isEmpty(imageID)){
                Picasso.with(mContext).load(imageID).into(homeImage);
            }
        }
    }

}

