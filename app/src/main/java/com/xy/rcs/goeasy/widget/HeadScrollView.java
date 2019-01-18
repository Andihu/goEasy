package com.xy.rcs.goeasy.widget;

import android.annotation.SuppressLint;
import android.app.admin.DeviceAdminService;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2019/1/3/003<p>
 * <p>更改时间：2019/1/3/003<p>
 * <p>版本号：1<p>
 */
public class HeadScrollView extends ScrollView {
    Context mContext;
    private int MAX_VISI_HEIGIT;
    HeaderView headerView;
    Scroller scroller;

    private float mLastY=-1;

    private void init() {

    }

    public HeadScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        MAX_VISI_HEIGIT=getScreenheight()/5;
        scroller=new Scroller(context,new DecelerateInterpolator());

    }


    public int getScreenheight(){
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LinearLayout frist= (LinearLayout) getChildAt(0);
        headerView= (HeaderView) frist.getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY==-1){
            mLastY=ev.getRawY();
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY=ev.getRawY();
            break;
            case MotionEvent.ACTION_MOVE:
                float offsetY=ev.getRawY()-mLastY;
                if (offsetY>0){
                    upViewHeight(offsetY);
                }
            break;
            case MotionEvent.ACTION_UP:
                if (headerView.getHeadViewHeight() > 0) {
                    resetHeaderHeight();
                }
//                headerView.getHeadViewHeight()>500?moveback():move
//                if (headerView.getHeadViewHeight()>500){
//                    //回位到500
//                }else (headerView.getHeadViewHeight()<500){
//                    }

            break;
        }
        return super.onTouchEvent(ev);
    }

    private void resetHeaderHeight() {
        int height=headerView.getHeadViewHeight();
        scroller.startScroll(0,height,0,400-height,400);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            headerView.setHieght(scroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    private void  moveback() {
    }

    private void upViewHeight(float offsetY) {
       float height= headerView.getHeadViewHeight();
       headerView.setHieght((int) (height+offsetY));
    }
}
