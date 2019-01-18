package com.xy.rcs.goeasy.module;

import android.content.Context;

import com.xy.rcs.goeasy.MyApplication;

import org.json.JSONObject;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/13/013<p>
 * <p>更改时间：2018/12/13/013<p>
 * <p>版本号：1<p>
 */
public class AddCartImpl implements AddCart {
    MyApplication application;
    @Override
    public void add(Context context, JSONObject good) {
        application= (MyApplication) context.getApplicationContext();
        application.addCart(good);
    }
}
