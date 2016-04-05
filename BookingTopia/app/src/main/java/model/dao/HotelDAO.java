package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.Company;
import model.Hotel;
import model.User;

/**
 * Created by user-17 on 4/3/16.
 */
public class HotelDAO {

    private static HotelDAO instance;

    private DatabaseHelper mDb;

    private HotelDAO(Context context){this.mDb = DatabaseHelper.getInstance(context);}

    public static HotelDAO getInstance(Context context){
        if(instance == null)
            instance = new HotelDAO(context);

        return instance;
    }

    public long registerHotel(Hotel hotel){

        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        Calendar calendar = hotel.getWorkFrom();
        String date = calendar.getInstance().get(Calendar.YEAR) + "-"
                + calendar.getInstance().get(Calendar.MONTH) + "-"
                + calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        Calendar calendar2 = hotel.getWorkTo();
        String date2 = calendar.getInstance().get(Calendar.YEAR) + "-"
                + calendar.getInstance().get(Calendar.MONTH) + "-"
                + calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        values.put(mDb.HOTEL_ID,            hotel.getHotelId());
        values.put(mDb.COMPANY_ID,          hotel.getCompanyId());
        values.put(mDb.HOTEL_NAME,          hotel.getName());
        values.put(mDb.HOTEL_STARS,         hotel.getStars());
        values.put(mDb.HOTEL_ADDRESS,       hotel.getAddress());
        values.put(mDb.HOTEL_WORK_FROM,     date);
        values.put(mDb.HOTEL_WORK_TO,       date2);
        values.put(mDb.HOTEL_EXTRAS,        hotel.getExtras());
        values.put(mDb.HOTEL_RATING,        hotel.getRating());
        values.put(mDb.HOTEL_WEBPAGE,       hotel.getWebpage());
        values.put(mDb.HOTEL_FACEBOOK,      hotel.getLinkToFacebook());
        values.put(mDb.HOTEL_DESCRIPTION,   hotel.getDescription());
        values.put(mDb.HOTEL_POLICIES,      hotel.getPolicies());
        values.put(mDb.HOTEL_CITY,          hotel.getCity());

        long hotelId = db.insert(mDb.HOTELS, null, values);
        db.close();

        return hotelId;
    }

    public void deleteHotel(Hotel hotel){
        SQLiteDatabase db = mDb.getWritableDatabase();
        db.delete(mDb.HOTELS, mDb.HOTEL_ID + " = ?",
                new String[]{String.valueOf(hotel.getHotelId())});

        db.close();
    }

    public long changeCompanyData(Hotel hotel){
        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        Calendar calendar = hotel.getWorkFrom();
        String date = calendar.getInstance().get(Calendar.YEAR) + "-"
                + calendar.getInstance().get(Calendar.MONTH) + "-"
                + calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        Calendar calendar2 = hotel.getWorkTo();
        String date2 = calendar.getInstance().get(Calendar.YEAR) + "-"
                + calendar.getInstance().get(Calendar.MONTH) + "-"
                + calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        values.put(mDb.HOTEL_ID,            hotel.getHotelId());
        values.put(mDb.COMPANY_ID,          hotel.getCompanyId());
        values.put(mDb.HOTEL_NAME,          hotel.getName());
        values.put(mDb.HOTEL_STARS,         hotel.getStars());
        values.put(mDb.HOTEL_ADDRESS,       hotel.getAddress());
        values.put(mDb.HOTEL_WORK_FROM,     date);
        values.put(mDb.HOTEL_WORK_TO,       date2);
        values.put(mDb.HOTEL_EXTRAS,        hotel.getExtras());
        values.put(mDb.HOTEL_RATING,        hotel.getRating());
        values.put(mDb.HOTEL_WEBPAGE,       hotel.getWebpage());
        values.put(mDb.HOTEL_FACEBOOK,      hotel.getLinkToFacebook());
        values.put(mDb.HOTEL_DESCRIPTION,   hotel.getDescription());
        values.put(mDb.HOTEL_POLICIES,      hotel.getPolicies());
        values.put(mDb.HOTEL_CITY,          hotel.getCity());

        long companyId = db.update(mDb.HOTELS, values, mDb.HOTEL_ID + " = ? ", new String[]{String.valueOf(hotel.getHotelId())});
        db.close();

        return companyId;
    }

    public Hotel login (long hotelId) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.USERS
                + " WHERE " + mDb.HOTEL_ID + " = \"" + hotelId + "\"";

        Cursor c = db.rawQuery(selectQuery, null);


        Hotel hotel = null;

        if(c.moveToFirst()){
            long id = c.getLong(c.getColumnIndex(mDb.USER_ID));
            String uname = c.getString(c.getColumnIndex(mDb.USERNAME));
            String uemail = c.getString(c.getColumnIndex(mDb.EMAIL));
            String upassword = c.getString(c.getColumnIndex(mDb.PASSWORD));
            String name = c.getString(c.getColumnIndex(mDb.USER_NAME));
            String country = c.getString(c.getColumnIndex(mDb.COUNTRY));
            String phone = c.getString(c.getColumnIndex(mDb.TELEPHONE));
            String date = c.getString(c.getColumnIndex(mDb.DATE_OF_BIRTH));
            DateFormat formater = new SimpleDateFormat("yy-MM-dd");
            Date date2= null;
            try {
                date2 = formater.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(date2);
            String gender = c.getString(c.getColumnIndex(mDb.GENDER));
            boolean smoking = (c.getInt(c.getColumnIndex(mDb.SMOKING)) == 1) ? true : false;
            byte[] avatar = c.getBlob(c.getColumnIndex(mDb.AVATAR));

            //hotel = new User(id, uname, upassword, avatar , email, uname, phone, cal, gender, country, smoking);
        }

        c.close();
        db.close();
        return hotel;
    }

}
