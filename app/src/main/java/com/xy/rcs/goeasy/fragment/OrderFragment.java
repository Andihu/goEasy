package com.xy.rcs.goeasy.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.adapter.IorderClick;
import com.xy.rcs.goeasy.adapter.OrderListAdapter;
import com.xy.rcs.goeasy.db.OrderDao;
import com.xy.rcs.goeasy.db.OrderHelper;
import com.xy.rcs.goeasy.utils.GetFragment;
import com.xy.rcs.goeasy.utils.Utils;

import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements IorderClick {
    private static final String TAG = "OrderFragment";


    private View mRootView;
    private ListView listView;
    private List<JSONObject> mOrders;
    private OrderListAdapter orderListAdapter;
    private RelativeLayout mNoOrderShow;


    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_order, container, false);
        listView=mRootView.findViewById(R.id.main_listvie);
        mNoOrderShow=mRootView.findViewById(R.id.order_no_order);


        mOrders = OrderHelper.queryOrderMain(getActivity());
        if (mOrders==null){
            mNoOrderShow.setVisibility(View.VISIBLE);
        }else {
            mNoOrderShow.setVisibility(View.GONE);
            orderListAdapter = new OrderListAdapter(mOrders,getActivity(),this);

            listView.setAdapter(orderListAdapter);

            listView.setDividerHeight(0);
        }
        return mRootView;
    }

    @Override
    public void ondeleteClick(JSONObject js, int position) {
        orderListAdapter.getJsonObjects().remove(js);
        OrderHelper.deleteOrder(getActivity(),js.optString("_id"));
        orderListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStatusClick(List<JSONObject> orders, int position) {
        Utils.Toast(getActivity(),orders.get(position).optString("total"));
        Utils.startFragment(getActivity(), GetFragment.PAY_FRAGMENT,"支付");
    }
}
