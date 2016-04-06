package bg.ittalents.bookingtopia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import model.Hotel;

/**
 * Created by Preshlen on 4/6/2016.
 */

public class HotelsCardViewAdapter extends RecyclerView.Adapter<HotelsCardViewAdapter.CustomViewHolder> {

    private Activity activity;
    private ArrayList<Hotel> hotels;

    NumberFormat formatter = new DecimalFormat("#0.00");

    public HotelsCardViewAdapter(Activity activity, ArrayList<Hotel> dataSource) {
        this.activity = activity;
        this.hotels = dataSource;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.hotel_list_card_row, parent, false);
        CustomViewHolder holder = new CustomViewHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {

        Hotel hotel = hotels.get(position);

        holder.rating.setText(formatter.format(hotel.getRating()));
        holder.name.setText(hotel.getName());
        holder.city.setText(hotel.getCity());
        holder.description.setText(hotel.getDescription());

        byte[] byteImage = hotel.getImages().get(0);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        holder.image.setImageBitmap(bmp);

        int stars = 7 - hotel.getStars();
        switch (stars){
            case 7:  holder.star7.setVisibility(View.GONE);
            case 6:  holder.star6.setVisibility(View.GONE);
            case 5:  holder.star5.setVisibility(View.GONE);
            case 4:  holder.star4.setVisibility(View.GONE);
            case 3:  holder.star3.setVisibility(View.GONE);
            case 2:  holder.star2.setVisibility(View.GONE);
            case 1:  holder.star1.setVisibility(View.GONE);
        }



//        holder.hotelCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.linearLayoutTicketBoughtInfo.getVisibility() == View.VISIBLE) {
//                    holder.linearLayoutTicketBoughtInfo.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_up));
//                    holder.linearLayoutTicketBoughtInfo.setVisibility(View.INVISIBLE);
//                } else {
//                    holder.linearLayoutTicketBoughtInfo.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_down));
//                    holder.linearLayoutTicketBoughtInfo.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    protected class CustomViewHolder extends RecyclerView.ViewHolder {

        private CardView hotelCardView;

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
        private TextView description;

        CustomViewHolder(View view) {
            super(view);

            hotelCardView = (CardView) view.findViewById(R.id.hotel_list_rec_view);

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

            rating = (TextView) view.findViewById(R.id.rating);
            description = (TextView) view.findViewById(R.id.description);
        }
    }

}