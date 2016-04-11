package bg.ittalents.bookingtopia.controller.activities;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

import bg.ittalents.bookingtopia.R;
import bg.ittalents.bookingtopia.controller.adapters.ImageAdapter;
import bg.ittalents.bookingtopia.controller.adapters.ReviewAdapter;
import bg.ittalents.bookingtopia.controller.adapters.RoomCardViewAdapter;
import bg.ittalents.bookingtopia.controller.fragments.MakeReviewFragment;
import model.Hotel;
import model.Review;
import model.Room;
import model.dao.HotelDAO;
import model.dao.IHotelDAO;
import model.dao.IReviewDAO;
import model.dao.IRoomDAO;
import model.dao.IUserDAO;
import model.dao.ReviewDAO;
import model.dao.RoomDAO;
import model.dao.UserDAO;

public class ViewHotelActivity extends AbstractDrawerActivity implements View.OnClickListener {

    public static final int CALL_REQUEST_CODE = 111;
    public static final int SEND_CODE = 10;
    private static DecimalFormat df = new DecimalFormat("#0.0");

    private static LinearLayout webPageLayout;
    private static LinearLayout facebookLayout;
    private static LinearLayout policiesLayout;
    private static LinearLayout fabLayout;
    private static LinearLayout phoneLayout;
    private static LinearLayout roomsLayout;
    private static LinearLayout commentsLayout;


    private static TextView hotelInfo;
    private static TextView hotelRooms;
    private static TextView hotelComments;
    private static TextView hotelName;
    private static TextView hotelCityName;
    private static TextView hotelDesciption;
    private static TextView hotelAddress;
    private static TextView hotelExtras;
    private static TextView hotelWebPage;
    private static TextView hotelFacebook;
    private static TextView hotelPolicies;
    private static TextView hotelPhone;
    private static ImageView stars;
    private static FloatingActionButton fab;
    private static TextView rating;

    private static ImageSwitcher imageSwitcher;
    private static RecyclerView imagesRecView;
    private static RecyclerView roomsRecView;
    private static RecyclerView reviewsRecView;
    private static LinearLayoutManager lim;
    private static CardView hotelInfoCardView;

    private static IRoomDAO roomDAO;
    private static IReviewDAO reviewDAO;
    private static IUserDAO userDAO;
    private static IHotelDAO hotelDAO;

    private static String hPhone;
    private static boolean isRatingChanged = false;
    private static boolean isHotelInfoClicked;
    private static boolean isAvailableRoomsClicked;
    private static boolean isCommentsClicked;

    private static double rate;
    private static boolean isClicked = true;
    private static Bundle bundle;
    private static long hotelId;
    private static Hotel hotel;
    private static ArrayList<byte[]> images;
    private static int imagesCount;
    private static int currentIndex = -1;
    private static Animation in, out;
    private static Handler myHandler = new Handler();

    private static ImageAdapter imageAdapter;
    private static RoomCardViewAdapter roomAdapter;
    private static ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("View Hotel");

        MakeReviewFragment.viewHotActiv = ViewHotelActivity.this;

        roomDAO = RoomDAO.getInstance(this);
        reviewDAO = ReviewDAO.getInstance(this);
        userDAO = UserDAO.getInstance(this);
        hotelDAO = HotelDAO.getInstance(this);

        bundle = getIntent().getExtras();
        hotelId = (long) bundle.get("hotel_id");
        hotel = hotelDAO.getHotel(hotelId);
        images = hotel.getImages();
        imagesCount = images.size();

        hPhone = hotelDAO.getHotelPhoneFromCompanyByHotel(hotel);

