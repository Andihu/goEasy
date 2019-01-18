package com.xy.rcs.goeasy.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.RepertoryActivity;
import com.xy.rcs.goeasy.adapter.IonPositionClick;
import com.xy.rcs.goeasy.adapter.PositionAdapter;
import com.xy.rcs.goeasy.db.OrderDao;
import com.xy.rcs.goeasy.db.OrderHelper;
import com.xy.rcs.goeasy.utils.GetFragment;
import com.xy.rcs.goeasy.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PositionListFragment extends Fragment implements IonPositionClick {
    private static final String TAG;
    public static final int RESULT_CODE = 2;

    static {
        TAG = "PositionListFragment";
    }

    public static final String ID = "_id";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String DETAIL_ADDRESS = "detail_address";
    public static final String RECEIVER = "receiver";
    private static final int request = -1;

    private View mRootView;
    private RelativeLayout mNoPosition;
    private ListView mPositionList;
    private PositionAdapter adapter;
    private LinearLayout ll;
    private Context context;
    private List<JSONObject> positons;

    public PositionListFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_position_list, container, false);
        context = getActivity();
        bindView();
        List<JSONObject> mpositons = OrderHelper.queryPosition(context);
        if (mpositons==null){
            adapter = new PositionAdapter(new ArrayList<JSONObject>(), context, this);
        }else {
            adapter = new PositionAdapter(mpositons, context, this);
        }

        setfootView();
        mPositionList.setAdapter(adapter);
        mPositionList.setDividerHeight(0);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(context, GetFragment.POSITION_SETTING_FRAGMENT, null);
            }
        });

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        positons = OrderHelper.queryPosition(context);
        if (positons == null) {
            adapter.getPositons().clear();
            adapter.setPositons(new ArrayList<JSONObject>());
        } else {
            adapter.setPositons(positons);
        }
        adapter.notifyDataSetChanged();
    }

    private void setfootView() {
        ll = new LinearLayout(context);
        ll.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                Utils.dip2px(context, 80)));
        ll.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(context);

        AbsListView.LayoutParams imgpareams = new AbsListView.LayoutParams(Utils.dip2px
                (context, 40), Utils.dip2px(context, 40));



        imageView.setLayoutParams(imgpareams);
        imageView.setImageResource(R.drawable.ic_add);
        ll.addView(imageView);
        mPositionList.addFooterView(ll);
    }

    private void bindView() {
        mNoPosition = mRootView.findViewById(R.id.position_no_position);
        mPositionList = mRootView.findViewById(R.id.position_list_listView);
    }


    @Override
    public void onItemClick(JSONObject jsonObject, int position) {

        Log.e(TAG, "onItemClick: " + jsonObject.toString());
        //todo 返回结果

        Intent intent = this.getActivity().getIntent();
        if (intent.getExtras().getBoolean("isresult")) {
            intent.putExtra(PHONE, jsonObject.optString("phone"));
            intent.putExtra(RECEIVER, jsonObject.optString("receiver"));
            intent.putExtra(ADDRESS, jsonObject.optString("address"));
            intent.putExtra(DETAIL_ADDRESS, jsonObject.optString("detail_address"));
            this.getActivity().setResult(RESULT_CODE, intent);
            getActivity().finish();
        }

    }

    @Override
    public void onSettingClick(JSONObject jsonObject, int position) {

        startFragment(context, GetFragment.POSITION_SETTING_FRAGMENT, jsonObject);

    }

    @Override
    public void onDeleteClick(JSONObject jsonObject, int position) {
        OrderHelper.deletePositionbyID(context, jsonObject.optString(OrderDao.ID));
        adapter.setPositons(OrderHelper.queryPosition(context));
        adapter.notifyDataSetChanged();
    }

    public static void startFragment(Context context, int productFragment, JSONObject position) {
        Intent mIntent = new Intent(context, RepertoryActivity.class);
        mIntent.putExtra("fragment_code", productFragment);
        if (position != null) {
            mIntent.putExtra("title", "修改地址");
            mIntent.putExtra(ID, position.optString("_id"));
            mIntent.putExtra(PHONE, position.optString("phone"));
            mIntent.putExtra(RECEIVER, position.optString("receiver"));
            mIntent.putExtra(ADDRESS, position.optString("address"));
            mIntent.putExtra(DETAIL_ADDRESS, position.optString("detail_address"));
        } else {
            mIntent.putExtra("title", "添加地址");
        }
        context.startActivity(mIntent);
    }

}
