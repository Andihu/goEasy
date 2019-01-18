package com.xy.rcs.goeasy.adapter;

import org.json.JSONObject;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/24/024<p>
 * <p>更改时间：2018/12/24/024<p>
 * <p>版本号：1<p>
 */
public interface IOnLikeOrderClickListener {
    void onDeletClick(int position, JSONObject jsonObject);
    void onListItemLongClick(int position, JSONObject jsonObject,boolean selectStutus,boolean multSelect);
    void onListItemClick(int position, JSONObject jsonObject,boolean selectStutus,boolean multSelect);
}
