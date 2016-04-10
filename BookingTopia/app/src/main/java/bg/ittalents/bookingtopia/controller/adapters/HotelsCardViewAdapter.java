package bg.ittalents.bookingtopia.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import bg.ittalents.bookingtopia.controller.activities.AbstractDrawerActivity;
import bg.ittalents.bookingtopia.controller.activities.CreateHotelActivity;
import bg.ittalents.bookingtopia.controller.activities.HotelListActivity;
import bg.ittalents.bookingtopia.controller.activities.ViewHotelActivity;
import model.Hotel;

/**
 * Created by Preshlen on 4/6/2016.
 */

public class HotelsCardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int FOOTER_VIEW = 1;

    private Activity activity;
    private ArrayList<Hotel> hotels = new ArrayList<>();


    NumberFormat formatter = new DecimalFormat("#0.0");

    public HotelsCardViewAdapter(Activity activity, ArrayList<Hotel> dataSource) {
        this.activity = activity;
        this.hotels = dataSource;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        if (!((AbstractDrawerActivity) activity).isUser()) {
            if (viewType == FOOTER_VIEW) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plus_layout_for_recycler_view_row, parent, false);

                FooterViewHolder vh = new FooterViewHolder(v);

                return vh;
            }
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
                vh.rating.setText(formatter.format(hotel.getRating()));
                vh.name.setText(hotel.getName());
                vh.city.setText(hotel.getCity());
                vh.description.setText(hotel.getDescription());

                byte[] byteImage = hotel.getImages().get(0);
                Bitmap bmp = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                vh.image.setImageBitmap(bmp);

                switch (hotel.getStars()) {
                    case 7:
                        vh.starImage.setImageResource(R.drawable.star7);
                        break;
                    case 6:
                        vh.starImage.setImageResource(R.drawable.star6);
                        break;

                    case 5:
                        vh.starImage.setImageResource(R.drawable.star5);
                        break;

                    case 4:
                        vh.starImage.setImageResource(R.drawable.star4);
                        break;

                    case 3:
                        vh.starImage.setImageResource(R.drawable.star3);
                        break;

                    case 2:
                        vh.starImage.setImageResource(R.drawable.star2);
                        break;

                    case 1:
                        vh.starImage.setImageResource(R.drawable.star1);
                        break;

                }


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((HotelListActivity) activity).callViewHotel(hotels.get(position).getHotelId());

                    }
                });


            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder vh = (FooterViewHolder) holder;
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((HotelListActivity) activity).callCreateHotel();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        if (hotels == null) {
            return 0;
        }

        if (hotels.size() == 0) {

            return 1;
        }

        if (!((AbstractDrawerActivity) activity).isUser()) {
            return hotels.size() + 1;
        } else {
            return hotels.size();
        }
    }

    public void notifyAdapter() {

        notifyDataSetChanged();
    }


    protected class CustomViewHolder extends ViewHolder {

        private TextView name;
        private TextView city;
        private ImageView starImage;


        private ImageView image;

        private TextView rating;
        private TextView description;

        CustomViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.hotel_image);
            name = (TextView) view.findViewById(R.id.hotel_title);
            city = (TextView) view.findViewById(R.id.hotel_city);
            starImage = (ImageView) view.findViewById(R.id.starsImage);
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
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(int position) {
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == hotels.size()) {
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }
}

