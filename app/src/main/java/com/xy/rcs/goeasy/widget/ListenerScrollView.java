package com.xy.rcs.goeasy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/19/019<p>
 * <p>更改时间：2018/12/19/019<p>
 * <p>版本号：1<p>
 */
public class ListenerScrollView extends ScrollView {
    private static final String TAG = "ListenerScrollView";

    private Context mContext;
    private int MAX_VISI_HEIGIT;
    private HeaderView headerView;
    private Scroller scroller;
    private int mLastY = -1;
    private boolean isTop = false;

    public interface ScrollViewListener {
        void onScrollChanged(ListenerScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    private ScrollViewListener scrollViewListener = null;


    public ListenerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        MAX_VISI_HEIGIT = getScreenheight() / 5;
        scroller = new Scroller(context, new DecelerateInterpolator());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LinearLayout frist = (LinearLayout) getChildAt(0);
        headerView = (HeaderView) frist.getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = (int) ev.getRawY();
        }

        Log.i(TAG, "onTouchEvent: "+mLastY);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                final float offsetY = ev.getRawY() - mLastY;
                mLastY = (int) ev.getRawY();
                if ( headerView.getHeadViewHeight() >=0) {
                    updataheight((int) (offsetY));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastY = -1;
                if (headerView.getHeadViewHeight() >= 300) {
                    headerView.setState(HeaderView.STATE_REFRESHING);
                }
                if (headerView.getHeadViewHeight() >= 0) {
                    //重置headview的高度
                    resetHeaderHeight();
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    private void resetHeaderHeight() {

        int height = headerView.getHeadViewHeight();

        if (headerView.getHeadViewHeight() <= 0) {
            return;
        }
        scroller.startScroll(0, height, 0, 0 - height, 400);
        invalidate();

    }


    //滑动的时候更新headview的高度

    private void updataheight(int offsetY) {

        headerView.setHieght(headerView.getHeadViewHeight() + offsetY);
        if (headerView.getHeadViewHeight() >= 300) {
            headerView.setState(HeaderView.STATE_READY);
        } else {
            headerView.setState(HeaderView.STATE_NOMAL);
        }

    }
    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            headerView.setHieght(scroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }


    public int getScreenheight() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context
                .WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {


        super.onScrollChanged(x, y, oldx, oldy);
        if (getScrollY() == 0) {
            isTop = true;
        } else {
            isTop = false;
        }
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
