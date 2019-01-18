package com.xy.rcs.goeasy.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.xy.rcs.goeasy.IonKeyPress;
import com.xy.rcs.goeasy.MainActivity;
import com.xy.rcs.goeasy.MyApplication;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.RepertoryActivity;
import com.xy.rcs.goeasy.adapter.HomeListAdapter;
import com.xy.rcs.goeasy.adapter.IonPositionClick;
import com.xy.rcs.goeasy.adapter.RecyclerViewAdapter;
import com.xy.rcs.goeasy.utils.GetFragment;
import com.xy.rcs.goeasy.utils.Utils;
import com.xy.rcs.goeasy.widget.ChildLiistView;
import com.xy.rcs.goeasy.widget.ListenerScrollView;

import org.json.JSONObject;

import java.util.Objects;

import widget.CollapsedTextView;


public class HomeFragment extends Fragment implements com.xy.rcs.goeasy.widget.ListenerScrollView
        .ScrollViewListener {


    protected static final String TAG = "HomeFragment";

    private View mRootView;
    private Intent mIntent;
    private ImageView mAd;
    private ImageView mAdare;
    private ImageView mAdare2;
    //    private ImageView mCard;
//    private ImageView mCard2;
    private ImageView mHeadad1;
    private ImageView mHeadad2;
    private ImageView mHeadad3;
    private LinearLayout mMySpaceTop;
    private LinearLayout mMySpace;
    private MyApplication application;
    private RelativeLayout mhitiBar;

    private ChildLiistView mlistView;

    protected RecyclerView mHotSellrecyclerView;
    protected RecyclerView mPromoteRecyclerView;
    protected RecyclerViewAdapter mPromoteAdapter;
    protected RecyclerViewAdapter mHotSellAdapter;
    protected LinearLayoutManager mPromoteLayoutManager;
    protected LinearLayoutManager mHotSellLayoutManager;
    private ListenerScrollView ListenerScrollView;

    private int mAdbottomPosition;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);

        application = (MyApplication) Objects.requireNonNull(getActivity()).getApplication();

        initView();

        setImage();

        mAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(GetFragment.WEB_FRAGMENT, "https://market.m.taobao" +
                        ".com/apps/abs/10/473/442fba?spm=a21bo.2017.201862-1.d1.5af911d9wOE8xG" +
                        "&pos=1&_wvUseWKWebView=YES&psId=1976032&acm=20140506001.1003.2.5023112" +
                        "&scm=1003.2.20140506001.OTHER_1546563340654_5023112");
            }
        });
        mAdare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(GetFragment.WEB_FRAGMENT, "https://pages.tmall" +
                        ".com/wow/chaoshi/act/wupr?spm=a3204.7139825.1997320513.2.pMkEQf&wh_biz" +
                        "=tm&pos=1&wh_pid=act%2Fmuthr23&acm=201505201.1003.2.5036938&scm" +
                        "=1003.2.201505201.OTHER_1545138453475_5036938");

            }
        });
        mAdare2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(GetFragment.WEB_FRAGMENT, "https://pages.tmall" +
                        ".com/wow/chaoshi/act/wupr?spm=a3204.7139825.1997320513.4.pMkEQf&wh_biz" +
                        "=tm&pos=3&wh_pid=act%2F753951&acm=201505201.1003.2.5036938&scm" +
                        "=1003.2.201505201.OTHER_1546294151311_5036938");

            }
        });
        /* 触摸事件的注册 */

        ((MainActivity) this.getActivity()).registerMyTouchListener(myTouchListener);

        /*注册OnWindowFocusChange*/
        ((MainActivity) this.getActivity()).setonWindowFocusChanged(listener);


        return mRootView;
    }


    MainActivity.onWindowFocusChangedListener listener = new MainActivity
            .onWindowFocusChangedListener() {
        @Override
        public void doonWindowFocusChanged() {
            mAdbottomPosition = mAd.getHeight();
            ListenerScrollView.setScrollViewListener(HomeFragment.this);
        }
    };

    MainActivity.MyTouchListener myTouchListener = new MainActivity.MyTouchListener() {
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            return false;
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        /*触摸事件的注销 */
        ((MainActivity) Objects.requireNonNull(this.getActivity())).unRegisterMyTouchListener
                (myTouchListener);
    }

    private void setImage() {
        Glide.with(getActivity()).load("https://goss.veer" +
                ".com/creative/vcg/veer/800water/veer-200049529.jpg").into(mHeadad1);
        Glide.with(getActivity()).load("https://goss.veer" +
                ".com/creative/vcg/veer/800water/veer-200050524.jpg").into(mHeadad2);
        Glide.with(getActivity()).load("https://goss.veer" +
                ".com/creative/vcg/veer/800water/veer-200050467.jpg").into(mHeadad3);
//        Glide.with(getActivity()).load("https://goss.veer" +
//                ".com/creative/vcg/veer/800water/veer-303006921.jpg").into(mCard);
//        Glide.with(getActivity()).load("https://goss.veer" +
//                ".com/creative/vcg/veer/800water/veer-312147765.jpg").into(mCard2);
        Glide.with(getActivity()).load("https://img.alicdn.com/tps/i4/TB1tJAmypzqK1RjSZFCSuvbxVXa" +
                ".jpg_1080x1800Q60s50.jpg").into(mAdare2);
        Glide.with(getActivity()).load("https://img.alicdn.com/tps/i4/TB1SIM5zyLaK1RjSZFxSuumPFXa" +
                ".jpg_1080x1800Q60s50.jpg").into
                (mAdare);
//        Glide.with(getActivity()).load("http://pic.qiantucdn" +
//                ".com/58pic/32/79/89/86558PICb6Mc8nV5u28yI_PIC2018.jpg!/fw/1024/watermark/url" +
//                "/L2ltYWdlcy93YXRlcm1hcmsveGlhb3R1LnBuZw==/align/center").into(mAd);
        Glide.with(getActivity()).load("https://img.alicdn" +
                ".com/tfs/TB1RGANyxnaK1RjSZFtXXbC2VXa-1920-700.png").into(mAd);
    }


    @Override
    public void onResume() {
        super.onResume();

        mMySpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mIntent = new Intent(getActivity(), RepertoryActivity.class);
                mIntent.putExtra("fragment_code", GetFragment.MYSPACE_FRAGMENT);
                mIntent.putExtra("title", "我的");
                startActivity(mIntent);

            }
        });

    }


    private void initView() {
        mAd = mRootView.findViewById(R.id.home_fagment_advertis);
//        mCard = mRootView.findViewById(R.id.home_fragment_card);
        mAdare = mRootView.findViewById(R.id.home_fagment_advertising_area);
//        mCard2 = mRootView.findViewById(R.id.home_fragment_card1);
        mAdare2 = mRootView.findViewById(R.id.home_fragmentadvertising_area2);
        mMySpace = mRootView.findViewById(R.id.home_fragment_myspace);
        mHeadad1 = mRootView.findViewById(R.id.home_fragment_img_card1);
        mHeadad2 = mRootView.findViewById(R.id.home_fragment_img_card2);
        mHeadad3 = mRootView.findViewById(R.id.home_fragment_img_card3);
        mhitiBar = mRootView.findViewById(R.id.acctionbar);
        mMySpaceTop = mRootView.findViewById(R.id.home_title_myspace);

        ListenerScrollView = mRootView.findViewById(R.id.scrollView1);

        mlistView = mRootView.findViewById(R.id.home_listview);

        mlistView.setAdapter(new HomeListAdapter(application.getmVegetables(), getActivity(), new
                IonPositionClick() {


                    @Override
                    public void onItemClick(JSONObject jsonObject, int position) {
                        application.setCurrentGood(jsonObject);
                        Utils.startFragment(getContext(), GetFragment.PRODUCTINFO_FRAGMENT, "商品详情");
                    }

                    @Override
                    public void onSettingClick(JSONObject jsonObject, int position) {

                    }

                    @Override
                    public void onDeleteClick(JSONObject jsonObject, int position) {

                    }
                }));
        mlistView.setDividerHeight(0);


        mHotSellrecyclerView = mRootView.findViewById(R.id.home_fragment_recyclerview);
        mPromoteRecyclerView = mRootView.findViewById(R.id.home_fragment_recyclerview2);
        mHotSellLayoutManager = new LinearLayoutManager(getActivity());
        mPromoteLayoutManager = new LinearLayoutManager(getActivity());

        mHotSellLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPromoteLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPromoteLayoutManager.setSmoothScrollbarEnabled(true);
        mHotSellLayoutManager.setSmoothScrollbarEnabled(true);
        mHotSellrecyclerView.setLayoutManager(mHotSellLayoutManager);
        mPromoteRecyclerView.setLayoutManager(mPromoteLayoutManager);

        mHotSellAdapter = new RecyclerViewAdapter(getActivity(), application.getmRecommends());
        mPromoteAdapter = new RecyclerViewAdapter(getActivity(), application.getmVegetables());

        mHotSellrecyclerView.setAdapter(mHotSellAdapter);
        mPromoteRecyclerView.setAdapter(mPromoteAdapter);

        mHotSellAdapter.setOnItemClickListener(new RecyclerViewAdapter
                .OnItemClickListener() {
            @Override
            public void onItemClickListener(JSONObject good) {
                application.setCurrentGood(good);
                startActy(good);
            }
        });

        mPromoteAdapter.setOnItemClickListener(new RecyclerViewAdapter
                .OnItemClickListener() {
            @Override
            public void onItemClickListener(JSONObject good) {
                application.setCurrentGood(good);
                startActy(good);
            }
        });
        mMySpaceTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mIntent = new Intent(getActivity(), RepertoryActivity.class);
                mIntent.putExtra("fragment_code", GetFragment.MYSPACE_FRAGMENT);
                mIntent.putExtra("title", "我的");
                startActivity(mIntent);
            }
        });

    }

    private void startActy(JSONObject good) {
        mIntent = new Intent(getActivity(), RepertoryActivity.class);
        mIntent.putExtra("fragment_code", GetFragment.PRODUCTINFO_FRAGMENT);
        mIntent.putExtra("name", good.optString("name"));
        mIntent.putExtra("price", good.optString("price"));
        mIntent.putExtra("image", good.optString("image"));
        mIntent.putExtra("title", good.optString("name"));
        startActivity(mIntent);
    }

    private void startFragment(int productFragment, String string) {
        mIntent = new Intent(getActivity(), RepertoryActivity.class);
        mIntent.putExtra("fragment_code", productFragment);
        mIntent.putExtra("title", string);
        startActivity(mIntent);
    }

    @Override
    public void onScrollChanged(ListenerScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y >= mAdbottomPosition) {
            //todo 设置actionBar显示
            mhitiBar.setVisibility(View.VISIBLE);
        } else {
            // todo 设置actionBar隐藏
            mhitiBar.setVisibility(View.GONE);
        }
    }

}
