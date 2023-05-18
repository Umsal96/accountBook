package com.example.myapplication.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.item.StatisticsItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StatisticsAdapter extends BaseAdapter {
    private ArrayList<StatisticsItem> sData;
    private LayoutInflater mInflater;

    public StatisticsAdapter(Context context, ArrayList<StatisticsItem> data){
        sData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sData.size();
    }

    @Override
    public Object getItem(int position) {
        return sData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.listPercent = convertView.findViewById(R.id.listPercent);
            holder.listCategory = convertView.findViewById(R.id.listCategory);
            holder.listMoney = convertView.findViewById(R.id.listMoney);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        float percent = sData.get(position).getPercent();
        String category = sData.get(position).getCategory();
        int money = sData.get(position).getMoney();

        String sPercent = String.format("%.2f", percent);
        DecimalFormat df = new DecimalFormat("#,###");
        String sMoney = df.format(money);

        holder.listPercent.setText(sPercent + "%");
        holder.listCategory.setText(category);
        holder.listMoney.setText(sMoney);

        return convertView;
    }

    private static class ViewHolder{
        TextView listPercent, listCategory, listMoney;
    }
}

