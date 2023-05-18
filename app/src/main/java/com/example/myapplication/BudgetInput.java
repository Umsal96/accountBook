package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Uitl.DateUtil;
import com.example.myapplication.item.BudgetItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BudgetInput extends AppCompatActivity implements ExpensesSheetDialog.BottomSheetListener{

    private EditText  textMoney;
    private Button button8, textCategory;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
    DateUtil dateUtil = new DateUtil();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_budget);

        textCategory = findViewById(R.id.textCategory);
        textMoney = findViewById(R.id.textMoney);
        button8 = findViewById(R.id.button8);

        textMoney.addTextChangedListener(new CustomTextWatcher(textMoney));

        textCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpensesSheetDialog expensesSheepDialog = new ExpensesSheetDialog();
                expensesSheepDialog.show(getSupportFragmentManager(), "ExpensesSheepDialog");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetItem budgetItem = new BudgetItem();
                Calendar calendar = Calendar.getInstance();
                String formattedDate = dateUtil.getYearMonth(calendar, 0, dateFormat);
                String inputMoney = textMoney.getText().toString();
                String inputMoneyNul = inputMoney.replace(",","");
                String realDate = formattedDate.replace(".","");
                int money = Integer.parseInt(inputMoneyNul);
                String category = textCategory.getText().toString();

                budgetItem.setDate(realDate);
                budgetItem.setMoney(money);
                budgetItem.setCategory(category);

                SharedPreferences sharedPreferences = getSharedPreferences("budget", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String input = realDate + "_" + category;

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("date", budgetItem.getDate());
                    jsonObject.put("category", budgetItem.getCategory());
                    jsonObject.put("money", budgetItem.getMoney());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String jsonString = jsonObject.toString();
                editor.putString(input , jsonString);
                editor.apply();

                Intent intent = new Intent(BudgetInput.this, Main.class);
                intent.putExtra("fragmentToLoad", "BudgetFragment");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onButtonLicked(String text) {
         textCategory.setText(text);
    }
}
