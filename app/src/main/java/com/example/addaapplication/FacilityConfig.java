package com.example.addaapplication;

public class FacilityConfig {

    private String name;
    private String startTime;
    private String endTime;
    private int price;
    private String bookingData;
    private String status;
    private int totalAmount;

    public FacilityConfig(String name, String bookingData, String startTime, String endTime,
                          String status, int totalAmount) {
        this.name = name;
        this.bookingData= bookingData;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBookingData() {
        return bookingData;
    }

    public void setBookingData(String bookingData) {
        this.bookingData = bookingData;
    }
}