        hotelInfoCardView = (CardView) findViewById(R.id.room_list_card_view);
        webPageLayout = (LinearLayout) findViewById(R.id.web_page_layout);
        facebookLayout = (LinearLayout) findViewById(R.id.facebook_layout);
        policiesLayout = (LinearLayout) findViewById(R.id.policies_layout);
        phoneLayout = (LinearLayout) findViewById(R.id.view_hotel_phone_layout);
        commentsLayout = (LinearLayout) findViewById(R.id.comments_layout);
        roomsLayout = (LinearLayout) findViewById(R.id.rooms_layout);
        if (!isUser() && hotel.getCompanyId() != getLoggedId()) {
            roomsLayout.setVisibility(View.GONE);
        }

        hotelInfo = (TextView) findViewById(R.id.hotel_info_text);
        hotelInfo.setOnClickListener(this);
        hotelRooms = (TextView) findViewById(R.id.hotel_rooms);
        hotelRooms.setOnClickListener(this);
        hotelComments = (TextView) findViewById(R.id.hotel_comments);
        hotelComments.setOnClickListener(this);

        hotelName = (TextView) findViewById(R.id.view_hotel_name);
        hotelCityName = (TextView) findViewById(R.id.view_hotel_city);
        hotelDesciption = (TextView) findViewById(R.id.view_hotel_description);
        hotelPhone = (TextView) findViewById(R.id.biew_hotel_phone);
        hotelAddress = (TextView) findViewById(R.id.view_hotel_address);
        hotelExtras = (TextView) findViewById(R.id.view_hotel_extrass);
        hotelWebPage = (TextView) findViewById(R.id.view_hotel_web_page);
        rating = (TextView) findViewById(R.id.view_hotel_rating);
        stars = (ImageView) findViewById(R.id.view_hotel_stars);
        setStars();

