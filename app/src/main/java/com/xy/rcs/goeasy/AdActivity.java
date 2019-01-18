package com.xy.rcs.goeasy;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xy.rcs.goeasy.db.OrderDao;
import com.xy.rcs.goeasy.db.OrderHelper;
import com.xy.rcs.goeasy.utils.FullScreenVideoView;

public class AdActivity extends AppCompatActivity {

    private OrderDao orderDao;
    private MyApplication application;
    private FullScreenVideoView fullScreenVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        application= (MyApplication) getApplication();
        application.addActivity(this);
        getSupportActionBar().hide();
        initView();

        findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("com.xy.rcs.goeasy.MainActivity"));
            }
        });
    }
    private void initView() {

        //加载视频资源控件
        fullScreenVideoView = findViewById(R.id.ad_video);
        //设置播放加载路径
        fullScreenVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.moive_bg));
        //播放
        fullScreenVideoView.start();
        //循环播放
        fullScreenVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                fullScreenVideoView.start();
            }
        });
    }
    //返回重启加载
    @Override
    protected void onRestart() {
        initView();
        super.onRestart();
    }
   // 防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        fullScreenVideoView.stopPlayback();
        super.onStop();
    }
}
