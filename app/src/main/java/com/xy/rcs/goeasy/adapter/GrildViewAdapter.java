package com.xy.rcs.goeasy.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.utils.Constant;

import org.json.JSONObject;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/11/011<p>
 * <p>更改时间：2018/12/11/011<p>
 * <p>版本号：1<p>
 */
public class GrildViewAdapter extends BaseAdapter {
    private static final String TAG = "GrildViewAdapter";
    private Context mContext;
    private IOnGridViewItemClick iOnGridViewItemClick;

    public GrildViewAdapter(Context mContext, List<JSONObject> mGoods,IOnGridViewItemClick iOnGridViewItemClick) {
        this.mContext = mContext;
        this.mGoods = mGoods;
        this.iOnGridViewItemClick=iOnGridViewItemClick;
    }

    private List<JSONObject> mGoods;

    public List<JSONObject> getmGoods() {
        return mGoods;
    }


    public void setmGoods(List<JSONObject> mGoods) {
        this.mGoods = mGoods;
    }

    @Override
    public int getCount() {
        return mGoods.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HoldView holdView;
        if (convertView==null){
            holdView=new HoldView();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_product_child_gridview,null);
            holdView.icon= convertView.findViewById(R.id.item_grid_imageView);
            holdView.name= convertView.findViewById(R.id.item_grid_name);
            holdView.prices= convertView.findViewById(R.id.item_grid_prices);
            convertView.setTag(holdView);
        }else{
            holdView= (HoldView) convertView.getTag();
        }
        holdView.name.setText(mGoods.get(position).optString(Constant.TABLE_ROW_NAME));
        holdView.prices.setText(mGoods.get(position).optString(Constant.TABLE_ROW_PRICES)+"元");
        Glide.with(mContext).load(mGoods.get(position).optString(Constant.TABLE_ROW_ICON)).into(holdView.icon);

        if (iOnGridViewItemClick!=null){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iOnGridViewItemClick.onItemClick(mGoods.get(position),position);
                }
            });
        }
        return convertView;
    }
    class HoldView {
        ImageView icon;
        TextView name;
        TextView prices;
    }
}
