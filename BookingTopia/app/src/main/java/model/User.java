package model;

import java.util.Date;

/**
 * Created by user-17 on 4/2/16.
 */
public class User {

    private long    userId;
    private String  names;
    private byte[]  avatar;
    private String  email;
    private String  username;
    private String  mobilePhone;
    private Date    dateOfBirth;
    private String  gender;
    private String  country;
    private boolean smoking;

    public User(long userId, String names, byte[] avatar, String email, String username, String mobilePhone, Date dateOfBirth, String gender, String country, boolean smoking) {
        this.userId = userId;
        this.names = names;
        this.avatar = avatar;
        this.email = email;
        this.username = username;
        this.mobilePhone = mobilePhone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.country = country;
        this.smoking = smoking;
    }

    public User(String names, byte[] avatar, String email, String username, String mobilePhone, Date dateOfBirth, String gender, String country, boolean smoking) {
        this.names = names;
        this.avatar = avatar;
        this.email = email;
        this.username = username;
        this.mobilePhone = mobilePhone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.country = country;
        this.smoking = smoking;
    }
}
