package com.xy.rcs.goeasy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.util.Log;

import com.xy.rcs.goeasy.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/21/021<p>
 * <p>更改时间：2018/12/21/021<p>
 * <p>版本号：1<p>
 */
public class OrderDao implements OrderDataBase {

    private static final String TAG = "OrderDao";
    public static final String ORDER_DB = "order.db";
    public static final String ID = "_id";

    public static final String NAME = Constant.TABLE_ROW_NAME;
    public static final String POSITION = Constant.TABLE_ROW_POSITION;
    public static final String ICON = Constant.TABLE_ROW_ICON;
    public static final String IMGICON = Constant.TABLE_ROW_IMG_ICON;
    public static final String INFO = Constant.TABLE_ROW_INFO;
    public static final String PRICES = Constant.TABLE_ROW_PRICES;
    public static final String MODEL = Constant.TABLE_ROW_MODDEL;

    public static final String ALL_ORDER = "all_order";
    public static final String CREATE_AT = "createAt";
    public static final String SUB_ID = "sub_id";
    public static final String SUB_ORDER = "sub_order";
    public static final String LIKE_ORDER = "like_order";
    public static final String AMOUNT = "amount";
    public static final String TOTAL = "total";

    public static final String RECEIVER = "receiver";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String DETAIL_ADDRESS = "detail_" + ADDRESS;

    public static final String ORDER_STUTUS = "order_stutus";


    public static final String CREAT_MAIN_TABLE = "create table " + ALL_ORDER + "(" +
            ID + " Integer primary key autoincrement," +
            CREATE_AT + " text," + TOTAL + " text , " + ORDER_STUTUS + "  text default('待支付'))";

    public static final String CREATE_SUB_TABLE = "create table " + SUB_ORDER + "(" +
            ID + " Integer primary key autoincrement," +
            NAME + " text ," +
            POSITION + " text," +
            IMGICON + " text," +
            ICON + " text," +
            INFO + " text," +
            PRICES + " text," +
            MODEL + " text," +
            AMOUNT +" text,"+
            SUB_ID + " Integer)";
    public static final String DISCOUNT = "discount";
    public static final String CREAT_TABLE = "create table " + LIKE_ORDER + "(" +
            ID + " Integer primary key autoincrement," +
            NAME + " text ," +
            POSITION + " text," +
            IMGICON + " text," +
            ICON + " text," +
            INFO + " text," +
            PRICES + " text," +
            MODEL + " text ," +
            DISCOUNT + " text)";


    public static  final String CREATE_POSITION="create table position(" +
            "_id Integer primary key autoincrement," +
            RECEIVER + " text," +
            PHONE + " text," +
            ADDRESS + " text," +
            DETAIL_ADDRESS + " text)";

    SQLiteDatabase db;
    private static volatile OrderDao orderDao;

    public static OrderDao getInstance(Context context) {
        if (orderDao == null) {
            synchronized (OrderDao.class) {
                if (orderDao == null) {
                    orderDao = new OrderDao(context);
                }
            }
        }
        return orderDao;
    }

