package com.example.myapplication.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.item.BudgetItem;
import com.example.myapplication.item.StatisticsItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BudgetFragmentListAdapter extends BaseAdapter {
    private ArrayList<BudgetItem>  bData;
    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<StatisticsItem> SData;

    public BudgetFragmentListAdapter(Context context, ArrayList<BudgetItem> data, ArrayList<StatisticsItem> sData){
        this.bData = data;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.SData = sData;
    }

    @Override
    public int getCount() {
        return bData.size();
    }

    @Override
    public Object getItem(int position) {
        return bData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.fragment_debt_item, null);
            holder = new ViewHolder();
            holder.fCategory = convertView.findViewById(R.id.fCategory);
            holder.fMoney = convertView.findViewById(R.id.fMoney);
            holder.currentMoney = convertView.findViewById(R.id.currentMoney);
            holder.totalMoney = convertView.findViewById(R.id.totalMoney);
            holder.progressBar = convertView.findViewById(R.id.progressBar);
            holder.progressText = convertView.findViewById(R.id.progressText);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println("position " + position);

        holder.fCategory.setText(bData.get(position).getCategory());
        holder.progressBar.setProgress(0);
        holder.progressBar.setMax(100);
        for (int i = 0; i < SData.size(); i++) {
            if (bData.get(position).getCategory().equals(SData.get(i).getCategory())){
                int m = SData.get(i).getMoney();
                String sm = df.format(m);
                holder.currentMoney.setText(sm);
                int maxValue = bData.get(position).getMoney();
                int currentValue = SData.get(i).getMoney();
                int percent = currentValue * 100 / maxValue;
                holder.progressBar.setProgress(percent);
                holder.progressText.setText(Integer.toString(percent)+"%");
            }
        }
        int money = bData.get(position).getMoney();
        String sMoney = df.format(money);
        holder.fMoney.setText(sMoney);
        holder.totalMoney.setText(sMoney);

        return convertView;
    }

    public static class ViewHolder{
        private ProgressBar progressBar;
        private TextView fCategory, fMoney, currentMoney, totalMoney, progressText;
    }
}
