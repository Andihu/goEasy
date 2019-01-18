package com.xy.rcs.goeasy;

import android.app.Activity;
import android.app.Application;
import android.util.Log;


import com.xy.rcs.goeasy.BombBean.Appliances;
import com.xy.rcs.goeasy.BombBean.Bags;
import com.xy.rcs.goeasy.BombBean.Books;
import com.xy.rcs.goeasy.BombBean.Computers;
import com.xy.rcs.goeasy.BombBean.Good;
import com.xy.rcs.goeasy.BombBean.Jewelry;
import com.xy.rcs.goeasy.BombBean.Maternal;
import com.xy.rcs.goeasy.BombBean.MenCloth;
import com.xy.rcs.goeasy.BombBean.Phone;
import com.xy.rcs.goeasy.BombBean.Recommend;
import com.xy.rcs.goeasy.BombBean.Underwear;
import com.xy.rcs.goeasy.BombBean.Vegetables;
import com.xy.rcs.goeasy.utils.Constant;
import com.xy.rcs.goeasy.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/12/012<p>
 * <p>更改时间：2018/12/12/012<p>
 * <p>版本号：1<p>
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private JSONObject temp;
    private List<JSONObject> curOrders;

    public List<JSONObject> getCurOrders() {
        return curOrders;
    }

    public void setCurOrders(List<JSONObject> curOrders) {
        this.curOrders = curOrders;
    }

    public List<JSONObject> getmBooks() {
        return mBooks;
    }


    public void setmBooks(List<JSONObject> mBooks) {
        this.mBooks = mBooks;
    }

    private List<Activity> activities=new ArrayList<>();

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void ExitApp(){
        for (Activity a:activities) {
            a.finish();
        }
    }


    private List<JSONObject> mSorts;
    private List<JSONObject> mFoods;
    private List<JSONObject> cart;
    private List<JSONObject> mBooks;
    private List<JSONObject> mBags;
    private List<JSONObject> mPhones;
    private List<JSONObject> mJewelry;
    private List<JSONObject> mMaternal;
    private List<JSONObject> mMenCloth;
    private List<JSONObject> mComputers;
    private List<JSONObject> mUnderwear;
    private List<JSONObject> mAppliances;
    private List<JSONObject> mRecommends;
    private List<JSONObject> mVegetables;

    public List<JSONObject> getmBags() {
        return mBags;
    }

    public List<JSONObject> getmPhones() {
        return mPhones;
    }

    public List<JSONObject> getmJewelry() {
        return mJewelry;
    }

    public List<JSONObject> getmMaternal() {
        return mMaternal;
    }

    public List<JSONObject> getmMenCloth() {
        return mMenCloth;
    }

    public List<JSONObject> getmComputers() {
        return mComputers;
    }

    public List<JSONObject> getmUnderwear() {
        return mUnderwear;
    }

    public List<JSONObject> getmAppliances() {
        return mAppliances;
    }

    public List<JSONObject> getmRecommends() {
        return mRecommends;
    }

    public List<JSONObject> getmVegetables() {
        return mVegetables;
    }


    public JSONObject currentGood;

    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(this, Constant.APPLICATION_ID);

        init();

        LoadRecommend().LoadVegetable().LoadUnderwear().LoadAppliances().LoadComputer().LoadPhone().LoadJewelry()
                .LoadBags().LoadMenCloth().LoadMaternal().LoadBooks();
    }


    public MyApplication LoadBags() {
        /**
         * 查询多条数据
         */
        BmobQuery<Bags> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Bags>() {
            @Override
            public void done(List<Bags> categories, BmobException e) {
                if (e == null) {

                    for (Bags m : categories) {
                        toJson(m.getName(), m.getModel(), m.getInfo(), m.getIcon(), m.getImgicon(), m.getPosition(), m.getPrice(), m.getDiscount());
                        mBags.add(temp);
                    }

                } else {

                    Log.e(TAG, "done: "+"getBags fail!" );
                }
            }
        });
        return this;
    }

    public MyApplication LoadPhone() {
        /**
         * 查询多条数据
         */
        BmobQuery<Phone> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Phone>() {
            @Override
            public void done(List<Phone> categories, BmobException e) {

                if (e == null) {
                    for (Phone m : categories) {
                        toJson(m.getName(), m.getModel(), m.getInfo(), m.getIcon(), m.getImgicon(), m.getPosition(), m.getPrice(), m.getDiscount());
                        mPhones.add(temp);
                    }
                } else {
                    Log.e(TAG, "done: "+"getPhone fail!" );
                }
            }
        });
        return this;
    }

    public MyApplication LoadBooks() {
        /**
         * 查询多条数据
         */
        BmobQuery<Books> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> categories, BmobException e) {

                if (e == null) {

                    for (Books m : categories) {
                        toJson(m.getName(), m.getModel(), m.getInfo(), m.getIcon(), m.getImgicon(), m.getPosition(), m.getPrice(), m.getDiscount());
                        mBooks.add(temp);
                    }

                } else {
                    Log.e(TAG, "done: "+"Books fail!" );
                }
            }
        });
        return this;
    }

    public MyApplication LoadJewelry() {
        /**
         * 查询多条数据
         */
        BmobQuery<Jewelry> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Jewelry>() {
            @Override
            public void done(List<Jewelry> categories, BmobException e) {
                if (e == null) {

                    for (Jewelry m : categories) {
                        toJson(m.getName(), m.getModel(), m.getInfo(), m.getIcon(), m.getImgicon(), m.getPosition(), m.getPrice(), m.getDiscount());
                        mJewelry.add(temp);
                    }
                } else {
                    Log.e(TAG, "done: "+"Jewelry fail!" );
                }
            }
        });
        return this;
    }

    public MyApplication LoadComputer() {
        /**
         * 查询多条数据
         */
        BmobQuery<Computers> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Computers>() {
            @Override
            public void done(List<Computers> categories, BmobException e) {
                if (e == null) {
                    for (Computers m : categories) {
                        toJson(m.getName(), m.getModel(), m.getInfo(), m.getIcon(), m.getImgicon(), m.getPosition(), m.getPrice(), m.getDiscount());
                        mComputers.add(temp);
                    }
                } else {
                    Log.e(TAG, "done: "+"Computers fail!" );
                }
            }
        });
        return this;
    }

    public MyApplication LoadMaternal() {
        /**
         * 查询多条数据
         */
        BmobQuery<Maternal> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Maternal>() {
            @Override
            public void done(List<Maternal> categories, BmobException e) {
                if (e == null) {
                    for (Maternal m : categories) {
                        toJson(m.getName(), m.getModel(), m.getInfo(), m.getIcon(), m.getImgicon(), m.getPosition(), m.getPrice(), m.getDiscount());
                        mMaternal.add(temp);
                    }
                } else {
                    Log.e(TAG, "done: "+"Maternal fail!" );
                }
            }
        });
        return this;
    }

    public MyApplication LoadMenCloth() {
        /**
         * 查询多条数据
         */
        BmobQuery<MenCloth> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<MenCloth>() {
            @Override
            public void done(List<MenCloth> categories, BmobException e) {
                if (e == null) {

                    for (MenCloth m : categories) {
                        toJson(m.getName(), m.getModel(), m.getInfo(), m.getIcon(), m.getImgicon(), m.getPosition(), m.getPrice(), m.getDiscount());
                        mMenCloth.add(temp);
                    }
                } else {
                    Log.e(TAG, "done: "+"MenCloth fail!" );
                }
            }
        });
        return this;
    }

    public MyApplication LoadRecommend() {
        /**
         * 查询多条数据
         */
        BmobQuery<Recommend> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Recommend>() {
            @Override
            public void done(List<Recommend> categories, BmobException e) {

                if (e == null) {
                    for (Recommend m : categories) {
                        toJson(m.getName(), m.getModel(), m.getInfo(), m.getIcon(), m.getImgicon(), m.getPosition(), m.getPrice(), m.getDiscount());
                        mRecommends.add(temp);
                    }
                } else {
                    Log.e(TAG, "done: "+"Recommend fail!" );
                }
            }
        });
        return this;
    }

    public MyApplication LoadUnderwear() {
        /**
         * 查询多条数据
         */
        BmobQuery<Underwear> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Underwear>() {
            @Override
            public void done(List<Underwear> categories, BmobException e) {

                if (e == null) {
                    for (Underwear m : categories) {
                        toJson(m.getName(), m.getModel(), m.getInfo(), m.getIcon(), m.getImgicon
                                (), m.getPosition(), m.getPrice(), m.getDiscount());
                        mUnderwear.add(temp);
                    }
                } else {
                    Log.e(TAG, "done: "+"Underwear fail!" );
                }
            }
        });
        return this;
    }

    public MyApplication LoadVegetable() {
        /**
         * 查询多条数据
         */
        BmobQuery<Vegetables> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Vegetables>() {
            @Override
            public void done(List<Vegetables> categories, BmobException e) {

                if (e == null) {
                    for (Vegetables m : categories) {
                        toJson(m.getName(), m.getModel(), m.getInfo(), m.getIcon(), m.getImgicon(), m.getPosition(), m.getPrice(), m.getDiscount());
                        mVegetables.add(temp);
                    }
                } else {
                    Log.e(TAG, "done: "+"Vegetables fail!" );
                }
            }
        });
        return this;
    }

    public MyApplication LoadAppliances() {
        /**
         * 查询多条数据
         */
        BmobQuery<Appliances> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Appliances>() {
            @Override
            public void done(List<Appliances> categories, BmobException e) {

                if (e == null) {

                    for (Appliances m : categories) {
                        toJson(m.getName(), m.getModel(), m.getInfo(), m.getIcon(), m.getImgicon
                                (), m.getPosition(), m.getPrice(), m.getDiscount());
                        mAppliances.add(temp);
                    }
                } else {
                    Log.e(TAG, "done: "+"Appliances fail!" );
                }
            }
        });
        return this;
    }

    private void toJson(String name, String model, String info, String icon, String imgicon,
                        String position, Number price, Number discount) {
        temp = new JSONObject();
        try {
            temp.put(Constant.TABLE_ROW_NAME, name);
            temp.put(Constant.TABLE_ROW_MODDEL, model);
            temp.put(Constant.TABLE_ROW_INFO, info);
            temp.put(Constant.TABLE_ROW_ICON, icon);
            temp.put(Constant.TABLE_ROW_IMG_ICON, imgicon);
            temp.put(Constant.TABLE_ROW_POSITION, position);
            temp.put(Constant.TABLE_ROW_PRICES, price);
            temp.put(Constant.TABLE_ROW_DISCOUNT, discount);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    private void init() {
        cart = new ArrayList<>();
        mBags = new ArrayList<>();
        mBooks = new ArrayList<>();
        mSorts = new ArrayList<>();
        mFoods = new ArrayList<>();
        mPhones = new ArrayList<>();
        mJewelry = new ArrayList<>();
        mMaternal = new ArrayList<>();
        mMenCloth = new ArrayList<>();
        mComputers = new ArrayList<>();
        mUnderwear = new ArrayList<>();
        mAppliances = new ArrayList<>();
        mRecommends = new ArrayList<>();
        mVegetables = new ArrayList<>();
    }

    public JSONObject getCurrentGood() {
        return currentGood;
    }

    public void setCurrentGood(JSONObject currentGood) {
        Log.e(TAG, "setCurrentGood: " + currentGood);
        this.currentGood = currentGood;
    }

    public void addCart(JSONObject Good) {
        if (!cart.contains(Good)) {
            cart.add(Good);
        } else {
            cart.set(cart.indexOf(Good), Good);
        }
    }

    public List<JSONObject> getCart() {
        return cart;
    }

    public void removeCart(JSONObject Good) {
        cart.remove(Good);
    }

}
