package com.d3.duy.citipocket.model;

import com.d3.duy.citipocket.model.enums.Month;

import java.util.Calendar;

/**
 * Created by daoducduy0511 on 3/11/16.
 */
public class MonthYear {

    private Month month;
    private int year;

    public MonthYear(Month month, int year) {

        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return month.name() + " " + year;
    }

    public String toShortString() {
        //int monthInt = month.getValue() + 1;
        String monthStr = month.name(); //(monthInt < 10 ? "0" + monthInt : "" + monthInt);
        String yearStr = "" + (year % 100);
        return monthStr + "/" + yearStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MonthYear monthYear = (MonthYear) o;

        if (year != monthYear.year) return false;
        return month == monthYear.month;

    }

    @Override
    public int hashCode() {
        int result = month.hashCode();
        result = 31 * result + year;
        return result;
    }

    public Month getMonth() {

        return month;
    }

    public int getYear() {
        return year;
    }

    public MonthYear getPrevious() {
        // Set calendar to current month year
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month.getValue() - 1); // In our Month, Jan is 1
        calendar.set(Calendar.YEAR, year);

        // Calculate previous month
        calendar.add(Calendar.MONTH, -1);
        Month m = Month.values()[calendar.get(Calendar.MONTH)]; // in Java Calendar, Jan is 0
        int y = calendar.get(Calendar.YEAR);

        return new MonthYear(m, y);
    }

    public MonthYear getNext() {
        // Set calendar to current month year
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month.getValue() - 1); // In our Month, Jan is 1
        calendar.set(Calendar.YEAR, year);

        // Calculate previous month
        calendar.add(Calendar.MONTH, 1);
        Month m = Month.values()[calendar.get(Calendar.MONTH)]; // in Java Calendar, Jan is 0
        int y = calendar.get(Calendar.YEAR);

        return new MonthYear(m, y);
    }

    public static MonthYear getCurrent() {
        // Find this month text and set text
        Calendar calendar = Calendar.getInstance();
        Month m = Month.values()[calendar.get(Calendar.MONTH)]; // in Java Calendar, Jan is 0
        int y = calendar.get(Calendar.YEAR);

        return new MonthYear(m, y);
    }
}
