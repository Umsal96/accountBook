package com.example.myapplication.Uitl;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.item.listItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class GetDateUtil {
    public listItem setItem(String realDate, String type, String category, int money, String content, int index){
        listItem item = new listItem();
        item.setReDate(realDate);
        item.setReType(type);
        item.setReCategory(category);
        item.setReMoney(money);
        item.setReContent(content);
        item.setIndex(index);

        return item;
    }

    public ArrayList<listItem> getToday (String Date, Context context){
        String realDate = Date.replace(".", ""); // 온점 제거
        SharedPreferences sharedPreferences = context.getSharedPreferences("account_book", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        ArrayList<String> matchingKeys = new ArrayList<>();
        ArrayList<String> indexSelect = new ArrayList<>();
        ArrayList<String> realKey = new ArrayList<>();

        for (String key : allEntries.keySet()){
            if(key.startsWith(realDate)){
                matchingKeys.add(key);
            }
        }

        for (String index : matchingKeys){
            if(index.contains("index")){
                indexSelect.add(index);
            }else {
                realKey.add(index);
            }
        }

        ArrayList<listItem> list = new ArrayList<>();

        System.out.println(realKey);
        for (String key : realKey) {
            if (key.startsWith(realDate)) {
                try {
                    JSONObject jsonObject = new JSONObject(sharedPreferences.getString(key, ""));
                    String getType = jsonObject.getString("type");
                    String getDate = jsonObject.getString("date");
                    String getContent = jsonObject.getString("content");
                    String getCategory = jsonObject.getString("category");
                    int getMoney = jsonObject.getInt("money");
                    int getIndex = jsonObject.getInt("index");
                    list.add(setItem(getDate, getType, getCategory, getMoney, getContent, getIndex));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public ArrayList<listItem> getMonth(String Date, Context context){

        String[] token = Date.split("\\.");

        String sMonth = token[1];
        String sYear = token[0];
        String MY = sYear + sMonth;

        SharedPreferences sharedPreferences = context.getSharedPreferences("account_book", MODE_PRIVATE);
        ArrayList<listItem> returnList = new ArrayList<>();
        ArrayList<String> matchingKeys = new ArrayList<>(); // 해당 년월에 맞는 키가 들어가는 배열
        ArrayList<String> indexKey = new ArrayList<>(); // 인덱스 키값이 들어가는 배열
        ArrayList<String> listKey = new ArrayList<>(); // 인덱스 이회의 키값이 들어가는 배열
        
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (String key : allEntries.keySet()) {
            if (key.startsWith(MY)) {
                matchingKeys.add(key);
            }
        }
        for (String key : matchingKeys){
            if(key.contains("index")){
                indexKey.add(key);
            }else {
                listKey.add(key.substring(0, 14));
            }
        }

        for (String key : listKey){
            if (key.startsWith(MY)) {
                try {
                    JSONObject jsonObject = new JSONObject(sharedPreferences.getString(key, ""));
                    String getType = jsonObject.getString("type");
                    String getDate = jsonObject.getString("date");
                    String getContent = jsonObject.getString("content");
                    String getCategory = jsonObject.getString("category");
                    int getMoney = jsonObject.getInt("money");
                    int getIndex = jsonObject.getInt("index");
                    returnList.add(setItem(getDate, getType, getCategory, getMoney, getContent, getIndex));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return returnList;
    }

    public ArrayList<listItem> getMonthType(String Date, Context context , String tt){

        String[] token = Date.split("\\.");

        String sMonth = token[1];
        String sYear = token[0];
        String MY = sYear + sMonth;

        SharedPreferences sharedPreferences = context.getSharedPreferences("account_book", MODE_PRIVATE);

        ArrayList<String> matchingKeys = new ArrayList<>(); // 해당 년월에 맞는 키가 들어가는 배열
        ArrayList<String> indexKey = new ArrayList<>(); // 인덱스 키값이 들어가는 배열
        ArrayList<String> listKey = new ArrayList<>();
        ArrayList<listItem> le = new ArrayList<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();

        // 모든 키값을 가져오기
        for (String key : allEntries.keySet()) {
            if (key.startsWith(MY)) {
                matchingKeys.add(key);
            }
        }
        // E 가 포함된 키값을 가져오기
        for (String key : matchingKeys){
            if(key.contains("index")){
                indexKey.add(key);
            }else if (key.contains(tt)){
                listKey.add(key.substring(0, 14));
            }
        }

        for (String key : listKey){
            if (key.startsWith(MY)) {
                try {
                    JSONObject jsonObject = new JSONObject(sharedPreferences.getString(key, ""));
                    String getType = jsonObject.getString("type");
                    String getDate = jsonObject.getString("date");
                    String getContent = jsonObject.getString("content");
                    String getCategory = jsonObject.getString("category");
                    int getMoney = jsonObject.getInt("money");
                    int getIndex = jsonObject.getInt("index");
                    le.add(setItem(getDate, getType, getCategory, getMoney, getContent, getIndex));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return le;
    }

}
