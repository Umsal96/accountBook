package com.example.myapplication.Adpter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.item.HomeListItem;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<HomeListItem> localDataSet;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(String link, int num, String type, String department, String title, String date, int cnt);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView apiNum, apiType, apiTitle, apiDepartment, apiDate, apiCount;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            apiNum = itemView.findViewById(R.id.apiNum); // 번호
            apiType = itemView.findViewById(R.id.apiType); // 유형
            apiTitle = itemView.findViewById(R.id.apiTitle); // 제목
            apiDate = itemView.findViewById(R.id.apiDate); // 게시일
            apiDepartment = itemView.findViewById(R.id.apiDepartment); // 담당부서
            apiCount = itemView.findViewById(R.id.apiCount); // 조회수
        }
    }

    public HomeAdapter(ArrayList<HomeListItem> DataSet){
        this.localDataSet = DataSet;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.api_item, parent, false);
        HomeAdapter.ViewHolder viewHolder = new HomeAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        HomeListItem item = localDataSet.get(position);
        int num = item.getrNum();
        int count = item.getInqCnt();
        String sNum = String.valueOf(num);
        String sCount = String.valueOf(count);
        holder.apiNum.setText(sNum);
        holder.apiType.setText(item.getAisTpCdNm());
        holder.apiTitle.setText(item.getBbsTl()); // 제목
        holder.apiDepartment.setText(item.getDepNm());
        holder.apiDate.setText(item.getBbsWouDttm());
        holder.apiCount.setText(sCount);

        holder.apiTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = item.getLinkUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(localDataSet == null){
            return 0;
        }
        return localDataSet.size();
    }
}
