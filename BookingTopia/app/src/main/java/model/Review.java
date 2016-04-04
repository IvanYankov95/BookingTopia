package model;

/**
 * Created by user-17 on 4/2/16.
 */
public class Review {

    private long reviewId;
    private long hotelId;

    private Hotel hotel;
    private String userName;
    private String pros;
    private String cons;
    private double rating;

    public Review(String pros, String cons, double rating, User user) {
        //shte se setva direktno pri suzdavane ot lognatiq user
        this.userName = user.getUsername();
        this.pros = pros;
        this.cons = cons;
        this.rating = rating;
    }

    public String getPros() {
        return pros;
    }

    public String getCons() {
        return cons;
    }

    public double getRating() {
        return rating;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

}
