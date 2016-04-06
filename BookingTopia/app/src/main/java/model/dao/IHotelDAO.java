package model.dao;

import java.util.ArrayList;

import model.Hotel;

/**
 * Created by user-20 on 4/6/16.
 */
public interface IHotelDAO {

    public long registerHotel(Hotel hotel);

    public ArrayList<Hotel> getAllHotelsByCompanyID(long companyID);

    public void deleteHotel(Hotel hotel);

    public long changeCompanyData(Hotel hotel);

    public Hotel getHotel(long hotelId);

}
