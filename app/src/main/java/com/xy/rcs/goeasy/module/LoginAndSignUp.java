package com.xy.rcs.goeasy.module;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/2/002<p>
 * <p>更改时间：2018/12/2/002<p>
 * <p>版本号：1<p>
 */
public class LoginAndSignUp {

    interface ILogin{
        BmobException login(String username, String password, ILoginListener listener);
    }
    interface IsingnUp{
        BmobException signUp(String username, String password, String realname, String nickname,
                             String country, Integer gender, String age, BmobFile avatar);

    }

}

