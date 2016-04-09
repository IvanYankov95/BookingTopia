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
import model.User;

/**
 * Created by user-17 on 4/2/16.
 */
public class CompanyDAO implements ICompanyDAO{

    private static CompanyDAO instance;

    private DatabaseHelper mDb;

    private CompanyDAO(Context context){this.mDb = DatabaseHelper.getInstance(context);}

    public static CompanyDAO getInstance(Context context){
        if(instance == null)
            instance = new CompanyDAO(context);

        return instance;
    }

    public long registerCompany(Company company){

        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(mDb.COMPANY_NAME, company.getName());
        values.put(mDb.PASSWORD, company.getPassword());
        values.put(mDb.EMAIL, company.getEmail());
        values.put(mDb.COMPANY_OFFICE_ADDRESS, company.getAddress());
        values.put(mDb.AVATAR, company.getAvatar());
        values.put(mDb.TELEPHONE, company.getPhone());
        values.put(mDb.COMPANY_DESCRIPTION, company.getDescription());

        long companyId = db.insert(mDb.COMPANIES, null, values);
        db.close();

        return companyId;
    }

    public void deleteCompany(Company company){
        SQLiteDatabase db = mDb.getWritableDatabase();
        db.delete(mDb.COMPANIES, mDb.EMAIL + " = ?",
                new String[]{company.getEmail()});

        db.close();
    }

    public long changeCompanyData(Company company){
        SQLiteDatabase db = mDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(mDb.COMPANY_NAME, company.getName());
        values.put(mDb.PASSWORD, company.getPassword());
        values.put(mDb.EMAIL, company.getEmail());
        values.put(mDb.COMPANY_OFFICE_ADDRESS, company.getAddress());
        values.put(mDb.AVATAR, company.getAvatar());
        values.put(mDb.TELEPHONE, company.getPhone());
        values.put(mDb.COMPANY_DESCRIPTION, company.getDescription());

        long companyId = db.update(mDb.COMPANIES, values, mDb.EMAIL + " = ? ", new String[]{company.getEmail()});
        db.close();

        return companyId;
    }

    public boolean checkCompanyName(String name) {
        SQLiteDatabase db = mDb.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mDb.COMPANIES
                + " WHERE " + mDb.COMPANY_NAME + " = \"" + name + "\"";

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
        String selectQuery = "SELECT * FROM " + mDb.COMPANIES
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


    public Company login (String email, String password) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.COMPANIES
                + " WHERE " + mDb.EMAIL + " = \"" + email
                + "\" AND " + mDb.PASSWORD + " = \"" + password + "\"";

        Cursor c = db.rawQuery(selectQuery, null);


        Company company = null;

        if(c.moveToFirst()){
            long id = c.getLong(c.getColumnIndex(mDb.COMPANY_ID));
            String name = c.getString(c.getColumnIndex(mDb.COMPANY_NAME));
            String companyPassword = c.getString(c.getColumnIndex(mDb.PASSWORD));
            String companyEmail = c.getString(c.getColumnIndex(mDb.EMAIL));
            String address = c.getString(c.getColumnIndex(mDb.COMPANY_OFFICE_ADDRESS));
            byte[] avatar = c.getBlob(c.getColumnIndex(mDb.AVATAR));
            String phone = c.getString(c.getColumnIndex(mDb.TELEPHONE));
            String description = c.getString(c.getColumnIndex(mDb.COMPANY_DESCRIPTION));

            company = new Company(id, name, companyEmail, companyPassword, address , avatar, phone, description);
        }

        c.close();
        db.close();
        return company;
    }

    public byte[] getAvatar(long id){
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.COMPANIES
                + " WHERE " + mDb.COMPANY_ID + " = \"" + id + "\"";
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

        String selectQuery = "SELECT * FROM " + mDb.COMPANIES
                + " WHERE " + mDb.COMPANY_ID + " = \"" + id + "\"";
        Cursor c = db.rawQuery(selectQuery, null);

        String name = null;
        if(c.moveToFirst()){
            name = c.getString(c.getColumnIndex(mDb.COMPANY_NAME));
        }

        c.close();
        db.close();
        return name;
    }

    @Override
    public Company getCompanyById(long hotelId) {
        SQLiteDatabase db = mDb.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + mDb.COMPANIES
                + " WHERE " + mDb.COMPANY_ID + " = \"" + hotelId
                + "\"";
        Cursor c = db.rawQuery(selectQuery, null);

        Company company = null;
        if(c.moveToFirst()){
            long id = c.getLong(c.getColumnIndex(mDb.COMPANY_ID));
            String name = c.getString(c.getColumnIndex(mDb.COMPANY_NAME));
            String companyPassword = c.getString(c.getColumnIndex(mDb.PASSWORD));
            String companyEmail = c.getString(c.getColumnIndex(mDb.EMAIL));
            String address = c.getString(c.getColumnIndex(mDb.COMPANY_OFFICE_ADDRESS));
            byte[] avatar = c.getBlob(c.getColumnIndex(mDb.AVATAR));
            String phone = c.getString(c.getColumnIndex(mDb.TELEPHONE));
            String description = c.getString(c.getColumnIndex(mDb.COMPANY_DESCRIPTION));

            company = new Company(id, name, companyEmail, companyPassword, address , avatar, phone, description);
             }

        c.close();
        db.close();
        return company;
    }
}
