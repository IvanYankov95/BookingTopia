package bg.ittalents.bookingtopia;

import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;

public class HomeActivity extends AbstractDrawerActivity  {

    HashMap<String, String> user ;
    long logId;
    boolean isUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Home");

        user = session.getUserDetails();

        logId = Long.parseLong(user.get(session.KEY_ID));
        String s =user.get(session.IS_USER);
        isUser = s.equals("true");

        Toast.makeText(HomeActivity.this, "" + isUser, Toast.LENGTH_SHORT).show();
    }
}
