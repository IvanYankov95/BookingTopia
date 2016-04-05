package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Book;
import model.Room;
import model.User;

/**
 * Created by user-17 on 4/2/16.
 */
public class BookDAO {

    private DatabaseHelper mDb;


    private BookDAO(Context context) {
        this.mDb = DatabaseHelper.getInstance(context);
    }

    public long addBook(Book book) {

        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(mDb.ROOM_ID, book.getRoomID());
        values.put(mDb.USER_ID, book.getUserID());

        long bookId = db.insert(mDb.BOOKINGS, null, values);
        db.close();

        return bookId;
    }

    public void removeBook(Book book) {
        SQLiteDatabase db = mDb.getWritableDatabase();
        db.delete(mDb.BOOKINGS, mDb.BOOKING_ID + " = ?",
                new String[]{String.valueOf(book.getBookID())});
        db.close();
    }

    public Book getBooksByUser(User user) {

        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.BOOKINGS
                + " WHERE " + mDb.USER_ID + " = \"" + user.getUserId() + "\" ";

        Cursor c = db.rawQuery(selectQuery, null);

        Book book = null;

        if (c.moveToFirst()) {
            long id = c.getLong(c.getColumnIndex(mDb.BOOKING_ID));
            long room_id = c.getLong(c.getColumnIndex(mDb.ROOM_ID));
            long user_id = c.getLong(c.getColumnIndex(mDb.USER_ID));

            book = new Book(id, room_id, user_id);
        }

        c.close();
        db.close();
        return book;
    }

    public Book getBooksByID(long bookID) {

        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.BOOKINGS
                + " WHERE " + mDb.BOOKING_ID + " = \"" + bookID + "\" ";

        Cursor c = db.rawQuery(selectQuery, null);
        Book book = null;

        if (c.moveToFirst()) {
            long id = c.getLong(c.getColumnIndex(mDb.BOOKING_ID));
            long room_id = c.getLong(c.getColumnIndex(mDb.ROOM_ID));
            long user_id = c.getLong(c.getColumnIndex(mDb.USER_ID));

            book = new Book(id, room_id, user_id);
        }

        c.close();
        db.close();
        return book;
    }

}
