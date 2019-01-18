package com.xy.rcs.goeasy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.transition.Explode;
import android.transition.Transition;
import android.view.KeyEvent;
import android.view.View;

import com.xy.bizport.sdk.publicinfo.PublicInfoService;
import com.xy.rcs.goeasy.utils.GetFragment;

public class TempActivity extends Activity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();//获取屏幕的decorView
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//设置全屏
        if(Build.VERSION.SDK_INT >= 21) {
            decorView = getWindow().getDecorView();
            //设置全屏和状态栏透明
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_temp);
        Transition transition = new Explode();
        getWindow().setEnterTransition(transition);
        getWindow().setExitTransition(transition);
        intent=new Intent();
        intent.putExtra("fragment_code", GetFragment.POSITION_LIST_FRAGMENT);
        intent.putExtra("title", "地址设置");

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
