package com.framgia.moneymanagement.data.model;

public class Income {
    private String mId;
    private String mGroup;
    private String mDescription;
    private String mAmount;
    private String mTime;
    private String mYear;
    private String mMonth;

    public Income() {
    }

    public Income(String id) {
        mId = id;
    }

    public Income(String id, String group, String description, String amount,
                  String time, String year, String month) {
        mId = id;
        mGroup = group;
        mDescription = description;
        mAmount = amount;
        mMonth = month;
        mTime = time;
        mYear = year;
        mMonth = month;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String discription) {
        mDescription = discription;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public static class Key {
        public static final String INCOME = "income";
    }
}
