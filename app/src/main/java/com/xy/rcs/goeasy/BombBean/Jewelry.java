package com.xy.rcs.goeasy.BombBean;

import cn.bmob.v3.BmobObject;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/14/014<p>
 * <p>更改时间：2018/12/14/014<p>
 * <p>版本号：1<p>
 */
public class Jewelry extends BmobObject implements Good{
    public Jewelry() {
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getModel() {
        return model;
    }
    @Override
    public void setModel(String model) {
        this.model = model;
    }
    @Override
    public Number getPrice() {
        return price;
    }
    @Override
    public void setPrice(Number price) {
        this.price = price;
    }
    @Override
    public String getInfo() {
        return info;
    }
    @Override
    public void setInfo(String info) {
        this.info = info;
    }
    @Override
    public String getIcon() {
        return icon;
    }
    @Override
    public void setIcon(String icon) {
        this.icon = icon;
    }
    @Override
    public String getImgicon() {
        return imgicon;
    }
    @Override
    public void setImgicon(String imgicon) {
        this.imgicon = imgicon;
    }
    @Override
    public String getPosition() {
        return position;
    }
    @Override
    public void setPosition(String position) {
        this.position = position;
    }
    @Override
    public Number getDiscount() {
        return discount;
    }
    @Override
    public void setDiscount(Number discount) {
        this.discount = discount;
    }

    public Jewelry(String name, String model, Number price, String info, String icon, String
            imgicon, String position, Number discount) {
        this.name = name;
        this.model = model;
        this.price = price;
        this.info = info;
        this.icon = icon;
        this.imgicon = imgicon;
        this.position = position;
        this.discount = discount;
    }

    String name;
    String model;
    Number price;
    String info;
    String icon;
    String imgicon;
    String position;
    Number discount;
}
