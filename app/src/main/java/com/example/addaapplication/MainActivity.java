package com.example.addaapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addaapplication.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static com.example.addaapplication.Utility.facilityConfigs;
import static com.example.addaapplication.Utility.getDifferenceBetweenTime;
import static com.example.addaapplication.Utility.getFacilityNameList;
import static com.example.addaapplication.Utility.getTimeDisplay;
import static com.example.addaapplication.Utility.pickDate;
import static com.example.addaapplication.Utility.pickTime;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private ActivityMainBinding binding;
    private Dialog dialog;
    private ArrayList<DialogDataDetails> listFacilityList;
    private String  facilityName = "", bookingDate = "", bookingStartTime= "", bookingEndTime = "";
    private boolean setStartTime = false, setEndTime = false;
    private int totalTimeOccupied = 0;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        listFacilityList = new ArrayList<>();
        facilityConfigs = new ArrayList<>();
        listFacilityList = getFacilityNameList();
        // on clicks
        binding.textSelect.setOnClickListener(this);
        binding.textAddDate.setOnClickListener(this);
        binding.imageDate.setOnClickListener(this);
        binding.textStartTime.setOnClickListener(this);
        binding.imageStartTiming.setOnClickListener(this);
        binding.textEndTime.setOnClickListener(this);
        binding.imageEndTiming.setOnClickListener(this);
        binding.buttonCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.view = view;
        switch (view.getId()){
            case R.id.textSelect:
                getDialogSpinnerList();
                break;
            case R.id.textAddDate:
            case R.id.imageDate:
                if(!facilityName.isEmpty())
                    pickDate(this, this);
                else
                    displayError("Select Facility First");
                break;
            case R.id.textStartTime:
            case R.id.imageStartTiming:
                setStartTime = true;
                if(!facilityName.isEmpty())
                    pickTime(this, this);
                else
                    displayError("Select Date First");
                break;
            case R.id.textEndTime:
            case R.id.imageEndTiming:
                setEndTime = true;
                if(!bookingStartTime.isEmpty())
                    pickTime(this, this);
                else
                    displayError("Select Start Time First");
                break;
            case R.id.buttonCheck:
                if(!bookingEndTime.isEmpty()){
                    checkData();
                }else {
                    displayError("Select End Time First");
                }
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"PrivateResource", "ClickableViewAccessibility", "SetTextI18n"})
    private void getDialogSpinnerList(){
        dialog = new Dialog(this, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).getAttributes()
                .windowAnimations = R.style.Animation_AppCompat_Dialog;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.dialog_recyclerview_layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        RelativeLayout cardViewDialogSpinner = dialog.findViewById(R.id.cardViewDialogSpinner);
        RelativeLayout dialogLayoutSpinnerBackground =
                dialog.findViewById(R.id.dialogLayoutSpinnerBackground);
        TextView textDialogHeading = dialog.findViewById(R.id.textDialogHeading);

        RecyclerView recyclerShowList =  dialog.findViewById(R.id.recyclerShowList);
        textDialogHeading.setText("Select Facility");
        recyclerShowList.setHasFixedSize(true);
        recyclerShowList.setLayoutManager(new LinearLayoutManager(this));
        recyclerShowList.setAdapter(new DialogSpinnerAdapter(this, listFacilityList,
                this));

        dialogLayoutSpinnerBackground.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dialog.dismiss();
                return true;
            }
        });

        cardViewDialogSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dialog.setCancelable(false);
                return true;
            }
        });
        dialog.show();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        facilityName = listFacilityList.get(i).getName();
        binding.textSelect.setText(facilityName);
        clearViews();
        dialog.dismiss();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        if (datePicker.isShown()) {
            month = month+1;
            String day = String.valueOf(dayOfMonth);
            String mon = String.valueOf(month);
            if(day.length() == 1){
                day = "0"+day;
            }
            if(mon.length()==1){
                mon = "0"+mon;
            }
            bookingDate = day+"-"+mon+"-"+year;
            binding.textAddDate.setText(bookingDate);
            binding.textStartTime.setText("");
            binding.textEndTime.setText("");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        if(timePicker != null){
            Calendar datetime = Calendar.getInstance();
            Calendar calendarInstance = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            datetime.set(Calendar.MINUTE, minute);
            if (datetime.getTimeInMillis() > calendarInstance.getTimeInMillis()) {
                if(hourOfDay>= 10 && hourOfDay<=22) {
                @SuppressLint("DefaultLocale")
                String timeFormat = String.format("%02d:%02d %s", hourOfDay,
                        minute, hourOfDay < 12 ? "am" : "pm");
                    String showTime = getTimeDisplay(timeFormat);
                if (setStartTime) {
                    setStartTime = false;
                    bookingStartTime = timeFormat;
                    binding.textStartTime.setText(showTime);
                    binding.textEndTime.setText("");
                } else if (setEndTime) {
                    setEndTime = false;
                    bookingEndTime = timeFormat;
                    long elapsed = getDifferenceBetweenTime(bookingStartTime, bookingEndTime);
                    long diffMinutes = elapsed / (60 * 1000) % 60;
                    long diffHours = elapsed / (60 * 60 * 1000) % 24;
                    if (Integer.parseInt(String.valueOf(diffHours)) >= 1 &&
                            Integer.parseInt(String.valueOf(diffMinutes)) == 0) {
                        totalTimeOccupied = Integer.parseInt(String.valueOf(diffHours));
                        binding.textEndTime.setText(showTime);
                    } else {
                        displayError("Select a Time For At list 1 hours Plus.. " +
                                "with same minutes");
                    }
                }
                }else
                    displayError("Cannot book before 10 Am and After 10 Pm");
            }else
                displayError("Invalid Time Select Future " +
                        "Time No Past Time");

        }
    }

    private void checkData(){
        int totalAmount = 0;
       switch (facilityName){
           case "Tennis Court":
               totalAmount = totalTimeOccupied * 50;
               operationListType(totalAmount);
               break;
           case "ClubHouse":
               String [] breakTime = bookingStartTime.split(" ");
                   if (breakTime[1].endsWith("am")) {
                       String [] splitStartData = bookingStartTime.split(":");
                       if(Integer.parseInt(splitStartData[0])>10 &&
                               Integer.parseInt(splitStartData[0])<12){
                           totalAmount = totalTimeOccupied * 100;
                       }
                   } else if (breakTime[1].endsWith("pm")) {
                       if(breakTime[1].endsWith("pm") && bookingEndTime.endsWith("pm")){
                           String [] splitStartData = bookingStartTime.split(":");
                           String [] splitEndDate = bookingEndTime.split(":");
                           if(Integer.parseInt(splitStartData[0])>=16 &&
                                   Integer.parseInt(splitEndDate[0])<=22){
                               totalAmount = totalTimeOccupied * 500;
                           }
                       }else
                           displayError("Invalid Time Format !!");
                   }
                   if(totalAmount!=0) {
                       operationListType(totalAmount);
                   }
               break;
       }
    }

    private void operationListType(int totalAmount){
        if (facilityConfigs.size() > 0) {
            for (FacilityConfig itemCheck : facilityConfigs) {
                if (itemCheck.getName().equals(facilityName)
                        && itemCheck.getTotalAmount() == totalAmount &&
                        itemCheck.getStartTime().equals(bookingStartTime) &&
                        itemCheck.getEndTime().equals(bookingEndTime)) {
                    operationList("isExist", 0);
                } else {
                    operationList("newEntry", totalAmount);
                }
            }
        } else {
            operationList("newEntry", totalAmount);
        }
    }

    private void operationList(String type, int totalAmount){
        switch (type){
            case "isExist":
                displayError("Booking Failed !!, Already Booked...");
                break;
            case "newEntry":
                facilityConfigs.add(new FacilityConfig(facilityName, bookingDate,
                        bookingStartTime, bookingEndTime, "Booked", totalAmount));
                break;
        }
        clearViews();
    }

    private void clearViews(){
        binding.textSelect.setText("");
        binding.textAddDate.setText("");
        binding.textStartTime.setText("");
        binding.textEndTime.setText("");
    }

    private void displayError(String response){
        Snackbar.make(this.view, response, Snackbar.LENGTH_SHORT).show();
    }
}