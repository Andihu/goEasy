package com.xy.rcs.goeasy.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.db.OrderDao;
import com.xy.rcs.goeasy.widget.ChildLiistView;

import org.json.JSONObject;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/25/025<p>
 * <p>更改时间：2018/12/25/025<p>
 * <p>版本号：1<p>
 */
public class OrderListAdapter extends BaseAdapter {
    private static final String TAG = "OrderListAdapter";

    private List<JSONObject> jsonObjects;
    private Context context;
    private OrderDao orderDao;
    private IorderClick listensr;

    public List<JSONObject> getJsonObjects() {
        return jsonObjects;
    }

    public void setJsonObjects(List<JSONObject> jsonObjects) {
        this.jsonObjects = jsonObjects;
    }




    public OrderListAdapter(List<JSONObject> jsonObjects, Context context, IorderClick listensr) {
        this.jsonObjects = jsonObjects;
        this.context = context;
        this.listensr = listensr;
        orderDao = new OrderDao(context);
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView == null) {
            viewHoder = new ViewHoder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order, null);
            viewHoder.listView = convertView.findViewById(R.id.item_listview);
            viewHoder.time = convertView.findViewById(R.id.attime);
            viewHoder.total = convertView.findViewById(R.id.order_tatol);
            viewHoder.delete = convertView.findViewById(R.id.order_delet);
            viewHoder.orderStatus = convertView.findViewById(R.id.order_stutas);
            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        Cursor c = orderDao.querySubAllbyID(jsonObjects.get(position).optString("_id"));
        viewHoder.listView.setAdapter(new SubAdapter(context, c, false));
        viewHoder.listView.setDividerHeight(0);
        viewHoder.time.setText(jsonObjects.get(position).optString("createAt"));
        viewHoder.total.setText(jsonObjects.get(position).optString("total"));
        viewHoder.orderStatus.setText(jsonObjects.get(position).optString("status"));
        if (listensr != null) {
            viewHoder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listensr.ondeleteClick(jsonObjects.get(position), position);
                }
            });
            viewHoder.orderStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listensr.onStatusClick(jsonObjects, position);
                }
            });
        }
        return convertView;
    }

    class ViewHoder {
        ChildLiistView listView;
        TextView time;
        TextView total;
        TextView delete;
        TextView orderStatus;
    }

    class SubAdapter extends CursorAdapter {

        public SubAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }


        @Override

        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sub_item, viewGroup,
                    false);
            ViewHoder viewHoder = new ViewHoder();
            viewHoder.name = view.findViewById(R.id.order_cart_name);
            viewHoder.size = view.findViewById(R.id.order_cart_size);
            viewHoder.prices = view.findViewById(R.id.order_cart_price);
            viewHoder.icon = view.findViewById(R.id.order_cart_img);
            viewHoder.amount = view.findViewById(R.id.order_cart_amount);
            view.setTag(viewHoder);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHoder viewHoder = (ViewHoder) view.getTag();


            String name = cursor.getString(cursor.getColumnIndex(OrderDao.NAME));
            String size = cursor.getString(cursor.getColumnIndex(OrderDao.MODEL));
            String prices = cursor.getString(cursor.getColumnIndex(OrderDao.PRICES));
            String img = cursor.getString(cursor.getColumnIndex(OrderDao.ICON));
            String amountt = cursor.getString(cursor.getColumnIndex(OrderDao.AMOUNT));

            viewHoder.name.setText(name);
            viewHoder.size.setText(size);
            viewHoder.prices.setText(prices+"￥");
            viewHoder.amount.setText("x" + amountt);

            Glide.with(context).load(img).into(viewHoder.icon);

        }

        public class ViewHoder {
            private ImageView icon;
            private TextView name;
            private TextView size;
            private TextView prices;
            private TextView amount;
        }

    }


}
