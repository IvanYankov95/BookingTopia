package bg.ittalents.bookingtopia.controller.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;
import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.activities.ViewHotelActivity;
import bg.ittalents.bookingtopia.controller.activities.ViewRoomActivity;
import model.Hotel;
import model.Reservation;
import model.Room;
import model.CalendarHelper;
import model.dao.HotelDAO;
import model.dao.IHotelDAO;
import model.dao.IReservationDAO;
import model.dao.IRoomDAO;
import model.dao.ReservationDAO;
import model.dao.RoomDAO;

public class ReservationFragment extends DialogFragment {

    public static ViewRoomActivity context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final IReservationDAO reservationDAO = ReservationDAO.getInstance(context);
        final IRoomDAO roomDAO = RoomDAO.getInstance(context);

        Bundle bundle = getArguments();

        long hotel_id = (long) bundle.get("hotel_id");
        long room_id  = (long) bundle.get("room_id");
        final long user_id  = (long) bundle.get("user_id");

        IHotelDAO hotelDAO = HotelDAO.getInstance(context);
        final Hotel hotel = hotelDAO.getHotel(hotel_id);

        Room room2 = null;
        for(Room room3 : hotel.getRooms()){
            if(room3.getRoomId() == room_id)
                room2 = room3;
        }

        final Room room  = room2;

        View v = inflater.inflate(R.layout.fragment_reservation, container, false);

        TextView hotelName   = (TextView) v.findViewById(R.id.fragment_reservation_hotel_name);
        ImageView hotelImage = (ImageView) v.findViewById(R.id.fragment_reservation_hotel_image);

        ImageView roomImage = (ImageView) v.findViewById(R.id.fragment_reservation_room_image);
        TextView dates   = (TextView) v.findViewById(R.id.fragment_reservation_dates);
        TextView price   = (TextView) v.findViewById(R.id.fragment_reservation_total_price);

        Button confirmButton = (Button) v.findViewById(R.id.fragment_reservation_confirm_button);

        hotelName.setText(hotel.getName());
        Bitmap bitmap = BitmapFactory.decodeByteArray(hotel.getImages().get(0), 0, hotel.getImages().get(0).length );
        hotelImage.setImageBitmap(bitmap);

        Bitmap bitmap2 = BitmapFactory.decodeByteArray(room.getImages().get(0), 0, room.getImages().get(0).length);
        roomImage.setImageBitmap(bitmap2);

        LocalDate helperFrom = CalendarHelper.fromDate;
        LocalDate helperTo = CalendarHelper.toDate;

        LocalDate fromDate = new LocalDate(helperFrom);
        LocalDate toDate = new LocalDate(helperTo);

        int counter = 1;
        while(fromDate.isBefore(toDate)){
            counter++;
            fromDate = fromDate.plusDays(1);
        }

        dates.setText(helperFrom.toString() + " - " + helperTo);

        price.setText(""+ (counter * room.getPricePerDay()) + " BGN");

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reservation reservation = new Reservation(0, room.getRoomId(), user_id);
                long reservationID = reservationDAO.reserve(reservation);

                roomDAO.registerTakenDate(room, reservationID);
                Toast.makeText(context, "Reservation successful", Toast.LENGTH_SHORT).show();
                dismiss();
                context.communicate();
            }
        });

        return v;
    }

}
