package bg.ittalents.bookingtopia.controller.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.widget.ImageSwitcher;

import java.util.ArrayList;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.adapters.ImageAdapter;
import model.Hotel;

public class ViewRoomActivity extends AbstractDrawerActivity {

    private ImageSwitcher imageSwitcher;
    private RecyclerView imagesRecView;

    boolean isClicked = true;
    Bundle bundle;
    long hotelId;
    Hotel hotel;
    ArrayList<byte[]> images;
    int imagesCount;
    int currentIndex = -1;

    Animation in, out;

    private Handler myHandler = new Handler();
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("View Room");

        Bundle bundle = getIntent().getExtras();

        long room_id  = (long) bundle.get("room_id");

    }
}
