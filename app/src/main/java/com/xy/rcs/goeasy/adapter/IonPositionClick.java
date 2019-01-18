package com.xy.rcs.goeasy.adapter;

import org.json.JSONObject;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/28/028<p>
 * <p>更改时间：2018/12/28/028<p>
 * <p>版本号：1<p>
 */
public interface IonPositionClick  {
    void onItemClick(JSONObject jsonObject,int position);
    void onSettingClick(JSONObject jsonObject,int position);
    void onDeleteClick(JSONObject jsonObject,int position);
}
