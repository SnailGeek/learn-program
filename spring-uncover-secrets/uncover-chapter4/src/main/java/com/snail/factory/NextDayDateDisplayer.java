package com.snail.factory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NextDayDateDisplayer {
    private Date dateOfNextDay;

    public void setDateOfNextDay(Date dateOfNextDay) {
        this.dateOfNextDay = dateOfNextDay;
    }

    public void printDay() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(dateOfNextDay));
    }

}
