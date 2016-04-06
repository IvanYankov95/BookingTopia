package bg.ittalents.bookingtopia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReserveActivity extends AbstractDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Reserve rooms");



    }
}
