package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.util.Random;

public class DayFragment extends Fragment {

    private TextView todayCalendar;
    private FloatingActionButton toInput;
    // 35는 수익 44는 잔액 26 지출 31 저축 36 부채 37 상환
    private TextView textView35, textView44, textView26, textView31, textView36, textView37, wiseSaying;
    private Button next, before, apiButton, UapiButton;
    int moveDate = 0;
    private final String[] Strings = {
            "돈에 관해 자식을 교육시키는 \n 가장 손쉬운 방법은 \n 그 부모가 돈이 없는 것이다.",
            "돈을 빌려주지도 말고 빌리지도 말라. \n 빌린 사람은 기가 죽고, \n 빌려준 사람도 자칫하면 그 본전은 물론이고,\n 그 친구까지도 잃게 된다.",
            "사람을 상처 입히는 세 개 있다. \n 번민, 말다툼, 텅 빈 지갑이다. \n 그리고 그 중에서 텅 빈 지갑이 가장 크게 사람을 상처 입힌다.",
            "가난하게 태어난 것은 당신의 잘못이 아니다. \n 하지만 가난하게 죽는 것은 당신의 잘못이다.",
            "돈이 세상의 전부는 아니다. \n 적어도 산소만큼은 중요하다.",
            "돈이란 훌룡한 하인이기도 하지만, \n 나쁜 주인이기도 하다.",
            "가격은 우리가 내는 돈이며, \n 가치는 그것을 통해 얻는것이다.",
            "부자들은 투자하고 가난한 사람들은 소비한다. \n 백만 장자들은 저축하고 난 뒤에 남는것을 쓰지, 쓰고 난 뒤에 남는것을 저축하지 않는다. \n 이것이 그들만의 성공비결이다.",
            "돈은 인간을 자유롭게 하지만 \n 지나친 재산은 사람을 노예로 만들곤 한다.",
            "돈 생각을 떨쳐내는 유일한 방법은  \n 돈을 많이 갖는 것이다."
    };

    ArrayList<listItem> list;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    DateUtil dateUtil = new DateUtil();
    GetDateUtil getDateUtil = new GetDateUtil();
    AccountUtil accountUtil = new AccountUtil();
    private void update(Calendar calendar, int moveDate){
        String formattedDate = dateUtil.getDate(calendar, moveDate, dateFormat);
        list = getDateUtil.getToday(formattedDate, getContext());
        todayCalendar.setText(formattedDate);

        String formattedIncome = accountUtil.calculateIncome(list);
        String formattedExpenses = accountUtil.calculateExpenses(list);
        String formattedSaving = accountUtil.calculateSaving(list);
        String formattedDebt = accountUtil.calculateDebt(list);
        String formattedDebtRepayment = accountUtil.calculateDebtRepayment(list);
        String formattedBalance = accountUtil.calculateDebtRepaymentBalance();

        textView26.setText(formattedExpenses);
        textView35.setText(formattedIncome);
        textView31.setText(formattedSaving);
        textView37.setText(formattedDebtRepayment);
        textView36.setText(formattedDebt);
        textView44.setText(formattedBalance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        todayCalendar = view.findViewById(R.id.todayCalendar);
        toInput = view.findViewById(R.id.toInput);
        textView44 = view.findViewById(R.id.textView44); // 잔액
        textView35 = view.findViewById(R.id.textView35); // 수익
        textView26 = view.findViewById(R.id.textView26); // 지출
        textView31 = view.findViewById(R.id.textView31); // 저축
        textView36 = view.findViewById(R.id.textView36); // 부채
        textView37 = view.findViewById(R.id.textView37); // 상환
        wiseSaying = view.findViewById(R.id.wiseSaying);
        apiButton = view.findViewById(R.id.apiButton);
        UapiButton = view.findViewById(R.id.UapiButton);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int index = new Random().nextInt(Strings.length);
                wiseSaying.setText(Strings[index]);
                handler.postDelayed(this, 5000);
            }
        }, 5000);

        next = view.findViewById(R.id.next);
        before = view.findViewById(R.id.before);

        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), input.class);
                startActivity(intent);
            }
        });

        UapiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UApiActivity.class);
                startActivity(intent);
            }
        });

        apiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApiActivity.class);
                startActivity(intent);
            }
        });

        Calendar calendar = Calendar.getInstance();
        update(calendar, moveDate);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveDate = moveDate + 1;
                update(calendar, moveDate);
                moveDate = 0;
            }
        });

        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveDate = moveDate - 1;
                update(calendar, moveDate);
                moveDate = 0;
            }
        });

        return view;
    }
}
