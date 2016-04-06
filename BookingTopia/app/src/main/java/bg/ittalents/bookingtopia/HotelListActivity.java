package bg.ittalents.bookingtopia;


import android.os.Bundle;

public class HotelListActivity extends AbstractDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Create room");




    }
}
