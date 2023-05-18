package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adpter.VideoAdapter;
import com.example.myapplication.item.VideoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UApiActivity extends AppCompatActivity {

    private static final String TAG = "YouTubeAPIExample";
    private static final String API_KEY = "AIzaSyDc-hvoSdvNVGb1gZ02sw5qnugdNgPhApM";
    private ArrayList<VideoItem> videoItems;
    private VideoAdapter videoAdapter;
    private RecyclerView URecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_api);

        videoItems = new ArrayList<>();
        videoAdapter = new VideoAdapter(videoItems);
        URecyclerView = findViewById(R.id.URecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        URecyclerView.setLayoutManager(layoutManager);

        URecyclerView.setAdapter(videoAdapter);

        new Thread(new YouTubeSearchRunnable()).start();
    }

    private class YouTubeSearchRunnable implements Runnable{

        @Override
        public void run() {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try{
                // YouTube API 요청 URL 생성
                String requestUrl = "https://www.googleapis.com/youtube/v3/search" +
                        "?part=snippet" +
                        "&q=풍차돌리기예금" +
                        "&maxResults=10" +
                        "&key=" + API_KEY;

                URL url = new URL(requestUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // HTTP 연결 및 응답 데이터 가져오기
                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    response.append(line);
                }

                //JSON 파싱하여 검색 결과 가져오기
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray items = jsonResponse.getJSONArray("items");

                // 검색 결과 출력
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    JSONObject snippet = item.getJSONObject("snippet");

                    String videoTitle = snippet.getString("title");
                    String videoDescription = snippet.getString("description");
                    String videoId = item.getJSONObject("id").getString("videoId");
                    String thumbnailUrl = snippet.getJSONObject("thumbnails")
                            .getJSONObject("default")
                            .getString("url");

                    VideoItem videoItem = new VideoItem();
                    videoItem.setVideoId(videoId);
                    videoItem.setDescription(videoDescription);
                    videoItem.setTitle(videoTitle);
                    videoItem.setThumbnailUrl(thumbnailUrl);

                    videoItems.add(videoItem);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        videoAdapter.notifyDataSetChanged();
                    }
                });
            }catch (IOException | JSONException e){
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }
                if(reader != null){
                    try{
                        reader.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            }


        }
    }
