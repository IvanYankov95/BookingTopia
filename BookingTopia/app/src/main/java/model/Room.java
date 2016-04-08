package model;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user-17 on 4/2/16.
 */
public class Room implements Serializable{

    private long    roomId;
    private long    hotelId;
    private double  pricePerDay;
    private String  description;
    private int     maxGuests;
    private String  beds;
    private double[]roomSize;
    private String  extras;
    private boolean smoking;

    private ArrayList<LocalDate> reservedDates = new ArrayList<>();
    private ArrayList<byte[]> images = new ArrayList<>();

    //fullconstructor
    public Room(long roomId, long hotelId, double pricePerDay, String description, int maxGuests, String beds, double x, double y, String extras, boolean smoking, ArrayList<LocalDate> reservedDates, ArrayList<byte[]> images) {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.pricePerDay = pricePerDay;
        this.description = description;
        this.maxGuests = maxGuests;
        this.beds = beds;
        this.roomSize = new double[2];
        this.roomSize[0] = x;
        this.roomSize[1] = y;
        this.extras = extras;
        this.smoking = smoking;
        this.reservedDates = reservedDates;
        this.images = images;
    }


    public void addImage(byte[] image){
        images.add(image);
    }

    public void reserveDate(LocalDate date){
        this.reservedDates.add(date);
    }

    public void reserveDates(ArrayList<LocalDate> dates){
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

    public ArrayList<LocalDate> getReservedDates() {
        return reservedDates;
    }

    public ArrayList<byte[]> getImages() {
        return images;
    }

}
