package bg.ittalents.bookingtopia;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateHotelActivity extends AbstractDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hotel_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Create hotel");



    }
}
