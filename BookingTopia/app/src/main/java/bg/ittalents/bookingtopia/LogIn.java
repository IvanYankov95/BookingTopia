package bg.ittalents.bookingtopia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LogIn extends AbstractDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("qwesad");
    }
}
