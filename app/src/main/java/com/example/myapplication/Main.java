package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

public class Main extends AppCompatActivity {

    StatisticsExpensesFragment statisticsExpensesFragment; // 지출 통계
    StatisticsIncomeFragment statisticsIncomeFragment; // 수입 통계
    DebtFragment debtFragment; // 부채
    BudgetFragment budgetFragment; // 예산
    DayFragment dayFragment;
    MonthFragment monthFragment;
    CalendarFragment calendarFragment;
    ListFragment listFragment;
//    private MyPreferenceUtils myPreferenceUtils = new MyPreferenceUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        statisticsExpensesFragment = new StatisticsExpensesFragment();
        statisticsIncomeFragment = new StatisticsIncomeFragment();

        debtFragment = new DebtFragment();
        budgetFragment = new BudgetFragment();
        dayFragment = new DayFragment();
        monthFragment = new MonthFragment();
        calendarFragment = new CalendarFragment();
        listFragment = new ListFragment();

        NavigationBarView MainNavigationBarView = findViewById(R.id.button_main_navigationview);
        NavigationBarView SubNavigationBarView = findViewById(R.id.button_sub_navigationview);
        NavigationBarView StatisticsNavigationBarView = findViewById(R.id.button_statistics);

        Intent intent = getIntent();
        String fragmentToLoad = intent.getStringExtra("fragmentToLoad");
        StatisticsNavigationBarView.setVisibility(View.GONE);

        //외부 엑티비티에서 프레그먼트로 들어왔을때 인텐트에 들어있는 텍스트를 보고 프래그먼트 강제 이동

        if (fragmentToLoad != null && fragmentToLoad.equals("ListFragment")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.containers, listFragment).commit();
            SubNavigationBarView.setVisibility(View.GONE);
            MainNavigationBarView.setSelectedItemId(R.id.list);
        } else if (fragmentToLoad != null && fragmentToLoad.equals("BudgetFragment")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.containers, budgetFragment).commit();
            MainNavigationBarView.setSelectedItemId(R.id.budget);
            SubNavigationBarView.setVisibility(View.GONE);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.containers, dayFragment).commit();
        }

        MainNavigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id){
                    case R.id.accountBook:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, dayFragment).commit();
                        SubNavigationBarView.setVisibility(View.VISIBLE);
                        StatisticsNavigationBarView.setVisibility(View.GONE);
                        SubNavigationBarView.setSelectedItemId(R.id.day);
                        return true;
                    case R.id.statistics:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, statisticsExpensesFragment).commit();
                        StatisticsNavigationBarView.setVisibility(View.VISIBLE);
                        SubNavigationBarView.setVisibility(View.GONE);
                        StatisticsNavigationBarView.setSelectedItemId(R.id.expenses);
                        return true;
                    case R.id.debt:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, debtFragment).commit();
                        SubNavigationBarView.setVisibility(View.GONE);
                        StatisticsNavigationBarView.setVisibility(View.GONE);
                        return true;
                    case R.id.budget:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, budgetFragment).commit();
                        SubNavigationBarView.setVisibility(View.GONE);
                        StatisticsNavigationBarView.setVisibility(View.GONE);
                        return true;
                    case R.id.list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, listFragment).commit();
                        SubNavigationBarView.setVisibility(View.GONE);
                        StatisticsNavigationBarView.setVisibility(View.GONE);
                        return true;

                }
                return false;
            }
        });

        SubNavigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.day:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, dayFragment).commit();
                        return true;
                    case R.id.month:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, monthFragment).commit();
                        return true;
                    case R.id.calendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, calendarFragment).commit();
                        return true;
                }
                return false;
            }
        });
        StatisticsNavigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.income:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, statisticsIncomeFragment).commit();
                        return true;
                    case R.id.expenses:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, statisticsExpensesFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}