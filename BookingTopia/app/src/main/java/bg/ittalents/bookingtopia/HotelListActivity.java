package bg.ittalents.bookingtopia;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import model.dao.HotelDAO;

public class HotelListActivity extends AbstractDrawerActivity {
    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Hotels");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.hotel_list_rec_view);

        HotelsCardViewAdapter adapter = new HotelsCardViewAdapter(this, HotelDAO.getInstance(this).getAllHotelsByCompanyID(1));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }
}
