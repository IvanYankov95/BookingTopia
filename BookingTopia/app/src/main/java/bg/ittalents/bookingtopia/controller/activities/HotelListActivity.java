package bg.ittalents.bookingtopia.controller.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import bg.ittalents.bookingtopia.controller.adapters.HotelsCardViewAdapter;
import bg.ittalents.bookingtopia.R;
import model.dao.HotelDAO;

public class HotelListActivity extends AbstractDrawerActivity {
    RecyclerView recyclerView ;
    HotelsCardViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Hotels");

        recyclerView = (RecyclerView) findViewById(R.id.hotel_list_rec_view);

        adapter = new HotelsCardViewAdapter(this, HotelDAO.getInstance(this).getAllHotelsByCompanyID(getLoggedId()));
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
