package com.xy.rcs.goeasy.db;

import android.content.Context;
import android.database.Cursor;

import com.xy.rcs.goeasy.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/26/026<p>
 * <p>更改时间：2018/12/26/026<p>
 * <p>版本号：1<p>
 */
public class OrderHelper {
    public static List<JSONObject> queryLiskOrder(Context context) {
        OrderDao orderDao = OrderDao.getInstance(context);
        return orderDao.queryLikeOrder();
    }

    public static List<JSONObject> queryOrderMain(Context context) {
        OrderDao orderDao = OrderDao.getInstance(context);
        return orderDao.queryMain2json();
    }

    public static void addOrder(Context context, String total, List<JSONObject> orders) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String time = format.format(new Date());

        OrderDao orderDao = OrderDao.getInstance(context);
        long id = orderDao.insertMain(time, total);
        for (JSONObject js : orders) {
            orderDao.insertSub(
                    String.valueOf(id),
                    js.optString(Constant.TABLE_ROW_NAME),
                    js.optString(Constant.TABLE_ROW_MODDEL),
                    js.optString(Constant.TABLE_ROW_PRICES),
                    js.optString(Constant.TABLE_ROW_ICON),
                    js.optString(Constant.TABLE_ROW_IMG_ICON),
                    js.optString(Constant.TABLE_ROW_POSITION),
                    js.optString(Constant.TABLE_ROW_INFO),
                    js.optString("amount"));
        }
    }

    public static void addLikeOrder(Context context, JSONObject order) {
        OrderDao orderDao = new OrderDao(context);
        orderDao.insertLikeOrder(
                order.optString(Constant.TABLE_ROW_NAME),
                order.optString(Constant.TABLE_ROW_PRICES),
                order.optString(Constant.TABLE_ROW_MODDEL),
                order.optString(Constant.TABLE_ROW_POSITION),
                order.optString(Constant.TABLE_ROW_IMG_ICON),
                order.optString(Constant.TABLE_ROW_ICON),
                order.optString(Constant.TABLE_ROW_INFO),
                order.optString(Constant.TABLE_ROW_DISCOUNT));
    }

    public static void deleteMinOrder(Context context, String id) {
        OrderDao orderDao = OrderDao.getInstance(context);
        orderDao.deletMainOrder(id);
    }

    public static void deleteSubOrder(Context context, String sub_id) {
        OrderDao orderDao = OrderDao.getInstance(context);
        orderDao.deletSubOrder(sub_id);
    }

    public static void deleteOrder(Context context, String id) {
        OrderDao orderDao = OrderDao.getInstance(context);
        orderDao.deletMainOrder(id);
        orderDao.deletSubOrder(id);
    }

    public static void deletLikeOrder(Context context, String id) {
        OrderDao orderDao = OrderDao.getInstance(context);
        orderDao.deletLikebyID(id);
    }

    public static void setOrderStutas(Context context, String id, String status) {
        OrderDao orderDao = OrderDao.getInstance(context);
        orderDao.upOrderStutas(id, status);
    }

    public static void deleteAllLike(Context context) {
        OrderDao orderDao = OrderDao.getInstance(context);
        orderDao.deleteAllLike();
    }

    public static void insertPosition(Context context, String receiver, String phone, String
            address, String detail_address) {
        OrderDao orderDao = OrderDao.getInstance(context);
        orderDao.insertPosition(receiver, phone, address, detail_address);
    }

    public static List<JSONObject> queryPosition(Context context) {
        List<JSONObject> jsonObjects = new ArrayList<>();
        JSONObject jsonObject;
        OrderDao orderDao = OrderDao.getInstance(context);
        Cursor c = orderDao.queryPosition();
        if (c.getCount() == 0) {
            return null;
        } else {
            c.moveToFirst();
            do {
                jsonObject = new JSONObject();
                try {
                    jsonObject.put(OrderDao.ID, c.getString(c.getColumnIndex(OrderDao.ID)));
                    jsonObject.put(OrderDao.RECEIVER, c.getString(c.getColumnIndex(OrderDao.RECEIVER)));
                    jsonObject.put(OrderDao.PHONE, c.getString(c.getColumnIndex(OrderDao.PHONE)));
                    jsonObject.put(OrderDao.ADDRESS,c.getString(c.getColumnIndex(OrderDao.ADDRESS)));
                    jsonObject.put(OrderDao.DETAIL_ADDRESS,c.getString(c.getColumnIndex(OrderDao.DETAIL_ADDRESS)));
                    jsonObjects.add(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } while (c.moveToNext());

            c.close();
            return jsonObjects;
        }

    }

    public static void deletePositionbyID(Context context,String _id){
        OrderDao orderDao = OrderDao.getInstance(context);
        orderDao.deletePosition(_id);
    }

    public static int updatePosition(Context context, String _id ,String receiver, String phone, String
            address, String detail_address){
        OrderDao orderDao = OrderDao.getInstance(context);
        return orderDao.updatePosition(_id,receiver,phone,address,detail_address);
    }
}
