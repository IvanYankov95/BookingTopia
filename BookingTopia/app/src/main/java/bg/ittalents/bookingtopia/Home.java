package bg.ittalents.bookingtopia;


import android.os.Bundle;

public class Home extends AbstractDrawerActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("qwesad");
    }
}
