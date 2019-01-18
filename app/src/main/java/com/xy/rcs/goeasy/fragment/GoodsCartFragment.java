package com.xy.rcs.goeasy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xy.rcs.goeasy.MyApplication;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.RepertoryActivity;
import com.xy.rcs.goeasy.adapter.IOnListItemClickListener;
import com.xy.rcs.goeasy.adapter.ListviewAdapter;
import com.xy.rcs.goeasy.db.OrderHelper;
import com.xy.rcs.goeasy.utils.Constant;
import com.xy.rcs.goeasy.utils.GetFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsCartFragment extends Fragment {
    protected static final String TAG = "GoodsCartFragment";


    private View mRootView;
    private Intent mIntent;
    private ListView mListView;
    private TextView mToPay;
    private TextView mTotal;
    private MyApplication application;
    private RelativeLayout mNoGoods;
    private ListviewAdapter mLsAdapter;
    private List<JSONObject> mGoods;


    public GoodsCartFragment() {}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_goods_cart, container, false);
        initView();
        setListView();
        onClick();
        return mRootView;
    }


    private void initView() {
        application = (MyApplication) Objects.requireNonNull(getActivity()).getApplication();

        mGoods = application.getCart();

        mToPay = mRootView.findViewById(R.id.acrt_fragment_topay);
        mTotal = mRootView.findViewById(R.id.cart_fragment_total);
        mListView = mRootView.findViewById(R.id.good_cart_fragment_listview);
        mNoGoods=mRootView.findViewById(R.id.goods_cart_order);

    }

    private void setListView() {
        if (mGoods.size()>0) {
            mNoGoods.setVisibility(View.GONE);
            mLsAdapter = new ListviewAdapter(getActivity(), mGoods, ListviewAdapter.TYPE_GOODS_CART);
            mListView.setAdapter(mLsAdapter);
            mListView.setDividerHeight(0);
            float i=0;
            for (JSONObject js:application.getCart()) {
                i=i+ js.optInt("price")*js.optInt("amount")*Float.parseFloat(js.optString(Constant.TABLE_ROW_DISCOUNT));
            }
            mTotal.setText(i+"");
        }else {
            mNoGoods.setVisibility(View.VISIBLE);
        }
    }

    private void onClick() {
        if (mLsAdapter != null) {
            mLsAdapter.setListener(new IOnListItemClickListener.CheckListenerI() {
                @Override
                public void onAddGoodsClick(int position, JSONObject jsonObject) {
                    super.onAddGoodsClick(position, jsonObject);
                    try {
                        jsonObject.put("amount",jsonObject.optInt("amount")+1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    application.addCart(jsonObject);
                    setTotal();
                    setChange();

                }

                @Override
                public void onCheckBoxClick(CompoundButton buttonView, boolean isChecked, int
                        position, JSONObject jsonObject) {
                    super.onCheckBoxClick(buttonView, isChecked, position, jsonObject);

                }
                @Override
                public void onRemoveClick(int position, JSONObject jsonObject) {
                    if(jsonObject.optInt("amount")>1){
                        try {
                            jsonObject.put("amount",jsonObject.optInt("amount")-1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        application.addCart(jsonObject);

                        setTotal();

                    }else {
                        application.removeCart(jsonObject);
                        setTotal();
                    }
                    setChange();
                }
            });
        }

        mToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                application.setCurOrders(mGoods);
                startFragment(GetFragment.ORDER_INFO_FRAGMENT,"订单信息");

                insertOrder(mGoods);

            }
        });
    }

    private void insertOrder(List<JSONObject> orders) {
        OrderHelper.addOrder(getActivity(),mTotal.getText().toString(),orders);
    }

    private void startFragment(int productFragment,String title) {
        mIntent= new Intent(getActivity(), RepertoryActivity.class);
        mIntent.putExtra("fragment_code", productFragment);
        mIntent.putExtra("title", title);
        mIntent.putExtra("money",mTotal.getText());
        startActivity(mIntent);
    }

    private void setTotal() {
        float i=0;
        for (JSONObject js:application.getCart()) {
            i=i+ js.optInt("price")*js.optInt("amount")*Float.parseFloat(js.optString(Constant.TABLE_ROW_DISCOUNT));
            Log.e(TAG, "setTotal: "+i );
        }
        mTotal.setText(i+"");
    }

    private void setChange() {
        mLsAdapter.setmJsons(application.getCart());
        mLsAdapter.notifyDataSetChanged();
    }
}
