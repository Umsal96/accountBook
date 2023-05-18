package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Adpter.StatisticsAdapter;
import com.example.myapplication.Uitl.DateUtil;
import com.example.myapplication.Uitl.GetDateUtil;
import com.example.myapplication.Uitl.StatisticsUtil;
import com.example.myapplication.item.StatisticsItem;
import com.example.myapplication.item.listItem;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class StatisticsIncomeFragment extends Fragment {

    private Button next, before;
    private TextView calenderMonth, emptyTextView;
    int moveMonth = 0;
    DateUtil dateUtil = new DateUtil();
    GetDateUtil getDateUtil = new GetDateUtil();
    private ListView mListView;
    private StatisticsAdapter mAdapter;
    private PieChart pieChart;
    StatisticsUtil statisticsUtil =new StatisticsUtil();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
    String[] incomeButtonNames = {"월급", "용돈", "상여", "금용소득", "기타"};
    String[] incomesColor = {"#ff9f22", "#4CAF50", "#2196F3", "#9C27B0", "#673AB7"};

    private void setupPieChart(PieChart pieChart, ArrayList<PieEntry> entries, String title, ArrayList<Integer> colors){
        PieDataSet dataSet = new PieDataSet(entries, title);
        dataSet.setColors(colors);

        pieChart.setData(new PieData(dataSet));
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(20f);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleRadius(0f);
        pieChart.setTransparentCircleRadius(0f);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pieChart.animateY(1000, Easing.EaseInOutQuad);
            }
        }, 0);
    }

    public void updateViewForMonth(String month, PieChart pieChart){
        ArrayList<StatisticsItem> sii;

        ArrayList<listItem> li = getDateUtil.getMonthType(month, getContext(), "I");

        for (int i = 0; i < incomeButtonNames.length ; i++) {
            statisticsUtil.SetArray(li, incomeButtonNames[i]);
        }

        sii = statisticsUtil.SetItem();

        calenderMonth.setText(month);

        float total = 0;
        float[] fList = new float[li.size()];
        float[] fPercent = new float[li.size()];

        for (int i = 0; i < sii.size(); i++) {
            total += sii.get(i).getMoney();
            fList[i] = sii.get(i).getMoney();
        }

        for (int i = 0; i < sii.size(); i++) {
            fPercent[i] = fList[i] / total * 100f;
        }

        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < sii.size(); i++) {
            float percent = fPercent[i];
            String label = String.format(Locale.getDefault(), "%s %.2f%%", sii.get(i).getCategory(), percent);
            entries.add(new PieEntry(percent, label));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < incomesColor.length; i++) {
            colors.add(Color.parseColor(incomesColor[i]));
        }

        setupPieChart(pieChart, entries, "제목", colors);

        if(sii.size() == 0){
            emptyTextView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            pieChart.setVisibility(View.GONE);
        }else {
            mListView.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }

        mAdapter = new StatisticsAdapter(getContext(), sii);
        mListView.setAdapter(mAdapter);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_income_statistics, container, false);

        emptyTextView = view.findViewById(R.id.emptyTextView);
        calenderMonth = view.findViewById(R.id.calendarMonth);
        next = view.findViewById(R.id.next);
        before = view.findViewById(R.id.before);
        mListView = view.findViewById(R.id.listView1);
        PieChart pieChart = view.findViewById(R.id.pieChart);

        Calendar calendar = Calendar.getInstance();
        String month = dateUtil.getYearMonth(calendar, moveMonth, dateFormat);
        updateViewForMonth(month, pieChart);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String month = "";
                moveMonth = moveMonth + 1;

                month = dateUtil.getYearMonth(calendar, moveMonth, dateFormat);
                updateViewForMonth(month, pieChart);

                moveMonth = 0;
            }
        });

        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String month = "";

                moveMonth = moveMonth - 1;
                month = dateUtil.getYearMonth(calendar, moveMonth, dateFormat);
                updateViewForMonth(month, pieChart);
                moveMonth = 0;
            }
        });
        return view;
    }
}
