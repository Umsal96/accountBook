package com.example.myapplication.Adpter;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Uitl.CalendarUtil;
import com.example.myapplication.item.listItem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    ArrayList<Date> dayList;
    ArrayList<listItem> income;
    ArrayList<listItem> expenses;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    DecimalFormat df = new DecimalFormat("#,###");

    public CalendarAdapter(ArrayList<Date> dayList, ArrayList<listItem> income, ArrayList<listItem> expenses){
        this.dayList = dayList;
        this.income = income;
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.calendar_cell, parent, false);

        return new CalendarViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position){

        // 날짜 변수에 담기
        Date monthDate = dayList.get(position);

        String formatDate = sdf.format(monthDate);

        for (int i = 0; i < income.size(); i++) {
            if(formatDate.equals(income.get(i).getReDate())){

                int iMoney = income.get(i).getReMoney();
                holder.calendarIncome.setText("+" + df.format(iMoney));
                holder.calendarIncome.setTextColor(Color.BLUE);
            }
        }
        for (int i = 0; i < expenses.size(); i++) {
            if(formatDate.equals(expenses.get(i).getReDate())){

                int eMoney = expenses.get(i).getReMoney();
                holder.calendarExpenses.setText("-"+df.format(eMoney));
                holder.calendarExpenses.setTextColor(Color.RED);
            }
        }

        //달력 초기화
        Calendar dateCalendar = Calendar.getInstance();

        dateCalendar.setTime(monthDate);

        // 현재 년 월
        int currentDay = CalendarUtil.selectedDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = CalendarUtil.selectedDate.get(Calendar.MONTH) + 1;
        int currentYear = CalendarUtil.selectedDate.get(Calendar.YEAR);

        // 넘어온 데이터
        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);

        if(displayMonth == currentMonth && displayYear == currentYear){

            holder.parentView.setBackgroundColor(Color.parseColor("#D5D5D5"));

            // 날짜까지 맞으면 색상 표시
            holder.itemView.setBackgroundColor(Color.parseColor("#CEFBC9"));
        } else{
            holder.parentView.setBackgroundColor(Color.parseColor("#F6F6F6"));
        }

        // 날짜 변수에 담기
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);

        holder.dayText.setText(String.valueOf(dayNo));

        // 텍스트 색상 지정
        if ((position + 1) % 7 == 0){ // 토요일 파랑
            holder.dayText.setTextColor(Color.BLUE);
        } else if (position == 0 || position % 7 == 0) { // 일요일 빨강
            holder.dayText.setTextColor(Color.RED);
        }

        // 날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount(){
        return dayList.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder{

        TextView dayText, calendarIncome, calendarExpenses;
        View parentView;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.dayText);
            parentView = itemView.findViewById(R.id.parentView);
            calendarExpenses = itemView.findViewById(R.id.calendarExpenses);
            calendarIncome = itemView.findViewById(R.id.calendarIncome);
        }
    }
}
