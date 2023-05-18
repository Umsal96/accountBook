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

import com.example.myapplication.Adpter.CustomAdapter;
import com.example.myapplication.Uitl.DateUtil;
import com.example.myapplication.Uitl.GetDateUtil;
import com.example.myapplication.item.listItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class DetailListModify extends AppCompatActivity implements IncomeSheetDialog.BottomSheetListener,
        ExpensesSheetDialog.BottomSheetListener, SavingSheetDialog.BottomSheetListener, DebtSheetDialog.BottomSheetListener,
        DebtRepaymentSheetDialog.BottomSheetListener{

    private DatePickerDialog datePickerDialog;
    private EditText textView32, modifyContent;
    private Button button29, button30, button2, button28, button26;
    private String choiceType;
    ArrayList<listItem> li;
    DateUtil dateUtil = new DateUtil();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    listItem inItem = new listItem();
    private GetDateUtil getDateUtil = new GetDateUtil();

    String toType(String type){
        String reTo = "";
        if(type.equals("수입")){
            reTo = "I";
        } else if (type.equals("지출")) {
            reTo = "E";
        } else if (type.equals("저금")) {
            reTo = "S";
        } else if (type.equals("부채")) {
            reTo = "D";
        } else if (type.equals("상환")) {
            reTo = "R";
        }
        return reTo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list_modify);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        button28 = findViewById(R.id.button28); // 저장
        button29 = findViewById(R.id.button29); // 분류버튼
        button30 = findViewById(R.id.button30); // 날짜
        button2 = findViewById(R.id.button2); // 카테고리
        button26 = findViewById(R.id.button26); // 취소
        textView32 = findViewById(R.id.textView32); // 금액

        textView32.addTextChangedListener(new CustomTextWatcher(textView32)); // 쉼표 추가 코드

        modifyContent = findViewById(R.id.modifyContent); // 내용

        Calendar calendar = Calendar.getInstance();
        String formattedDate = dateUtil.getDate(calendar, 0, dateFormat);
        li = getDateUtil.getMonth(formattedDate, getApplicationContext());

        // 수정하기 전 데이터를 넣어둔다 이유는 수정을 하려면 원래 데이터를 지워야 하는데 전 데이터를 수정해버리면 원래 있던 데이터가 뭔지 모르기 떄문
        String beforeType = li.get(position).getReType();
        String beforeDate = li.get(position).getReDate();
        int beforeIndex = li.get(position).getIndex();

        button29.setText(li.get(position).getReType());
        button30.setText(li.get(position).getReDate());
        button2.setText(li.get(position).getReCategory());

        DecimalFormat df = new DecimalFormat("#,###");
        String formattedNumber = df.format(li.get(position).getReMoney());

        textView32.setText(formattedNumber);

        modifyContent.setText(li.get(position).getReContent());

        // 카테고리 선택상자
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(button29.getText().toString().equals("수입")){
                    IncomeSheetDialog incomeSheetDialog = new IncomeSheetDialog();
                    incomeSheetDialog.show(getSupportFragmentManager(), "IncomeSheepDialog");
                } else if (button29.getText().toString().equals("지출")) {
                    ExpensesSheetDialog expensesSheepDialog = new ExpensesSheetDialog();
                    expensesSheepDialog.show(getSupportFragmentManager(), "ExpensesSheepDialog");
                } else if (button29.getText().toString().equals("부채")) {
                    DebtSheetDialog debtSheetDialog = new DebtSheetDialog();
                    debtSheetDialog.show(getSupportFragmentManager(), "DebtSheetDialog");
                }else if (button29.getText().toString().equals("저금")) {
                    SavingSheetDialog savingSheetDialog = new SavingSheetDialog();
                    savingSheetDialog.show(getSupportFragmentManager(), "SavingSheetDialog");
                }else if (button29.getText().toString().equals("상환")) {
                    DebtRepaymentSheetDialog debtRepaymentSheetDialog = new DebtRepaymentSheetDialog();
                    debtRepaymentSheetDialog.show(getSupportFragmentManager(), "DebtRepaymentSheetDialog");
                }
            }
        });

        // 분류 메세지 박스를 보여준다.
        button29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceDialog();
            }
        });

        // 날짜 데이터 수정
        button30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar();
            }
        });

        // 저장버튼을 눌렀을때
        button28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String realDate = beforeDate.replace(".","");
                String realType = null;

                // 타입설정
                realType = toType(beforeType);

                String removeSuffix = realDate + "_" + realType + "_" + String.format("%03d", beforeIndex);
                
                SharedPreferences preferences = getSharedPreferences("account_book", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove(removeSuffix);

                editor.apply();

                String inputMoney = textView32.getText().toString();
                String inMoney = inputMoney.replace(",", ""); // 쉼표 제거
                int money = Integer.parseInt(inMoney);
                inItem.setReMoney(money);
                inItem.setReDate(button30.getText().toString());
                inItem.setReType(button29.getText().toString());
                inItem.setReCategory(button2.getText().toString());
                inItem.setReContent(modifyContent.getText().toString());
                inItem.setIndex(beforeIndex);
                CustomAdapter customAdapter = new CustomAdapter(li);
                li.set(position, inItem);
                customAdapter.notifyItemChanged(position);

                realType = toType(inItem.getReType());

                String makeSuffix = realDate + "_" + realType + "_" + String.format("%03d", beforeIndex);

                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("type", inItem.getReType());
                    jsonObject.put("date", inItem.getReDate());
                    jsonObject.put("content", inItem.getReContent());
                    jsonObject.put("category", inItem.getReCategory());
                    jsonObject.put("money", inItem.getReMoney());
                    jsonObject.put("index", inItem.getIndex());

                }catch (JSONException e){
                    e.printStackTrace();
                }
                String jsonString  = jsonObject.toString();
                editor.putString(makeSuffix, jsonString);
                editor.apply();

                Intent intent = new Intent(DetailListModify.this, Main.class);
                intent.putExtra("fragmentToLoad", "ListFragment");
                startActivity(intent);
                finish();
            }
        });
        // 취소 버튼을 눌렀을때
        button26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailListModify.this, Main.class);
                intent.putExtra("fragmentToLoad", "ListFragment");
                startActivity(intent);
                finish();
            }
        });

    }
    private void showCalendar(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(DetailListModify.this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                String date = year + "." + (month+1) + "." + dayOfMonth;
                button30.setText(date);
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
    private void showChoiceDialog(){
        final String[] items = {"수입", "지출", "저금", "부채", "상환"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailListModify.this);
        builder.setTitle("유형을 선택 하세요")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        button29.setText(items[i]);
                        choiceType = items[i];
                        button2.setText("클릭");
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onButtonLicked(String text) {
        button2.setText(text);
    }
}