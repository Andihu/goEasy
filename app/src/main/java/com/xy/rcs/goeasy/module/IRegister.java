package com.xy.rcs.goeasy.module;

import android.content.Context;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/31/031<p>
 * <p>更改时间：2018/12/31/031<p>
 * <p>版本号：1<p>
 */
public interface IRegister {
    public void requestSMS(Context context,String phone);
    public void verifySmsCode(Context context,String code, final String phone);
    public  void register(Context context,String phone,String code);
}
