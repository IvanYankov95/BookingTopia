package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

import model.Company;
import model.Hotel;

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

}
