package bg.ittalents.bookingtopia.controller.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.activities.AbstractDrawerActivity;
import bg.ittalents.bookingtopia.controller.adapters.ImageAdapter;
import bg.ittalents.bookingtopia.controller.adapters.ShowReservationAdapter;
import model.Reservation;
import model.dao.ReservationDAO;

public class ShowReservationFragment extends DialogFragment {

    public static Context context;
    public static long loggedID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_reservation, container, false);

        ArrayList<Reservation> reservations = ReservationDAO.getInstance(context).getReservationsByUser(loggedID);

        RecyclerView imagesRecView = (RecyclerView) v.findViewById(R.id.show_reservation_recycler_view);
        ShowReservationAdapter adapter = new ShowReservationAdapter((AbstractDrawerActivity)context, reservations);
        imagesRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        imagesRecView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

}
