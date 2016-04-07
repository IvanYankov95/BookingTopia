package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Reservation;
import model.User;

/**
 * Created by user-17 on 4/2/16.
 */
public class ReservationDAO implements IReservationDAO {

    private DatabaseHelper mDb;

    private static ReservationDAO instance;

    public static ReservationDAO getInstance(Context context){
        if(instance == null)
            instance = new ReservationDAO(context);

        return instance;
    }


    private ReservationDAO(Context context) {
        this.mDb = DatabaseHelper.getInstance(context);
    }

    public long reserve(Reservation reservation) {

        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(mDb.ROOM_ID, reservation.getRoomID());
        values.put(mDb.USER_ID, reservation.getUserID());

        long bookId = db.insert(mDb.BOOKINGS, null, values);
        db.close();

        return bookId;
    }

    public void removeReservation(Reservation reservation) {
        SQLiteDatabase db = mDb.getWritableDatabase();
        db.delete(mDb.BOOKINGS, mDb.BOOKING_ID + " = ?",
                new String[]{String.valueOf(reservation.getBookID())});
        db.close();
    }

    public Reservation getReservationsByUser(User user) {

        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.BOOKINGS
                + " WHERE " + mDb.USER_ID + " = \"" + user.getUserId() + "\" ";

        Cursor c = db.rawQuery(selectQuery, null);

        Reservation reservation = null;

        if (c.moveToFirst()) {
            long id = c.getLong(c.getColumnIndex(mDb.BOOKING_ID));
            long room_id = c.getLong(c.getColumnIndex(mDb.ROOM_ID));
            long user_id = c.getLong(c.getColumnIndex(mDb.USER_ID));

            reservation = new Reservation(id, room_id, user_id);
        }

        c.close();
        db.close();
        return reservation;
    }

    public Reservation getReservationsByID(long bookID) {

        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.BOOKINGS
                + " WHERE " + mDb.BOOKING_ID + " = \"" + bookID + "\" ";

        Cursor c = db.rawQuery(selectQuery, null);
        Reservation reservation = null;

        if (c.moveToFirst()) {
            long id = c.getLong(c.getColumnIndex(mDb.BOOKING_ID));
            long room_id = c.getLong(c.getColumnIndex(mDb.ROOM_ID));
            long user_id = c.getLong(c.getColumnIndex(mDb.USER_ID));

            reservation = new Reservation(id, room_id, user_id);
        }

        c.close();
        db.close();
        return reservation;
    }

}
