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
import bg.ittalents.bookingtopia.controller.activities.CreateRoomActivity;
import bg.ittalents.bookingtopia.controller.activities.ViewHotelActivity;
import bg.ittalents.bookingtopia.controller.activities.ViewRoomActivity;
import model.Room;

/**
 * Created by Preshlen on 4/7/2016.
 */
public class RoomCardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int FOOTER_VIEW = 1;

    private long hotelId;
    private Activity activity;
    private ArrayList<Room> rooms = new ArrayList<>();

    public RoomCardViewAdapter(Activity activity, ArrayList<Room> dataSource, long hotelId) {
        this.activity = activity;
        this.rooms = dataSource;
        this.hotelId = hotelId;

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
        View row = inflater.inflate(R.layout.room_list_card__row, parent, false);
        CustomViewHolder holder = new CustomViewHolder(row);

        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        try {
            if (holder instanceof CustomViewHolder) {
                CustomViewHolder vh = (CustomViewHolder) holder;

                vh.bindView(position);
//                    Intent intent = new Intent(activity, CreateRoomActivity.class);
//                    intent.putExtra("hotel_id",  hotelId);
//                    Log.e("hotel id ", "" + hotelId);
//                    activity.startActivityForResult(intent, Activity.RESULT_OK);


                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, ViewRoomActivity.class);

                        intent.putExtra("room_id", rooms.get(position).getRoomId());
                        activity.startActivityForResult(intent, 10);

                    }
                });


                Room room = rooms.get(position);

                vh.price.setText(String.valueOf(room.getPricePerDay()));
                vh.maxGuests.setText(String.valueOf(room.getMaxGuests()));
                vh.extras.setText(String.valueOf(room.getExtras()));


                byte[] byteImage = room.getImages().get(0);
                Bitmap bmp = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                vh.image.setImageBitmap(bmp);

            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder vh = (FooterViewHolder) holder;
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ViewHotelActivity)activity).callCreateRoom();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected class CustomViewHolder extends ViewHolder {

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
        if (rooms == null) {
            return 0;
        }

        if (rooms.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }


        return rooms.size() + 1;
    }

    public class FooterViewHolder extends ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);

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
        if (position == rooms.size()) {
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }
}
