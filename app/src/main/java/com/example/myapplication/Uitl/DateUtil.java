package com.example.myapplication.Uitl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

    public String getDate(Calendar calendar, int moveDate, SimpleDateFormat dateFormat){
        calendar.add(Calendar.DAY_OF_MONTH, moveDate);
        String formattedDate = dateFormat.format(calendar.getTime());

        // 현재 달의 마지막 날인지 확인
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        if(currentDay == lastDay + 1){
            // 다음 달로 이동
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            formattedDate = dateFormat.format(calendar.getTime());
        }

        return formattedDate;
    }
    public String getYearMonth(Calendar calendar, int moveDate, SimpleDateFormat dateFormat){
        calendar.add(Calendar.MONTH, moveDate);
        String formattedDate = dateFormat.format(calendar.getTime());

        if(calendar.get(Calendar.MONTH) == 13){
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.MONTH, 1);
            formattedDate = dateFormat.format(calendar.getTime());
        }
        return formattedDate;
    }

}
