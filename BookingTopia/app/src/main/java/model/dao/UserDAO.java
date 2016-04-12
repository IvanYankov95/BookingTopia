package model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.User;

/**
 * Created by user-17 on 4/2/16.
 */
public class UserDAO implements IUserDAO{

    private static UserDAO instance;

    private DatabaseHelper mDb;

    private UserDAO(Context context){this.mDb = DatabaseHelper.getInstance(context);}

    public static UserDAO getInstance(Context context){
        if(instance == null)
            instance = new UserDAO(context);

        return instance;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = mDb.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mDb.USERS
                + " WHERE " + mDb.USERNAME + " = \"" + username + "\"";

        Cursor c = db.rawQuery(selectQuery, null);


        if (c != null && c.moveToFirst()) {
            db.close();
            c.close();
            return true;
        }
        else{
            db.close();
            c.close();
            return false;
        }
    }

    public boolean checkUserEmail(String email) {
        SQLiteDatabase db = mDb.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mDb.USERS
                + " WHERE " + mDb.EMAIL + " = \"" + email + "\"";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {
            db.close();
            return true;
        }
        else{
            db.close();
            return false;
        }
    }

    public long registerUser(User user){
        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        LocalDate localDate = user.getDateOfBirth();
        String date = localDate.toString();

        values.put(mDb.USERNAME, user.getUsername());
        values.put(mDb.EMAIL,    user.getEmail());
        values.put(mDb.PASSWORD, user.getPassword());
        values.put(mDb.USER_NAME, user.getNames());
        values.put(mDb.COUNTRY, user.getCountry());
        values.put(mDb.TELEPHONE, user.getMobilePhone());
        values.put(mDb.DATE_OF_BIRTH, date);
        values.put(mDb.GENDER, user.getGender());
        values.put(mDb.SMOKING, user.isSmoking() ? 1 : 0);
        values.put(mDb.AVATAR, user.getAvatar());


        long userId = db.insert(mDb.USERS, null, values);
        db.close();

        return userId;
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = mDb.getWritableDatabase();
        db.delete(mDb.USERS, mDb.USERNAME + " = ?",
                new String[]{user.getUsername()});

        db.close();
    }

    public long updateUser(User user) {
        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        LocalDate localDate = user.getDateOfBirth();
        String date = localDate.toString();

        values.put(mDb.USERNAME, user.getUsername());
        values.put(mDb.EMAIL,    user.getEmail());
        values.put(mDb.PASSWORD, user.getPassword());
        values.put(mDb.USER_NAME, user.getNames());
        values.put(mDb.COUNTRY, user.getCountry());
        values.put(mDb.TELEPHONE, user.getMobilePhone());
        values.put(mDb.DATE_OF_BIRTH, date);
        values.put(mDb.GENDER, user.getGender());
        values.put(mDb.SMOKING, user.isSmoking() ? 1 : 0);
        values.put(mDb.AVATAR, user.getAvatar());

        long userId = db.update(mDb.USERS, values, mDb.USERNAME + " = ? ", new String[]{user.getUsername()});
        db.close();
        return userId;
    }

    public User login (String email, String password) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.USERS
                + " WHERE " + mDb.EMAIL + " = \"" + email
                + "\" AND " + mDb.PASSWORD + " = \"" + password + "\"";
        Cursor c = db.rawQuery(selectQuery, null);

        User user = null;
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

            LocalDate ld = new LocalDate(date2);
            String gender = c.getString(c.getColumnIndex(mDb.GENDER));
            boolean smoking = (c.getInt(c.getColumnIndex(mDb.SMOKING)) == 1) ? true : false;
            byte[] avatar = c.getBlob(c.getColumnIndex(mDb.AVATAR));

            user = new User(id, uname, upassword, avatar , email, name, phone, ld, gender, country, smoking);
        }

        c.close();
        db.close();
        return user;
    }

    public byte[] getAvatar(long id){
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.USERS
                + " WHERE " + mDb.USER_ID + " = \"" + id + "\"";
        Cursor c = db.rawQuery(selectQuery, null);

        byte[] avatar = null;
        if(c.moveToFirst()){
            avatar = c.getBlob(c.getColumnIndex(mDb.AVATAR));
        }

        c.close();
        db.close();
        return avatar;
    }

    public String getName(long id){
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.USERS
                + " WHERE " + mDb.USER_ID + " = \"" + id + "\"";
        Cursor c = db.rawQuery(selectQuery, null);

        String name = null;
        if(c.moveToFirst()){
            name = c.getString(c.getColumnIndex(mDb.USERNAME));
        }

        c.close();
        db.close();
        return name;
    }

    @Override
    public User getUserById(long id) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.USERS
                + " WHERE " + mDb.USER_ID + " = \"" + id
                + "\"";
        Cursor c = db.rawQuery(selectQuery, null);

        User user = null;
        if(c.moveToFirst()){
            String uname = c.getString(c.getColumnIndex(mDb.USERNAME));
            String uemail = c.getString(c.getColumnIndex(mDb.EMAIL));
            String upassword = c.getString(c.getColumnIndex(mDb.PASSWORD));
            String name = c.getString(c.getColumnIndex(mDb.USER_NAME));
            String country = c.getString(c.getColumnIndex(mDb.COUNTRY));
            String phone = c.getString(c.getColumnIndex(mDb.TELEPHONE));
            String date = c.getString(c.getColumnIndex(mDb.DATE_OF_BIRTH));
            String email = c.getString(c.getColumnIndex(mDb.EMAIL));
            DateFormat formater = new SimpleDateFormat("yy-MM-dd");
            Date date2= null;
            try {
                date2 = formater.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            LocalDate ld = new LocalDate(date2);
            String gender = c.getString(c.getColumnIndex(mDb.GENDER));
            boolean smoking = (c.getInt(c.getColumnIndex(mDb.SMOKING)) == 1) ? true : false;
            byte[] avatar = c.getBlob(c.getColumnIndex(mDb.AVATAR));

            user = new User(id, uname, upassword, avatar , email, name, phone, ld, gender, country, smoking);
        }

        c.close();
        db.close();
        return user;
    }
}
