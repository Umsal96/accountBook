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

public class DebtRepaymentAdapter extends RecyclerView.Adapter<DebtRepaymentAdapter.ViewHolder> {
    private ArrayList<listItem> localDataSet;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(int position, String date, String category, String content, int money);
    }
    public void setItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView debt_repayment_Date, debt_repayment_Category, debt_repayment_Content, debt_repayment_Money;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            debt_repayment_Date = itemView.findViewById(R.id.debt_repayment_Date);
            debt_repayment_Category = itemView.findViewById(R.id.debt_repayment_Category);
            debt_repayment_Content = itemView.findViewById(R.id.debt_repayment_Content);
            debt_repayment_Money = itemView.findViewById(R.id.debt_repayment_Money);
        }
    }

    public DebtRepaymentAdapter(ArrayList<listItem> DataSet){ this.localDataSet = DataSet;}

    @NonNull
    @Override
    public DebtRepaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.debt_repayment_list_item, parent, false);
        DebtRepaymentAdapter.ViewHolder viewHolder = new DebtRepaymentAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DebtRepaymentAdapter.ViewHolder holder, int position) {
        listItem item = localDataSet.get(position);
        int num = item.getReMoney();
        DecimalFormat df = new DecimalFormat("#,###");
        String formattedNumber = df.format(num);
        holder.debt_repayment_Date.setText(item.getReDate());
        holder.debt_repayment_Category.setText(item.getReCategory());
        holder.debt_repayment_Money.setText(formattedNumber);
        holder.debt_repayment_Content.setText(item.getReContent());
    }

    @Override
    public int getItemCount() {
        if(localDataSet == null){
            return 0;
        }
        return localDataSet.size();
    }
}
