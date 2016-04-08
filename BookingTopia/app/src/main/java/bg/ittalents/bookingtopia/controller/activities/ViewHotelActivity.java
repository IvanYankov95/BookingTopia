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
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.adapters.ImageAdapter;
import bg.ittalents.bookingtopia.controller.adapters.RoomCardViewAdapter;
import model.Hotel;
import model.dao.HotelDAO;

public class ViewHotelActivity extends AbstractDrawerActivity {

    private ImageSwitcher imageSwitcher;
    private RecyclerView imagesRecView;
    private RecyclerView roomsRecView;


    boolean isClicked = true;
    Bundle bundle;
    long hotelId;
    Hotel hotel;
    ArrayList<byte[]> images;
    int imagesCount;
    int currentIndex = -1;
    Animation in, out;
    private Handler myHandler = new Handler();

    ImageAdapter imageAdapter;
    RoomCardViewAdapter roomAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("View Hotel");

        bundle = getIntent().getExtras();
        hotelId = (long) bundle.get("hotel");
        hotel = HotelDAO.getInstance(this).getHotel(hotelId);
        images = hotel.getImages();
        imagesCount = images.size();

        imagesRecView = (RecyclerView) findViewById(R.id.image_list_view);
        imageAdapter = new ImageAdapter(this, hotel.getImages());
        imagesRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesRecView.setAdapter(imageAdapter);

        roomsRecView = (RecyclerView) findViewById(R.id.room_cardview_in_viewHotel_rec_view);
        roomAdapter = new RoomCardViewAdapter(this, hotel.getRooms());
        roomsRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        roomsRecView.setAdapter(roomAdapter);

        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
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
}