    public OrderDao(Context context) {
        SQLiteOpenHelper helper = new SQLiteOpenHelper(context, ORDER_DB, null, 2) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREAT_MAIN_TABLE);
                db.execSQL(CREATE_SUB_TABLE);
                db.execSQL(CREAT_TABLE);
                db.execSQL(CREATE_POSITION);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
        };
        db = helper.getWritableDatabase();
    }

    public void upOrderStutas(String id,String status){
        db.execSQL("update all_order set " + ORDER_STUTUS + " = ? where id = ?",new String[]{status,id});
    }

    public List<JSONObject> queryMain2json() {
        List<JSONObject> jsonObjectLists = new ArrayList<>();
        Cursor c = db.query(ALL_ORDER, null, null, null, null, null, null, null);

        if (c.getCount() == 0) {
            c.close();
            return null;

        } else {
            c.moveToFirst();
            JSONObject jsonObject;
            do {
                jsonObject = new JSONObject();
                try {
                    jsonObject.put(ID, c.getInt(c.getColumnIndex(ID)));
                    jsonObject.put(CREATE_AT, c.getString(c.getColumnIndex(CREATE_AT)));
                    jsonObject.put("total", c.getString(c.getColumnIndex("total")));
                    jsonObject.put("status",c.getString(c.getColumnIndex(ORDER_STUTUS)));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonObjectLists.add(jsonObject);
            } while (c.moveToNext());

            c.close();

            return jsonObjectLists;
        }


    }

    public void deletMainOrder(String id) {
        db.execSQL(" delete from all_order where _id=?", new String[]{id});

    }
    public void deleteAllLike(){
        db.execSQL("delete from like_order");
    }

    public void deletSubOrder(String sub_id) {
        db.execSQL(" delete from sub_order where sub_id=?", new String[]{sub_id});

    }

    @Override
    public long insertMain(String createAt, String total) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CREATE_AT, createAt);
        contentValues.put(TOTAL, total);
        Long result = db.insert(ALL_ORDER, null, contentValues);

        return result;
    }

    @Override
    public Cursor queryMainAll() {
        Cursor result = db.rawQuery("select * from all_order", null, null);

        return result;
    }

    @Override
    public long insertSub(String sub_id, String name, String model, String prices, String
            icon, String imgicon, String position, String info,String amount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(POSITION, position);
        contentValues.put(IMGICON, imgicon);
        contentValues.put(ICON, icon);
        contentValues.put(INFO, info);
        contentValues.put(MODEL, model);
        contentValues.put(PRICES, prices);
        contentValues.put(SUB_ID, sub_id);
        contentValues.put(AMOUNT,amount);

        Long result = db.insert("sub_order", null, contentValues);

        return result;

    }

    @Override
    public Cursor querySubAllbyID(String id) {

        Cursor result = db.rawQuery("select * from sub_order where sub_id=?", new String[]{id},
                null);
        return result;
    }

    public List<JSONObject> queryLikeOrder() {

        List<JSONObject> jsonObjectLists = new ArrayList<>();
        Cursor c = db.query(
                LIKE_ORDER,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        if (c.getCount() == 0) {
            c.close();
            return null;
        } else {
            c.moveToFirst();
            JSONObject jsonObject;
            do {
                jsonObject = new JSONObject();
                try {
                    jsonObject.put(ID, c.getInt(c.getColumnIndex(ID)));
                    jsonObject.put(NAME, c.getString(c.getColumnIndex(NAME)));
                    jsonObject.put(POSITION, c.getString(c.getColumnIndex(POSITION)));
                    jsonObject.put(IMGICON, c.getString(c.getColumnIndex(IMGICON)));
                    jsonObject.put(ICON, c.getString(c.getColumnIndex(ICON)));
                    jsonObject.put(IMGICON, c.getString(c.getColumnIndex(IMGICON)));
                    jsonObject.put(INFO, c.getString(c.getColumnIndex(INFO)));
                    jsonObject.put(PRICES, c.getString(c.getColumnIndex(PRICES)));
                    jsonObject.put(MODEL, c.getString(c.getColumnIndex(MODEL)));
                    jsonObject.put(DISCOUNT,c.getString(c.getColumnIndex(DISCOUNT)));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonObjectLists.add(jsonObject);

            } while (c.moveToNext());
            if (!c.isClosed()) {
                c.close();
            }
            return jsonObjectLists;
        }

    }

    public int deletLikebyID(String id) {
        int result = db.delete(LIKE_ORDER, "_id=?", new String[]{id});

        return result;
    }

    public Long insertLikeOrder(String name, String prices, String size, String position, String
            imgIcon, String icon, String info,String discount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(POSITION, position);
        contentValues.put(IMGICON, imgIcon);
        contentValues.put(ICON, icon);
        contentValues.put(INFO, info);
        contentValues.put(MODEL, size);
        contentValues.put(PRICES, prices);
        contentValues.put(DISCOUNT,discount);
        Long result = db.insert(LIKE_ORDER, null, contentValues);

        return result;
    }

    public void insertPosition(String receiver,String phone,String address,String detail_address){
        ContentValues contentValues=new ContentValues();
        contentValues.put(RECEIVER,receiver);
        contentValues.put(PHONE,phone);
        contentValues.put(ADDRESS,address);
        contentValues.put(DETAIL_ADDRESS,detail_address);
        db.insert("position",null,contentValues);
    }
    public void deletePosition(String _id){
        db.delete("position","_id=?",new String[]{_id});
    }

    public Cursor queryPosition(){
        return db.query("position",null,null,null,null,null,null,null);
    }

    public int updatePosition(String _id,String receiver,String phone,String address,String detail_address){
        ContentValues contentValues=new ContentValues();
        contentValues.put(RECEIVER,receiver);
        contentValues.put(PHONE,phone);
        contentValues.put(ADDRESS,address);
        contentValues.put(DETAIL_ADDRESS,detail_address);
       return db.update("position",contentValues,"_id=?",new String[]{_id});
    }
}
