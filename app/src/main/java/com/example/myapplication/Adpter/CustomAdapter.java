package com.example.myapplication.Adpter;

import android.graphics.Color;
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

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private ArrayList<listItem> localDataSet;
    private OnItemClickListener itemClickListener;

    // 클릭 이벤트를 위한 코드
    public interface OnItemClickListener{
        void onItemClicked(int position, String reType, String reDate, int reMoney);
    }
    public void setList(ArrayList<listItem> list) {
        localDataSet = list;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }
    //========== 뷰 홀더 클래스 ==================================
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView reType, reDate, reMoney, reContent;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            reType = itemView.findViewById(R.id.reType);
            reDate = itemView.findViewById(R.id.reDate);
            reMoney = itemView.findViewById(R.id.reMoney);
//            reContent = itemView.findViewById(R.id.reContent);
        }

    }

    //----- 생성자 -----------------------------------------
    // 생성자를 통해서 데이터를 전달 받도록 함
    public CustomAdapter(ArrayList<listItem> DataSet){
        this.localDataSet = DataSet;
    }

    // -------------- RecyclerView 필수 구현 항목 ------------------
    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        CustomAdapter.ViewHolder viewHolder = new CustomAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getLayoutPosition();
                if(position != RecyclerView.NO_POSITION){
                    String reType = viewHolder.reType.getText().toString();
                    String reDate = viewHolder.reDate.getText().toString();
//                    String reContent = viewHolder.reContent.getText().toString();
                    String inputMoneyNul = viewHolder.reMoney.getText().toString().replace(",",""); // 쉼표 제거
                    int reMoney = Integer.parseInt(inputMoneyNul);
                    itemClickListener.onItemClicked(position, reType, reDate, reMoney);
                }
            }
        });
        // ==================================
        return viewHolder;
    }

    @Override // ViewHolder안의 내용을 position에 해당되는 데이터로 교체한다.
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        listItem item = localDataSet.get(position);
        int num = item.getReMoney();
        DecimalFormat df = new DecimalFormat("#,###");
        String formattedNumber = df.format(num);
        holder.reType.setText(item.getReType());
//        holder.reContent.setText(item.getReContent());
        holder.reDate.setText(item.getReDate());
        holder.reMoney.setText(formattedNumber);

        if(item.getReType().equals("수입")){
            holder.reType.setTextColor(Color.BLUE);
            holder.reMoney.setTextColor(Color.BLUE);
            holder.reDate.setTextColor(Color.BLUE);
        } else if (item.getReType().equals("지출")) {
            holder.reType.setTextColor(Color.RED);
            holder.reMoney.setTextColor(Color.RED);
            holder.reDate.setTextColor(Color.RED);
        }else if (item.getReType().equals("저금")) {
            holder.reType.setTextColor(Color.parseColor("#008000"));
            holder.reMoney.setTextColor(Color.parseColor("#008000"));
            holder.reDate.setTextColor(Color.parseColor("#008000"));
        }else if (item.getReType().equals("부채")) {
            holder.reType.setTextColor(Color.YELLOW);
            holder.reMoney.setTextColor(Color.YELLOW);
            holder.reDate.setTextColor(Color.YELLOW);
        }else if (item.getReType().equals("상환")) {
            holder.reType.setTextColor(Color.parseColor("#800080"));
            holder.reMoney.setTextColor(Color.parseColor("#800080"));
            holder.reDate.setTextColor(Color.parseColor("#800080"));
        }
    }

    @Override
    public int getItemCount() {
        if(localDataSet == null){
            return 0;
        }
        return localDataSet.size();
    }
}
