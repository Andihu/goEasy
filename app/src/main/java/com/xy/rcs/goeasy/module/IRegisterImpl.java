package com.xy.rcs.goeasy.module;

import android.content.Context;
import android.content.Intent;
import android.icu.lang.UScript;

import com.xy.rcs.goeasy.BombBean.User;
import com.xy.rcs.goeasy.utils.Constant;
import com.xy.rcs.goeasy.utils.Utils;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.b.From.e;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/31/031<p>
 * <p>更改时间：2018/12/31/031<p>
 * <p>版本号：1<p>
 */
public class IRegisterImpl implements IRegister {


    /**
     * 请求发送短信验证码
     * @param phone
     */
    @Override
    public void requestSMS(Context context,String phone){
        BmobSMS.requestSMSCode(phone, "DataSDK", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    //todo 成功
                } else {
                    //todo 失败
                }
            }
        });
    }

    @Override
    /**
     * 验证验证码
     */
    public void verifySmsCode(final Context context, String code, final String phone){

        BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Utils.Toast(context,"验证码验证成功，您可以在此时进行绑定操作！\n");

                    User user = BmobUser.getCurrentUser(User.class);
                    user.setMobilePhoneNumber(phone);
                    user.setMobilePhoneNumberVerified(true);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Utils.Toast(context,"绑定手机号码成功");
                            } else {
                                Utils.Toast(context,"绑定手机号码失败：" + e.getErrorCode() + "-" + e.getMessage());
                            }
                        }
                    });
                } else {
                    Utils.Toast(context,"验证码验证失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                }
            }
        });
    }


    @Override
    public void register(final Context context, String phone, String password) {
        User user = new User();
        //设置手机号码（必填）
        user.setMobilePhoneNumber(phone);
        //设置用户名，如果没有传用户名，则默认为手机号码
        user.setUsername(phone);
        //设置用户密码
        user.setPassword(password);
        user.signOrLogin(password, new SaveListener<User>() {

            @Override
            public void done(User user,BmobException e) {
                if (e == null) {
                    Utils.Toast(context,"短信注册或登录成功：" + user.getUsername());
                } else {
                    Utils.Toast(context,"短信注册或登录失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                }
            }
        });
    }

    public void signOrLogin(final Context context, String phone, String code, String password) {
        final User user = new User();
        //设置手机号码（必填）
        user.setMobilePhoneNumber(phone);
        //设置用户名，如果没有传用户名，则默认为手机号码
        user.setUsername(phone);
        //设置用户密码
        user.setPassword(password);
        //设置额外信息：此处为年龄
        user.signOrLogin(code, new SaveListener<User>() {
            @Override
            public void done(User user,BmobException e) {
                if (e == null) {
                    Utils.Toast(context,"短信注册或登录成功：" + user.getUsername());
                } else {
                    Utils.Toast(context,"短信注册或登录失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                }
            }
        });
    }
}
