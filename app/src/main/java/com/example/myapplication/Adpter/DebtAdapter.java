package com.example.myapplication.Adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.item.listItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DebtAdapter extends RecyclerView.Adapter<DebtAdapter.ViewHolder> {

    private ArrayList<listItem> localDataSet;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(int position, String date, String category, String content, int money);
    }
    public void setItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView debt_Date, debt_Category, debt_Content, debt_Money;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            debt_Date = itemView.findViewById(R.id.debt_Date);
            debt_Category = itemView.findViewById(R.id.debt_Category);
            debt_Content = itemView.findViewById(R.id.debt_Content);
            debt_Money = itemView.findViewById(R.id.debt_Money);
        }
    }

    public DebtAdapter(ArrayList<listItem> DataSet){
        this.localDataSet = DataSet;
    }

    @NonNull
    @Override
    public DebtAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.debt_list_item, parent, false);
        DebtAdapter.ViewHolder viewHolder = new DebtAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DebtAdapter.ViewHolder holder, int position) {
        listItem item = localDataSet.get(position);
        int num = item.getReMoney();
        DecimalFormat df = new DecimalFormat("#,###");
        String formattedNumber = df.format(num);
        holder.debt_Date.setText(item.getReDate());
        holder.debt_Category.setText(item.getReCategory());
        holder.debt_Money.setText(formattedNumber);
        holder.debt_Content.setText(item.getReContent());
    }

    @Override
    public int getItemCount() {
        if(localDataSet == null){
            return 0;
        }
        return localDataSet.size();
    }
}
