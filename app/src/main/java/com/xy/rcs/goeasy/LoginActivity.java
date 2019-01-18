package com.xy.rcs.goeasy;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xy.rcs.goeasy.adapter.MyFragmentAdapter;
import com.xy.rcs.goeasy.fragment.LoginFragment;
import com.xy.rcs.goeasy.fragment.RegisterFragment;
import com.xy.rcs.goeasy.module.ILoginListener;
import com.xy.rcs.goeasy.module.LoginImpl;
import com.xy.rcs.goeasy.module.SignUpImpl;
import com.xy.rcs.goeasy.utils.Constant;
import com.xy.rcs.goeasy.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private MyApplication application;
    private SharedPreferences mkeep;
    private CheckBox checkBox;
    private SharedPreferences.Editor editor;

    private ViewPager viewPager;
    private ImageView imageView;
    private Animation animation;
    private Animation animation2;
    private int stat = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        application = (MyApplication) getApplication();
        application.addActivity(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();

    }

    private void initView() {

        imageView = findViewById(R.id.item_tag);

        viewPager = findViewById(R.id.viewpager);
        LoginFragment login = new LoginFragment();
        RegisterFragment Register = new RegisterFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(login);
        fragments.add(Register);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setPageMargin(0);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anrt);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anrt2);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (stat == 0) {
                    imageView.startAnimation(animation);
                } else if (stat == 1) {
                    imageView.startAnimation(animation2);
                }
                stat = i;
            }
            
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


}

