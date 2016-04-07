package bg.ittalents.bookingtopia.controller.activities;

import android.os.Bundle;

import bg.ittalents.bookingtopia.R;
import model.Hotel;

public class ViewRoomActivity extends AbstractDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("View Room");

        Bundle bundle = getIntent().getExtras();

        long hotel_id = (long) bundle.get("hotel_id");
        long room_id  = (long) bundle.get("room_id");

    }
}
