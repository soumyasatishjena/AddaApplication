package com.example.addaapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Utility {

    public static ArrayList<FacilityConfig> facilityConfigs;

    public static ArrayList<DialogDataDetails> getFacilityNameList(){
        ArrayList<DialogDataDetails> list = new ArrayList<>();
        list.add(new DialogDataDetails("ClubHouse") );
        list.add(new DialogDataDetails("Tennis Court") );
        return list;
    }

    public static ArrayList<FacilityConfig> getFacilityTimePriceList(){
        ArrayList<FacilityConfig> list = new ArrayList<>();
        list.add(new FacilityConfig("ClubHouse", "10 am" ,"4 pm", 100));
        list.add(new FacilityConfig("ClubHouse", "4 pm",  "10 pm", 500));
        list.add(new FacilityConfig("Tennis Court", "10 am",  "10 pm", 50));
        return list;
    }

    @SuppressLint("SimpleDateFormat")
    public static long getDifferenceBetweenTime(String bookingStartTime, String bookingEndTime){
        String []startTime = bookingStartTime.split(" ");
        String []endTime = bookingEndTime.split(" ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = simpleDateFormat.parse(startTime[0]);
            d2 = simpleDateFormat.parse(endTime[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(d2).getTime() -
                Objects.requireNonNull(d1).getTime();
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeDisplay(String timeFormat){
        String showTime = null;
        String dateString3 = timeFormat.substring(0, 5);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        try{
            Date date3 = sdf1.parse(dateString3);
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa");
            showTime = sdf2.format(Objects.requireNonNull(date3));
        }catch(ParseException e){
            e.printStackTrace();
        }
        return showTime;
    }

    public static void pickDate(Context context,
                                DatePickerDialog.OnDateSetListener onDateSetListener) {
        final Calendar c = Calendar.getInstance();
        int myYear = c.get(Calendar.YEAR);
        int myMonth = (c.get(Calendar.MONTH));
        int myDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, onDateSetListener,
                myYear, myMonth, myDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public static void pickTime(Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        final Calendar calendarTime = Calendar.getInstance();
        int mHour = calendarTime.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendarTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, onTimeSetListener,
                mHour, mMinute, false);
        timePickerDialog.show();
    }

}
