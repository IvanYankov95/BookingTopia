package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by user-17 on 4/2/16.
 */
public class Hotel implements Serializable{

    private long hotelId;
    private long companyId;
    private String name = new String();
    private int stars;
    private String address = new String();
    private double xCoordinate;
    private double yCoordinate;
    private Calendar workFrom;
    private Calendar workTo;
    private String extras = new String();
    private double rating;
    private String webpage = new String();
    private String linkToFacebook = new String();
    private String description = new String();
    private String policies = new String();
    private String city = new String();

    private ArrayList<Room>   rooms = new ArrayList<>();
    private ArrayList<byte[]> images = new ArrayList<>();
    private ArrayList<Review> reviews = new ArrayList<>();

    public Hotel(long hotelId, long companyId, String name, int stars, String address, double xCoordinate, double yCoordinate, Calendar workFrom, Calendar workTo, String extras, double rating, String webpage, String linkToFacebook, String description, String policies, ArrayList<Room> rooms, ArrayList<byte[]> images, ArrayList<Review> reviews , String city) {
        this.hotelId = hotelId;
        this.companyId = companyId;
        this.name = name;
        this.stars = stars;
        this.address = address;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.workFrom = workFrom;
        this.workTo = workTo;
        this.extras = extras;
        this.rating = rating;
        this.webpage = webpage;
        this.linkToFacebook = linkToFacebook;
        this.description = description;
        this.policies = policies;
        this.rooms = rooms;
        this.images = images;
        this.reviews = reviews;
        this.city = city;

        this.rooms = rooms;
        this.images = images;

    }

    public void setImages(ArrayList<byte[]> images) {
        this.images = images;
    }

    public void  addReview(User user, String pros, String cons, double rating){
       // reviews.add(new Review(pros,cons,rating, user));
    }

    public void addReview(Review review) {
        getReviews().add(review);
    }

    public void addRoom(Room room) {
        this.getRooms().add(room);
    }

    public void fillHotelRooms(List<Room> rooms) {
        this.getRooms().addAll(rooms);
    }

    public long getHotelId() {
        return hotelId;
    }

    public String getName() {
        return name;
    }

    public int getStars() {
        return stars;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public Calendar getWorkFrom() {
        return workFrom;
    }

    public Calendar getWorkTo() {
        return workTo;
    }

    public String getExtras() {
        return extras;
    }

    public double getRating() {
        return rating;
    }

    public String getWebpage() {
        return webpage;
    }

    public String getLinkToFacebook() {
        return linkToFacebook;
    }

    public String getDescription() {
        return description;
    }

    public String getPolicies() {
        return policies;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<byte[]> getImages() {
        return images;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public long getCompanyId() {
        return companyId;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }
}
