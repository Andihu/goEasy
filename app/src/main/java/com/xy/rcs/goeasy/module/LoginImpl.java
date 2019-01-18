package com.xy.rcs.goeasy.module;


import android.content.Context;

import com.xy.rcs.goeasy.BombBean.User;
import com.xy.rcs.goeasy.utils.Utils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/2/002<p>
 * <p>更改时间：2018/12/2/002<p>
 * <p>版本号：1<p>
 */
public class LoginImpl implements LoginAndSignUp.ILogin {

    Context context;
    BmobException exception;
    public LoginImpl(Context context) {
    this.context=context;
    }

    @Override
    public BmobException login(String username, String password, final ILoginListener listener) {
        User user=new User();
        user.setPassword(password);
        user.setUsername(username);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    Utils.Toast(context,"登陆成功！");
                    exception=e;
                    listener.success();
                }else {
                    Utils.Toast(context,"登陆失败!");
                    exception=e;
                    listener.failed();
                }
            }
        });
        return exception;
    }
}
