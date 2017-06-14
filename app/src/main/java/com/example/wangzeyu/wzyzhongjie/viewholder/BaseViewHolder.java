package com.example.wangzeyu.wzyzhongjie.viewholder;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangzeyu on 2017/6/12.
 */

public abstract class BaseViewHolder {

    protected Context mContext;
    protected ViewGroup mParent;
    private View mView;

    public BaseViewHolder(Context context) {
        this.mContext = context;
    }

    public BaseViewHolder(ViewGroup parent) {
        this.mParent = parent;
        this.mContext = parent.getContext();
    }

    public void init() {
        initView();
        initData();
        initListener();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected void setContentView(@LayoutRes int layoutId) {
        this.mView = LayoutInflater.from(this.mContext).inflate(layoutId, null);
    }

    protected void setContentViewByParent(@LayoutRes int layoutId) {
        this.mView = LayoutInflater.from(this.mContext).inflate(layoutId, this.mParent, false);
    }

    protected View findViewById(@IdRes int viewId) {
        return getView().findViewById(viewId);
    }

    public View getView() {
        return mView;
    }
}
