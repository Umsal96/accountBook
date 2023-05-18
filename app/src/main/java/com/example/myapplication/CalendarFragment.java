package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adpter.CalendarAdapter;
import com.example.myapplication.Uitl.CalendarUtil;
import com.example.myapplication.Uitl.GetDateUtil;
import com.example.myapplication.item.listItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment{

    TextView monthYearText; // 년월 텍스트뷰

    RecyclerView CalendarRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // 초기화
        monthYearText = view.findViewById(R.id.monthYearText);
        ImageButton prevBtn = view.findViewById(R.id.pre_btn);
        ImageButton nextBtn = view.findViewById(R.id.next_btn);
        CalendarRecyclerView = view.findViewById(R.id.CalendarRecyclerView);

        // 현재 날짜
        CalendarUtil.selectedDate = Calendar.getInstance();

        // 화면 설정
        setMonthView();

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                // -1 한 월을 넣어준다. (2월 -> 1월)
                CalendarUtil.selectedDate.add(Calendar.MONTH, -1);
                setMonthView();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                CalendarUtil.selectedDate.add(Calendar.MONTH, 1);
                setMonthView();
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String yearMonthFromDate(Calendar calendar){

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        String yearMonth = year + "년 " + month + "월";

        return yearMonth;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        //년월 텍스트뷰 셋팅
        monthYearText.setText(yearMonthFromDate(CalendarUtil.selectedDate));

        GetDateUtil getDateUtil = new GetDateUtil();

        ArrayList<listItem> income = new ArrayList<>();
        ArrayList<listItem> expenses = new ArrayList<>();

        String origin = yearMonthFromDate(CalendarUtil.selectedDate);

        // 포맷을 yyyy.MM 형태로 바꿈
        String convert = transYearMonth(origin);

        income = getDateUtil.getMonthType(convert, getContext(), "I");
        expenses = getDateUtil.getMonthType(convert, getContext(), "E");

        ArrayList<Date> dayList = daysInMonthArray();

        CalendarAdapter adapter = new CalendarAdapter(dayList, income, expenses);

        // 레이아웃 설정 ( 열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 7);

        // 레이아웃 적용
        CalendarRecyclerView.setLayoutManager(manager);

        // 어뎁터 적용
        CalendarRecyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Date> daysInMonthArray(){

        ArrayList<Date> dayList = new ArrayList<>();

        // 날짜 복사해서 변수 생성
        Calendar monthCalendar = (Calendar) CalendarUtil.selectedDate.clone();

        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;

        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while(dayList.size() < 42){
            dayList.add(monthCalendar.getTime());

            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dayList;
    }

    private String transYearMonth(String input){

        String part[] = input.split("년|월");

        String y = part[0];

        String inputNul = part[1].replace(" ",""); // 쉼표 제거

        int m = Integer.parseInt(inputNul);

        String formattedMonth = String.format("%02d", m);

        String convert = y + "." + formattedMonth;

        return convert;
    }
}
