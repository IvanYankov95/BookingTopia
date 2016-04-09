package model;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;

public class User {

    private ArrayList<Reservation> bookings = new ArrayList<>();

    private long     userId;
    private String   names;
    private String   password;
    private byte[]   avatar;
    private String   email;
    private String   username;
    private String   mobilePhone;
    private LocalDate dateOfBirth;
    private String   gender;
    private String   country;
    private boolean  smoking;

    public void addReservation(){

    }

//    for(Reservation booking, user.getBookings()){
//        for(Room room, booking.getRooms()){
//            for(Calendar date, room.getReservedDates()){
//                // 'date' sa vsichki zaeti dati ot tozi potrebitel za 'room' , a rooms sa vs zaeti ot usera stai
//            }
//        }
//    }

    public User(long userId, String names, String password, byte[] avatar, String email, String username, String mobilePhone, LocalDate dateOfBirth, String gender, String country, boolean smoking) {
        this.userId = userId;
        this.names = username;
        this.password = password;
        this.avatar = avatar;
        this.email = email;
        this.username = names;
        this.mobilePhone = mobilePhone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.country = country;
        this.smoking = smoking;
    }

    public User(String names, String password, byte[] avatar, String email, String username, String mobilePhone, LocalDate dateOfBirth, String gender, String country, boolean smoking) {
        this.names = names;
        this.password = password;
        this.avatar = avatar;
        this.email = email;
        this.username = username;
        this.mobilePhone = mobilePhone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.country = country;
        this.smoking = smoking;
    }



    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }



    public ArrayList<Reservation> getBookings() {
        return bookings;
    }

    public long getUserId() {
        return userId;
    }

    public String getNames() {
        return names;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
