package bg.ittalents.bookingtopia.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.activities.AbstractDrawerActivity;
import model.Hotel;
import model.Reservation;
import model.Room;
import model.User;
import model.dao.UserDAO;

/**
 * Created by Ivan on 10-Apr-16.
 */
public class ShowReservationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private ArrayList<Reservation> reservations = new ArrayList<>();

    protected class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView city;
        private TextView price;
        private TextView dates;

        private ImageView image;

        CustomViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.view_reservation_hotel_room_image);
            title = (TextView) view.findViewById(R.id.view_reservation_hotel_title);
            city = (TextView) view.findViewById(R.id.view_reservation_hotel_city);
            price = (TextView) view.findViewById(R.id.view_reservation_price);
            dates = (TextView) view.findViewById(R.id.view_reservation_dates);

        }
    }

    public ShowReservationAdapter(Activity activity, ArrayList<Reservation> dataSource) {
        this.activity = activity;
        this.reservations = dataSource;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.show_reservation_row, parent, false);
        CustomViewHolder holder = new CustomViewHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomViewHolder vh = (CustomViewHolder) holder;

        Reservation reservation = reservations.get(position);
        Room room = reservation.getRoom();
        Hotel hotel = reservation.getHotel();
        User user = UserDAO.getInstance(activity).getUserById(reservation.getUserID());
        Bitmap image = BitmapFactory.decodeByteArray(room.getImages().get(0) , 0, room.getImages().get(0).length);
        vh.image.setImageBitmap(image);
        vh.title.setText(hotel.getName());
        vh.city.setText(hotel.getCity() + "\nRes by: " + user.getNames());
        vh.price.setText("Price per day: " + room.getPricePerDay());
        vh.dates.setText(reservation.getDates().get(0) + " " + reservation.getDates().get(reservation.getDates().size() - 1));

    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }
}
