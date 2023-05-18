package com.example.myapplication;

import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;

public class CustomTextWatcher implements TextWatcher {

    private EditText editText;
    String strAmount = "";

    CustomTextWatcher(EditText et){
        editText = et;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(strAmount)) {
            strAmount = makeStringComma(s.toString().replace(",", ""));
            editText.setText(strAmount);
            Editable editable = editText.getText();
            Selection.setSelection(editable, strAmount.length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    protected String makeStringComma(String str) {    // 천단위 콤마설정.
        if (str.length() == 0) {
            return "";
        }
        long value = Long.parseLong(str);
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(value);
    }
}
