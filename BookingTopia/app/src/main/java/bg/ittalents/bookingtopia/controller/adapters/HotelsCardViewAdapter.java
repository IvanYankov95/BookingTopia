package bg.ittalents.bookingtopia.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.activities.CreateHotelActivity;
import bg.ittalents.bookingtopia.controller.activities.ViewHotelActivity;
import model.Hotel;

/**
 * Created by Preshlen on 4/6/2016.
 */

public class HotelsCardViewAdapter extends RecyclerView.Adapter<HotelsCardViewAdapter.CustomViewHolder> {

    private Activity activity;
    private ArrayList<Hotel> hotels = new ArrayList<>();


    NumberFormat formatter = new DecimalFormat("#0.00");

    public HotelsCardViewAdapter(Activity activity, ArrayList<Hotel> dataSource) {
        this.activity = activity;
        this.hotels = dataSource;

//        hotels.add(new Hotel(0, 0, null, 0, null, 0, 0, null, null, null, 0, null, null, null, null, null, null, null, null));
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.hotel_list_card_row, parent, false);
        CustomViewHolder holder = new CustomViewHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        if (position == hotels.size() - 1) {
            holder.linearLayoutStars.setVisibility(View.GONE);
            holder.startingRating.setVisibility(View.GONE);
            holder.finishRating.setVisibility(View.GONE);
            holder.relativeLayout.setBackgroundResource(R.drawable.plus);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivityForResult(new Intent(activity, CreateHotelActivity.class), Activity.RESULT_OK);
                }
            });
            return;
        }

        Hotel hotel = hotels.get(position);
        Log.e("----HOTEL", hotel.getName() + " " + hotel.getCity());
        holder.rating.setText(formatter.format(hotel.getRating()));
        holder.name.setText(hotel.getName());
        holder.city.setText(hotel.getCity());
        holder.description.setText(hotel.getDescription());

        byte[] byteImage = hotel.getImages().get(0);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        holder.image.setImageBitmap(bmp);

        int stars = 7 - hotel.getStars();
        switch (stars) {
            case 7:
                holder.star7.setVisibility(View.GONE);
            case 6:
                holder.star6.setVisibility(View.GONE);
            case 5:
                holder.star5.setVisibility(View.GONE);
            case 4:
                holder.star4.setVisibility(View.GONE);
            case 3:
                holder.star3.setVisibility(View.GONE);
            case 2:
                holder.star2.setVisibility(View.GONE);
            case 1:
                holder.star1.setVisibility(View.GONE);
        }


        if(position == hotels.size() - 1){

        }
        else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity , ViewHotelActivity.class);
                    intent.putExtra("hotel_id", hotels.get(position).getHotelId());
                    activity.startActivityForResult(intent, Activity.RESULT_OK);


                }
            });
        }

    }

    @Override
    public int getItemCount() {

        return hotels.size();
    }

    public void notifyAdapter() {

        notifyDataSetChanged();
    }

    public void orderList(String criteria) {
        if(criteria.equals("stars")){
            Comparator<Hotel> byStars = new Comparator<Hotel>() {
                @Override
                public int compare(Hotel o1, Hotel o2) {
                    return Integer.valueOf(o1.getStars()).compareTo(o2.getStars());
                }
            };

            Collections.sort(this.hotels, byStars);
        }
        else if(criteria.equals("rating")){
            Comparator<Hotel> byStars = new Comparator<Hotel>() {
                @Override
                public int compare(Hotel o1, Hotel o2) {
                    return Double.valueOf(o1.getRating()).compareTo(o2.getRating());
                }
            };

            Collections.sort(this.hotels, byStars);
        }
        notifyDataSetChanged();
    }

    protected class CustomViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayoutStars;
        private RelativeLayout relativeLayout;

        private TextView name;
        private TextView city;
        private ImageView star1;
        private ImageView star2;
        private ImageView star3;
        private ImageView star4;
        private ImageView star5;
        private ImageView star6;
        private ImageView star7;
        private ImageView image;

        private TextView rating;
        private TextView startingRating;
        private TextView finishRating;
        private TextView description;

        CustomViewHolder(View view) {
            super(view);

            linearLayoutStars = (LinearLayout) view.findViewById(R.id.stars);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.card_view_row_inner_layout);

            image = (ImageView) view.findViewById(R.id.hotel_image);
            name = (TextView) view.findViewById(R.id.hotel_title);
            city = (TextView) view.findViewById(R.id.hotel_city);
            star1 = (ImageView) view.findViewById(R.id.hotel_star1);
            star2 = (ImageView) view.findViewById(R.id.hotel_star2);
            star3 = (ImageView) view.findViewById(R.id.hotel_star3);
            star4 = (ImageView) view.findViewById(R.id.hotel_star4);
            star5 = (ImageView) view.findViewById(R.id.hotel_star5);
            star6 = (ImageView) view.findViewById(R.id.hotel_star6);
            star7 = (ImageView) view.findViewById(R.id.hotel_star7);

            startingRating = (TextView) view.findViewById(R.id.starting_rating);
            finishRating = (TextView) view.findViewById(R.id.ending_rating);

            rating = (TextView) view.findViewById(R.id.rating);
            description = (TextView) view.findViewById(R.id.description);
        }
    }


}

