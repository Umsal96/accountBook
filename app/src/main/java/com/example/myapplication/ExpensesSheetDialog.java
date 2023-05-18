package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ExpensesSheetDialog extends BottomSheetDialogFragment {

    private View view;
    private BottomSheetListener mListener;
    private Button[] expensesButtons = new Button[13];
    private int[] expensesButtonIds = {R.id.expenses_button1, R.id.expenses_button2, R.id.expenses_button3,
            R.id.expenses_button4, R.id.expenses_button5, R.id.expenses_button6, R.id.expenses_button7,
            R.id.expenses_button8, R.id.expenses_button9, R.id.expenses_button10, R.id.expenses_button11,
            R.id.expenses_button12, R.id.expenses_button13};


    String[] expensesButtonNames = {"식비", "카페/간식", "술/유흥", "생활", "쇼핑", "미용/패션", "주거/통신", "건강", "부모님",
            "교육", "경조사", "금용", "기타"};

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.expenses_sheet_layout, container, false);

        mListener = (BottomSheetListener) getContext();

        for (int i = 0; i < expensesButtons.length; i++) {
            expensesButtons[i] = view.findViewById(expensesButtonIds[i]);
            final int index = i;
            expensesButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onButtonLicked(expensesButtonNames[index]);
                    dismiss();
                }
            });
        }


        return view;
    }

    public interface BottomSheetListener{
        void onButtonLicked(String text);
    }

}
