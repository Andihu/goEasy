package com.xy.rcs.goeasy.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xy.rcs.goeasy.BombBean.User;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.utils.Utils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.b.V;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonInfoFragment extends Fragment {
    private static final String TAG = "PersonInfoFragment";


    private View mRootView;


    private TextView mphone;

    private TextView name;

    private EditText editName;
    private EditText editTextgender;

    private TextView gender;
    private LinearLayout settinginfo;
    private RelativeLayout noLogin;
    String getphone;
    String getname;
    String getgender;

    Context context;

    public PersonInfoFragment() {
        // Required empty public constructor
    }

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (getphone != null) {
                        mphone.setText(getphone);
                    }
                    if (name != null) {

                        name.setText(getname);
                    }
                    if (gender != null) {

                        gender.setText(getgender);
                    }
                    settinginfo.setVisibility(View.VISIBLE);
                    noLogin.setVisibility(View.GONE);
                    break;
                case 1:
                    settinginfo.setVisibility(View.GONE);
                    noLogin.setVisibility(View.VISIBLE);
                    break;
            }



        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_person_info, container, false);

        setHasOptionsMenu(true);
        context = getActivity();

        CheckLogin();

        bindView();
        onClick();

        return mRootView;
    }

    private void onClick() {

        name.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                name.setVisibility(View.GONE);
                editName.setVisibility(View.VISIBLE);
                editName.getFocusable();
            }
        });

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建弹出式菜单对象（最低版本11）
                PopupMenu popup = new PopupMenu(context, gender);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
                //填充菜单
                inflater.inflate(R.menu.sex, popup.getMenu());//绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        gender.setText(menuItem.getTitle());

                        return false;
                    }
                });
                popup.show(); //这一行代码

            }
        });
    }


    private boolean CheckLogin() {
        if (BmobUser.isLogin()) {
            User user = BmobUser.getCurrentUser(User.class);
            Utils.Toast(getActivity(), "已经登录：" + user.getUsername() + Snackbar.LENGTH_LONG);
            getphone = user.getMobilePhoneNumber()+"";
            getname = user.getUsername()+"";
            getgender = user.getGender()+"";

            handler.sendEmptyMessage(0);


            return true;
        } else {
            handler.sendEmptyMessage(1);

            Utils.Toast(getActivity(), "尚未登录：");
            return false;
        }
    }

    private void bindView() {

        mphone = mRootView.findViewById(R.id.info_phone);
        name = mRootView.findViewById(R.id.info_name);
        editName = mRootView.findViewById(R.id.info_editext);
        gender = mRootView.findViewById(R.id.info_gender);
        settinginfo = mRootView.findViewById(R.id.setting_info);
        noLogin = mRootView.findViewById(R.id.order_no_Login);

        editTextgender = mRootView.findViewById(R.id.info_gender_editext);
    }


}
