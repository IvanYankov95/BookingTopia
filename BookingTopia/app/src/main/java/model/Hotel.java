package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user-17 on 4/2/16.
 */
public class Hotel {

    private long   hotelId;
    private String name;
    private byte   stars;
    private double xCoordinate;
    private double yCoordinate;
    private Date   workFrom;
    private Date   workTo;
    private String extras;
    private double rating;
    private String webpage;
    private String linkToFacebook;
    private String description;
    private String policies;

    private ArrayList<byte[]> images;

}
