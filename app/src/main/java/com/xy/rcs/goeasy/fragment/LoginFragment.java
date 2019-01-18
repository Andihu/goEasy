package com.xy.rcs.goeasy.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xy.rcs.goeasy.AdActivity;
import com.xy.rcs.goeasy.BombBean.User;
import com.xy.rcs.goeasy.LoginActivity;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.module.ILoginListener;
import com.xy.rcs.goeasy.module.LoginImpl;
import com.xy.rcs.goeasy.utils.Utils;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.xy.rcs.goeasy.fragment.RegisterFragment.isPhoneNumberValid;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private AutoCompleteTextView name;
    private AutoCompleteTextView password;
    private RelativeLayout mPasswordlogin;
    private RelativeLayout mPhoneLogin;
    private TextView textView;
    private CheckBox checkBox;
    private TextView textView1;

    private AutoCompleteTextView phone;
    private AutoCompleteTextView code;
    private TextView getCode;

    private View mRootView;
    private SharedPreferences.Editor editor;
    private SharedPreferences share;
    private SharedPreferences.Editor editor1;

    public LoginFragment() {
    }
    private void keepdata() {
        share = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        editor1 = share.edit();
        name.setText(share.getString("name",""));
        password.setText(share.getString("password",""));
        checkBox.setChecked(share.getBoolean("check",false));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_login, container, false);

        init();

        keepdata();

        if (share.getBoolean("isLogin",false)){
            startActivity(new Intent(getActivity(), AdActivity.class));
        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    editor1.putBoolean("check", isChecked);
                    editor1.putString("name",name.getText().toString());
                    editor1.putString("password",password.getText().toString());
                    editor1.commit();
                } else {
                    editor1.putBoolean("check", isChecked);
                    editor1.putString("name", "");
                    editor1.putString("password", "");
                    editor1.commit();
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView1.getText().toString().equals("账号登录")) {
                    LoginImpl login = new LoginImpl(getActivity());
                    login.login(name.getText().toString().trim(), password.getText().toString()
                            .trim(), new ILoginListener() {
                        @Override
                        public void success() {
                            startActivity(new Intent(getActivity(), AdActivity.class));
                            editor1.putBoolean("isLogin",true);
                            editor1.commit();
                        }
                        @Override
                        public void failed() {

                        }
                    });
                } else {


                    String phone1 = phone.getText().toString().trim();
                    if (TextUtils.isEmpty(phone1)) {
                        Toast.makeText(getActivity(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String code1 = code.getText().toString().trim();
                    if (TextUtils.isEmpty(code1)) {
                        Toast.makeText(getActivity(), "请输入验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BmobUser.signOrLoginByMobilePhone(phone1, code1, new LogInListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                Utils.Toast(getActivity(), "短信注册或登录成功：" + bmobUser.getUsername());
                                startActivity(new Intent(getActivity(), AdActivity.class));

                                editor1.putBoolean("isLogin",true);
                                editor1.commit();
                            } else {
                                Utils.Toast(getActivity(), "短信注册或登录失败：" + e.getErrorCode() + "-"
                                        + e.getMessage() + "\n");
                            }
                        }
                    });

                }

            }
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView1.getText().equals("账号登录")) {
                    textView1.setText("手机号登录");
                    mPasswordlogin.setVisibility(View.GONE);
                    mPhoneLogin.setVisibility(View.VISIBLE);
                } else {
                    textView1.setText("账号登录");
                    mPasswordlogin.setVisibility(View.VISIBLE);
                    mPhoneLogin.setVisibility(View.GONE);
                }
            }
        });

        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(phone.getText().toString())) {
                    phone.setError("手机号不可为空");
                } else {
                    if (isPhoneNumberValid(phone.getText().toString())) {
                        //   RegisterImpl.requestSMS(getActivity(),mphone.getText().toString());

                        BmobSMS.requestSMSCode(phone.getText().toString(), "", new
                                QueryListener<Integer>() {
                                    @Override
                                    public void done(Integer smsId, BmobException e) {
                                        if (e == null) {
                                            Utils.Toast(getActivity(), "发送验证码成功，短信ID：" + smsId +
                                                    "\n");
                                        } else {
                                            Utils.Toast(getActivity(), "发送验证码失败：" + e
                                                    .getErrorCode() +
                                                    "-" + e.getMessage() + "\n");
                                        }
                                    }
                                });
                    } else {
                        phone.setError("请输入正确的手机号格式");
                    }
                }

            }
        });

        return mRootView;
    }



    private void init() {
        name = mRootView.findViewById(R.id.name);
        password = mRootView.findViewById(R.id.password1);
        textView = mRootView.findViewById(R.id.login);
        mPasswordlogin = mRootView.findViewById(R.id.psddenlu);
        mPhoneLogin = mRootView.findViewById(R.id.denlu_phone);
        textView1 = mRootView.findViewById(R.id.login_btn_phone);
        checkBox = mRootView.findViewById(R.id.login_checkbox);
        phone = mRootView.findViewById(R.id.login_phone1);
        code = mRootView.findViewById(R.id.login_code1);
        getCode = mRootView.findViewById(R.id.register_request_code1);
    }
}
