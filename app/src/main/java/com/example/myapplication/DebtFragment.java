package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adpter.DebtAdapter;
import com.example.myapplication.Adpter.DebtRepaymentAdapter;
import com.example.myapplication.Uitl.DateUtil;
import com.example.myapplication.Uitl.GetDateUtil;
import com.example.myapplication.item.listItem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DebtFragment extends Fragment {
    RecyclerView dept_recyclerView, dept_repayment_recyclerView;
    GetDateUtil getDateUtil = new GetDateUtil();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
    DateUtil dateUtil = new DateUtil();
    ConstraintLayout debt_layout;
    TextView textView61, textView62, textView63, textView64;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_debt, container, false);
        ArrayList<listItem> li;

        int totalDebt = 0;
        int totalDeptRepayment = 0;
        int reDebt = 0;

        debt_layout = view.findViewById(R.id.debt_layout);
        textView61 = view.findViewById(R.id.textView61); // 이달 부채 총합
        textView62 = view.findViewById(R.id.textView62); // 이달 부채상환 총합
        textView63 = view.findViewById(R.id.textView63); // 남은 부채
        textView64 = view.findViewById(R.id.textView64);

        dept_recyclerView = view.findViewById(R.id.dept_recyclerView);
        dept_repayment_recyclerView = view.findViewById(R.id.dept_repayment_recyclerView);
        Calendar calendar = Calendar.getInstance();

        String formattedDate = dateUtil.getYearMonth(calendar, 0, dateFormat);
        li = getDateUtil.getMonthType(formattedDate, getContext(), "D");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        DecimalFormat format = new DecimalFormat("#,###");
        // 이달의 총 부채
        for (int i = 0; i < li.size(); i++) {
            totalDebt = totalDebt + li.get(i).getReMoney();
        }
        String d = format.format(totalDebt);
        textView61.setText(d);

        dept_recyclerView.setLayoutManager(linearLayoutManager);

        DebtAdapter debtAdapter = new DebtAdapter(li);
        dept_recyclerView.setAdapter(debtAdapter);

        ArrayList<listItem> si;
        si = getDateUtil.getMonthType(formattedDate, getContext(), "R");
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());

        // 이달의 총 상환
        for (int i = 0; i < si.size(); i++) {
            totalDeptRepayment = totalDeptRepayment + si.get(i).getReMoney();
        }

        String r = format.format(totalDeptRepayment);

        textView62.setText(r);

        // 남은 부채
        reDebt = totalDebt - totalDeptRepayment;

        if(reDebt < 0) reDebt = 0;

        String rd = format.format(reDebt);

        textView63.setText(rd);

        if(reDebt == 0){
            debt_layout.setBackgroundResource(R.drawable.border_blue);
            String ddd = "이달은 부채를 다 갚았습니다.";
            textView64.setText(ddd);
        }else {
            debt_layout.setBackgroundResource(R.drawable.border_red);
            String ddd = "이달은 부채를 다 갚지 못하였습니다.";
            textView64.setText(ddd);
        }

        dept_repayment_recyclerView.setLayoutManager(linearLayoutManager1);

        DebtRepaymentAdapter debtRepaymentAdapter = new DebtRepaymentAdapter(si);
        dept_repayment_recyclerView.setAdapter(debtRepaymentAdapter);

        return view;
    }

}
