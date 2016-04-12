package model;


import org.joda.time.LocalDate;

import java.util.ArrayList;

public class Reservation {
    private long bookID;
    private long roomID;
    private long userID;
    private long companyID;
    private boolean isNotificationShowed;
    private Hotel hotel;
    private Room room;
    private ArrayList<LocalDate> dates;

    public Reservation(long bookID, long roomID, long userID) {
        this.bookID = bookID;
        this.roomID = roomID;
        this.userID = userID;

    }

    public void setBookID(long bookID) {
        this.bookID = bookID;
    }

    public long getBookID() {
        return bookID;
    }

    public long getRoomID() {
        return roomID;
    }

    public long getUserID() {
        return userID;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public ArrayList<LocalDate> getDates() {
        return dates;
    }

    public void setDates(ArrayList<LocalDate> dates) {
        this.dates = dates;
    }

    public long getCompanyID() {
        return companyID;
    }

    public void setCompanyID(long companyID) {
        this.companyID = companyID;
    }

    public boolean isNotificationShowed() {
        return isNotificationShowed;
    }

    public void setNotificationShowed(boolean notificationShowed) {
        isNotificationShowed = notificationShowed;
    }
}