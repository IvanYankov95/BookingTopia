package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user-17 on 4/2/16.
 */
public class Room {

    private long    roomId;
    private long    hotelId;

    private double  pricePerDay;
    private String  description;
    private int     maxGuests;
    private String  beds;
    private double[]roomSize;
    private String  extras;
    private boolean smoking;

    private ArrayList<Calendar> reservedDates = new ArrayList<>();
    private ArrayList<byte[]> images = new ArrayList<>();

    //fullconstructor
    public Room(double pricePerDay, String description, int maxGuests, String beds, double x, double y, String extras, boolean smoking, ArrayList<Calendar> reservedDates, ArrayList<byte[]> images) {
        this.pricePerDay = pricePerDay;
        this.description = description;
        this.maxGuests = maxGuests;
        this.beds = beds;
        roomSize[0] = x;
        roomSize[1] = y;
        this.extras = extras;
        this.smoking = smoking;
        this.reservedDates = reservedDates;
        this.images = images;
    }


    public void addImage(byte[] image){
        images.add(image);
    }

    public void reserveDate(Calendar date){
        this.reservedDates.add(date);
    }

    public void reserveDates(ArrayList<Calendar> dates){
        this.reservedDates.addAll(dates);
    }


    public long getRoomId() {
        return roomId;
    }

    public long getHotelId() {
        return hotelId;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public String getBeds() {
        return beds;
    }

    public double[] getRoomSize() {
        return roomSize;
    }

    public String getExtras() {
        return extras;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public ArrayList<Calendar> getReservedDates() {
        return reservedDates;
    }

    public ArrayList<byte[]> getImages() {
        return images;
    }
}
