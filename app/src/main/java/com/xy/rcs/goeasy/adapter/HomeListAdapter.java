package com.xy.rcs.goeasy.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jayhu.JayHuLoader;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.utils.Constant;

import org.json.JSONObject;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2019/1/2/002<p>
 * <p>更改时间：2019/1/2/002<p>
 * <p>版本号：1<p>
 */
public class HomeListAdapter extends BaseAdapter {
private IonPositionClick IorderClick;
    public HomeListAdapter(List<JSONObject> goods, Context context,IonPositionClick IorderClick) {
        this.goods = goods;
        this.context = context;
        this.IorderClick=IorderClick;
    }

    private List<JSONObject> goods;
    private Context context;

    @Override
    public int getCount() {
        return goods.size();
    }

    @Override
    public Object getItem(int position) {
        return goods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView==null){
            viewHoder=new ViewHoder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_home_list,parent,false);
            viewHoder.imageView=convertView.findViewById(R.id.home_list_cart_img);
            viewHoder.name=convertView.findViewById(R.id.home_list_cart_name);
            viewHoder.size=convertView.findViewById(R.id.home_list_cart_size);
            viewHoder.prices=convertView.findViewById(R.id.home_list_cart_price);
            viewHoder.discount=convertView.findViewById(R.id.home_list_cart_discount);
            convertView.setTag(viewHoder);
        }else {
            viewHoder= (ViewHoder) convertView.getTag();
        }
//        Glide.with(context).load(goods.get(position).optString(Constant.TABLE_ROW_ICON)).into(viewHoder.imageView);
        JayHuLoader.getInstence().with(context).loadInmage(goods.get(position).optString(Constant.TABLE_ROW_ICON),viewHoder.imageView);
        viewHoder.name.setText(goods.get(position).optString(Constant.TABLE_ROW_NAME));
        viewHoder.size.setText(goods.get(position).optString(Constant.TABLE_ROW_MODDEL));
        viewHoder.prices.setText(goods.get(position).optString(Constant.TABLE_ROW_PRICES));
        viewHoder.discount.setText(Float.parseFloat(goods.get(position).optString(Constant.TABLE_ROW_DISCOUNT))*100+"%");

        if (IorderClick!=null){
         convertView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 IorderClick.onItemClick(goods.get(position),position);
             }
         });
        }
        return convertView;
    }


    class ViewHoder{
        ImageView imageView;
        TextView name;
        TextView size;
        TextView prices;
        TextView discount;
    }
}
