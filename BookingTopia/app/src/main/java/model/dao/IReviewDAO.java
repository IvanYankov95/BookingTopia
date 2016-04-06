package model.dao;

import model.Hotel;
import model.Review;

/**
 * Created by user-20 on 4/6/16.
 */
public interface IReviewDAO {

    public long addReview(Review review);

    public long getReviewByHotel(Hotel hotel);
}
