
package com.xy.rcs.goeasy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xy.rcs.goeasy.fragment.HomeFragment;
import com.xy.rcs.goeasy.utils.GetFragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final int REQ_CODE_PERMISSION = 0x1111;
    private static final String TAG = "MainActivity";


    private MyApplication application;
    private Intent mIntent;
    private ImageView mPaymenter;
    private ImageView mCarts;
    private LinearLayout mSweepCode;
    private HomeFragment fragment;
    private FragmentManager fragmentManager;


    //是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useThemestatusBarColor = false;
    //是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
    protected boolean useStatusBarColor = true;



    private static final int FRAGMENT_LAYOUT = R.id.fragment_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        application= (MyApplication) getApplication();
        application.addActivity(this);
        getSupportActionBar().hide();

        setStatusBar();
        initView();
        requsetPremiss();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (listener!=null){
            listener.doonWindowFocusChanged();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            application.ExitApp();
        }

        return super.onKeyDown(keyCode, event);
    }

    private void requsetPremiss() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission
                .CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Do not have the permission of camera, request it.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest
                    .permission.CAMERA}, REQ_CODE_PERMISSION);
        } else {
            // Have gotten the permission
            //   startSweepCode(GetFragment.COUPON_FRAGMENT, "扫码购");

        }

    }



    private void initView() {
        mCarts = findViewById(R.id.bottom_cart);
        mSweepCode = findViewById(R.id.bottom_sweepcode);
        mPaymenter = findViewById(R.id.bottom_aymenter);
        mCarts.setOnClickListener(this);
        mSweepCode.setOnClickListener(this);
        mPaymenter.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();
        //加载主页fragment
        fragment = new HomeFragment();
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.goods:
                startFragment(GetFragment.PRODUCT_FRAGMENT, "小时的商品仓库");
                break;
            case R.id.integrals:
                startFragment(GetFragment.SCORE_FRAGMENT, "我的积分");
                break;
            case R.id.coupons:
                startFragment(GetFragment.ORDER_FRAGMENT, "订单中心");
                break;
            case R.id.bottom_cart:
                startFragment(GetFragment.GOODSCART_FRAGMENT, "购物车");
                break;
            case R.id.bottom_sweepcode:
                startFragment(GetFragment.SWEEP_FRAGMENT, "扫码购");
                break;
            case R.id.bottom_aymenter:
                startFragment(GetFragment.COUPON_FRAGMENT, "付款码");
                break;
        }
    }

    private void startFragment(int productFragment, String string) {
        mIntent = new Intent(MainActivity.this, RepertoryActivity.class);
        mIntent.putExtra("fragment_code", productFragment);
        mIntent.putExtra("title", string);
        startActivity(mIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    // User agree the permission
                    // startSweepCode(GetFragment.COUPON_FRAGMENT, "扫码购");
                } else {
                    // User disagree the permission
                    Toast.makeText(this, "You must agree the camera permission request before you" +
                            " use the code scan function", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }
    /** 保存MyTouchListener接口的列表 */
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<>();

    /** 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法 */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /** 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法 */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove( listener );
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }


    public interface MyTouchListener {
        /** onTOuchEvent的实现 */
        boolean onTouchEvent(MotionEvent event);
    }
    onWindowFocusChangedListener listener;

    public interface onWindowFocusChangedListener{
        void doonWindowFocusChanged();
    }
   public void  setonWindowFocusChanged(onWindowFocusChangedListener listener){
        this.listener=listener;
    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(Color.WHITE);//设置状态栏背景色
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        } else {
            Toast.makeText(this, "低于4.4的android系统版本不存在沉浸式状态栏", Toast.LENGTH_SHORT).show();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && useStatusBarColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

}
