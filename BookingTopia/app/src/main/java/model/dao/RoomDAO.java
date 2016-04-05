package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

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
}
