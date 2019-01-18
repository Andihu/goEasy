package com.xy.rcs.goeasy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.db.OrderDao;

import org.json.JSONObject;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/28/028<p>
 * <p>更改时间：2018/12/28/028<p>
 * <p>版本号：1<p>
 */
public class PositionAdapter extends BaseAdapter {
    private List<JSONObject>  positons;
    private Context context;
    private IonPositionClick positionClick;

    public List<JSONObject> getPositons() {
        return positons;
    }

    public void setPositons(List<JSONObject> positons) {
        this.positons = positons;
    }

    public PositionAdapter(List<JSONObject> positons, Context context, IonPositionClick listener) {
        this.positons = positons;
        this.context = context;
        this.positionClick=listener;
    }

    @Override
    public int getCount() {
        return positons.size();
    }

    @Override
    public Object getItem(int position) {
        return positons.get(position);
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_position,parent,false);
            viewHoder.textView=convertView.findViewById(R.id.item_position_positon);
            viewHoder.setting=convertView.findViewById(R.id.item_position_setting);
            viewHoder.name=convertView.findViewById(R.id.position_name);
            viewHoder.phone=convertView.findViewById(R.id.position_phone);
            viewHoder.delete=convertView.findViewById(R.id.position_delete);
            convertView.setTag(viewHoder);
        }else {
            viewHoder= (ViewHoder) convertView.getTag();
        }
        viewHoder.textView.setText(positons.get(position).optString(OrderDao.ADDRESS));
        viewHoder.name.setText(positons.get(position).optString(OrderDao.RECEIVER));
        viewHoder.phone.setText(positons.get(position).optString(OrderDao.PHONE));

        if (positionClick!=null){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionClick.onItemClick(positons.get(position),position);
                }
            });

            viewHoder.setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionClick.onSettingClick(positons.get(position),position);
                }
            });
            viewHoder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionClick.onDeleteClick(positons.get(position),position);
                }
            });

        }
        return convertView;
    }
    class  ViewHoder{
        TextView textView;
        ImageView setting;
        ImageView delete;
        TextView name;
        TextView phone;
    }
}
