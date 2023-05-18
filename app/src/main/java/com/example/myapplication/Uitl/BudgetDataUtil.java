package com.example.myapplication.Uitl;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.item.BudgetItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class BudgetDataUtil {

    public ArrayList<BudgetItem> budgeMonth(String date, Context context){
        String realDate = date.replace(".","");
        SharedPreferences sharedPreferences = context.getSharedPreferences("budget", MODE_PRIVATE);
        ArrayList<String> matchingKeys = new ArrayList<>();
        ArrayList<String> jsonData = new ArrayList<>();
        ArrayList<BudgetItem> bData = new ArrayList<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();

        for (String key : allEntries.keySet()){
            if(key.startsWith(realDate)){
                matchingKeys.add(key);
            }
        }

        for (int i = 0; i < matchingKeys.size(); i++) {
            jsonData.add(sharedPreferences.getString(matchingKeys.get(i), ""));
        }

        for (int i = 0; i < jsonData.size(); i++) {

            if(!jsonData.get(i).isEmpty()){
                try{
                    BudgetItem budgetItem = new BudgetItem();
                    JSONObject jsonObject = new JSONObject(jsonData.get(i));
                    String getDate = jsonObject.getString("date");
                    String category = jsonObject.getString("category");
                    int money = jsonObject.getInt("money");
                    budgetItem.setCategory(category);
                    budgetItem.setDate(getDate);
                    budgetItem.setMoney(money);
                    bData.add(budgetItem);
                    System.out.println("카테고리 : " + budgetItem.getCategory() + " 날짜 : " + budgetItem.getDate() + " 돈 " + budgetItem.getMoney());
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }
        return bData;
    }
}
