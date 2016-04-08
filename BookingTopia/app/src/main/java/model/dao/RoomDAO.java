package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

import model.CalendarHelper;
import model.Room;

/**
 * Created by user-17 on 4/3/16.
 */
public class RoomDAO implements IRoomDAO {

    private static RoomDAO instance;

    private DatabaseHelper mDb;

    private RoomDAO(Context context){this.mDb = DatabaseHelper.getInstance(context);}

    public static RoomDAO getInstance(Context context){
        if(instance == null)
            instance = new RoomDAO(context);

        return instance;
    }

    public long registerRoom(Room room){

        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(mDb.HOTEL_ID,           room.getHotelId());
        values.put(mDb.ROOM_PRICE_PER_DAY, room.getPricePerDay());
        values.put(mDb.ROOM_DESCRIPTION,   room.getDescription());
        values.put(mDb.ROOM_MAX_GUESTS,    room.getMaxGuests());
        values.put(mDb.ROOM_BEDS,          room.getBeds());
        values.put(mDb.ROOM_X,             room.getRoomSize()[0]);
        values.put(mDb.ROOM_Y,             room.getRoomSize()[1]);
        values.put(mDb.ROOM_EXTRAS,        room.getExtras());
        values.put(mDb.ROOM_SMOKING,       room.isSmoking() ? 1 : 0);

        long roomId = db.insert(mDb.ROOMS, null, values);

        for (byte[] image : room.getImages()) {
            Log.e("---image", "" + image.toString());
            ContentValues values2 = new ContentValues();

            values2.put(mDb.ROOM_ID, roomId);
            values2.put(mDb.CONTENT, image);

            db.insert(mDb.ROOM_IMAGES, null, values2);
        }

        db.close();

        return roomId;
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

    public Room getRoomById (long roomId) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.ROOMS
                + " WHERE " + mDb.ROOM_ID + " = \"" + roomId + "\"";

        Cursor c = db.rawQuery(selectQuery, null);

        Room room = null;

        if(c.moveToFirst()){
            long id = c.getLong(c.getColumnIndex(mDb.ROOM_ID));
            long hotelId = c.getLong(c.getColumnIndex(mDb.HOTEL_ID));
            double pricePerDay = c.getDouble(c.getColumnIndex(mDb.ROOM_PRICE_PER_DAY));
            String description = c.getString(c.getColumnIndex(mDb.ROOM_DESCRIPTION));
            int maxGuests = c.getInt(c.getColumnIndex(mDb.ROOM_MAX_GUESTS));
            String beds = c.getString(c.getColumnIndex(mDb.ROOM_BEDS));
            double x = c.getDouble(c.getColumnIndex(mDb.ROOM_X));
            double y = c.getDouble(c.getColumnIndex(mDb.ROOM_Y));

            String extras = c.getString(c.getColumnIndex(mDb.ROOM_EXTRAS));
            boolean smoking = (c.getInt(c.getColumnIndex(mDb.ROOM_SMOKING)) == 1) ? true : false;

            ArrayList<Calendar> dates = getTakenDatesPerRoom(roomId);

            ArrayList<byte[]> images = getImages(roomId);

            room = new Room(id,hotelId,pricePerDay,description,maxGuests,beds,x,y,extras,smoking,dates,images);

        }

        c.close();
        db.close();
        return room;
    }

    public ArrayList<Room> getAllRoomsByHotelID(long hotelID){
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.ROOMS
                + " WHERE " + mDb.HOTEL_ID + " = \"" + hotelID + "\"";

        Cursor c = db.rawQuery(selectQuery, null);

        ArrayList<Room> rooms = new ArrayList<>();

        if(c.moveToFirst()){
            do {
                Room room = null;

                long id = c.getLong(c.getColumnIndex(mDb.ROOM_ID));
                long hotelId = c.getLong(c.getColumnIndex(mDb.HOTEL_ID));
                double pricePerDay = c.getDouble(c.getColumnIndex(mDb.ROOM_PRICE_PER_DAY));
                String description = c.getString(c.getColumnIndex(mDb.ROOM_DESCRIPTION));
                int maxGuests = c.getInt(c.getColumnIndex(mDb.ROOM_MAX_GUESTS));
                String beds = c.getString(c.getColumnIndex(mDb.ROOM_BEDS));
                double x = c.getDouble(c.getColumnIndex(mDb.ROOM_X));
                double y = c.getDouble(c.getColumnIndex(mDb.ROOM_Y));

                String extras = c.getString(c.getColumnIndex(mDb.ROOM_EXTRAS));
                boolean smoking = (c.getInt(c.getColumnIndex(mDb.ROOM_SMOKING)) == 1) ? true : false;

                ArrayList<Calendar> dates = getTakenDatesPerRoom(id);

                ArrayList<byte[]> images = getImages(id);

                room = new Room(id, hotelId, pricePerDay, description, maxGuests, beds, x, y, extras, smoking, dates, images);

                rooms.add(room);
            }while (c.moveToNext());
        }

        c.close();
        db.close();
        return rooms;
    }

