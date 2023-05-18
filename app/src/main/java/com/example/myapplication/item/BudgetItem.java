package com.example.myapplication.item;

public class BudgetItem {
    private String date;
    private String category;
    private int money;

    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getCategory(){
        return this.category;
    }
    public void setMoney(int money){
        this.money = money;
    }
    public int getMoney(){
        return this.money;
    }
}
