package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SavingSheetDialog extends BottomSheetDialogFragment {

    private View view;
    private BottomSheetListener mListener;
    private Button saving_button1, saving_button2, saving_button3, saving_button4;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.saving_sheet_layout, container, false);

        mListener = (BottomSheetListener) getContext();

        saving_button1 = view.findViewById(R.id.saving_button1);
        saving_button2 = view.findViewById(R.id.saving_button2);
        saving_button3 = view.findViewById(R.id.saving_button3);
        saving_button4 = view.findViewById(R.id.saving_button4);

        saving_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonLicked("예금");
                dismiss();
            }
        });
        saving_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonLicked("적금");
                dismiss();
            }
        });
        saving_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonLicked("주택청약");
                dismiss();
            }
        });
        saving_button4.setOnClickListener(new View.OnClickListener() {
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
