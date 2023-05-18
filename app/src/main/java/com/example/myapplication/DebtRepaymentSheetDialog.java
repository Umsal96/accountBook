package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DebtRepaymentSheetDialog extends BottomSheetDialogFragment {

    private View view;
    private BottomSheetListener mListener;
    private Button debt_repayment_button;

    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){
        view = inflater.inflate(R.layout.debt_repayment_sheet_layout, container, false);

        mListener = (BottomSheetListener) getContext();

        debt_repayment_button = view.findViewById(R.id.debt_repayment_button);

        debt_repayment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonLicked("상환");
                dismiss();
            }
        });

        return view;
    }

    public interface BottomSheetListener{
        void onButtonLicked(String text);
    }
}
