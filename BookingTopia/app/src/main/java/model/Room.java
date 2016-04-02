package model;

import java.util.ArrayList;

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

    private ArrayList<byte[]> images;
}
