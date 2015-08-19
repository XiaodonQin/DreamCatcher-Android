/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/12.
 */
package com.xiaodong.dream.catcher.demo.dreamcatcher.express.search;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.sdk.model.LogisticsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class SearchExpressResultAdapter extends BaseAdapter{

    private Activity context;
    private LayoutInflater inflater;

    private List<LogisticsItem> logisticsItemList;

    public SearchExpressResultAdapter(Activity context) {
        this.context = context;

        logisticsItemList = new ArrayList<LogisticsItem>();

        inflater = LayoutInflater.from(context);
    }

    public void addData(List<LogisticsItem> items){
        logisticsItemList.clear();

        if(items != null){
            logisticsItemList.addAll(items);
        }

        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_express_search_result, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.time = (TextView) convertView.findViewById(R.id.express_time);
            viewHolder.data = (TextView) convertView.findViewById(R.id.express_data);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LogisticsItem logisticsItem = logisticsItemList.get(position);

        viewHolder.time.setText(logisticsItem.getTime());
        viewHolder.data.setText(logisticsItem.getContext());

        return convertView;
    }

    @Override
    public int getCount() {
        return logisticsItemList.size();
    }

    @Override
    public LogisticsItem getItem(int position) {
        return logisticsItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView time;
        TextView data;
    }
}
