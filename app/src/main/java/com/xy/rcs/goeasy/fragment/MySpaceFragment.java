package com.xy.rcs.goeasy.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xy.rcs.goeasy.MyApplication;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.RepertoryActivity;
import com.xy.rcs.goeasy.utils.GetFragment;
import com.xy.rcs.goeasy.utils.Utils;

import java.util.Objects;

import cn.bmob.v3.BmobUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySpaceFragment extends Fragment implements View.OnClickListener {

    protected Intent mIntent;
    protected TextView mName;
    protected TextView mScroe;
    protected TextView mPositon;
    protected TextView quickAccount;
    protected ImageView mBack;
    protected ImageView mIcon;
    private ImageView msetting;
    protected LinearLayout titlearea;
    protected RelativeLayout mItem_Like;
    protected RelativeLayout mItem_order;
    protected RelativeLayout mItem_Position;
    protected View mRootView;
    protected MyApplication myApplication;
    private  SharedPreferences share;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_my_space, container, false);
        ininView();
        setView();
        return mRootView;
    }

    private void setView() {
    }

    private void ininView() {
        myApplication = (MyApplication) Objects.requireNonNull(getActivity()).getApplication();
        titlearea = getActivity().findViewById(R.id.tital_area);
        titlearea.setVisibility(View.GONE);
        mIcon = mRootView.findViewById(R.id.myspace_icon);
        mName = mRootView.findViewById(R.id.myspace_name);
        mBack = mRootView.findViewById(R.id.myspace_back);
        mScroe = mRootView.findViewById(R.id.myspace_score);
        msetting = mRootView.findViewById(R.id.setting);
        mPositon = mRootView.findViewById(R.id.myspace_area);
        mItem_Like = mRootView.findViewById(R.id.item_like);
        mItem_order = mRootView.findViewById(R.id.item_order);
        quickAccount = mRootView.findViewById(R.id.quitlanding);
        mItem_Position = mRootView.findViewById(R.id.item_position);
        mBack.setOnClickListener(this);
        mScroe.setOnClickListener(this);
        msetting.setOnClickListener(this);
        mItem_Like.setOnClickListener(this);
        mItem_order.setOnClickListener(this);
        quickAccount.setOnClickListener(this);
        mItem_Position.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myspace_back:
                getActivity().onBackPressed();
                break;
            case R.id.myspace_score:
                startFragment(GetFragment.MYSPACE_FRAGMENT, "积分中心");
                break;
            case R.id.item_order:
                startFragment(GetFragment.ORDER_FRAGMENT, "订单中心");
                break;
            case R.id.item_position:
                startFragment(GetFragment.POSITION_LIST_FRAGMENT, "地址设置");
                break;
            case R.id.item_like:

                startFragment(GetFragment.LIKE_FRAGMENT, "我的收藏");
                break;
            case R.id.quitlanding:


                Utils.Toast(getActivity(),"退出登录");
                BmobUser.logOut();
                share = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
                editor= share.edit();
                editor.putBoolean("isLogin",false);
                editor.commit();

                break;
            case R.id.setting:
                startFragment(GetFragment.PERSONAL_INFO_FRAGMENT, "我的信息");
                break;
        }
    }

    private void startFragment(int positionSettingFragment, String title) {
        mIntent = new Intent(getActivity(), RepertoryActivity.class);
        mIntent.putExtra("fragment_code", positionSettingFragment);
        mIntent.putExtra("title", title);
        startActivity(mIntent);
    }
}
