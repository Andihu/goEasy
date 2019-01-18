package com.xy.rcs.goeasy.adapter;

import android.widget.CompoundButton;

import org.json.JSONObject;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/12/012<p>
 * <p>更改时间：2018/12/12/012<p>
 * <p>版本号：1<p>
 */
public interface IOnListItemClickListener {
    void onCheckBoxClick(CompoundButton buttonView, boolean isChecked,int position, JSONObject jsonObject);

    void onAddGoodsClick(int position, JSONObject jsonObject);

    void onRemoveClick(int position, JSONObject jsonObject);

    void onClickListener(int position,JSONObject jsonObject);

    public abstract class CheckListenerI implements IOnListItemClickListener {
        @Override
        public void onAddGoodsClick(int position, JSONObject jsonObject) {

        }

        @Override
        public void onRemoveClick(int position, JSONObject jsonObject) {

        }

        @Override
        public void onCheckBoxClick(CompoundButton buttonView, boolean isChecked,int position, JSONObject
                jsonObject) {

        }

        @Override
        public void onClickListener(int position, JSONObject jsonObject) {

        }
    }
}
