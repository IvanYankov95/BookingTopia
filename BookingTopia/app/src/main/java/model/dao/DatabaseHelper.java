package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by owner on 22/02/2016.
 */
class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;
    private Context context;

    // database name and version
    public static final String DATABASE_NAME = "OLXLIKE_DATABASE";
    public static final int DATABASE_VERSION = 1;

    // tables
    public static final String USERS = "users";
    public static final String OFFERS = "offers";
    public static final String MESSAGES = "messages";
    public static final String CATEGORIES = "categories";
    public static final String IMAGES = "images";

    //common fields
    public static final String COUNTRY = "country";
    public static final String USER_ID = "user_id";
    public static final String COMPANY_ID = "company_id";
    public static final String COMPANY_NAME = "company_name";
    public static final String CONTENT = "content";
    public static final String DATE = "date";

    // USERS table colmns
    public static final String USERNAME = "user_username";
    public static final String EMAIL = "user_email";
    public static final String PASSWORD = "user_password";
    public static final String FIRST_NAME = "user_fname";
    public static final String LAST_NAME = "user_lname";
    public static final String TELEPHONE = "user_phone";
    public static final String DATE_OF_BIRTH = "user_date_of_birth";
    public static final String AVATAR = "user_avatar";
    public static final String GENDER = "user_gender";
    public static final String SMOKING = "user_smoking";

    // REVIEW table colmns
    public static final String REVIEW_ID = "review_id";
    public static final String REVIEW_PROS = "review_pros";
    public static final String REVIEW_CONS = "review_cons";
    public static final String REVIEW_RATING = "rveiw_rating";

    // HOTEL table columns
    public  static final String HOTEL_ID = "hotel_id";
    public  static final String HOTEL_NAME = "hotel_name";
    public  static final String HOTEL_ADDRESS = "hotel_address";
    public  static final String HOTEL_STARS = "hotel_stars";
    public  static final String HOTEL_X = "hotel_x";
    public  static final String HOTEL_Y = "hotel_y";
    public  static final String HOTEL_WORK_FROM = "hotel_work_from";
    public  static final String HOTEL_WORK_TO = "hotel_work_to";
    public  static final String HOTEL_EXTRAS = "hotel_extras";
    public  static final String HOTEL_RATING = "hotel_rating";
    public  static final String HOTEL_WEBPAGE = "hotel_webpage";
    public  static final String HOTEL_FACEBOOK = "hotel_facebook";
    public  static final String HOTEL_DESCRIPTION = "hotel_description";
    public  static final String HOTEL_POLICIES = "hotel_policies";
    public  static final String HOTEL_CITY = "hotel_city";



    //IMAGES table columns
    public static final String IMAGE_ID = "image_id";
    public static final String IS_MAIN = "is_main";

    // CATEGORIES table columns
    public static final String NAME = "name";
    
    // CREATE statements
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + USERS + " ("
            + USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "
            + USERNAME + " VARCHAR(255) NOT NULL, "
            + EMAIL + " VARCHAR (255) NOT NULL, "
            + PASSWORD + " VARCHAR(255) NOT NULL, "
            + FIRST_NAME + " VARCHAR (255), "
            + LAST_NAME + " VARCHAR(255), "
            + COUNTRY + " VARCHAR(255), "
            + TELEPHONE + " VARCHAR(255), "
            + DATE_OF_BIRTH + " DATE, "
            + GENDER + " VARCHAR(255), "
            + SMOKING + " INTEGER, "
            + AVATAR + " BLOB NOT NULL"
            +") ";

    private static final String CREATE_COMPANIES_TABLE = "CREATE TABLE IF NOT EXISTS " +OFFERS + " ("
            + COMPANY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COMPANY_NAME + " INTEGER, "
            + PASSWORD + " VARCHAR(255) NOT NULL, "
            + EMAIL + " VARCHAR (255) NOT NULL, "
            //+ OFFICE_ADDRESS + " VARCHAR(255) NOT NULL, "
            + AVATAR + " BLOB NOT NULL, "
            //+ DESCRIPTION + " VARCHAR(2000) NOT NULL"
            +") ";

    private static final String CREATE_HOTEL_TABLE = "CREATE TABLE IF NOT EXISTS " +MESSAGES + " ("
            + HOTEL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HOTEL_NAME + " VARCHAR (255) NOT NULL, "
            + HOTEL_STARS + " INTEGER, "
            + HOTEL_ADDRESS + " VARCHAR(255) NOT NULL, "
            + HOTEL_X + " VARCHAR(255) NOT NULL, "
            + HOTEL_Y + " VARCHAR(255) NOT NULL, "
            + HOTEL_WORK_FROM + " VARCHAR(255) NOT NULL, "
            + HOTEL_WORK_TO + " VARCHAR(255) NOT NULL, "
            + HOTEL_EXTRAS + " VARCHAR(255) NOT NULL, "
            + HOTEL_RATING + " VARCHAR(255) NOT NULL, "
            + HOTEL_WEBPAGE + " VARCHAR(255) NOT NULL, "
            + HOTEL_FACEBOOK + " VARCHAR(255) NOT NULL, "
            + HOTEL_DESCRIPTION + " VARCHAR(255) NOT NULL, "
            + HOTEL_POLICIES + " VARCHAR(255) NOT NULL, "
            + HOTEL_CITY + " VARCHAR(255) NOT NULL"
            +") ";

    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE IF NOT EXISTS " +CATEGORIES + " ("
            + COMPANY_NAME +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " VARCHAR(255) NOT NULL "
            +") ";

    private static final String CREATE_IMAGES_TABLE =  "CREATE TABLE IF NOT EXISTS " +IMAGES + " ("
            + IMAGE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COMPANY_ID + " INTEGER, "
            + CONTENT + " BLOB NOT NULL, "
            + IS_MAIN + " BOOL NOT NULL, "
            + "FOREIGN KEY ("+ COMPANY_ID +") REFERENCES "+ OFFERS +"("+ COMPANY_ID +")"
            +") ";



    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_HOTEL_TABLE);
        db.execSQL(CREATE_IMAGES_TABLE);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        db.execSQL("DROP TABLE IF EXISTS " + OFFERS);
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + IMAGES);

        //create new tables
        onCreate(db);

    }

    //IMAGES
    //create image
    long createImage(byte[] array, int offerId){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COMPANY_ID, offerId);
        values.put(CONTENT, array);

        long id = db.insert(IMAGES, null, values);
        return id;
    }

    // closing database
    void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}