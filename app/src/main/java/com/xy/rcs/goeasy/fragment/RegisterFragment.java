package com.xy.rcs.goeasy.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.xy.rcs.goeasy.BombBean.User;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.module.IRegisterImpl;
import com.xy.rcs.goeasy.utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";


    private AutoCompleteTextView mphone;

    private AutoCompleteTextView mCode;

    private AutoCompleteTextView mPassword;

    private AutoCompleteTextView mVerifyPassword;

    private IRegisterImpl RegisterImpl;

    private TextView mRequestCode;

    private TextView mToReginter;

    private View mRootview;

    private String phone;

    private String code;

    private String psw;

    private String Vpsw;

    ProgressDialog progressDialog;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootview = inflater.inflate(R.layout.fragment_register, container, false);
        RegisterImpl = new IRegisterImpl();

        bindID();

        mRequestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mphone.getText().toString())) {
                    mphone.setError("手机号不可为空");
                } else {
                    if (isPhoneNumberValid(mphone.getText().toString())) {
                        //   RegisterImpl.requestSMS(getActivity(),mphone.getText().toString());

                        BmobSMS.requestSMSCode(mphone.getText().toString(), "", new
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
                        mphone.setError("请输入正确的手机号格式");
                    }
                }
            }
        });


        mToReginter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo();
                if (TextUtils.isEmpty(phone) || !isPhoneNumberValid(phone)) {
                    mphone.setError("请正确输入");
                } else if ((TextUtils.isEmpty(code))) {
                    mCode.setError("请输入验证码");
                } else if (TextUtils.isEmpty(psw)) {
                    mPassword.setError("请输入密码");
                } else if (TextUtils.isEmpty(Vpsw)) {
                    mVerifyPassword.setError("请输入密码");
                } else if (TextUtils.equals(psw, Vpsw)) {
                    mVerifyPassword.setError("请确认密码");
                } else {

                    progressDialog.show();

                    register();
                }

            }
        });

        return mRootview;
    }

    private void register() {
        String username = mphone.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String verify = mVerifyPassword.getText().toString().trim();
        String code = mCode.getText().toString().trim();

        User user = new User();
        //TODO 设置手机号码（必填）
        user.setMobilePhoneNumber(phone);
        //TODO 设置用户名，如果没有传用户名，则默认为手机号码
        user.setUsername(username);
        //TODO 设置用户密码
        user.setPassword(password);

        user.signOrLogin(code, new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Utils.Toast(getActivity(), "短信注册成功：" + bmobUser.getUsername());
                    progressDialog.dismiss();
                } else {
                    Log.e(TAG, "done: " + e.getErrorCode() + "-" + e.getMessage());
                    Utils.Toast(getActivity(), "短信注册失败：" + e.getErrorCode() + "-" + e.getMessage
                            () + "\n");
                }
            }
        });
    }


    private void bindID() {
        mVerifyPassword = mRootview.findViewById(R.id.register_verify_password);
        mRequestCode = mRootview.findViewById(R.id.register_request_code);
        mToReginter = mRootview.findViewById(R.id.register_to_register);
        mPassword = mRootview.findViewById(R.id.register_password);
        mphone = mRootview.findViewById(R.id.register_phone);
        mCode = mRootview.findViewById(R.id.register_code);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("验证手机号");
    }

    public void getInfo() {
        phone = mphone.getText().toString();
        code = mphone.getText().toString();
        psw = mphone.getText().toString();
        Vpsw = mVerifyPassword.getText().toString();
    }


    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
        String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        Pattern pattern2 = Pattern.compile(expression2);
        Matcher matcher2 = pattern2.matcher(inputStr);
        if (matcher.matches() || matcher2.matches()) {
            isValid = true;
        }
        return isValid;
    }


}
