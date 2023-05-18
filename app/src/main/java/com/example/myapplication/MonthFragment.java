package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Uitl.AccountUtil;
import com.example.myapplication.Uitl.DateUtil;
import com.example.myapplication.Uitl.GetDateUtil;
import com.example.myapplication.item.listItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MonthFragment extends Fragment {

    private TextView monthCalendar;
    private FloatingActionButton toInput2;

    // 58 지출, 59 수익, 53 저축, 47 부채, 46 상환, 48 잔액
    private TextView textView58, textView59, textView53, textView47, textView46, textView48;
    private Button before, next;
    GetDateUtil getDateUtil = new GetDateUtil();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
    int moveMonth = 0;
    DateUtil dateUtil = new DateUtil();
    AccountUtil accountUtil = new AccountUtil();
    private void update(Calendar calendar, int moveMonth){

        ArrayList<listItem> list = new ArrayList<>();

        String formattedDate = dateUtil.getYearMonth(calendar, moveMonth, dateFormat);
        list = getDateUtil.getMonth(formattedDate, getContext());

        monthCalendar.setText(formattedDate);

        String formattedIncome = accountUtil.calculateIncome(list);
        String formattedExpenses = accountUtil.calculateExpenses(list);
        String formattedSaving = accountUtil.calculateSaving(list);
        String formattedDebt = accountUtil.calculateDebt(list);
        String formattedDebtRepayment = accountUtil.calculateDebtRepayment(list);
        String formattedBalance = accountUtil.calculateDebtRepaymentBalance();

        textView58.setText(formattedExpenses);
        textView59.setText(formattedIncome);
        textView53.setText(formattedSaving);
        textView47.setText(formattedDebt);
        textView46.setText(formattedDebtRepayment);
        textView48.setText(formattedBalance);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_month, container, false);
        monthCalendar = view.findViewById(R.id.monthCalendar);
        toInput2 = view.findViewById(R.id.toInput2);

        textView58 = view.findViewById(R.id.textView58); // 지출
        textView59 = view.findViewById(R.id.textView59); // 수익
        textView53 = view.findViewById(R.id.textView53); // 저축
        textView47 = view.findViewById(R.id.textView47); // 부채
        textView46 = view.findViewById(R.id.textView46); // 상환
        textView48 = view.findViewById(R.id.textView48); // 잔액

        before = view.findViewById(R.id.before);
        next = view.findViewById(R.id.next);

        Calendar calendar = Calendar.getInstance();
        update(calendar, moveMonth);

        toInput2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), input.class);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveMonth = moveMonth + 1;
                update(calendar, moveMonth);
                moveMonth = 0;
            }
        });

        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveMonth = moveMonth - 1;
                update(calendar, moveMonth);
                moveMonth = 0;
            }
        });

        return view;
    }
}
