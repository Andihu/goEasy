package com.xy.rcs.goeasy.utils;

import com.xy.rcs.goeasy.fragment.GoodsCartFragment;
import com.xy.rcs.goeasy.fragment.HomeFragment;
import com.xy.rcs.goeasy.fragment.LikeFragment;
import com.xy.rcs.goeasy.fragment.MyCouponFragment;
import com.xy.rcs.goeasy.fragment.MyScoreFragment;
import com.xy.rcs.goeasy.fragment.MySpaceFragment;
import com.xy.rcs.goeasy.fragment.OrderFragment;
import com.xy.rcs.goeasy.fragment.OrderInfoFragment;
import com.xy.rcs.goeasy.fragment.PaymentFragment;
import com.xy.rcs.goeasy.fragment.PersonInfoFragment;
import com.xy.rcs.goeasy.fragment.PositionListFragment;
import com.xy.rcs.goeasy.fragment.PositionSettingFragment;
import com.xy.rcs.goeasy.fragment.ProductFragment;
import com.xy.rcs.goeasy.fragment.ProductInfoFragment;
import com.xy.rcs.goeasy.fragment.SettingFragment;
import com.xy.rcs.goeasy.fragment.SweepCodeFragment;
import com.xy.rcs.goeasy.fragment.TempFragment;
import com.xy.rcs.goeasy.fragment.WebFragment;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/9/009<p>
 * <p>更改时间：2018/12/9/009<p>
 * <p>版本号：1<p>
 */
public class GetFragment {
    public static final int HOME_FRAGMENT = 0;
    public static final int PRODUCT_FRAGMENT = 1;
    public static final int COUPON_FRAGMENT = 2;
    public static final int SCORE_FRAGMENT = 3;
    public static final int PAY_FRAGMENT = 4;
    public static final int SWEEP_FRAGMENT = 5;
    public static final int GOODSCART_FRAGMENT = 6;
    public static final int PRODUCTINFO_FRAGMENT = 7;
    public static final int MYSPACE_FRAGMENT = 8;
    public static final int WEB_FRAGMENT = 9;
    public static final int PERSONAL_INFO_FRAGMENT = 10;
    public static final int SETTING_FRAGMENT = 11;
    public static final int POSITION_SETTING_FRAGMENT = 12;
    public static final int ORDER_FRAGMENT = 13;
    public static final int LIKE_FRAGMENT = 14;
    public static final int ORDER_INFO_FRAGMENT = 15;
    public static final int POSITION_LIST_FRAGMENT = 16;
    public static final int TEMP_FRAGMENT = 17;
    public static Object getFragment(int fragmentCode) {

        if (fragmentCode == HOME_FRAGMENT) {

            return new HomeFragment();

        } else if (fragmentCode == PRODUCT_FRAGMENT) {

            return new ProductFragment();

        } else if (fragmentCode == COUPON_FRAGMENT) {

            return new MyCouponFragment();
        } else if (fragmentCode == SCORE_FRAGMENT) {

            return new MyScoreFragment();
        } else if (fragmentCode == PAY_FRAGMENT) {

            return new PaymentFragment();
        } else if (fragmentCode == SWEEP_FRAGMENT) {

            return new SweepCodeFragment();
        } else if (fragmentCode == GOODSCART_FRAGMENT) {

            return new GoodsCartFragment();
        } else if (fragmentCode == PRODUCTINFO_FRAGMENT) {

            return new ProductInfoFragment();
        } else if (fragmentCode == MYSPACE_FRAGMENT) {

            return new MySpaceFragment();
        } else if (fragmentCode == WEB_FRAGMENT) {

            return new WebFragment();
        } else if (fragmentCode == PERSONAL_INFO_FRAGMENT) {

            return new PersonInfoFragment();
        } else if (fragmentCode == SETTING_FRAGMENT) {

            return new SettingFragment();
        } else if (fragmentCode == POSITION_SETTING_FRAGMENT) {

            return new PositionSettingFragment();
        }else if (fragmentCode == ORDER_FRAGMENT) {

            return new OrderFragment();
        }else if (fragmentCode == LIKE_FRAGMENT) {

            return new LikeFragment();
        }else if (fragmentCode == ORDER_INFO_FRAGMENT) {

            return new OrderInfoFragment();
        }else if (fragmentCode == POSITION_LIST_FRAGMENT) {

            return new PositionListFragment();
        }else if (fragmentCode == TEMP_FRAGMENT) {

            return new TempFragment();
        }else {
            return null;
        }
    }

}
