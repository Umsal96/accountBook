package com.example.myapplication.Adpter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.item.VideoItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private ArrayList<VideoItem> localDataSet;
    private Context context;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(String link, int num, String type, String department, String title, String date, int cnt);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView thumbnailImageView;
        private TextView titleTextView, descriptionTextView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }

    public VideoAdapter(ArrayList<VideoItem> DataSet){
        this.localDataSet = DataSet;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        VideoAdapter.ViewHolder viewHolder = new VideoAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getLayoutPosition();
                if(position != RecyclerView.NO_POSITION){
                    String url = "https://www.youtube.com/watch?v=" + localDataSet.get(position).getVideoId();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    v.getContext().startActivity(intent);
                }

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {
        VideoItem item = localDataSet.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());

        Picasso.get()
                .load(item.getThumbnailUrl())
                .placeholder(R.drawable.image_loading_drawable)
                .error(R.drawable.image_error_drawable)
                .into(holder.thumbnailImageView);
    }

    @Override
    public int getItemCount() {
        if(localDataSet == null){
            return 0;
        }
        return localDataSet.size();
    }
}
