package com.example.myapplication.Uitl;

import com.example.myapplication.item.StatisticsItem;
import com.example.myapplication.item.listItem;

import java.util.ArrayList;

public class StatisticsUtil {
    ArrayList<StatisticsItem> si = new ArrayList<>();
    public void SetArray(ArrayList<listItem> li, String categoryName){
        StatisticsItem s = new StatisticsItem();

        int categorySum = 0;
        for (int i = 0; i < li.size(); i++) {
            if(li.get(i).getReCategory().equals(categoryName)){
                categorySum += li.get(i).getReMoney();
            }
        }

        if(categorySum != 0){
            s.setCategory(categoryName);
            s.setMoney(categorySum);
            si.add(s);
        }
    }

    public ArrayList<StatisticsItem> SetItem(){
        ArrayList<StatisticsItem> sii;
        sii = si;
        float total = 0;
        for (int i = 0; i < sii.size(); i++) {
            total += sii.get(i).getMoney();
        }

        for (int i = 0; i < sii.size(); i++) {

            float a = sii.get(i).getMoney() / total * 100f;
            float b = Float.parseFloat(String.format("%.2f", a));

            sii.get(i).setPercent(b);
        }
        this.si = new ArrayList<>();
        return sii;
    }

}
