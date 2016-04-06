package model.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import model.Review;

/**
 * Created by user-17 on 4/2/16.
 */
public class ReviewDAO {
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
        return 0;
    }

    public ArrayList<Review> getAllReviewsByHotelId(long id) {

        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.REVIEWS
                + " WHERE " + mDb.HOTEL_ID + " = \"" + id + "\"";

        Cursor c = db.rawQuery(selectQuery, null);

        ArrayList<Review> reviews = new ArrayList<>();
        if (c.moveToFirst()) {

            long hotelId = c.getLong(c.getColumnIndex(mDb.HOTEL_ID));
            String pros = c.getString(c.getColumnIndex(mDb.REVIEW_PROS));
            String cons = c.getString(c.getColumnIndex(mDb.REVIEW_CONS));
            double rating = c.getDouble(c.getColumnIndex(mDb.REVIEW_RATING));
            String writer = c.getString(c.getColumnIndex(mDb.REVIEW_WRITER));

            Review review = new Review(hotelId, writer, pros, cons, rating);
            reviews.add(review);
        }

        c.close();
        db.close();
        return reviews;
    }


}
