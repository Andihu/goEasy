package com.xy.rcs.goeasy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/30/030<p>
 * <p>更改时间：2018/12/30/030<p>
 * <p>版本号：1<p>
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> list;
    public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.list=fragments;
    }
    /**
     * 返回需要展示的fragment
     * @param i
     * @return
     */
    @Override
    public Fragment getItem(int i) {

        return list.get(i);
    }
    /**
     * 返回需要展示的fangment数量
     * @return
     */
    @Override
    public int getCount() {
        return list.size();
    }
}
