package com.xy.rcs.goeasy.BombBean;

import cn.bmob.v3.BmobObject;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/3/003<p>
 * <p>更改时间：2018/12/3/003<p>
 * <p>版本号：1<p>
 */
public class Sorts extends BmobObject {
    public String getSortName() {
        return SortName;
    }

    public void setSortName(String sortName) {
        SortName = sortName;
    }

    public Sorts(String sortName) {
        SortName = sortName;
    }

    String SortName;
}
