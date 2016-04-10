package bg.ittalents.bookingtopia.controller.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.activities.ViewHotelActivity;
import model.Review;
import model.dao.IReviewDAO;
import model.dao.IUserDAO;
import model.dao.ReviewDAO;
import model.dao.UserDAO;


public class MakeReviewFragment extends DialogFragment {

    public static ViewHotelActivity viewHotActiv;

    EditText rating;
    EditText pros;
    EditText cons;
    Button submitButton;

    Context context;

    IReviewDAO reviewDAO;
    IUserDAO userDAO;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

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
        rating.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});
        pros = (EditText) v.findViewById(R.id.write_pros);
        cons = (EditText) v.findViewById(R.id.write_cons);

        submitButton = (Button) v.findViewById(R.id.submit_button_review);

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(rating.getText().toString().isEmpty()){
                           rating.setError("You need to set rating!");
                            return;
                        }

                        final String writerName = userDAO.getUserById(user_id).getUsername();
                        final double ratingDouble = Double.valueOf(rating.getText().toString());
                        Review review = new Review(hotel_id, writerName, pros.getText().toString(),cons.getText().toString(), ratingDouble);
                        reviewDAO.addReview(review);

                        viewHotActiv.communicate();
                        dismiss();
                    }
                });
        return v;
    }




    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}
