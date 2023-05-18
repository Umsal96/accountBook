package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Adpter.BudgetFragmentListAdapter;
import com.example.myapplication.Uitl.BudgetDataUtil;
import com.example.myapplication.Uitl.DateUtil;
import com.example.myapplication.Uitl.GetDateUtil;
import com.example.myapplication.Uitl.StatisticsUtil;
import com.example.myapplication.item.BudgetItem;
import com.example.myapplication.item.StatisticsItem;
import com.example.myapplication.item.listItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BudgetFragment extends Fragment {

    private Button button6;
    DateUtil dateUtil = new DateUtil();
    int moveMonth = 0;
    GetDateUtil getDateUtil = new GetDateUtil();
    BudgetDataUtil budgetDataUtil = new BudgetDataUtil();
    private ListView listView;
    private BudgetFragmentListAdapter mAdapter;
    StatisticsUtil statisticsUtil = new StatisticsUtil();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
    String[] expensesButtonNames = {"식비", "카페/간식", "술/유흥", "생활", "쇼핑", "미용/패션", "주거/통신", "건강", "부모님",
            "교육", "경조사", "금용", "기타"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        Calendar calendar = Calendar.getInstance();
        String month = dateUtil.getYearMonth(calendar, 0, dateFormat);
        ArrayList<StatisticsItem> sii;
        ArrayList<listItem> li = getDateUtil.getMonthType(month, getContext(),"E");
        ArrayList<BudgetItem> bi = budgetDataUtil.budgeMonth(month, getContext());
        button6 = view.findViewById(R.id.button6);
        listView = view.findViewById(R.id.listView);

        for (int i = 0; i < expensesButtonNames.length; i++) {
            statisticsUtil.SetArray(li, expensesButtonNames[i]);
        }

        sii = statisticsUtil.SetItem();

        mAdapter = new BudgetFragmentListAdapter(getContext(), bi, sii);
        listView.setAdapter(mAdapter);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BudgetList.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
