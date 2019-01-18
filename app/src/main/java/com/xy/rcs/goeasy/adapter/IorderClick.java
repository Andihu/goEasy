package com.xy.rcs.goeasy.adapter;

import org.json.JSONObject;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/27/027<p>
 * <p>更改时间：2018/12/27/027<p>
 * <p>版本号：1<p>
 */
public interface IorderClick {
    void ondeleteClick(JSONObject js,int position);
    void onStatusClick(List<JSONObject> orders, int position);
}
