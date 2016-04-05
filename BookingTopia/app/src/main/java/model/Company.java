package model;

import java.util.ArrayList;

/**
 * Created by user-17 on 4/2/16.
 */
public class Company {

    private long   companyId;
    private String name;
    private String email;
    private String password;
    private String address;
    private byte[] avatar;
    private String phone;
    private String description;

    private ArrayList<Hotel> hotels = new ArrayList<>();

    //without hotels
    public Company(long companyId, String name, String email, String password, String address, byte[] avatar, String phone, String description) {
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.avatar = avatar;
        this.phone = phone;
        this.description = description;
    }

    //full constructor
    public Company(ArrayList<Hotel> hotels, long companyId, String name, String password, String email, String address, byte[] avatar,String phone, String description) {
        this.hotels = hotels;
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.avatar = avatar;
        this.phone = phone;
        this.description = description;
    }

    public void addHotel(Hotel hotel){
        this.hotels.add(hotel);
    }

    public long getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Hotel> getHotels() {
        return hotels;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
