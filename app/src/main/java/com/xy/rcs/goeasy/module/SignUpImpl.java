package com.xy.rcs.goeasy.module;

import android.content.Context;

import com.xy.rcs.goeasy.BombBean.User;
import com.xy.rcs.goeasy.utils.Utils;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/2/002<p>
 * <p>更改时间：2018/12/2/002<p>
 * <p>版本号：1<p>
 */
public class SignUpImpl implements LoginAndSignUp.IsingnUp {
    Context context;
    BmobException exception;

    public SignUpImpl(Context context) {
    this.context=context;
    }

    @Override
    public BmobException signUp(String username,String password, String realname, String nickname, String country, Integer gender, String age, BmobFile avatar) {
        User user=new User();
        user.setRealname(realname);
        user.setNickname(nickname);
        user.setCountry(country);
        user.setGender(gender);
        user.setAvatar(avatar);
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null) {
                    Utils.Toast(context, "注册成功！");
                    exception=e;
                }else {
                    Utils.Toast(context,"注册失败");
                    exception=e;
                }
            }
        });
        return exception;
    }
}
