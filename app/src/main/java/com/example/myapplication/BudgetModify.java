package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Uitl.BudgetDataUtil;
import com.example.myapplication.Uitl.DateUtil;
import com.example.myapplication.item.BudgetItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BudgetModify extends AppCompatActivity{

    private Button button8;
    private EditText textMoney;
    private TextView textCategory;
    private BudgetDataUtil budgetDataUtil = new BudgetDataUtil();
    private DateUtil dateUtil = new DateUtil();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
    DecimalFormat df = new DecimalFormat("#,###");

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_budget);

        textCategory = findViewById(R.id.textCategory);
        button8 = findViewById(R.id.button8);
        textMoney = findViewById(R.id.textMoney);
        textMoney.addTextChangedListener(new CustomTextWatcher(textMoney)); // 쉼표 추가 코드

        Calendar calendar = Calendar.getInstance();
        // 이번달 날짜를 String 형태로 가져온다
        String formattedDate = dateUtil.getYearMonth(calendar, 0, dateFormat);
        // 어레이 리스트에 예산 데이터를 가져온다.
        ArrayList<BudgetItem> bData =  budgetDataUtil.budgeMonth(formattedDate, getApplicationContext());

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        String sd = bData.get(position).getDate();
        textCategory.setText(bData.get(position).getCategory());
        String formattedNumber = df.format(bData.get(position).getMoney());
        textMoney.setText(formattedNumber);

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("budget", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String realDate = sd.replace(".", ""); // 온점 제거
                String realCategory = textCategory.getText().toString();
                String rMoney = textMoney.getText().toString();
                String reMoney = rMoney.replace(",","");
                int money = Integer.parseInt(reMoney);
                System.out.println(realDate + "_" + realCategory);
                String input = realDate + "_" + realCategory;

                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("date", realDate);
                    jsonObject.put("category", realCategory);
                    jsonObject.put("money", money);
                }catch (JSONException e){
                    e.printStackTrace();
                }

                String jsonString = jsonObject.toString();
                editor.putString(input, jsonString);
                editor.apply();

                Intent intent = new Intent(BudgetModify.this, BudgetList.class);

                startActivity(intent);
                finish();
            }
        });

    }
}
