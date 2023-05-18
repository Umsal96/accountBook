package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class IncomeSheetDialog extends BottomSheetDialogFragment {

    private View view;
    private BottomSheetListener mListener;
    private Button income_button1, income_button2, income_button3, income_button4, income_button5;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.income_sheet_layout, container, false);

        mListener = (BottomSheetListener) getContext();

        income_button1 = view.findViewById(R.id.income_button1);
        income_button2 = view.findViewById(R.id.income_button2);
        income_button3 = view.findViewById(R.id.income_button3);
        income_button4 = view.findViewById(R.id.income_button4);
        income_button5 = view.findViewById(R.id.income_button5);

        income_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonLicked("월급");
                dismiss();
            }
        });
        income_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonLicked("용돈");
                dismiss();
            }
        });
        income_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonLicked("상여");
                dismiss();
            }
        });
        income_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonLicked("금용소득");
                dismiss();
            }
        });
        income_button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonLicked("기타");
                dismiss();
            }
        });

        return view;
    }

    public interface BottomSheetListener{
        void onButtonLicked(String text);
    }

}
