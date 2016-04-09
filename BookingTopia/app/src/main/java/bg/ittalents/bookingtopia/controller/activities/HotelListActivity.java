package bg.ittalents.bookingtopia.controller.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import bg.ittalents.bookingtopia.controller.adapters.HotelsCardViewAdapter;
import bg.ittalents.bookingtopia.R;
import model.Hotel;
import model.dao.HotelDAO;
import model.dao.IHotelDAO;

public class HotelListActivity extends AbstractDrawerActivity {
    private static Spinner orderBy;
    private static RecyclerView recyclerView;

    private static HotelsCardViewAdapter adapter;
    private static IHotelDAO hotelDAO;
    private static ArrayList<Hotel> hotels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Hotels");

        hotels = new ArrayList<>();
        hotelDAO = HotelDAO.getInstance(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getBoolean("search")) {
            String searchName = (String) bundle.get("search_name");
            String searchStars = (String) bundle.get("search_stars");
            int stars = Integer.valueOf(searchStars);

            if (stars != 8) {
                if (!searchName.isEmpty())
                    hotels = hotelDAO.getAllHotelsByNameAndCity(searchName);
                else
                    hotels = hotelDAO.getAllHotels();
            } else {
                hotels = hotelDAO.getAllHotelsByStars(stars);
            }
        }

        ArrayList<String> orderElems = new ArrayList<>();
        orderElems.add("Order by:");
        orderElems.add("rating");
        orderElems.add("stars");

        recyclerView = (RecyclerView) findViewById(R.id.hotel_list_rec_view);
        adapter = new HotelsCardViewAdapter(this, hotels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        orderBy = (Spinner) findViewById(R.id.order_by);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderElems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderBy.setAdapter(dataAdapter);
        orderBy.bringToFront();

        orderBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {


                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.orderList(parent.getItemAtPosition(position).toString());
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("kakvo se sluchva", "dada");
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Log.e("check", "check");

            }
        }

        Log.e("kakvo se sluchva", "dada");
        adapter.notifyAdapter();


    }

    public void callCreateHotel() {
        Intent intent = new Intent(HotelListActivity.this, CreateHotelActivity.class);
        HotelListActivity.this.startActivityForResult(intent, Activity.RESULT_OK);
    }

    public void callViewHotel(long hotelId) {
        Intent intent = new Intent(HotelListActivity.this, ViewHotelActivity.class);
        intent.putExtra("hotel_id", hotelId);
        HotelListActivity.this.startActivityForResult(intent, Activity.RESULT_OK);
    }
}
