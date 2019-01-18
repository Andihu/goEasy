package com.xy.rcs.goeasy.utils;

import android.util.Log;

import com.xy.rcs.goeasy.BombBean.Foods;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/9/009<p>
 * <p>更改时间：2018/12/9/009<p>
 * <p>版本号：1<p>
 */
public class SetData {
    public static void setFoods() {
        List<Foods> foods = new ArrayList<>();

        Foods foods1 = new Foods("蒙牛早餐核桃牛奶250ml", null, 4, "超市二楼", "https://p0.meituan" +
                ".net/210.0/xianfu/ac34462a58e5f5ec1d24301a0639c4be52261.jpg", 1);
        Foods foods2 = new Foods("赛味馆美国樱桃干", null, 4, "超市二楼", "https://p0.meituan" +
                ".net/210.0/xianfu/49ea49afecb54fa0119c48b407d3b2fc43371.jpg", 1);
        Foods foods3 = new Foods("南瓜鸡蛋土豆泥沙拉", null, 4, "超市二楼", "https://p1.meituan" +
                ".net/210.0/xianfu/6c3303939f1e575641c17888f2233ece54964.jpg", 1);
        Foods foods4 = new Foods("光明巧克努力牛乳饮品", null, 4, "超市二楼", "https://p1.meituan" +
                ".net/210.0/xianfu/5176c1e2bbec72eb13034805309faa5328018.jpg", 1);
        Foods foods5 = new Foods("苏式豆沙蛋黄酥月饼", null, 4, "超市二楼", "https://p1.meituan" +
                ".net/210.0/xianfu/85c3e9a39e7a3c9d16f716323a32636253393.jpg", 1);
        Foods foods6 = new Foods("FreshDoze柠檬味瓶盖", null, 4, "超市二楼", "https://p0.meituan" +
                ".net/210.0/xianfu/4868f9f61d960296cb1aeedc565d927337584.jpg", 1);
        Foods foods7 = new Foods("椰子灰脆筒", null, 4, "超市二楼", "https://p0.meituan" +
                ".net/210.0/xianfu/cf8955b81278ee9a146e93529890318319064.jpg", 1);
        foods.add(foods1);
        foods.add(foods2);
        foods.add(foods3);
        foods.add(foods4);
        foods.add(foods5);
        foods.add(foods6);
        foods.add(foods7);
        for (Foods s : foods) {
            s.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Log.e("save", "添加数据成功，返回objectId为：" + s);
                    } else {
                        Log.e("save", "添加数据失败，返回objectId为：" + s);
                    }
                }
            });
        }


    }
}
