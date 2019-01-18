package com.xy.rcs.goeasy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.utils.Constant;

import org.json.JSONObject;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/27/027<p>
 * <p>更改时间：2018/12/27/027<p>
 * <p>版本号：1<p>
 */
public class OrderInfoAdapetr extends BaseAdapter {

    Context context;
    List<JSONObject> jsonObjects;

    public OrderInfoAdapetr(Context context, List<JSONObject> jsonObjects) {
        this.context = context;
        this.jsonObjects = jsonObjects;
    }

    @Override
    public int getCount() {
        return jsonObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return jsonObjects.get(position);
    }

    @Override
    public long getItemId(int position) {

    return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView==null){
            viewHoder=new ViewHoder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_order_info,parent,false);
            viewHoder.icon=convertView.findViewById(R.id.order_cart_img);
            viewHoder.name=convertView.findViewById(R.id.order_cart_name);
            viewHoder.size=convertView.findViewById(R.id.order_cart_size);
            viewHoder.prices=convertView.findViewById(R.id.order_cart_price);
            viewHoder.maount=convertView.findViewById(R.id.order_info_maount);
            convertView.setTag(viewHoder);
        }else{
            viewHoder= (ViewHoder) convertView.getTag();
        }
        Glide.with(context).load(jsonObjects.get(position).optString(Constant.TABLE_ROW_ICON)).into(viewHoder.icon);
        viewHoder.name.setText(jsonObjects.get(position).optString(Constant.TABLE_ROW_NAME));
        viewHoder.size.setText(jsonObjects.get(position).optString(Constant.TABLE_ROW_MODDEL));
        viewHoder.prices.setText(jsonObjects.get(position).optString(Constant.TABLE_ROW_PRICES));
        viewHoder.maount.setText(jsonObjects.get(position).optString("amount"));

        return convertView;
    }

        public class ViewHoder{
            private ImageView icon;
            private TextView name;
            private TextView size;
            private TextView prices;
            private TextView maount;
        }

}
