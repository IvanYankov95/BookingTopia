package bg.ittalents.bookingtopia.controller.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import bg.ittalents.bookingtopia.R;
import model.Hotel;
import model.Room;
import model.User;


public class ReservationFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        Hotel hotel = (Hotel) bundle.get("hotel");
        Room  room  = (Room)  bundle.get("room");

        View v = inflater.inflate(R.layout.fragment_reservation, container, false);

        TextView hotelName   = (TextView) v.findViewById(R.id.fragment_reservation_hotel_name);
        ImageView hotelImage = (ImageView) v.findViewById(R.id.fragment_reservation_hotel_image);

        ImageView roomImage = (ImageView) v.findViewById(R.id.fragment_reservation_room_image);
        TextView dates   = (TextView) v.findViewById(R.id.fragment_reservation_dates);
        TextView price   = (TextView) v.findViewById(R.id.fragment_reservation_total_price);

        hotelName.setText(hotel.getName());
        Bitmap bitmap = BitmapFactory.decodeByteArray(hotel.getImages().get(0), 0, hotel.getImages().get(0).length );
        hotelImage.setImageBitmap(bitmap);

        Bitmap bitmap2 = BitmapFactory.decodeByteArray(room.getImages().get(0), 0, room.getImages().get(0).length );
        roomImage.setImageBitmap(bitmap2);

        Calendar calendarFrom = User.getFromCal();
        Calendar calendarTo   = User.getToCal();

        String dateFrom = calendarFrom.getInstance().get(Calendar.YEAR) + "-"
                + calendarFrom.getInstance().get(Calendar.MONTH) + "-"
                + calendarFrom.getInstance().get(Calendar.DAY_OF_MONTH);

        String dateTo = calendarTo.getInstance().get(Calendar.YEAR) + "-"
                + calendarTo.getInstance().get(Calendar.MONTH) + "-"
                + calendarTo.getInstance().get(Calendar.DAY_OF_MONTH);

        dates.setText("Dates for reservation " + dateFrom + " - " + dateTo);

        int counter = 2;
        while(!calendarFrom.after(calendarTo))
        {
            counter++;
            calendarFrom.add(Calendar.DATE, 1);
        }

        price.setText("Total price : " + (counter * room.getPricePerDay()) + " leva");



        return v;
    }

}
