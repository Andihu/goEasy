package com.xy.rcs.goeasy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.xy.rcs.goeasy.BombBean.Sorts;
import com.xy.rcs.goeasy.MyApplication;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.RepertoryActivity;
import com.xy.rcs.goeasy.adapter.GrildViewAdapter;
import com.xy.rcs.goeasy.adapter.IOnGridViewItemClick;
import com.xy.rcs.goeasy.adapter.ListviewAdapter;
import com.xy.rcs.goeasy.utils.GetFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class ProductFragment extends Fragment implements IOnGridViewItemClick {

    private static final String TAG = "ProductFragment";

    public static final int BAGS = 6;
    public static final int PHONE = 4;
    public static final int BOOKS = 9;
    public static final int JEWELRY = 5;
    public static final int COMPUTER = 3;
    public static final int MENCLOTH = 8;
    public static final int MANTERNAL = 7;
    public static final int RECOMMEND = 1;
    public static final int UNDERWEAR = 0;
    public static final int APPLIANCES = 2;
    public static final int VEGETABLES = 10;

    private View mRootView;
    private ListView mMainListView;
    private MyApplication application;
    private ListviewAdapter mMainLsAdapter;
    private GrildViewAdapter mChildGdAdapter;
    private List<JSONObject> mSorts;
    private List<JSONObject> mBooks;
    private List<JSONObject> mBags;
    private List<JSONObject> mPhones;
    private List<JSONObject> mJewelry;
    private List<JSONObject> mMaternal;
    private List<JSONObject> mMenCloth;
    private List<JSONObject> mComputers;
    private List<JSONObject> mUnderwear;
    private List<JSONObject> mAppliances;
    private List<JSONObject> mRecommends;
    private List<JSONObject> mVegetables;

    protected Intent mIntent;
    protected GridView mCildGridView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_product, container, false);
        initView();
        getMianListviwData();
        listViewOnitemClick();
        return mRootView;

    }


    private void listViewOnitemClick() {
        //设置高亮
        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMainLsAdapter.setHighetlight(position);
                mMainLsAdapter.notifyDataSetChanged();

                Log.e("position", "onItemClick: " + position);
                setdata(position);
            }
        });
    }

    private void initView() {
        application = (MyApplication) Objects.requireNonNull(getActivity()).getApplication();
        mBooks = application.getmBooks();
        mBags = application.getmBags();
        mPhones = application.getmPhones();
        mJewelry = application.getmJewelry();
        mMaternal = application.getmMaternal();
        mMenCloth = application.getmMenCloth();
        mComputers = application.getmComputers();
        mUnderwear = application.getmUnderwear();
        mAppliances = application.getmAppliances();
        mRecommends = application.getmRecommends();
        mVegetables = application.getmVegetables();

        mMainListView = mRootView.findViewById(R.id.product_framgment_mian_listview);
        mCildGridView = mRootView.findViewById(R.id.product_framgment_child_listview);

        mMainListView.setDividerHeight(0);

        mChildGdAdapter = new GrildViewAdapter(getActivity(), application.getmRecommends(), this);
        mCildGridView.setAdapter(mChildGdAdapter);
    }


    @Override
    public void onItemClick(JSONObject jsonObject, int position) {
        Log.e(TAG, "doClick: " + jsonObject);
        application.setCurrentGood(jsonObject);
        startActy(jsonObject);
    }

    void setdata(int position) {
        switch (position) {
            case RECOMMEND:
                mChildGdAdapter.setmGoods(mRecommends);
                mChildGdAdapter.notifyDataSetChanged();
                break;
            case UNDERWEAR:
                mChildGdAdapter.setmGoods(mUnderwear);
                mChildGdAdapter.notifyDataSetChanged();
                break;
            case APPLIANCES:
                mChildGdAdapter.setmGoods(mAppliances);
                mChildGdAdapter.notifyDataSetChanged();
                break;
            case COMPUTER:

                mChildGdAdapter.setmGoods(mComputers);
                mChildGdAdapter.notifyDataSetChanged();
                break;
            case PHONE:

                mChildGdAdapter.setmGoods(mPhones);
                mChildGdAdapter.notifyDataSetChanged();
                break;
            case JEWELRY:
                mChildGdAdapter.setmGoods(mJewelry);
                mChildGdAdapter.notifyDataSetChanged();
                break;
            case BAGS:
                mChildGdAdapter.setmGoods(mBags);
                mChildGdAdapter.notifyDataSetChanged();
                break;
            case MENCLOTH:
                mChildGdAdapter.setmGoods(mMenCloth);
                mChildGdAdapter.notifyDataSetChanged();
                break;
            case MANTERNAL:
                mChildGdAdapter.setmGoods(mMaternal);
                mChildGdAdapter.notifyDataSetChanged();
                break;
            case BOOKS:
                mChildGdAdapter.setmGoods(mBooks);
                mChildGdAdapter.notifyDataSetChanged();
                break;
            case VEGETABLES:
                mChildGdAdapter.setmGoods(mVegetables);
                mChildGdAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void getMianListviwData() {
        mSorts = new ArrayList<>();
        BmobQuery<Sorts> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Sorts>() {
            @Override
            public void done(List<Sorts> list, BmobException e) {
                JSONObject json;
                for (Sorts sor : list) {
                    json = new JSONObject();
                    try {
                        json.put("name", sor.getSortName());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    mSorts.add(json);
                }
                mMainLsAdapter = new ListviewAdapter(getActivity(), mSorts, ListviewAdapter
                        .TYPE_MAIN);
                mMainLsAdapter.setHighetlight(1);
                if (e == null) {
                    mMainListView.setAdapter(mMainLsAdapter);
                }
            }
        });
    }

    private void startActy(JSONObject good) {
        mIntent = new Intent(getActivity(), RepertoryActivity.class);
        mIntent.putExtra("fragment_code", GetFragment.PRODUCTINFO_FRAGMENT);
        mIntent.putExtra("title", good.optString("name"));
        startActivity(mIntent);
    }

}






