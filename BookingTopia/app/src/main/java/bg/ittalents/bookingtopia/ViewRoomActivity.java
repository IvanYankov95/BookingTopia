package bg.ittalents.bookingtopia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewRoomActivity extends AbstractDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("View Room");



    }
}