        for (Review r : hotel.getReviews()) {
            rate += r.getRating();
        }
        if (hotel.getReviews().size() != 0) {
            rating.setText(String.valueOf(df.format(hotel.getRating())));
        } else {
            rating.setText(String.valueOf(df.format(rate)));
        }
        hotelName.setText(hotel.getName());
        hotelCityName.setText(hotel.getCity());
        hotelDesciption.setText(hotel.getDescription());
        hotelAddress.setText(hotel.getAddress());
        hotelExtras.setText(hotel.getExtras());
        if (hPhone.isEmpty()) {
            phoneLayout.setVisibility(View.GONE);
        } else {
            hotelPhone.setText(hPhone);
        }
        if (hotel.getWebpage().isEmpty()) {
            webPageLayout.setVisibility(View.GONE);
        } else {
            hotelWebPage.setText(hotel.getWebpage());
        }
        hotelWebPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(hotelWebPage.getText().toString());
            }
        });
        hotelFacebook = (TextView) findViewById(R.id.view_hotel_facebook);
        if (hotel.getLinkToFacebook().isEmpty()) {
            facebookLayout.setVisibility(View.GONE);
        } else {
            hotelFacebook.setText(hotel.getWebpage());
        }
        hotelPolicies = (TextView) findViewById(R.id.view_hotel_policies);
        if (hotel.getPolicies().isEmpty()) {
            policiesLayout.setVisibility(View.GONE);
        } else {
            hotelPolicies.setText(hotel.getPolicies());
        }

        webPageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(hotelWebPage.getText().toString());
            }
        });
        facebookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openWebPage(hotelWebPage.getText().toString());
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabLayout = (LinearLayout) findViewById(R.id.fab_layout);
        if (isUser()) {
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorSmoothRed)));
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pen));
            final String userName = userDAO.getUserById(getLoggedId()).getUsername();
            for (Review r : hotel.getReviews()) {
                if (r.getWriter().equals(userName)) {
                    fabLayout.setVisibility(View.GONE);
                    break;
                }
            }
            fabLayout.bringToFront();

        } else {
            fabLayout.setVisibility(View.GONE);
        }

        imagesRecView = (RecyclerView) findViewById(R.id.image_list_view);
        imageAdapter = new ImageAdapter(this, hotel.getImages());
        imagesRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesRecView.setAdapter(imageAdapter);

        if (!(isUser() && roomDAO.getAllRoomsByHotelWithAvailableDates(hotelId).size() == 0)) {
            roomsRecView = (RecyclerView) findViewById(R.id.room_cardview_in_viewHotel_rec_view);
            ArrayList<Room> containter = new ArrayList<>(roomDAO.getAllRoomsByHotelWithAvailableDates(hotelId));
            roomAdapter = new RoomCardViewAdapter(this, containter, hotelId);
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

        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "tel:" + hPhone;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));

                int hasCallPermission = ActivityCompat.checkSelfPermission(ViewHotelActivity.this, Manifest.permission.CALL_PHONE);
                if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ViewHotelActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
                } else
                    startActivity(intent);
            }
        });
    }

    Runnable r = new Runnable() {
        public void run() {
            try {
                updateImageSwitcherImage();
            } finally {
                myHandler.postDelayed(this, 3000);
                if (images.size() == 1) {
                    myHandler.removeCallbacks(this);
                }
            }
        }
    };


    private void updateImageSwitcherImage() {
        currentIndex++;
        if (currentIndex >= imagesCount)
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

        if (!(isUser() && roomDAO.getAllRoomsByHotelWithAvailableDates(hotelId).size() == 0)) {
            ArrayList<Room> containter = new ArrayList<>(roomDAO.getAllRoomsByHotelWithAvailableDates(hotelId));
            roomAdapter = new RoomCardViewAdapter(this, containter, hotelId);
            LinearLayoutManager lim = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            lim.setStackFromEnd(true);
            roomsRecView.setLayoutManager(lim);
            roomsRecView.setAdapter(roomAdapter);
        } else {
            roomsRecView.setVisibility(View.GONE);
        }
    }

    public void communicate() {

        isRatingChanged = true;
        rate = 0.0;
        ArrayList<Review> reviews = new ArrayList<>(reviewDAO.getAllReviewsByHotelId(hotelId));
        for (Review r : reviews) {
            rate += r.getRating();
        }

        fabLayout.setVisibility(View.GONE);
        rating.setText(String.valueOf(df.format(rate / reviews.size())));

        reviewsRecView = (RecyclerView) findViewById(R.id.review_cardview_in_viewHotel_rec_view);
        reviewAdapter = new ReviewAdapter(this, reviews);
        lim = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewsRecView.setLayoutManager(lim);
        reviewsRecView.setAdapter(reviewAdapter);

    }

    private void setStars() {
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


    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        if (isRatingChanged) {
            Log.e(" tursq --- ", " vuv ViewHotel if is rating changed = true prashtam result code");
            setResult(HotelListActivity.CHANGE_RATING_CODE, i);
        }
        finish();
        super.onBackPressed();

    }

    public void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, url);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CALL_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String url = "tel:" + hPhone;
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                    int hasCallPermission = ActivityCompat.checkSelfPermission(ViewHotelActivity.this, android.Manifest.permission.CALL_PHONE);
                    if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                        startActivity(intent);
                    }
                }
                return;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hotel_info_text:
                if (!isHotelInfoClicked) {
                    hotelInfoCardView.setVisibility(View.GONE);
                } else {
                    hotelInfoCardView.setVisibility(View.VISIBLE);
                }
                isHotelInfoClicked = !isHotelInfoClicked;
                break;
            case R.id.hotel_rooms:
                if (!(!isUser() && hotel.getCompanyId() != getLoggedId())) {
                    if (!isAvailableRoomsClicked) {
                        roomsLayout.setVisibility(View.GONE);
                    } else {
                        roomsLayout.setVisibility(View.VISIBLE);
                    }
                    isAvailableRoomsClicked = !isAvailableRoomsClicked;
                }
                break;
            case R.id.hotel_comments:
                if (!isCommentsClicked) {
                    commentsLayout.setVisibility(View.GONE);
                } else {
                    commentsLayout.setVisibility(View.VISIBLE);
                }
                isCommentsClicked = !isCommentsClicked;
                break;
        }
    }
}