    public ArrayList<Room> getAllRoomsByHotelWithAvailableDates(long hotelID){
        SQLiteDatabase db = mDb.getReadableDatabase();

//        Calendar calendar  = CalendarHelper.fromDBCal;
//        Calendar calendar2 = CalendarHelper.toDBCal;

//        String dateFrom = calendar.getInstance().get(Calendar.YEAR) + "-"
//                + calendar.getInstance().get(Calendar.MONTH) + "-"
//                + calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//
//        String dateTo = calendar2.getInstance().get(Calendar.YEAR) + "-"
//                + calendar2.getInstance().get(Calendar.MONTH) + "-"
//                + calendar2.getInstance().get(Calendar.DAY_OF_MONTH);

        String datesSelect = "SELECT " + mDb.ROOM_ID + " FROM " + mDb.TAKEN_DATES + " WHERE " + mDb.DATE + " BETWEEN " + 5 + " AND " + 5;

        Cursor c = db.rawQuery(datesSelect, null);

        TreeSet<Long> takenIDs = new TreeSet<>();

        if(c.moveToFirst()){
            do{
                takenIDs.add(c.getLong(c.getColumnIndex(mDb.ROOM_ID)));
            } while (c.moveToNext());
        }

        Log.e("Vurnati zaeti dati" , takenIDs.toString());

        //select all room IDs by hotel

        String idSelects = "SELECT " + mDb.ROOM_ID + " FROM " + mDb.ROOMS + " WHERE " + mDb.HOTEL_ID + " = \"" + hotelID + "\"";

        Cursor c2 = db.rawQuery(idSelects, null);

        TreeSet<Long> allIDs = new TreeSet<>();

        if(c2.moveToFirst()){
            do{
                allIDs.add(c.getLong(c.getColumnIndex(mDb.ROOM_ID)));
            } while (c2.moveToNext());
        }

        for(long l : takenIDs){
            allIDs.remove(l);
        }

        ArrayList<Room> rooms = new ArrayList<>();

        for(long l : allIDs){
            rooms.add(getRoomById(l));
        }

        return rooms;
    }

    private ArrayList<Calendar> getTakenDatesPerRoom(long roomID){

        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.TAKEN_DATES
                + " WHERE " + mDb.ROOM_ID + " = \"" + roomID + "\"";

        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<Calendar> dates = new ArrayList<>();

        if(c.moveToFirst()) {
            do {
                String date = c.getString(c.getColumnIndex(mDb.DATE));

                DateFormat formater = new SimpleDateFormat("yy-MM-dd");
                Date date2 = null;
                try {
                    date2 = formater.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(date2);

                dates.add(cal);
            }while (c.moveToNext());
        }

        c.close();
        db.close();
        return dates;
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

        c.close();
        db.close();
        return images;
    }


    private long getHotelIdByRoomId(long roomID){

        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.ROOMS
                + " WHERE " + mDb.ROOM_ID + " = \"" + roomID + "\"";

        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<Calendar> dates = new ArrayList<>();
        long hotelId =0;
        if(c.moveToFirst()) {
            hotelId = c.getLong(c.getColumnIndex(mDb.HOTEL_ID));
        }

        c.close();
        db.close();
        return hotelId;
    }

    @Override
    public void registerTakenDate(Room room, long reservationID) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        LocalDate fromDate = CalendarHelper.fromDate;
        LocalDate toDate   = CalendarHelper.toDate.plusDays(1);

        while(fromDate.isBefore(toDate)){
            ContentValues values2 = new ContentValues();

            values2.put(mDb.BOOKING_ID, reservationID);
            values2.put(mDb.ROOM_ID, room.getRoomId());
            values2.put(mDb.DATE, fromDate.toString());
            db.insert(mDb.TAKEN_DATES, null, values2);
            fromDate = fromDate.plusDays(1);
        }

        db.close();
    }
}
