package com.xy.rcs.goeasy.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jayhu.HuImageLoader;
import com.example.jayhu.JayHuLoader;
import com.xy.rcs.goeasy.MyApplication;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.RepertoryActivity;
import com.xy.rcs.goeasy.db.OrderHelper;
import com.xy.rcs.goeasy.utils.Constant;
import com.xy.rcs.goeasy.utils.GetFragment;
import com.xy.rcs.goeasy.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;


public class ProductInfoFragment extends Fragment {

    private static final String TAG = "ProductInfoFragment";


    private View mRootView;
    private Intent intent;
    private Button mAddtoCart;
    private TextView mInfoName;
    private TextView mInfoPrice;
    private TextView mInfoContent;
    private TextView mSize;
    private TextView mScannig;
    private TextView mdiscount;
    private TextView position;
    private ImageView mInfoImg;
    private JSONObject mGood;
    private LinearLayout mCollation;
    private MyApplication application;
    private AlertDialog.Builder alertDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_product_info, container, false);
        intent = getActivity().getIntent();
        initView();
        setView();
        toClick();
        return mRootView;
    }

    private void toClick() {
        mAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addCart();
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mCollation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderHelper.addLikeOrder(getActivity(), mGood);
                Utils.Toast(getActivity(), "收藏成功！");
            }
        });
        mScannig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startFragment(getActivity(),GetFragment.SWEEP_FRAGMENT,"扫码购");
            }
        });
    }

    private void addCart() throws JSONException {

        mGood.put("amount", 1);
        mGood.put("checkboxstaus", true);
        application.addCart(mGood);
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("提示").setMessage("您已添加购物车查看，前往购物车查看").setNegativeButton("前往查看", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.startFragment(getActivity(), GetFragment.GOODSCART_FRAGMENT, "我的购物车");
                    }
                }).setPositiveButton("取消", null);

        application = (MyApplication) getActivity().getApplication();
        mGood = application.getCurrentGood();
        mSize = mRootView.findViewById(R.id.info_size);
        position = mRootView.findViewById(R.id.info_position);
        mInfoImg = mRootView.findViewById(R.id.info_img);
        mdiscount = mRootView.findViewById(R.id.info_discount);
        mInfoName = mRootView.findViewById(R.id.info_name);
        mCollation = mRootView.findViewById(R.id.collation);
        mInfoPrice = mRootView.findViewById(R.id.info_price);
        mScannig=mRootView.findViewById(R.id.info_scanning);
        mAddtoCart = mRootView.findViewById(R.id.info_addtocart);
        mInfoContent = mRootView.findViewById(R.id.info_fragment_Content);
    }

    @SuppressLint("SetTextI18n")
    private void setView() {
        mSize.setText(mGood.optString(Constant.TABLE_ROW_MODDEL));
        position.setText(mGood.optString(Constant.TABLE_ROW_POSITION));
        mInfoName.setText(mGood.optString(Constant.TABLE_ROW_NAME));
        mInfoPrice.setText(mGood.optString(Constant.TABLE_ROW_PRICES) + "￥");
        mdiscount.setText(Float.parseFloat(mGood.optString(Constant.TABLE_ROW_DISCOUNT))*100+"%");
        mInfoContent.setText(mGood.optString(Constant.TABLE_ROW_INFO));
     //  Glide.with(getActivity()).load(mGood.optString(Constant.TABLE_ROW_IMG_ICON)).into(mInfoImg);
     //  JayHuLoader.getInstence().with(getActivity()).loadInmage(mGood.optString(Constant.TABLE_ROW_IMG_ICON),mInfoImg);

        HuImageLoader.getInstences().with(getActivity()).Load(mGood.optString(Constant.TABLE_ROW_IMG_ICON),mInfoImg);


    }
}
