package bg.ittalents.bookingtopia.controller.activities;

import android.os.Bundle;

import bg.ittalents.bookingtopia.R;

public class ReserveActivity extends AbstractDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Reserve rooms");



    }
}
