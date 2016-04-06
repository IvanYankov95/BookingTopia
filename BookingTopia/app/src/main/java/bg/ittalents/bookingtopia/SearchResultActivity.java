package bg.ittalents.bookingtopia;

import android.os.Bundle;

public class SearchResultActivity extends AbstractDrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Results");


    }
}
