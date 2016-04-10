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
import model.Review;
import model.dao.HotelDAO;
import model.dao.IReviewDAO;
import model.dao.IRoomDAO;
import model.dao.ReviewDAO;
import model.dao.RoomDAO;

public class ViewHotelActivity extends AbstractDrawerActivity {

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
    ImageView stars;
    FloatingActionButton fab;
    TextView addReview;
    TextView rating;
    LinearLayout fabLayout;

    private ImageSwitcher imageSwitcher;
    private RecyclerView imagesRecView;
    private RecyclerView roomsRecView;
    private RecyclerView reviewsRecView;
    private LinearLayoutManager lim;

    IRoomDAO roomDAO;
    IReviewDAO reviewDAO;

    double rate;
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

        MakeReviewFragment.viewHotActiv = ViewHotelActivity.this;

        bundle = getIntent().getExtras();
        hotelId = (long) bundle.get("hotel_id");
        hotel = HotelDAO.getInstance(this).getHotel(hotelId);
        images = hotel.getImages();
        imagesCount = images.size();
        roomDAO = RoomDAO.getInstance(this);
        reviewDAO = ReviewDAO.getInstance(this);

        hotelName = (TextView) findViewById(R.id.view_hotel_name);
        hotelCityName = (TextView) findViewById(R.id.view_hotel_city);
        hotelDesciption = (TextView) findViewById(R.id.view_hotel_description);
        hotelAddress = (TextView) findViewById(R.id.view_hotel_address);
        hotelExtras = (TextView) findViewById(R.id.view_hotel_extrass);
        hotelWebPage = (TextView) findViewById(R.id.view_hotel_web_page);
        rating  = (TextView) findViewById(R.id.view_hotel_rating);
        stars = (ImageView) findViewById(R.id.view_hotel_stars);
        setStars();

        for(Review r : hotel.getReviews()){
            rate += r.getRating();
        }
        if(hotel.getReviews().size()!=0){
            rating.setText(String.valueOf(rate/hotel.getReviews().size()));
        }
        else{
            rating.setText(String.valueOf(rate));
        }
        hotelName.setText(hotel.getName());
        hotelCityName.setText(hotel.getCity());
        hotelDesciption.setText(hotel.getDescription());
        hotelAddress.setText(hotel.getAddress());
        hotelExtras.setText(hotel.getExtras());
        if(hotel.getWebpage().isEmpty()){
            webPageLayout.setVisibility(View.GONE);
        }
        else{
            hotelWebPage.setText(hotel.getWebpage());
        }
        hotelFacebook = (TextView) findViewById(R.id.view_hotel_facebook);
        if(hotel.getLinkToFacebook().isEmpty()){
            facebookLayout.setVisibility(View.GONE);
        }
        else{
            hotelFacebook.setText(hotel.getWebpage());
        }
        hotelPolicies = (TextView) findViewById(R.id.view_hotel_policies);
        if(hotel.getPolicies().isEmpty()) {
            policiesLayout.setVisibility(View.GONE);
        }
        else{
            hotelPolicies.setText(hotel.getWebpage());
        }

        webPageLayout = (LinearLayout) findViewById(R.id.web_page_layout);
        facebookLayout = (LinearLayout) findViewById(R.id.facebook_layout);
        policiesLayout = (LinearLayout) findViewById(R.id.policies_layout);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabLayout = (LinearLayout) findViewById(R.id.fab_layout);
        fabLayout.bringToFront();

        addReview = (TextView) findViewById(R.id.review_add);
        if (!isUser()) {
            fabLayout.setVisibility(View.GONE);
        }

        imagesRecView = (RecyclerView) findViewById(R.id.image_list_view);
        imageAdapter = new ImageAdapter(this, hotel.getImages());
        imagesRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesRecView.setAdapter(imageAdapter);

        if (!(isUser() && roomDAO.getAllRoomsByHotelWithAvailableDates(hotelId).size() == 0)) {
            roomsRecView = (RecyclerView) findViewById(R.id.room_cardview_in_viewHotel_rec_view);
            roomAdapter = new RoomCardViewAdapter(this, roomDAO.getAllRoomsByHotelWithAvailableDates(hotelId), hotelId);
            LinearLayoutManager lim = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            roomsRecView.setLayoutManager(lim);
            roomsRecView.setAdapter(roomAdapter);
        }

        reviewsRecView = (RecyclerView) findViewById(R.id.review_cardview_in_viewHotel_rec_view);
        reviewAdapter = new ReviewAdapter(this, hotel.getReviews());
        lim = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
        imageSwitcher.setAnimateFirstView(false);

        myHandler.postDelayed(r, 1000);

        imageSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isClicked) {
                    myHandler.removeCallbacks(r);
                } else {
                    myHandler.postDelayed(r, 3000);
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
                if(images.size() == 1){
                    myHandler.removeCallbacks(this);
                }
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

    public void callCreateRoom() {

        Intent intent = new Intent(this, CreateRoomActivity.class);
        intent.putExtra("hotel_id", hotelId);
        startActivityForResult(intent, SEND_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        roomAdapter = new RoomCardViewAdapter(this, roomDAO.getAllRoomsByHotelWithAvailableDates(hotelId), hotelId);
        LinearLayoutManager lim = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lim.setStackFromEnd(true);
        roomsRecView.setLayoutManager(lim);
        roomsRecView.setAdapter(roomAdapter);
    }

    public void communicate() {
        hotel = HotelDAO.getInstance(this).getHotel(hotelId);
        for(Review r : hotel.getReviews()){
            rate += r.getRating();
        }

        fabLayout.setVisibility(View.GONE);
        rating.setText(String.valueOf(rate/hotel.getReviews().size()));

        reviewsRecView = (RecyclerView) findViewById(R.id.review_cardview_in_viewHotel_rec_view);
        ArrayList<Review> reviews = new ArrayList<>(reviewDAO.getAllReviewsByHotelId(hotelId));
        reviewAdapter = new ReviewAdapter(this, reviews);
        lim = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewsRecView.setLayoutManager(lim);
        reviewsRecView.setAdapter(reviewAdapter);

    }

    private void setStars(){
        switch (hotel.getStars()) {
            case 7:
                stars.setImageResource(R.drawable.star7);
                break;
            case 6:
                stars.setImageResource(R.drawable.star6);
                break;
            case 5:
                stars.setImageResource(R.drawable.star5);
                break;
            case 4:
                stars.setImageResource(R.drawable.star4);
                break;
            case 3:
                stars.setImageResource(R.drawable.star3);
                break;
            case 2:
                stars.setImageResource(R.drawable.star2);
                break;
            case 1:
                stars.setImageResource(R.drawable.star1);
                break;

        }
    }

}
