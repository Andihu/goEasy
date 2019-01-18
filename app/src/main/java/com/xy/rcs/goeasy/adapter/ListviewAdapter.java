package com.xy.rcs.goeasy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/3/003<p>
 * <p>更改时间：2018/12/3/003<p>
 * <p>版本号：1<p>
 */
public class ListviewAdapter extends BaseAdapter {
    private static final String TAG = "ListviewAdapter";

    private Context mContext;
    private HashMap<Integer, Boolean> checked;

    private List<JSONObject> mJsons;
    private List<JSONObject> Selected;

    public static final int TYPE_MAIN = 0;
    public static final int TYPE_GOODS_CART = 1;

    public List<JSONObject> getChecked() {
        return Selected;
    }

    public void setmJsons(List<JSONObject> mJsons) {
        this.mJsons = mJsons;
    }

    public List<JSONObject> getmJsons() {
        return mJsons;
    }

    private IOnListItemClickListener.CheckListenerI listener;

    public void setListener(IOnListItemClickListener.CheckListenerI listener) {
        this.listener = listener;
    }

    private int type;

    private int highetlight = 0;

    public void setHighetlight(int highetlight) {
        this.highetlight = highetlight;
    }


    public ListviewAdapter(Context context, List<JSONObject> jsonObjects, int type) {
        this.mJsons = jsonObjects;
        this.mContext = context;
        checked = new HashMap<>();
        this.type = type;
        Selected = new ArrayList<>();
        for (int i = 0; i < mJsons.size(); i++) {
            checked.put(i, true);

        }
    }
    @Override
    public int getCount() {
        return mJsons.size();
    }

    @Override
    public Object getItem(int position) {
        return mJsons.iterator().next();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (type == TYPE_MAIN) {
            //加载mainlistview
            MainViewHold mainViewHold;
            if (convertView == null) {
                mainViewHold = new MainViewHold();
                convertView = LayoutInflater.from(mContext).inflate(R.layout
                        .item_product_mian_listview, parent, false);
                mainViewHold.view = convertView.findViewById(R.id.sort_line);
                mainViewHold.Name = convertView.findViewById(R.id.sort_name);
                mainViewHold.Count = convertView.findViewById(R.id.sort_count);
                convertView.setTag(mainViewHold);
            } else {
                mainViewHold = (MainViewHold) convertView.getTag();
            }
            if (position == highetlight) {
                mainViewHold.Name.setTextColor(mContext.getResources().getColor(R.color
                        .colorPrimary));
                mainViewHold.Count.setTextColor(mContext.getResources().getColor(R.color
                        .colorPrimary));
                mainViewHold.view.setVisibility(View.VISIBLE);
            } else {
                mainViewHold.Name.setTextColor(mContext.getResources().getColor(R.color
                        .text_color));
                mainViewHold.Count.setTextColor(mContext.getResources().getColor(R.color
                        .text_child_color));
                mainViewHold.view.setVisibility(View.INVISIBLE);
            }
            try {
                mainViewHold.Count.setText("总计："+mJsons.size()+"");
                mainViewHold.Name.setText(mJsons.get(position).getString(Constant.TABLE_ROW_NAME));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //加载购物车界面
        else if (type == TYPE_GOODS_CART) {
            GoodCartViewHold goodCartViewHold;
            if (convertView == null) {
                goodCartViewHold = new GoodCartViewHold();
                convertView = LayoutInflater.from(mContext).inflate(R.layout
                        .item_shopping_cart_listview, parent, false);
                goodCartViewHold.CheckBox = convertView.findViewById(R.id.good_checkbox);
                goodCartViewHold.goodimg = convertView.findViewById(R.id.item_cart_img);
                goodCartViewHold.goodName = convertView.findViewById(R.id.item_cart_name);
                goodCartViewHold.goodSize = convertView.findViewById(R.id.item_cart_size);
                goodCartViewHold.goodprics = convertView.findViewById(R.id.item_cart_price);
                goodCartViewHold.add = convertView.findViewById(R.id.item_cart_add);
                goodCartViewHold.remove = convertView.findViewById(R.id.item_cart_remove);
                goodCartViewHold.total = convertView.findViewById(R.id.item_cart_good_count);
                convertView.setTag(goodCartViewHold);
            } else {
                goodCartViewHold = (GoodCartViewHold) convertView.getTag();
            }
            //设置以选中的数量
            goodCartViewHold.total.setText(mJsons.get(position).opt("amount").toString());
            goodCartViewHold.goodName.setText(mJsons.get(position).opt(Constant.TABLE_ROW_NAME).toString());
            goodCartViewHold.goodprics.setText(mJsons.get(position).opt(Constant.TABLE_ROW_PRICES).toString() + "元");
            //  goodCartViewHold.CheckBox.setChecked(mJsons.get(position).optBoolean
            // ("checkboxstaus"));


            goodCartViewHold.CheckBox.setChecked(checked.get(position));
            if (checked.get(position)) {
                Selected.add(mJsons.get(position));
            }

            //   goodCartViewHold.goodSize.setText(mJsons.get(position).opt("size").toString());

            Glide.with(mContext).load(mJsons.get(position).optString(Constant.TABLE_ROW_ICON)).into
                    (goodCartViewHold.goodimg);


            goodCartViewHold.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAddGoodsClick(position, mJsons.get(position));
                    }
                }
            });
            goodCartViewHold.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRemoveClick(position, mJsons.get(position));
                }
            });

            goodCartViewHold.CheckBox.setOnCheckedChangeListener(new CompoundButton
                    .OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.onCheckBoxClick(buttonView, isChecked, position, mJsons.get(position));
                }
            });

        }
        return convertView;
    }

    class MainViewHold {
        View view;
        TextView Name;
        TextView Count;
    }

    class GoodCartViewHold {
        ImageView goodimg;
        CheckBox CheckBox;
        TextView goodName;
        TextView goodSize;
        TextView goodprics;
        ImageView add;
        ImageView remove;
        TextView total;
    }
}
