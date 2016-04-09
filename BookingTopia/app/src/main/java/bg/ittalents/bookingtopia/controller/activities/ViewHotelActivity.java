package bg.ittalents.bookingtopia.controller.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.w3c.dom.Text;

import java.util.ArrayList;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.adapters.ImageAdapter;
import bg.ittalents.bookingtopia.controller.adapters.ReviewAdapter;
import bg.ittalents.bookingtopia.controller.adapters.RoomCardViewAdapter;
import bg.ittalents.bookingtopia.controller.fragments.MakeReviewFragment;
import model.Hotel;
import model.dao.HotelDAO;
import model.dao.IRoomDAO;
import model.dao.RoomDAO;

public class ViewHotelActivity extends AbstractDrawerActivity implements ReviewCommunicator {

    public static final int SEND_CODE = 10;

    LinearLayout webPageLayout;
    LinearLayout facebookLayout;
    LinearLayout policiesLayout;

    TextView hotelName;
    TextView hotelCityName;
    TextView hotelDesciption;
    TextView hotelAddress;
    TextView hotelExtras;
    TextView hotelWebPage;
    TextView hotelFacebook;
    TextView hotelPolicies;
    FloatingActionButton fab;

    private ImageSwitcher imageSwitcher;
    private RecyclerView imagesRecView;
    private RecyclerView roomsRecView;
    private RecyclerView reviewsRecView;
    private LinearLayoutManager lim;

    IRoomDAO roomDAO;

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
    ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("View Hotel");

        roomDAO  = RoomDAO.getInstance(this);

        bundle = getIntent().getExtras();
        hotelId = (long) bundle.get("hotel_id");
        hotel = HotelDAO.getInstance(this).getHotel(hotelId);
        images = hotel.getImages();
        imagesCount = images.size();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        if(!isUser()){
            fab.setVisibility(View.GONE);
        }

        imagesRecView = (RecyclerView) findViewById(R.id.image_list_view);
        imageAdapter = new ImageAdapter(this, hotel.getImages());
        imagesRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesRecView.setAdapter(imageAdapter);

        if(!(isUser() && roomDAO.getAllRoomsByHotelWithAvailableDates(hotelId).size() ==0)) {
            roomsRecView = (RecyclerView) findViewById(R.id.room_cardview_in_viewHotel_rec_view);
            roomAdapter = new RoomCardViewAdapter(this, roomDAO.getAllRoomsByHotelWithAvailableDates(hotelId), hotelId);
            LinearLayoutManager lim = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            lim.setStackFromEnd(false);
            roomsRecView.setLayoutManager(lim);
            roomsRecView.setAdapter(roomAdapter);
        }

        reviewsRecView = (RecyclerView) findViewById(R.id.review_cardview_in_viewHotel_rec_view);
        reviewAdapter = new ReviewAdapter(this, hotel.getReviews());
        lim = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lim.setStackFromEnd(false);
        reviewsRecView.setLayoutManager(lim);
        reviewsRecView.setAdapter(reviewAdapter);

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeReviewFragment dialog = new MakeReviewFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("hotel_id", hotelId);
                bundle.putLong("user_id", getLoggedId());

                dialog.setArguments(bundle);
                dialog.show(ViewHotelActivity.this.getFragmentManager(), "MyDialogFragment");
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

    public void callCreateRoom(){
        Intent intent = new Intent(this, CreateRoomActivity.class);
        intent.putExtra("hotel_id", hotelId);
        startActivityForResult(intent, SEND_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        roomAdapter = new RoomCardViewAdapter(this, roomDAO.getAllRoomsByHotelWithAvailableDates(hotelId) , hotelId);
        LinearLayoutManager lim = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lim.setStackFromEnd(true);
        roomsRecView.setLayoutManager(lim);
        roomsRecView.setAdapter(roomAdapter);
    }

    @Override
    public void communicate() {
        reviewAdapter = new ReviewAdapter(this, hotel.getReviews());
        lim = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lim.setStackFromEnd(true);
        reviewsRecView.setLayoutManager(lim);
        reviewsRecView.setAdapter(reviewAdapter);
    }
}
