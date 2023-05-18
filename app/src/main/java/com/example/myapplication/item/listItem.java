package com.example.myapplication.item;

public class listItem {
    private String reType;
    private String reDate;
    private String reCategory;
    private String reContent;
    private int reMoney;
    private int index;

    public void setReType(String reType){
        this.reType = reType;
    }

    public String getReType(){
        return this.reType;
    }

    public void setReDate(String reDate){
        this.reDate = reDate;
    }

    public String getReDate(){
        return this.reDate;
    }

    public void setReCategory(String reCategory){
        this.reCategory = reCategory;
    }

    public String getReCategory(){
        return this.reCategory;
    }

    public void setReMoney(int reMoney){
        this.reMoney = reMoney;
    }

    public int getReMoney(){
        return this.reMoney;
    }

    public void setReContent(String reContent){
        this.reContent = reContent;
    }

    public String getReContent() {
        return this.reContent;
    }

    public void setIndex(int index){ this.index = index; }
    public int getIndex(){return this.index;}
}
