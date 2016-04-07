package model;

import java.io.Serializable;

import model.dao.ReviewDAO;

/**
 * Created by user-17 on 4/2/16.
 */
public class Review implements Serializable{

    private long reviewId;

    private long hotelID;
    private String writer;
    private String pros;
    private String cons;
    private double rating;

    public Review(long hotelID, String writer, String pros, String cons, double rating) {
        //shte se setva direktno pri suzdavane ot lognatiq user
        this.writer = writer;
        this.hotelID = hotelID;
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



}
