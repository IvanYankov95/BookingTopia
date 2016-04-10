package bg.ittalents.bookingtopia.controller.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.adapters.ImageAdapter;
import bg.ittalents.bookingtopia.controller.fragments.ReservationFragment;
import model.Hotel;
import model.Room;
import model.dao.RoomDAO;

public class ViewRoomActivity extends AbstractDrawerActivity {

    private ImageSwitcher imageSwitcher;
    private RecyclerView imagesRecView;

    private TextView roomPrice;
    private TextView maxGuests;
    private TextView description;
    private TextView beds;
    private TextView extras;

    private Button reserveBtn;

    boolean isClicked;
    Bundle bundle;
    long hotelId;
    Room room;
    ArrayList<byte[]> images = new ArrayList<>();
    int imagesCount;
    int currentIndex = -1;

    Animation in, out;

    private Handler myHandler = new Handler();
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("View Room");

        bundle = getIntent().getExtras();
        final long room_id  = (long) bundle.get("room_id");

        ReservationFragment.context = this;
        room = RoomDAO.getInstance(this).getRoomById(room_id);
        images = room.getImages();
        imagesCount = images.size();

        roomPrice = (TextView) findViewById(R.id.view_room_price);
        roomPrice.setText(String.valueOf(room.getPricePerDay()));
        maxGuests = (TextView) findViewById(R.id.view_room_max_guests);
        maxGuests.setText(" " + room.getMaxGuests());
        description = (TextView) findViewById(R.id.view_room_description_text);
        description.setText(" " + room.getDescription());
        beds = (TextView) findViewById(R.id.view_room_beds_text);
        beds.setText(" " + room.getBeds());
        extras = (TextView) findViewById(R.id.view_room_extras_text);
        extras.setText(" " + room.getExtras());
        reserveBtn = (Button) findViewById(R.id.view_room_reserve_button);

        if(!isUser()) {
            reserveBtn.setVisibility(View.GONE);
        }

        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservationFragment dialog = new ReservationFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("hotel_id", room.getHotelId());
                bundle.putLong("room_id", room_id);
                bundle.putLong("user_id", getLoggedId());
                dialog.setArguments(bundle);
                dialog.show(ViewRoomActivity.this.getFragmentManager(), "MyDialogFragment");
            }
        });

        imagesRecView = (RecyclerView) findViewById(R.id.image_list_view_room_view_room);
        imageAdapter = new ImageAdapter(this, room.getImages());
        imagesRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesRecView.setAdapter(imageAdapter);

        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcherViewRoom);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });

        in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);

        myHandler.postDelayed(r, 1000);
        if(images.size()==1) {
            myHandler.removeCallbacks(r);
        }

        imageSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isClicked){
                    myHandler.removeCallbacks(r);
                }
                else{
                    myHandler.postDelayed(r, 1000);
                }
                isClicked = !isClicked;
            }
        });
    }


    Runnable r = new Runnable() {
        public void run() {
            try {
                updateImageSwitcherImage();
            } finally {
                myHandler.postDelayed(this, 3000);
            }
        }
    };


    private void updateImageSwitcherImage() {
        currentIndex++;
        if (currentIndex == imagesCount)
            currentIndex = 0;
        byte[] imageA = images.get(currentIndex);
        Bitmap bmp = BitmapFactory.decodeByteArray(imageA, 0, imageA.length);
        Drawable d = new BitmapDrawable(getResources(), bmp);
        imageSwitcher.setImageDrawable(d);
    }

    public void communicate(){
        finish();
    }

}
