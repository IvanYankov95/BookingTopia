package model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by owner on 22/02/2016.
 */
class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    // database name and version
    public static final String DATABASE_NAME = "BOOKINGTOPIA_DATABASE";
    public static final int DATABASE_VERSION = 6;

    // tables
    public static final String USERS        = "users";
    public static final String COMPANIES    = "companies";
    public static final String HOTELS       = "hotels";
    public static final String ROOMS        = "rooms";
    public static final String HOTEL_IMAGES = "hotel_images";
    public static final String ROOM_IMAGES  = "room_images";
    public static final String BOOKINGS     = "booking";
    public static final String TAKEN_DATES  = "taken_dates";
    public static final String REVIEWS      = "reviews";

    //ID fields
    public static final String USER_ID        = "user_id";
    public static final String COMPANY_ID     = "company_id";
    public static final String HOTEL_ID       = "hotel_id";
    public static final String ROOM_ID        = "room_id";
    public static final String HOTEL_IMAGE_ID = "hotel_image_id";
    public static final String ROOM_IMAGE_ID  = "room_image_id";
    public static final String TAKEN_DATE_ID  = "taken_date_id";
    public static final String BOOKING_ID     = "booking_id";
    public static final String REVIEW_ID      = "review_id";

    //common fields
    public static final String EMAIL    = "email";
    public static final String PASSWORD = "password";
    public static final String CONTENT  = "content";
    public static final String TELEPHONE= "user_phone";

    // USERS table columns
    public static final String USERNAME     = "user_username";
    public static final String COUNTRY      = "user_country";
    public static final String USER_NAME    = "user_name";
    public static final String DATE_OF_BIRTH= "user_date_of_birth";
    public static final String AVATAR       = "user_avatar";
    public static final String GENDER       = "user_gender";
    public static final String SMOKING      = "user_smoking";

    // REVIEW table columns
    public static final String REVIEW_PROS   = "review_pros";
    public static final String REVIEW_CONS   = "review_cons";
    public static final String REVIEW_RATING = "review_rating";
    public static final String REVIEW_WRITER = "review_writer";


    // HOTEL table columns
    public static final String HOTEL_NAME        = "hotel_name";
    public static final String HOTEL_ADDRESS     = "hotel_address";
    public static final String HOTEL_STARS       = "hotel_stars";
    public static final String HOTEL_WORK_FROM   = "hotel_work_from";
    public static final String HOTEL_WORK_TO     = "hotel_work_to";
    public static final String HOTEL_EXTRAS      = "hotel_extras";
    public static final String HOTEL_RATING      = "hotel_rating";
    public static final String HOTEL_X           = "hotel_x_coordinate";
    public static final String HOTEL_Y           = "hotel_y_coordinate";
    public static final String HOTEL_WEBPAGE     = "hotel_webpage";
    public static final String HOTEL_FACEBOOK    = "hotel_facebook";
    public static final String HOTEL_DESCRIPTION = "hotel_description";
    public static final String HOTEL_POLICIES    = "hotel_policies";
    public static final String HOTEL_CITY        = "hotel_city";
    public static final String HOTEL_COORDINATESx = "hotel_coordinate_x";
    public static final String HOTEL_COORDINATESy = "hotel_coordinate_y";


    // COMPANY table columns
    public static final String COMPANY_DESCRIPTION    = "company_description";
    public static final String COMPANY_OFFICE_ADDRESS = "company_office_address";
    public static final String COMPANY_NAME           = "company_name";

    // ROOMS table columns
    public static final String ROOM_PRICE_PER_DAY = "room_price_per_day";
    public static final String ROOM_DESCRIPTION   = "room_description";
    public static final String ROOM_MAX_GUESTS    = "room_max_guests";
    public static final String ROOM_BEDS          = "room_beds";
    public static final String ROOM_X             = "room_x";
    public static final String ROOM_Y             = "room_y";
    public static final String ROOM_EXTRAS        = "room_extras";
    public static final String ROOM_SMOKING       = "room_smoking";

    //TAKEN_DATES table columns
    public static final String DATE = "date";

    //Booking table columns
    public static final String NOTIFICATION_SHOWED = "notification_showed";

    
    // CREATE statements
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + USERS + " ("
            + USER_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + USERNAME      + " VARCHAR(255) NOT NULL, "
            + EMAIL         + " VARCHAR(255) NOT NULL, "
            + PASSWORD      + " VARCHAR(255) NOT NULL, "
            + USER_NAME     + " VARCHAR(255), "
            + COUNTRY       + " VARCHAR(255), "
            + TELEPHONE     + " VARCHAR(255), "
            + DATE_OF_BIRTH + " DATE, "
            + GENDER        + " VARCHAR(255), "
            + SMOKING       + " INTEGER, "
            + AVATAR        + " BLOB NOT NULL"
            +") ";

    private static final String CREATE_COMPANIES_TABLE = "CREATE TABLE IF NOT EXISTS " + COMPANIES + " ("
            + COMPANY_ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COMPANY_NAME          + " VARCHAR(255) NOT NULL, "
            + PASSWORD              + " VARCHAR(255) NOT NULL, "
            + EMAIL                 + " VARCHAR(255) NOT NULL, "
            + COMPANY_OFFICE_ADDRESS+ " VARCHAR(255) NOT NULL, "
            + AVATAR                + " BLOB NOT NULL, "
            + TELEPHONE             + " VARCHAR(255), "
            + COMPANY_DESCRIPTION   + " VARCHAR(2000) NOT NULL"
            +") ";

    private static final String CREATE_HOTELS_TABLE = "CREATE TABLE IF NOT EXISTS " + HOTELS + " ("
            + HOTEL_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COMPANY_ID        + " INTEGER, "
            + HOTEL_NAME        + " VARCHAR(255) NOT NULL, "
            + HOTEL_STARS       + " INTEGER, "
            + HOTEL_ADDRESS     + " VARCHAR(255) NOT NULL, "
            + HOTEL_WORK_FROM   + " DATE, "
            + HOTEL_WORK_TO     + " DATE, "
            + HOTEL_EXTRAS      + " VARCHAR(500) NOT NULL, "
            + HOTEL_RATING      + " DOUBLE, "
            + HOTEL_X           + " DOUBLE, "
            + HOTEL_Y           + " DOUBLE, "
            + HOTEL_WEBPAGE     + " VARCHAR(255) NOT NULL, "
            + HOTEL_FACEBOOK    + " VARCHAR(255) NOT NULL, "
            + HOTEL_DESCRIPTION + " VARCHAR(2000) NOT NULL, "
            + HOTEL_POLICIES    + " VARCHAR(255) NOT NULL, "
            + HOTEL_CITY        + " VARCHAR(255) NOT NULL, "
            + "FOREIGN KEY ("+ COMPANY_ID +") REFERENCES "+ COMPANIES +"("+ COMPANY_ID +")"
            +") ";

    private static final String CREATE_ROOMS_TABLE = "CREATE TABLE IF NOT EXISTS " + ROOMS + " ("
            + ROOM_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HOTEL_ID              + " INTEGER, "
            + ROOM_PRICE_PER_DAY    + " DOUBLE NOT NULL, "
            + ROOM_DESCRIPTION      + " VARCHAR(2000) NOT NULL, "
            + ROOM_MAX_GUESTS       + " INTEGER NOT NULL, "
            + ROOM_BEDS             + " VARCHAR(255) NOT NULL, "
            + ROOM_X                + " INTEGER , "
            + ROOM_Y                + " INTEGER , "
            + ROOM_EXTRAS           + " VARCHAR(500) NOT NULL, "
            + ROOM_SMOKING          + " INTEGER NOT NULL, "
            + "FOREIGN KEY ("+ HOTEL_ID +") REFERENCES "+ HOTELS +"("+ HOTEL_ID +")"
            +") ";

    private static final String CREATE_HOTELS_IMAGES_TABLE =  "CREATE TABLE IF NOT EXISTS " + HOTEL_IMAGES + " ("
            + HOTEL_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HOTEL_ID       + " INTEGER, "
            + CONTENT        + " BLOB NOT NULL, "
            + "FOREIGN KEY ("+ HOTEL_ID +") REFERENCES "+ HOTELS +"("+ HOTEL_ID +")"
            +") ";

    private static final String CREATE_ROOMS_IMAGES_TABLE =  "CREATE TABLE IF NOT EXISTS " + ROOM_IMAGES + " ("
            + ROOM_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ROOM_ID       + " INTEGER, "
            + CONTENT       + " BLOB NOT NULL, "
            + "FOREIGN KEY ("+ ROOM_ID +") REFERENCES "+ ROOMS +"("+ ROOM_ID +")"
            +") ";

    private static final String CREATE_BOOKINGS_TABLE =  "CREATE TABLE IF NOT EXISTS " + BOOKINGS + " ("
            + BOOKING_ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ROOM_ID               + " INTEGER, "
            + USER_ID               + " INTEGER, "
            + COMPANY_ID            + " INTEGER, "
            + NOTIFICATION_SHOWED   + " INTEGER, "
            + "FOREIGN KEY ("+ ROOM_ID +") REFERENCES "+ ROOMS +"("+ ROOM_ID +")"
            + "FOREIGN KEY ("+ USER_ID +") REFERENCES "+ USERS +"("+ USER_ID +")"
            +") ";

    private static final String CREATE_TAKEN_DATES_TABLE =  "CREATE TABLE IF NOT EXISTS " + TAKEN_DATES + " ("
            + TAKEN_DATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BOOKING_ID    + " INTEGER, "
            + ROOM_ID       + " INTEGER, "
            + DATE          + " DATE, "
            + "FOREIGN KEY ("+ BOOKING_ID +") REFERENCES "+ BOOKINGS +"("+ BOOKING_ID +")"
            + "FOREIGN KEY ("+ ROOM_ID +") REFERENCES "+ ROOMS +"("+ ROOM_ID +")"
            +") ";

    private static final String CREATE_REVIEWS_TABLE =  "CREATE TABLE IF NOT EXISTS " + REVIEWS + " ("
            + REVIEW_ID     + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HOTEL_ID      + " INTEGER, "
            + REVIEW_PROS   + " VARCHAR(255), "
            + REVIEW_CONS   + " VARCHAR(255), "
            + REVIEW_RATING + " DOUBLE, "
            + REVIEW_WRITER + " VARCHAR(255), "
            + "FOREIGN KEY ("+ HOTEL_ID +") REFERENCES "+ HOTELS +"("+ HOTEL_ID +")"
            +") ";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static synchronized  DatabaseHelper getInstance(Context context){
        if(instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_COMPANIES_TABLE);
        db.execSQL(CREATE_ROOMS_TABLE);
        db.execSQL(CREATE_HOTELS_TABLE);
        db.execSQL(CREATE_HOTELS_IMAGES_TABLE);
        db.execSQL(CREATE_ROOMS_IMAGES_TABLE);
        db.execSQL(CREATE_BOOKINGS_TABLE);
        db.execSQL(CREATE_TAKEN_DATES_TABLE);
        db.execSQL(CREATE_REVIEWS_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        db.execSQL("DROP TABLE IF EXISTS " + COMPANIES);
        db.execSQL("DROP TABLE IF EXISTS " + HOTELS);
        db.execSQL("DROP TABLE IF EXISTS " + ROOMS);
        db.execSQL("DROP TABLE IF EXISTS " + HOTEL_IMAGES);
        db.execSQL("DROP TABLE IF EXISTS " + ROOM_IMAGES);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TAKEN_DATES);
        db.execSQL("DROP TABLE IF EXISTS " + REVIEWS);

        //create new tables
        onCreate(db);
    }

    // closing database
    void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}