package com.xy.rcs.goeasy.BombBean;

import cn.bmob.v3.BmobObject;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/9/009<p>
 * <p>更改时间：2018/12/9/009<p>
 * <p>版本号：1<p>
 */
public class Foods extends BmobObject {
    public Foods(String name, String size, float price, String position, String image, float
            discount) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.position = position;
        this.image = image;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    String name;
    String size;
    float price;
    String position;
    String image;
    float discount;
}
