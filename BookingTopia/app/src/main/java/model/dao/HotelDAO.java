package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.Hotel;
import model.Review;
import model.Room;

/**
 * Created by user-17 on 4/3/16.
 */
public class HotelDAO implements IHotelDAO {

    private static HotelDAO instance;
    private static RoomDAO roomInstance;
    private static ReviewDAO reviewInstance;


    private DatabaseHelper mDb;

    private HotelDAO(Context context) {
        this.mDb = DatabaseHelper.getInstance(context);
    }

    public static HotelDAO getInstance(Context context) {
        if (instance == null)
            instance = new HotelDAO(context);

        roomInstance = RoomDAO.getInstance(context);
        reviewInstance = ReviewDAO.getInstance(context);

        return instance;
    }

    public long registerHotel(Hotel hotel) {

        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        Calendar calendar = hotel.getWorkFrom();
        String date = calendar.getInstance().get(Calendar.YEAR) + "-"
                + calendar.getInstance().get(Calendar.MONTH) + "-"
                + calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        Calendar calendar2 = hotel.getWorkTo();
        String date2 = calendar2.getInstance().get(Calendar.YEAR) + "-"
                + calendar2.getInstance().get(Calendar.MONTH) + "-"
                + calendar2.getInstance().get(Calendar.DAY_OF_MONTH);

        values.put(mDb.COMPANY_ID, hotel.getCompanyId());
        values.put(mDb.HOTEL_NAME, hotel.getName());
        values.put(mDb.HOTEL_STARS, hotel.getStars());
        values.put(mDb.HOTEL_ADDRESS, hotel.getAddress());
        values.put(mDb.HOTEL_WORK_FROM, date);
        values.put(mDb.HOTEL_WORK_TO, date2);
        values.put(mDb.HOTEL_EXTRAS, hotel.getExtras());
        values.put(mDb.HOTEL_RATING, hotel.getRating());
        values.put(mDb.HOTEL_X, hotel.getxCoordinate());
        values.put(mDb.HOTEL_Y, hotel.getyCoordinate());
        values.put(mDb.HOTEL_WEBPAGE, hotel.getWebpage());
        values.put(mDb.HOTEL_FACEBOOK, hotel.getLinkToFacebook());
        values.put(mDb.HOTEL_DESCRIPTION, hotel.getDescription());
        values.put(mDb.HOTEL_POLICIES, hotel.getPolicies());
        values.put(mDb.HOTEL_CITY, hotel.getCity());

        long hotelId = db.insert(mDb.HOTELS, null, values);


        for (byte[] image : hotel.getImages()) {
            Log.e("---image", "" + image.toString());
            ContentValues values2 = new ContentValues();

            values2.put(mDb.HOTEL_ID, hotelId);
            values2.put(mDb.CONTENT, image);

            db.insert(mDb.HOTEL_IMAGES, null, values2);
        }
        db.close();
        return hotelId;
    }


    public void deleteHotel(Hotel hotel) {
        SQLiteDatabase db = mDb.getWritableDatabase();
        db.delete(mDb.HOTELS, mDb.HOTEL_ID + " = ?",
                new String[]{String.valueOf(hotel.getHotelId())});

        db.close();
    }

    public long changeCompanyData(Hotel hotel) {
        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        Calendar calendar = hotel.getWorkFrom();
        String date = calendar.getInstance().get(Calendar.YEAR) + "-"
                + calendar.getInstance().get(Calendar.MONTH) + "-"
                + calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        Calendar calendar2 = hotel.getWorkTo();
        String date2 = calendar2.getInstance().get(Calendar.YEAR) + "-"
                + calendar2.getInstance().get(Calendar.MONTH) + "-"
                + calendar2.getInstance().get(Calendar.DAY_OF_MONTH);

        values.put(mDb.HOTEL_ID, hotel.getHotelId());
        values.put(mDb.COMPANY_ID, hotel.getCompanyId());
        values.put(mDb.HOTEL_NAME, hotel.getName());
        values.put(mDb.HOTEL_STARS, hotel.getStars());
        values.put(mDb.HOTEL_ADDRESS, hotel.getAddress());
        values.put(mDb.HOTEL_COORDINATESx, hotel.getxCoordinate());
        values.put(mDb.HOTEL_COORDINATESy, hotel.getyCoordinate());

        values.put(mDb.HOTEL_WORK_FROM, date);
        values.put(mDb.HOTEL_WORK_TO, date2);
        values.put(mDb.HOTEL_EXTRAS, hotel.getExtras());
        values.put(mDb.HOTEL_RATING, hotel.getRating());
        values.put(mDb.HOTEL_WEBPAGE, hotel.getWebpage());
        values.put(mDb.HOTEL_FACEBOOK, hotel.getLinkToFacebook());
        values.put(mDb.HOTEL_DESCRIPTION, hotel.getDescription());
        values.put(mDb.HOTEL_POLICIES, hotel.getPolicies());
        values.put(mDb.HOTEL_CITY, hotel.getCity());

        long companyId = db.update(mDb.HOTELS, values, mDb.HOTEL_ID + " = ? ", new String[]{String.valueOf(hotel.getHotelId())});
        db.close();

        return companyId;
    }

