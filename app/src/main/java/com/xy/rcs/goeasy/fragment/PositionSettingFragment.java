package com.xy.rcs.goeasy.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.RepertoryActivity;
import com.xy.rcs.goeasy.db.OrderDao;
import com.xy.rcs.goeasy.db.OrderHelper;
import com.xy.rcs.goeasy.utils.GetFragment;
import com.xy.rcs.goeasy.utils.Utils;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.xy.rcs.goeasy.utils.Utils.startFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PositionSettingFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "PositionSettingFragment";

    private View mRootView;
    private Intent mIntent;
    private LinearLayout imageView;
    private EditText mAddress;
    private AutoCompleteTextView mRecevices;
    private AutoCompleteTextView mPhone;
    private AutoCompleteTextView mDetailed_address;

    private CityPickerView mcityPicker;
    private FragmentActivity context;


    private String id;
    private String name;
    private String phone;
    private String address;
    private String detail_Address;

    private boolean isAddDate = false;
    private ActivityOptions options;

    public PositionSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_position_setting, container, false);

        context = getActivity();

        mIntent = context.getIntent();


        initView();
        setData();

        return mRootView;
    }

    private void setData() {
        if (mIntent != null) {
            if ((id = mIntent.getExtras().getString(PositionListFragment.ID)) != null) {

                name = mIntent.getExtras().getString(PositionListFragment.RECEIVER);
                phone = mIntent.getExtras().getString(PositionListFragment.PHONE);
                address = mIntent.getExtras().getString(PositionListFragment.ADDRESS);
                detail_Address = mIntent.getExtras().getString(PositionListFragment.DETAIL_ADDRESS);
                mRecevices.setText(name);
                mPhone.setText(phone);
                mDetailed_address.setText(detail_Address);
                mAddress.setText(address);
            }else {
                isAddDate = true;
            }
        } else {
            context.finish();
        }
    }

    private void initView() {
        mAddress = mRootView.findViewById(R.id.address);
        mAddress.setOnClickListener(this);
        imageView = mRootView.findViewById(R.id.position_setting_image);
        mcityPicker = new CityPickerView();
        mcityPicker.init(getActivity());
        mRecevices = mRootView.findViewById(R.id.receiver);
        mPhone = mRootView.findViewById(R.id.phone);
        mDetailed_address = mRootView.findViewById(R.id.deta_address);
        imageView.setOnClickListener(this);

    }
    void setAnimate(){
        Transition transition=new Explode();
        getActivity().getWindow().setEnterTransition(transition);
        getActivity().getWindow().setExitTransition(transition);
        Pair<LinearLayout,String> share=Pair.create(imageView,"img");
        options = ActivityOptions.makeSceneTransitionAnimation(this.getActivity(), new Pair[]{share});


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address:
                CityConfig cityConfig = new CityConfig.Builder().build();
                mcityPicker.setConfig(cityConfig);
                mcityPicker.showCityPicker();
                mcityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean
                            district) {
                        super.onSelected(province, city, district);

                        String answer = province.getName() + city.getName() + district.getName();
                        mAddress.setText(answer);
                    }
                });

                break;
            case R.id.position_setting_image:
                String receiver, phone, address, detail_address;
                receiver = mRecevices.getText().toString();
                phone = mPhone.getText().toString();
                address = mAddress.getText().toString();
                detail_address = mDetailed_address.getText().toString();
                if (TextUtils.isEmpty(receiver)) {
                    mRecevices.setError("请输入数据");
                } else if (TextUtils.isEmpty(phone)) {
                    mPhone.setError("请输入数据");
                } else if (TextUtils.isEmpty
                        (address)) {
                    mAddress.setError("请输入数据");
                } else if (TextUtils.isEmpty(detail_address)) {
                    mDetailed_address.setError("请输入数据");
                } else {
                    if (isAddDate) {
                        OrderHelper.insertPosition(context, receiver, phone, address,
                                detail_address);
                        isAddDate=false;
                        setAnimate();

                        Intent mIntent= new Intent(context, RepertoryActivity.class);
                        mIntent.putExtra("fragment_code", GetFragment.TEMP_FRAGMENT);
                        startActivity(mIntent,options.toBundle());

                     //   startFragment(getActivity(), GetFragment.TEMP_FRAGMENT,null);
                    } else {
                        OrderHelper.updatePosition(context, id, receiver, phone, address,
                                detail_address);

                        getActivity().finish();

                    }
                }
                break;
            default:

                break;
        }
    }
}
