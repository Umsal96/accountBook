package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adpter.CustomAdapter;
import com.example.myapplication.Uitl.DateUtil;
import com.example.myapplication.Uitl.GetDateUtil;
import com.example.myapplication.item.listItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ListFragment extends Fragment {
    GetDateUtil getDateUtil = new GetDateUtil();
    DateUtil dateUtil = new DateUtil();
    private Button before, next;
    private TextView monthCalendar;
    RecyclerView recyclerView;
    int moveMonth = 0;
    private CustomAdapter customAdapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
    private ArrayList<listItem> update(Calendar calendar, int moveMonth){
        ArrayList<listItem> list = new ArrayList<>();

        String formattedDate = dateUtil.getYearMonth(calendar, moveMonth, dateFormat);
        list = getDateUtil.getMonth(formattedDate, getContext());

        monthCalendar.setText(formattedDate);

        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ArrayList<listItem> li;
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        before = view.findViewById(R.id.before);
        next = view.findViewById(R.id.next);
        monthCalendar = view.findViewById(R.id.monthCalendar);
        recyclerView = view.findViewById(R.id.recyclerView1);

        Calendar calendar = Calendar.getInstance();

        li = update(calendar, moveMonth);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        customAdapter = new CustomAdapter(li);
        recyclerView.setAdapter(customAdapter);

        customAdapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String reType, String reDate, int reMoney) {
                Intent intent = new Intent(getActivity(), detailList.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveMonth = moveMonth + 1;
                ArrayList<listItem> li = update(calendar, moveMonth);
                customAdapter.setList(li);
                customAdapter.notifyDataSetChanged();
                moveMonth = 0;
            }
        });
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveMonth = moveMonth - 1;
                ArrayList<listItem> li = update(calendar, moveMonth);
                customAdapter.setList(li);
                customAdapter.notifyDataSetChanged();
                moveMonth = 0;
            }
        });
        li = new ArrayList<>();
        return view;
    }
}