    public Hotel getHotel(long hotelId) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.HOTELS
                + " WHERE " + mDb.HOTEL_ID + " = \"" + hotelId + "\"";

        Cursor c = db.rawQuery(selectQuery, null);

        Hotel hotel = null;


        if (c.moveToFirst()) {
            try {

                long id = c.getLong(c.getColumnIndex(mDb.HOTEL_ID));
                long companyId = c.getLong(c.getColumnIndex(mDb.COMPANY_ID));
                String name = c.getString(c.getColumnIndex(mDb.HOTEL_NAME));
                int stars = c.getInt(c.getColumnIndex(mDb.HOTEL_STARS));
                String address = c.getString(c.getColumnIndex(mDb.HOTEL_ADDRESS));

                String workFrom = c.getString(c.getColumnIndex(mDb.HOTEL_WORK_FROM));
                DateFormat formater = new SimpleDateFormat("yy-MM-dd");
                Date date2 = formater.parse(workFrom);
                Calendar calWorkFrom = Calendar.getInstance();
                calWorkFrom.setTime(date2);

                String workTo = c.getString(c.getColumnIndex(mDb.HOTEL_WORK_FROM));
                Date date3 = formater.parse(workTo);
                Calendar calWorkTo = Calendar.getInstance();
                calWorkTo.setTime(date3);

                String extras = c.getString(c.getColumnIndex(mDb.HOTEL_EXTRAS));
                double rating = c.getDouble(c.getColumnIndex(mDb.HOTEL_RATING));
                double xCoordinate = c.getDouble(c.getColumnIndex(mDb.HOTEL_X));
                double yCoordinate = c.getDouble(c.getColumnIndex(mDb.HOTEL_Y));
                String webPage = c.getString(c.getColumnIndex(mDb.HOTEL_WEBPAGE));
                String linkToFacebook = c.getString(c.getColumnIndex(mDb.HOTEL_FACEBOOK));
                String description = c.getString(c.getColumnIndex(mDb.HOTEL_DESCRIPTION));
                String policies = c.getString(c.getColumnIndex(mDb.HOTEL_POLICIES));
                String city = c.getString(c.getColumnIndex(mDb.HOTEL_CITY));

                ArrayList<Room> rooms = roomInstance.getAllRoomsByHotelID(hotelId);
                ArrayList<Review> reviews = reviewInstance.getAllReviewsByHotelId(hotelId);

                ArrayList<byte[]> images = this.getHotelImages(hotelId);

                hotel = new Hotel(hotelId, companyId, name, stars, address,
                        xCoordinate, yCoordinate, calWorkFrom, calWorkTo, extras, rating, webPage, linkToFacebook, description, policies,
                        rooms, images, reviews, city);


            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        c.close();
        db.close();
        return hotel;

    }

    public ArrayList<Hotel> getAllHotelsByCompanyID(long companyId) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.HOTELS
                + " WHERE " + mDb.COMPANY_ID + " = \"" + companyId + "\"";

        Cursor c = db.rawQuery(selectQuery, null);

        ArrayList<Hotel> hotels = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                long hotelId = c.getLong(c.getColumnIndex(mDb.HOTEL_ID));
                hotels.add(getHotel(hotelId));
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        return hotels;

    }


    private ArrayList<byte[]> getHotelImages(long hotelId) {
        ArrayList<byte[]> images = new ArrayList<byte[]>();

        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT " + mDb.CONTENT + " FROM " + mDb.HOTEL_IMAGES
                + " WHERE " + mDb.HOTEL_ID + " = \"" + hotelId + "\"";

        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            do {
                byte[] image = c.getBlob(c.getColumnIndex(mDb.CONTENT));
                images.add(image);

            }
            while (c.moveToNext());
        }

        c.close();
        db.close();
        return images;
    }
}
