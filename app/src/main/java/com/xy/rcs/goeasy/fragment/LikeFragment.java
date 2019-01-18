package com.xy.rcs.goeasy.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.xy.rcs.goeasy.MyApplication;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.adapter.IOnLikeOrderClickListener;
import com.xy.rcs.goeasy.adapter.LikeListViewAdapter;
import com.xy.rcs.goeasy.db.OrderDao;
import com.xy.rcs.goeasy.db.OrderHelper;
import com.xy.rcs.goeasy.utils.GetFragment;
import com.xy.rcs.goeasy.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeFragment extends Fragment implements IOnLikeOrderClickListener {

    private View mRootView;
    private ListView mLikeList;
    private TextView mSelectAll;
    private TextView mDelete;
    private TextView mCancal;
    private RelativeLayout mWrong;
    private LinearLayout mMenu;
    private List<JSONObject> mLikes;
    private LikeListViewAdapter adapter;
    private WeakHashMap<Integer, Boolean> mSelect;
    private static final String TAG = "LikeFragment";

    private MyApplication application;

    private List<JSONObject> mSelectData;

    public LikeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_like, container, false);
        application= (MyApplication) getActivity().getApplication();
        bindVidew();
        initView();
        return mRootView;
    }


    private void initView() {
        OrderDao orderDao = new OrderDao(getActivity());
        mSelect = new WeakHashMap<>();
        mSelectData = new ArrayList<>();
        mLikes = OrderHelper.queryLiskOrder(getActivity());

        Log.e(TAG, "initView: " + mLikes);

        if (mLikes == null || mLikes.size() <= 0) {
            mWrong.setVisibility(View.VISIBLE);
            mMenu.setVisibility(View.GONE);
        } else {
            mWrong.setVisibility(View.GONE);
            mMenu.setVisibility(View.VISIBLE);
            adapter = new LikeListViewAdapter(mLikes, getActivity(), mSelect, this);
            mLikeList.setAdapter(adapter);
        }
        mCancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setMultSelectStatus(false);
                mDelete.setVisibility(View.GONE);
                adapter.initSelected();
                adapter.notifyDataSetChanged();
                mSelectData.clear();
            }
        });

        mSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelete.setVisibility(View.VISIBLE);
                adapter.setMultSelectStatus(true);
                adapter.initSelectedTrue();
                adapter.notifyDataSetChanged();
                mSelectData = adapter.getMlikes();
                showSelect();
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getMlikes().clear();
                adapter.setMlikes(new ArrayList<JSONObject>());
                adapter.notifyDataSetChanged();
                OrderHelper.deleteAllLike(getActivity());
            }
        });
    }

    private void bindVidew() {

        mCancal = mRootView.findViewById(R.id.cancal);
        mWrong = mRootView.findViewById(R.id.wrong);
        mLikeList = mRootView.findViewById(R.id.like_listview);
        mMenu = mRootView.findViewById(R.id.like_content);
        mSelectAll = mRootView.findViewById(R.id.like_select_all);
        mDelete = mRootView.findViewById(R.id.like_delete);
        mLikeList.setDividerHeight(0);
    }

    @Override
    public void onDeletClick(int position, JSONObject jsonObject) {
        OrderHelper.deletLikeOrder(getActivity(), jsonObject.optString(OrderDao.ID));

        if (mLikes.contains(jsonObject)){
            mLikes.remove(jsonObject);
        }

        adapter.setMlikes(mLikes);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onListItemLongClick(int position, JSONObject jsonObject, boolean selectStutus,
                                    boolean multSelect) {
        adapter.setMultSelectStatus(true);

        if (multSelect) {
            if (selectStutus) {
                adapter.setmSelected(position, false);
                adapter.notifyDataSetChanged();
            } else {
                adapter.setmSelected(position, true);
                adapter.notifyDataSetChanged();
            }
            addSelected(jsonObject);
        }

        showSelect();

    }

    @Override
    public void onListItemClick(int position, JSONObject jsonObject, boolean selectStutus,
                                boolean multSelect) {
        if (multSelect) {
            if (selectStutus) {
                adapter.setmSelected(position, false);
                adapter.notifyDataSetChanged();
            } else {
                adapter.setmSelected(position, true);
                adapter.notifyDataSetChanged();
            }
            addSelected(jsonObject);
        } else {



            //todo 跳转详情页
            application.setCurrentGood(jsonObject);

            Utils.startFragment(getActivity(), GetFragment.PRODUCTINFO_FRAGMENT,"商品详情");

        }

    }

    private void addSelected(JSONObject jsonObject) {
        if (mSelectData.contains(jsonObject)) {
            mSelectData.remove(jsonObject);
        } else {
            mSelectData.add(jsonObject);
        }
    }
    
    private void showSelect() {
        Log.e(TAG, "showSelect: " + mSelectData.size());
    }

}
