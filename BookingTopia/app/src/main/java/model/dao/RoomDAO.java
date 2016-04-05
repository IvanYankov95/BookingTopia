package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

import model.Hotel;
import model.Room;

/**
 * Created by user-17 on 4/3/16.
 */
public class RoomDAO {

    private static RoomDAO instance;

    private DatabaseHelper mDb;

    private RoomDAO(Context context){this.mDb = DatabaseHelper.getInstance(context);}

    public static RoomDAO getInstance(Context context){
        if(instance == null)
            instance = new RoomDAO(context);

        return instance;
    }

    public long registerRoom(Hotel hotel, Room room){

        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(mDb.ROOM_ID,            room.getRoomId());
        values.put(mDb.HOTEL_ID,           hotel.getHotelId());
        values.put(mDb.ROOM_PRICE_PER_DAY, room.getPricePerDay());
        values.put(mDb.ROOM_DESCRIPTION,   room.getDescription());
        values.put(mDb.ROOM_MAX_GUESTS,    room.getMaxGuests());
        values.put(mDb.ROOM_BEDS,          room.getBeds());
        values.put(mDb.ROOM_X,             room.getRoomSize()[0]);
        values.put(mDb.ROOM_Y,             room.getRoomSize()[1]);
        values.put(mDb.ROOM_EXTRAS,        room.getExtras());
        values.put(mDb.ROOM_SMOKING,       room.isSmoking() ? 1 : 0);

        long hotelId = db.insert(mDb.ROOMS, null, values);
        db.close();

        return hotelId;
    }

    public void deleteRoom(Room room){
        SQLiteDatabase db = mDb.getWritableDatabase();
        db.delete(mDb.ROOMS, mDb.ROOM_ID + " = ?",
                new String[]{String.valueOf(room.getRoomId())});

        db.close();
    }



    public long changeRoomData(Room room){
        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(mDb.ROOM_ID,            room.getRoomId());
        values.put(mDb.HOTEL_ID,           room.getHotelId());
        values.put(mDb.ROOM_PRICE_PER_DAY, room.getPricePerDay());
        values.put(mDb.ROOM_DESCRIPTION,   room.getDescription());
        values.put(mDb.ROOM_MAX_GUESTS,    room.getMaxGuests());
        values.put(mDb.ROOM_BEDS,          room.getBeds());
        values.put(mDb.ROOM_X,             room.getRoomSize()[0]);
        values.put(mDb.ROOM_Y,             room.getRoomSize()[1]);
        values.put(mDb.ROOM_EXTRAS,        room.getExtras());
        values.put(mDb.ROOM_SMOKING,       room.isSmoking() ? 1 : 0);

        long companyId = db.update(mDb.ROOMS, values, mDb.ROOM_ID + " = ? ", new String[]{String.valueOf(room.getRoomId())});
        db.close();

        return companyId;
    }

    public Room getRoom (long roomId) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.ROOMS
                + " WHERE " + mDb.ROOM_ID + " = \"" + roomId + "\"";

        Cursor c = db.rawQuery(selectQuery, null);

        Room room = null;

        if(c.moveToFirst()){
            long id = c.getLong(c.getColumnIndex(mDb.ROOM_ID));
            String hotelId = c.getString(c.getColumnIndex(mDb.HOTEL_ID));
            String pricePerDay = c.getString(c.getColumnIndex(mDb.ROOM_PRICE_PER_DAY));
            String description = c.getString(c.getColumnIndex(mDb.ROOM_DESCRIPTION));
            String maxGuests = c.getString(c.getColumnIndex(mDb.ROOM_MAX_GUESTS));
            String beds = c.getString(c.getColumnIndex(mDb.ROOM_BEDS));
            String x = c.getString(c.getColumnIndex(mDb.ROOM_X));
            String y = c.getString(c.getColumnIndex(mDb.ROOM_Y));
            String extras = c.getString(c.getColumnIndex(mDb.ROOM_EXTRAS));
            boolean smoking = (c.getInt(c.getColumnIndex(mDb.ROOM_SMOKING)) == 1) ? true : false;

            ArrayList<byte[]> images = getImages(room.getRoomId());

            //hotel = new User(id, uname, upassword, avatar , email, uname, phone, cal, gender, country, smoking);
        }

        c.close();
        db.close();
        return room;
    }

    private ArrayList<byte[]> getImages(long roomId){
        ArrayList<byte[]> images = new ArrayList<byte[]>();

        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT "+ mDb.CONTENT +" FROM " + mDb.ROOM_IMAGES
                + " WHERE " + mDb.ROOM_ID + " = \"" + roomId + "\"";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                byte[] image = c.getBlob(c.getColumnIndex(mDb.CONTENT));
                images.add(image);
            }
            while (c.moveToNext());
        }

        return images;
    }


}
