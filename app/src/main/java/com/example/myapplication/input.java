package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Uitl.DateUtil;
import com.example.myapplication.Uitl.GetDateUtil;
import com.example.myapplication.item.listItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class input extends AppCompatActivity implements IncomeSheetDialog.BottomSheetListener,
        ExpensesSheetDialog.BottomSheetListener, SavingSheetDialog.BottomSheetListener, DebtSheetDialog.BottomSheetListener,
        DebtRepaymentSheetDialog.BottomSheetListener{
    private DatePickerDialog datePickerDialog;
    private ArrayList li = new ArrayList();
    private EditText amount; // 금액을 입력하는 칸
    private EditText editTextTextPersonName6; // 내용을 저장하는 칸
    private Button button, button3 ,button4, button5, category;
    private String content;
    private int money;
    private String choiceType;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    String dateString;
    listItem inItem = new listItem();
    String testType;
    DateUtil dateUtil = new DateUtil();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);

        editTextTextPersonName6 = findViewById(R.id.editTextTextPersonName6);

        button3 = findViewById(R.id.button3); // 날짜 데이터 추가

        button4 = findViewById(R.id.button4); // 버튼 저장

        category = findViewById(R.id.category);
        // 카테고리 버튼 선택시 다이얼로그 나오는것
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choiceType.equals("수입")){
                    IncomeSheetDialog incomeSheetDialog = new IncomeSheetDialog();
                    incomeSheetDialog.show(getSupportFragmentManager(), "IncomeSheepDialog");
                } else if (choiceType.equals("지출")) {
                    ExpensesSheetDialog expensesSheepDialog = new ExpensesSheetDialog();
                    expensesSheepDialog.show(getSupportFragmentManager(), "ExpensesSheepDialog");
                } else if (choiceType.equals("부채")) {
                    DebtSheetDialog debtSheetDialog = new DebtSheetDialog();
                    debtSheetDialog.show(getSupportFragmentManager(), "DebtSheetDialog");
                }else if (choiceType.equals("저금")) {
                    SavingSheetDialog savingSheetDialog = new SavingSheetDialog();
                    savingSheetDialog.show(getSupportFragmentManager(), "SavingSheetDialog");
                }else if (choiceType.equals("상환")) {
                    DebtRepaymentSheetDialog debtRepaymentSheetDialog = new DebtRepaymentSheetDialog();
                    debtRepaymentSheetDialog.show(getSupportFragmentManager(), "DebtRepaymentSheetDialog");
                }
            }
        });

        button = findViewById(R.id.button);

        amount = findViewById(R.id.amount);

        // 날짜 선택버튼
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar();
            }
        });

        amount.addTextChangedListener(new CustomTextWatcher(amount)); // 쉼표 추가 코드

        //저장된 리스트 화면 으로 욺직임
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int index;

                GetDateUtil getDateUtil = new GetDateUtil();

                Calendar calendar = Calendar.getInstance();
                String formattedDate = dateUtil.getDate(calendar, 0, dateFormat);
                li = getDateUtil.getToday(formattedDate, getApplicationContext());

                String inputMoney = amount.getText().toString();
                String inputMoneyNul = inputMoney.replace(",",""); // 쉼표 제거
                money = Integer.parseInt(inputMoneyNul);
                content = editTextTextPersonName6.getText().toString();
                inItem.setReContent(content);
                inItem.setReMoney(money);
                inItem.setReDate(dateString);
                inItem.setReType(choiceType);
                inItem.setReCategory(category.getText().toString());

                String realDate = dateString.replace(".", ""); // 온점 제거

                // 타입마다 키값을 다르게 주기 위해서 추가한것
                if(choiceType.equals("수입")){
                    testType = "I";
                } else if (choiceType.equals("지출")) {
                    testType = "E";
                } else if (choiceType.equals("저금")) {
                    testType = "S";
                } else if (choiceType.equals("부채")) {
                    testType = "D";
                } else if (choiceType.equals("상환")) {
                    testType = "R";
                }

                SharedPreferences sharedPreferences = getSharedPreferences("account_book", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // 저장한 인덱스 가져오기
                index = sharedPreferences.getInt(realDate + "_index", 0);
                // jsonObject 로 파싱하기
                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("type", choiceType);
                    jsonObject.put("date", dateString);
                    jsonObject.put("content", content);
                    jsonObject.put("category", category.getText().toString());
                    jsonObject.put("money", money);
                    jsonObject.put("index" , index);

                }catch (JSONException e){
                    e.printStackTrace();
                }
                inItem.setIndex(index);
                editor.putInt(realDate + "_index", index);

                // 가져올 키값 만들기
                String input = realDate + "_" + testType + "_" + String.format("%03d", index);

                // json 형태로 파싱한뒤 쉐어드 프리퍼런스로 저장하기
                String jsonString = jsonObject.toString();
                editor.putString(input, jsonString);
                editor.apply();

                index = sharedPreferences.getInt(realDate + "_index", 0);

                index++;

                editor.putInt(realDate + "_index", index);

                editor.apply();

                Intent intent = new Intent(input.this, Main.class);
                intent.putExtra("fragmentToLoad", "ListFragment");
                startActivity(intent);
                finish();
            }

        });

        // 분류 선택 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceDialog();
            }
        });
    }
    private void showChoiceDialog(){ // 타입을 설정하는 다이얼로그를 보여준다.
        final String[] items = {"수입", "지출", "저금", "부채", "상환"};
        AlertDialog.Builder builder = new AlertDialog.Builder(input.this);
        builder.setTitle("유형을 선택 하세요")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        button.setText(items[i]);
                        choiceType = items[i];
                        category.setText("클릭");
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showCalendar(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(input.this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);
                Date date = selectedDate.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                dateString = dateFormat.format(date);
                button3.setText(dateString);
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    @Override
    public void onButtonLicked(String text) {
        category.setText(text);
    }
}
