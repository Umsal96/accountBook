package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adpter.CustomAdapter;
import com.example.myapplication.Uitl.DateUtil;
import com.example.myapplication.Uitl.GetDateUtil;
import com.example.myapplication.item.listItem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class detailList extends AppCompatActivity {

    private TextView inputTypeDetail, inputDateDetail, category, amount, editTextTextPersonName6;

    private Button detailRemove, detailCancel, detailModify;

    private ArrayList<listItem> li = new ArrayList<>();

    GetDateUtil getDateUtil = new GetDateUtil();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    DateUtil dateUtil = new DateUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        Calendar calendar = Calendar.getInstance();
        String formattedDate = dateUtil.getDate(calendar, 0, dateFormat);
        li = getDateUtil.getMonth(formattedDate, getApplication());

        inputTypeDetail = findViewById(R.id.inputTypeDetail);
        inputDateDetail = findViewById(R.id.inputDateDetail);
        category = findViewById(R.id.category);
        amount = findViewById(R.id.amount);
        editTextTextPersonName6 = findViewById(R.id.editTextTextPersonName6);
        detailRemove = findViewById(R.id.detailRemove);
        detailCancel = findViewById(R.id.detailCancel);
        detailModify = findViewById(R.id.detailModify);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        DecimalFormat format = new DecimalFormat("#,###");
        String formattedNumber = format.format(li.get(position).getReMoney());

        inputTypeDetail.setText(li.get(position).getReType());
        inputDateDetail.setText(li.get(position).getReDate());
        category.setText(li.get(position).getReCategory());
        amount.setText(formattedNumber);
        editTextTextPersonName6.setText(li.get(position).getReContent());

        detailModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detailList.this, DetailListModify.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        detailRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAdapter customAdapter = new CustomAdapter(li);

                String realDate = li.get(position).getReDate().replace(".","");
                String realType = null;

                if(li.get(position).getReType().equals("수입")){
                    realType = "I";
                } else if (li.get(position).getReType().equals("지출")) {
                    realType = "E";
                } else if (li.get(position).getReType().equals("저금")) {
                    realType = "S";
                } else if (li.get(position).getReType().equals("부채")) {
                    realType = "D";
                } else if (li.get(position).getReType().equals("상환")) {
                    realType = "R";
                }

                String removeSuffix = realDate + "_" + realType + "_" + String.format("%03d", li.get(position).getIndex());

                System.out.println("삭제할 키 : " + removeSuffix);

                SharedPreferences preferences = getSharedPreferences("account_book", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove(removeSuffix);

                editor.apply();

                li.remove(position);
                customAdapter.notifyItemRemoved(position);
                Intent intent = new Intent(detailList.this, Main.class);
                intent.putExtra("fragmentToLoad", "ListFragment");
                startActivity(intent);
                finish();
            }
        });

    }
}