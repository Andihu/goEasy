package com.xy.rcs.goeasy.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.utils.Constant;

import org.json.JSONObject;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHoder> {
    private static final String TAG = "RecyclerViewAdapter";
    @NonNull
    private Context context;

    private List<JSONObject> jsonObjects;

    public interface OnItemClickListener {
        void onItemClickListener(JSONObject good);
    }

    private OnItemClickListener monItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.monItemClickListener = listener;

    }

    public RecyclerViewAdapter(Context context, List<JSONObject> jsonObjects) {

        this.context = context;
        this.jsonObjects=jsonObjects;

    }

    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recyclerview, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoder holder, final int position) {

        Glide.with(context).load(jsonObjects.get(position).optString(Constant.TABLE_ROW_ICON)).into(holder.goodImg);

        holder.name.setText(jsonObjects.get(position).optString(Constant.TABLE_ROW_NAME));

        holder.prices.setText( jsonObjects.get(position).optString(Constant.TABLE_ROW_PRICES));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (monItemClickListener != null) {
                    monItemClickListener.onItemClickListener(jsonObjects.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return jsonObjects.size();
    }

    class ViewHoder extends RecyclerView.ViewHolder {
        ImageView goodImg;
        TextView name;
        TextView prices;

        public ViewHoder(View itemView) {
            super(itemView);
            goodImg = itemView.findViewById(R.id.goods_icon);
            name = itemView.findViewById(R.id.name);
            prices = itemView.findViewById(R.id.price);
        }
    }


}

