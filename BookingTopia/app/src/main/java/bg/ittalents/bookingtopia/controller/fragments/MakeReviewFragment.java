package bg.ittalents.bookingtopia.controller.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.activities.AbstractDrawerActivity;
import bg.ittalents.bookingtopia.controller.activities.ReviewCommunicator;
import bg.ittalents.bookingtopia.controller.activities.ViewHotelActivity;
import model.Review;
import model.dao.IReviewDAO;
import model.dao.IRoomDAO;
import model.dao.IUserDAO;
import model.dao.ReviewDAO;
import model.dao.RoomDAO;
import model.dao.UserDAO;


public class MakeReviewFragment extends DialogFragment {

    EditText rating;
    EditText pros;
    EditText cons;
    Button submitButton;

    Context context;

    IReviewDAO reviewDAO;
    IUserDAO userDAO;
    ReviewCommunicator reviewCommunicator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
        reviewCommunicator = (ReviewCommunicator) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_make_review, container, false);

        reviewDAO = ReviewDAO.getInstance(context);
        userDAO = UserDAO.getInstance(context);

        Bundle bundle = getArguments();
        final long hotel_id = (long) bundle.get("hotel_id");
        final long user_id = (long) bundle.get("user_id");

        rating = (EditText) v.findViewById(R.id.write_rating);
        pros = (EditText) v.findViewById(R.id.write_pros);
        cons = (EditText) v.findViewById(R.id.write_cons);

        submitButton = (Button) v.findViewById(R.id.submit_button_review);

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String writerName = userDAO.getUserById(user_id).getNames();
                        final double ratingDouble = Double.valueOf(rating.getText().toString());
                        Review review = new Review(hotel_id, writerName, pros.getText().toString(),cons.getText().toString(), ratingDouble);

                        reviewDAO.addReview(review);
                        reviewCommunicator.communicate();
                        dismiss();
                    }
                });
        return v;
    }


}
