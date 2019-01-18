package com.xy.rcs.goeasy.db;

import android.database.Cursor;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/26/026<p>
 * <p>更改时间：2018/12/26/026<p>
 * <p>版本号：1<p>
 */
public interface OrderDataBase {
    long insertMain(String createAt,String total);

    Cursor queryMainAll();

    long insertSub(String sub_id, String name, String model, String prices, String icon,
                   String imgicon, String position, String info,String amount);

    Cursor querySubAllbyID(String id);

}
