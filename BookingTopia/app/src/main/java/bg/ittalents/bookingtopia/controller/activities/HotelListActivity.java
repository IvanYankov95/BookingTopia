package bg.ittalents.bookingtopia.controller.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import bg.ittalents.bookingtopia.controller.adapters.HotelsCardViewAdapter;
import bg.ittalents.bookingtopia.R;
import model.Hotel;
import model.dao.HotelDAO;
import model.dao.IHotelDAO;

public class HotelListActivity extends AbstractDrawerActivity {
    RecyclerView recyclerView ;
    HotelsCardViewAdapter adapter;
    IHotelDAO hotelDAO;
    ArrayList<Hotel> hotels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Hotels");

        hotelDAO = HotelDAO.getInstance(this);

        Bundle bundle = new Bundle();
        String searchName = (String) bundle.get("search_name");
        String searchStars = (String) bundle.get("search_stars");
        int stars = Integer.valueOf(searchStars);

        if(stars != 8){
            hotels = hotelDAO.getAllHotelsByNameAndCity(searchName);
        }
        else{
            hotels = hotelDAO.getAllHotelsByStars(stars);
        }


        recyclerView = (RecyclerView) findViewById(R.id.hotel_list_rec_view);
        adapter = new HotelsCardViewAdapter(this,hotels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       // if (resultCode == Activity.RESULT_OK) {
            Log.e("vliza" , "");
                adapter.notifyAdapter();

       // }

    }
}
