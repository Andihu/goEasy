package com.xy.rcs.goeasy.adapter;

import org.json.JSONObject;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/12/012<p>
 * <p>更改时间：2018/12/12/012<p>
 * <p>版本号：1<p>
 */
public interface IOnGridViewItemClick {
    void onItemClick(JSONObject jsonObject,int position);

}
