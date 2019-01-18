package com.xy.rcs.goeasy.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xy.rcs.goeasy.MyApplication;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.RegisterActivity;
import com.xy.rcs.goeasy.RepertoryActivity;
import com.xy.rcs.goeasy.adapter.OrderInfoAdapetr;
import com.xy.rcs.goeasy.utils.GetFragment;
import com.xy.rcs.goeasy.widget.ChildLiistView;
import com.xy.rcs.goeasy.widget.ListenerScrollView;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderInfoFragment extends Fragment {

    private static final String TAG = "OrderInfoFragment";
    public static final int REQUEST_CODE = 1;
    protected MyApplication application;
    protected View mRootView;
    private List<JSONObject> mOrders;
    private ChildLiistView listView;
    private TextView topay;
    private OrderInfoAdapetr adapter;
    private LinearLayout linearLayout;
    private TextView phone;
    private TextView position;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_order_info, container, false);
        getOrder();
        initView();
        click();

        return mRootView;
    }

    private void click() {

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RepertoryActivity.class);
                intent.putExtra("fragment_code", GetFragment.POSITION_LIST_FRAGMENT);
                intent.putExtra("title", "地址信息");
                intent.putExtra("isresult", true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void initView() {
        listView = mRootView.findViewById(R.id.order_info_listview);
        topay = mRootView.findViewById(R.id.topayment);
        phone = mRootView.findViewById(R.id.order_info_info);
        position = mRootView.findViewById(R.id.order_info_potion);
        linearLayout = mRootView.findViewById(R.id.order_info_setposition);
        adapter = new OrderInfoAdapetr(getActivity(), mOrders);
        listView.setAdapter(adapter);
        listView.setDividerHeight(0);
    }

    private void getOrder() {
        application = (MyApplication) Objects.requireNonNull(getActivity()).getApplication();
        mOrders = application.getCurOrders();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == PositionListFragment.RESULT_CODE) {
            phone.setText(Objects.requireNonNull(data.getExtras()).getString("phone"));
            position.setText(data.getExtras().getString("address") + data.getExtras().getString
                    ("detail_address"));
        }

    }
}
