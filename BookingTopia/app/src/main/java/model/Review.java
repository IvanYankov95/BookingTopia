package model;

import java.io.Serializable;
import java.util.Comparator;

import model.dao.ReviewDAO;

/**
 * Created by user-17 on 4/2/16.
 */
public class Review implements Serializable {

    private long reviewId;

    private long hotelID;
    private String writer;
    private String pros;
    private String cons;
    private double rating;

    public Review(long hotelID, String writer, String pros, String cons, double rating)  {
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

    public long getReviewId() {
        return reviewId;
    }

    public long getHotelID() {
        return hotelID;
    }

    public String getWriter() {
        return writer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;

        Review review = (Review) o;

        if (hotelID != review.hotelID) return false;
        return !(writer != null ? !writer.equals(review.writer) : review.writer != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (hotelID ^ (hotelID >>> 32));
        result = 31 * result + (writer != null ? writer.hashCode() : 0);
        return result;
    }
}
