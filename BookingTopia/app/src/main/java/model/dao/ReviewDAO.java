package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import model.Hotel;
import model.Review;

/**
 * Created by user-17 on 4/2/16.
 */



public class ReviewDAO implements  IReviewDAO{
    private DatabaseHelper mDb;
    private static ReviewDAO instance;

    private ReviewDAO(Context context) {
        this.mDb = DatabaseHelper.getInstance(context);
    }

    public static ReviewDAO getInstance(Context context) {
        if (instance == null) {
            instance = new ReviewDAO(context);
        }
        return instance;
    }

    public long addReview(Review review) {

        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(mDb.HOTEL_ID,           review.getHotelID());
        values.put(mDb.REVIEW_PROS, review.getPros());
        values.put(mDb.REVIEW_CONS,   review.getCons());
        values.put(mDb.REVIEW_RATING,    review.getRating());
        values.put(mDb.REVIEW_WRITER,          review.getWriter());

        long roomId = db.insert(mDb.REVIEWS, null, values);

        db.insert(mDb.REVIEWS, null, values);

        db.close();
        return roomId;
    }

    @Override
    public long getReviewByHotel(Hotel hotel) {
        return 0;
    }

    public HashSet<Review> getAllReviewsByHotelId(long id) {

        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.REVIEWS
                + " WHERE " + mDb.HOTEL_ID + " = \"" + id + "\"";

        Cursor c = db.rawQuery(selectQuery, null);

        HashSet<Review> reviews = new HashSet<>();
        if (c.moveToFirst()) {
            do {
                long hotelId = c.getLong(c.getColumnIndex(mDb.HOTEL_ID));
                String pros = c.getString(c.getColumnIndex(mDb.REVIEW_PROS));
                String cons = c.getString(c.getColumnIndex(mDb.REVIEW_CONS));
                double rating = c.getDouble(c.getColumnIndex(mDb.REVIEW_RATING));
                String writer = c.getString(c.getColumnIndex(mDb.REVIEW_WRITER));

                Review review = new Review(hotelId, writer, pros, cons, rating);
                reviews.add(review);
            }while (c.moveToNext());
        }

        c.close();
        db.close();
        return reviews;
    }



}
