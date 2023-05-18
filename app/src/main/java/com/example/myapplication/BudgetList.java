package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adpter.BudgetListAdapter;
import com.example.myapplication.Uitl.BudgetDataUtil;
import com.example.myapplication.Uitl.DateUtil;
import com.example.myapplication.item.BudgetItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class BudgetList extends AppCompatActivity {

    private Button button7;
    private ListView listView;
    private BudgetListAdapter mAdapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
    DateUtil dateUtil = new DateUtil();
    BudgetDataUtil budgetDataUtil = new BudgetDataUtil();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_budget);

        ArrayList<BudgetItem> bData;

        listView = findViewById(R.id.listView);
        button7 = findViewById(R.id.button7);

        Calendar calendar = Calendar.getInstance();
        String formattedDate = dateUtil.getYearMonth(calendar, 0, dateFormat);
        bData = budgetDataUtil.budgeMonth(formattedDate, getApplicationContext());

        mAdapter = new BudgetListAdapter(getApplicationContext(), bData);
        listView.setAdapter(mAdapter);

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BudgetList.this, BudgetInput.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
