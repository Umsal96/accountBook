package com.example.myapplication.Uitl;

import com.example.myapplication.item.listItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AccountUtil {
    int totalIncome , totalExpenses, totalSaving, totalDebt, totalDebtRepayment;
    int balance;
    String outIncome, outExpenses, outBalance , outSaving, outDebt, outDebtRepayment;
    DecimalFormat df = new DecimalFormat("#,###");

    public int getTotal(ArrayList<listItem> li, String type){
        int total = 0;
        for (int i = 0; i < li.size(); i++) {
            if(li.get(i).getReType().equals(type)){
                total += li.get(i).getReMoney();
            }
        }
        return total;
    }

    public String calculateIncome(ArrayList<listItem> li){ // 일일 수익 측정
        totalIncome = getTotal(li, "수입");
        return outIncome = df.format(totalIncome);
    }

    public String calculateExpenses(ArrayList<listItem> li){ // 일일 지출 측정
        totalExpenses = getTotal(li, "지출");
        return outExpenses = df.format(totalExpenses);
    }

    public String calculateSaving(ArrayList<listItem> li){ // 일일 저축 측정
        totalSaving = getTotal(li, "저금");
        return outSaving = df.format(totalSaving);
    }

    public String calculateDebt(ArrayList<listItem> li) { // 일일 부채 측정
        totalDebt = getTotal(li,"부채");
        return outDebt = df.format(totalDebt);
    }

    public String calculateDebtRepayment(ArrayList<listItem> li){ // 상환 측정
        totalDebtRepayment = getTotal(li, "상환");
        return outDebtRepayment = df.format(totalDebtRepayment);
    }

    public String calculateDebtRepaymentBalance(){ // 잔액측정
        balance = 0;
        int total = totalExpenses + totalSaving + totalDebtRepayment;
        balance = totalIncome - total;

        return outBalance = df.format(balance);
    }
}
