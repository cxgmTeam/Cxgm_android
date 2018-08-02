package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

public class DeliveryTime extends BaseEntity {

    String time;
    String date;
    boolean isCurrentDay;
    public boolean isChecked;

    public DeliveryTime(String time, String date,boolean isCurrentDay) {
        this.time = time;
        this.date = date;
        this.isCurrentDay = isCurrentDay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCurrentDay() {
        return isCurrentDay;
    }

    public void setCurrentDay(boolean currentDay) {
        isCurrentDay = currentDay;
    }
}
