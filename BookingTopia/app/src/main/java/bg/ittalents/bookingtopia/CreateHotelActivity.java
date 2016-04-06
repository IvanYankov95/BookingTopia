package bg.ittalents.bookingtopia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateHotelActivity extends AbstractDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hotel_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Create hotel");



    }
}
