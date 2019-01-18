package com.xy.rcs.goeasy.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.db.OrderDao;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/24/024<p>
 * <p>更改时间：2018/12/24/024<p>
 * <p>版本号：1<p>
 */
public class LikeListViewAdapter extends BaseAdapter {
    private static final String TAG = "LikeListViewAdapter";
    private List<JSONObject> mlikes;
    private Context context;
    IOnLikeOrderClickListener listener;
    private boolean isMultSelectStatus;
    private List<JSONObject> temp;



    public WeakHashMap<Integer, Boolean> getmSelected() {
        return mSelected;
    }


    public void setmSelected(int position,boolean isselect) {
        mSelected.put(position,isselect);
    }

    private WeakHashMap<Integer, Boolean> mSelected;

    public LikeListViewAdapter(List<JSONObject> likes, Context context, WeakHashMap<Integer,
            Boolean>
            mSelected, IOnLikeOrderClickListener listener) {
        this.mlikes = likes;
        this.context = context;
        this.mSelected = mSelected;
        this.listener = listener;
        isMultSelectStatus = false;
        this.mSelected = new WeakHashMap<>();
        initSelected();
    }

    public boolean isMultSelectStatus() {
        return isMultSelectStatus;
    }

    public void setMultSelectStatus(boolean multSelectStatus) {
        isMultSelectStatus = multSelectStatus;
    }

    public void initSelected() {

        for (int i = 0; i < mlikes.size(); i++) {
            mSelected.put(i, false);
        }
    }
    public void initSelectedTrue(){
        for (int i = 0; i < mlikes.size(); i++) {
            mSelected.put(i, true);
        }
    }


    public List<JSONObject> getMlikes() {
        temp=new ArrayList<>();
        for ( JSONObject js: mlikes) {
            temp.add(js);
        }
        return temp;
    }

    public void setMlikes(List<JSONObject> mlikes) {
        this.mlikes = mlikes;
    }


    @Override
    public int getCount() {
        return mlikes.size();
    }

    @Override
    public Object getItem(int position) {
        return mlikes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView == null) {
            viewHoder = new ViewHoder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_like_listview,parent,false);
            viewHoder.checkBox = convertView.findViewById(R.id.like_checkbox);
            viewHoder.imageView = convertView.findViewById(R.id.like_cart_img);
            viewHoder.name = convertView.findViewById(R.id.like_cart_name);
            viewHoder.modle = convertView.findViewById(R.id.like_cart_size);
            viewHoder.prices = convertView.findViewById(R.id.like_cart_price);
            viewHoder.Delete = convertView.findViewById(R.id.like_delete);
            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        if (!isMultSelectStatus) {
            viewHoder.checkBox.setVisibility(View.GONE);
        }else {
            viewHoder.checkBox.setVisibility(View.VISIBLE);
        }

        viewHoder.checkBox.setChecked(mSelected.get(position));

        Glide.with(context).load(mlikes.get(position).optString(OrderDao.ICON)).into(viewHoder
                .imageView);
        viewHoder.name.setText(mlikes.get(position).optString(OrderDao.NAME));
        viewHoder.modle.setText(mlikes.get(position).optString(OrderDao.MODEL));
        viewHoder.prices.setText(mlikes.get(position).optString(OrderDao.PRICES));
        if (listener != null) {
            viewHoder.Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeletClick(position, mlikes.get(position));
                }
            });

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onListItemLongClick(position,mlikes.get(position),mSelected.get(position),isMultSelectStatus);
                    return false;
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onListItemClick(position,mlikes.get(position),mSelected.get(position),isMultSelectStatus);
                }
            });
        }
        return convertView;
    }

    class ViewHoder {
        CheckBox checkBox;
        ImageView imageView;
        TextView name;
        TextView modle;
        TextView prices;
        TextView Delete;
    }


}
