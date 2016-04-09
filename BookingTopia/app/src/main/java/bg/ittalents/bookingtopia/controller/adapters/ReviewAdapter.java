package bg.ittalents.bookingtopia.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bg.ittalents.bookingtopia.R;
import model.Review;

/**
 * Created by Preshlen on 4/9/2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.CustomViewHolder> {

private Activity activity;
private ArrayList<Review> reviews = new ArrayList<>();

public ReviewAdapter(Activity activity, ArrayList<Review> dataSource) {
        this.activity = activity;
        this.reviews = dataSource;
        }

@Override
public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.review_card_row, parent, false);
        CustomViewHolder holder = new CustomViewHolder(row);

        return holder;
        }

@Override
public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        holder.rating.setText(String.valueOf(reviews.get(position).getRating()));
        holder.writer.setText(String.valueOf(reviews.get(position).getWriter()));
        holder.pros.setText(String.valueOf(reviews.get(position).getPros()));
        holder.cons.setText(String.valueOf(reviews.get(position).getCons()));

        }

@Override
public int getItemCount() {

        return reviews.size();
        }

public void notifyAdapter() {
        notifyDataSetChanged();
        }

class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView rating;
    TextView writer;
    TextView pros;
    TextView cons;


    CustomViewHolder(View view) {
        super(view);

        rating = (TextView) view.findViewById(R.id.review_rating);
        writer = (TextView) view.findViewById(R.id.review_writer);
        pros = (TextView) view.findViewById(R.id.review_pros);
        cons = (TextView) view.findViewById(R.id.review_cons);

    }
}


}
