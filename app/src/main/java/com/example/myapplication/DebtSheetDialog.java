package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DebtSheetDialog extends BottomSheetDialogFragment {

    private View view;
    private BottomSheetListener mListener;
    private Button debt_button1, debt_button2, debt_button3, debt_button4;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ){
         view = inflater.inflate(R.layout.debt_sheet_layout, container, false);

         mListener = (BottomSheetListener) getContext();

         debt_button1 = view.findViewById(R.id.debt_button1);
         debt_button2 = view.findViewById(R.id.debt_button2);
         debt_button3 = view.findViewById(R.id.debt_button3);
         debt_button4 = view.findViewById(R.id.debt_button4);

         debt_button1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mListener.onButtonLicked("카드빚");
                 dismiss();
             }
         });
        debt_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonLicked("대출");
                dismiss();
            }
        });
        debt_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonLicked("빌린돈");
                dismiss();
            }
        });
        debt_button4.setOnClickListener(new View.OnClickListener() {
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
