package com.xy.rcs.goeasy.BombBean;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/2/002<p>
 * <p>更改时间：2018/12/2/002<p>
 * <p>版本号：1<p>
 */
public class User extends BmobUser {
    /**
     * 昵称
     */
    private String nickname;
    /**
     * */
    private String realname;

    /**
     * 国家
     */

    private String country;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 头像
     */
    private BmobFile avatar;


    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getCountry() {
        return country;
    }

    public User setCountry(String country) {
        this.country = country;
        return this;
    }


    public Integer getGender() {
        return gender;
    }

    public User setGender(Integer gender) {
        this.gender = gender;
        return this;
    }


    public BmobFile getAvatar() {
        return avatar;
    }

    public User setAvatar(BmobFile avatar) {
        this.avatar = avatar;
        return this;
    }


}




