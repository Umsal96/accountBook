package com.example.myapplication.Adpter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.BudgetModify;
import com.example.myapplication.Main;
import com.example.myapplication.R;
import com.example.myapplication.item.BudgetItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BudgetListAdapter extends BaseAdapter {
    private ArrayList<BudgetItem> bData;
    private LayoutInflater mInflater;
    private Context context;

    public BudgetListAdapter(Context context, ArrayList<BudgetItem> data){
        bData = data;
        mInflater = LayoutInflater.from(context);
        this.context = context;
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
            convertView = mInflater.inflate(R.layout.budget_list_item, null);
            holder = new ViewHolder();
            holder.category = convertView.findViewById(R.id.category);
            holder.money = convertView.findViewById(R.id.money);
            holder.modify = convertView.findViewById(R.id.modify);
            holder.removeButton = convertView.findViewById(R.id.removeButton);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String category = bData.get(position).getCategory();
        int money = bData.get(position).getMoney();
        DecimalFormat df = new DecimalFormat("#,###");
        String sMoney = df.format(money);

        holder.category.setText(category);
        holder.money.setText(sMoney);
        holder.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BudgetModify.class);
                intent.putExtra("position", position);
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("budget", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String realDate = bData.get(position).getDate().replace(".","");
                String category = holder.category.getText().toString();
                String re = realDate+"_"+category;

                editor.remove(re);
                editor.apply();

                Intent intent = new Intent(context, Main.class);
                intent.putExtra("fragmentToLoad", "BudgetFragment");
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        return convertView;
    }
    private static class ViewHolder{
        TextView category, money;
        Button modify, removeButton;
    }
}
