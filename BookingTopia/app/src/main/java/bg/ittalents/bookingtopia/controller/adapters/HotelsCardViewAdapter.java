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

public class HotelsCardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int FOOTER_VIEW = 1;

    private Activity activity;
    private ArrayList<Hotel> hotels = new ArrayList<>();


    NumberFormat formatter = new DecimalFormat("#0.00");

    public HotelsCardViewAdapter(Activity activity, ArrayList<Hotel> dataSource) {
        this.activity = activity;
        this.hotels = dataSource;

//        hotels.add(new Hotel(0, 0, null, 0, null, 0, 0, null, null, null, 0, null, null, null, null, null, null, null, null));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        if (viewType == FOOTER_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plus_layout_for_recycler_view_row, parent, false);

            FooterViewHolder vh = new FooterViewHolder(v);

            return vh;
        }

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.hotel_list_card_row, parent, false);
        CustomViewHolder holder = new CustomViewHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        try {
            if (holder instanceof CustomViewHolder) {
                CustomViewHolder vh = (CustomViewHolder) holder;

                vh.bindView(position);


                Hotel hotel = hotels.get(position);
                Log.e("----HOTEL", hotel.getName() + " " + hotel.getCity());
                vh.rating.setText(formatter.format(hotel.getRating()));
                vh.name.setText(hotel.getName());
                vh.city.setText(hotel.getCity());
                vh.description.setText(hotel.getDescription());

                byte[] byteImage = hotel.getImages().get(0);
                Bitmap bmp = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                vh.image.setImageBitmap(bmp);

                int stars = 7 - hotel.getStars();
                switch (stars) {
                    case 7:
                        vh.star7.setVisibility(View.GONE);
                    case 6:
                        vh.star6.setVisibility(View.GONE);
                    case 5:
                        vh.star5.setVisibility(View.GONE);
                    case 4:
                        vh.star4.setVisibility(View.GONE);
                    case 3:
                        vh.star3.setVisibility(View.GONE);
                    case 2:
                        vh.star2.setVisibility(View.GONE);
                    case 1:
                        vh.star1.setVisibility(View.GONE);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, ViewHotelActivity.class);
                        intent.putExtra("hotel_id", hotels.get(position).getHotelId());
                        activity.startActivityForResult(intent, Activity.RESULT_OK);


                    }
                });
                //     }


            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder vh = (FooterViewHolder) holder;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//   @Override
//    public int getItemCount() {
//
//        return hotels.size();
//    }

    @Override
    public int getItemCount() {
        if (hotels == null) {
            return 0;
        }

        if (hotels.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }


        return hotels.size() + 1;
    }

    public void notifyAdapter() {

        notifyDataSetChanged();
    }

    public void orderList(String criteria) {

        if (criteria.equals("stars")) {
            Comparator<Hotel> byStars = new Comparator<Hotel>() {
                @Override
                public int compare(Hotel o1, Hotel o2) {
                    return Integer.valueOf(o1.getStars()).compareTo(o2.getStars());
                }
            };

            Collections.sort(this.hotels, byStars);
        } else if (criteria.equals("rating")) {
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

    protected class CustomViewHolder extends ViewHolder {

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

    public class FooterViewHolder extends ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivityForResult(new Intent(activity, CreateHotelActivity.class), Activity.RESULT_OK);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public void bindView(int position) { }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == hotels.size()) {
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }
}

