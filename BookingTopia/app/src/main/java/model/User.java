package model;

import java.util.Calendar;

public class User {

    private long     userId;
    private String   names;
    private String   password;
    private byte[]   avatar;
    private String   email;
    private String   username;
    private String   mobilePhone;
    private Calendar dateOfBirth;
    private String   gender;
    private String   country;
    private boolean  smoking;

    public User(long userId, String names, String password, byte[] avatar, String email, String username, String mobilePhone, Calendar dateOfBirth, String gender, String country, boolean smoking) {
        this.userId = userId;
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

    public User(String names, String password, byte[] avatar, String email, String username, String mobilePhone, Calendar dateOfBirth, String gender, String country, boolean smoking) {
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
}
