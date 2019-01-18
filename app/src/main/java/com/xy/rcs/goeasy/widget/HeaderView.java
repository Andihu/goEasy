package com.xy.rcs.goeasy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xy.rcs.goeasy.R;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2019/1/2/002<p>
 * <p>更改时间：2019/1/2/002<p>
 * <p>版本号：1<p>
 */
public class HeaderView extends LinearLayout {

    private View headview;
    private Button btn;
    private LinearLayout mlinearLayout;
    private TextView mHeadText;
    private ProgressBar progressBar;

    //初始状态值 箭头向下
    public static final int STATE_NOMAL=1;
    //准备刷新的状态 箭头向上
    public static final int STATE_READY=2;
    //正在刷新状态 旋转
    public static final int STATE_REFRESHING=3;
    private int mState =STATE_NOMAL;
    private RotateAnimation rotateAnimation_down;
    private RotateAnimation rotateAnimation_up;

    public HeaderView(Context context) {
        super(context,null);
        initeView();
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        initeView();
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initeView();

    }

    private void initeView() {
        headview = View.inflate(getContext(), R.layout.headview_layout,null);
        //设置初始高度
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,0);
        this.addView(headview,params);
        //绑定控件
        btn =headview.findViewById(R.id.head_arrow);
        mlinearLayout=headview.findViewById(R.id.head_info);
        mHeadText=headview.findViewById(R.id.head_text);
        progressBar=headview.findViewById(R.id.head_progressbar);
        //初始化动画
        rotateAnimation_up = new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation_up.setDuration(500);
        rotateAnimation_up.setFillAfter(true);
        rotateAnimation_down = new RotateAnimation(-180,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation_down.setDuration(500);
        rotateAnimation_down.setFillAfter(true);
    }
    public void setState(int state){
        if (state== mState){
            return;
        }
        if (state ==STATE_REFRESHING){
            progressBar.setVisibility(VISIBLE);
            mlinearLayout.setVisibility(GONE);
            btn.clearAnimation();
        }else {
            progressBar.setVisibility(GONE);
            mlinearLayout.setVisibility(VISIBLE);
        }
        switch (state) {
            case STATE_NOMAL:
                if (mState==STATE_REFRESHING){
                    //如果之前正在刷新
                }else if(mState==STATE_READY) {
                    btn.setAnimation(rotateAnimation_down);
                }
                mHeadText.setText("下拉刷新");
                break;
            case STATE_REFRESHING:
                mHeadText.setText("正在刷新");
                break;
            case STATE_READY:
                btn.startAnimation(rotateAnimation_up);
                mHeadText.setText("松开刷新");
                break;
        }
        mState=state;
    }



    public void setHieght(float height){
        LayoutParams layoutParams=new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) height);
        headview.setLayoutParams(layoutParams);
    }
    public int getHeadViewHeight(){
        return  headview.getLayoutParams().height;
    }
}
