package bg.ittalents.bookingtopia.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.activities.CreateHotelActivity;
import bg.ittalents.bookingtopia.controller.activities.ViewRoomActivity;
import model.Room;

/**
 * Created by Preshlen on 4/7/2016.
 */
public class RoomCardViewAdapter extends RecyclerView.Adapter<RoomCardViewAdapter.CustomViewHolder> {

    private Activity activity;
    private ArrayList<Room> rooms = new ArrayList<>();

    public RoomCardViewAdapter(Activity activity, ArrayList<Room> dataSource) {
        this.activity = activity;
        this.rooms = dataSource;

        rooms.add(new Room(0, 0, 0, null, 0, null, 0, 0, null, false, null, null));
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.room_list_card__row, parent, false);
        CustomViewHolder holder = new CustomViewHolder(row);

        return holder;
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        if (position == rooms.size() - 1) {
            holder.bgn.setVisibility(View.GONE);
            holder.pplImage.setVisibility(View.GONE);
            holder.relativeLayout.setBackgroundResource(R.drawable.plus);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivityForResult(new Intent(activity, CreateHotelActivity.class), Activity.RESULT_OK);
                }
            });
            return;
        }

        Room room = rooms.get(position);

        holder.price.setText(String.valueOf(room.getPricePerDay()));
        holder.maxGuests.setText(String.valueOf(room.getMaxGuests()));
        holder.extras.setText(String.valueOf(room.getExtras()));


        byte[] byteImage = room.getImages().get(0);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        holder.image.setImageBitmap(bmp);


        if (position == rooms.size() - 1) {

        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ViewRoomActivity.class);
                    intent.putExtra("room_id", rooms.get(position).getHotelId());
                    activity.startActivityForResult(intent, Activity.RESULT_OK);

                }
            });
        }

    }


    protected class CustomViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativeLayout;

        private TextView price;
        private TextView maxGuests;
        private TextView extras;
        private ImageView image;
        private ImageView pplImage;
        private TextView bgn;


        CustomViewHolder(View view) {
            super(view);

            relativeLayout = (RelativeLayout) view.findViewById(R.id.card_view_row_inner_layout_room);

            image = (ImageView) view.findViewById(R.id.room_image);
            //TODO fill room infovc
            price = (TextView) view.findViewById(R.id.room_price);
            maxGuests = (TextView) view.findViewById(R.id.max_guests);
            extras = (TextView) view.findViewById(R.id.extras);

            bgn = (TextView) view.findViewById(R.id.room_price_BGN);
            pplImage = (ImageView) view.findViewById(R.id.ppl_image);

        }
    }

    @Override
    public int getItemCount() {

        return rooms.size();
    }
}
