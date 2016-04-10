package model;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user-17 on 4/2/16.
 */
public class Room implements Serializable {

    private long roomId;
    private long hotelId;
    private double pricePerDay;
    private String description;
    private int maxGuests;
    private String beds;
    private double[] roomSize;
    private String extras;
    private boolean smoking;

    private ArrayList<LocalDate> reservedDates = new ArrayList<>();
    private ArrayList<byte[]> images = new ArrayList<>();


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;

        Room room = (Room) o;

        if (hotelId != room.hotelId) return false;
        if (Double.compare(room.pricePerDay, pricePerDay) != 0) return false;
        if (maxGuests != room.maxGuests) return false;
        if (smoking != room.smoking) return false;
        if (description != null ? !description.equals(room.description) : room.description != null)
            return false;
        if (beds != null ? !beds.equals(room.beds) : room.beds != null) return false;
        return !(images != null ? !images.equals(room.images) : room.images != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (hotelId ^ (hotelId >>> 32));
        temp = Double.doubleToLongBits(pricePerDay);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + maxGuests;
        result = 31 * result + (beds != null ? beds.hashCode() : 0);
        result = 31 * result + (smoking ? 1 : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }
}
