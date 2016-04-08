package model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Preshlen on 4/4/2016.
 */
public class HotelManager {

    private static HotelManager instance;

    private HotelManager(){

    }

    public  HotelManager getInstane(){
        if(this.instance == null){
            instance = new HotelManager();
        }
        return instance;
    }


//    public void reserveRooms(User user ,Hotel hotel, ArrayList<Room> rooms , ArrayList<Calendar> dates){
//
//        for(Room room: rooms){
//            room.reserveDates(dates);
//        }
//
//        //user.addBook(hotel, rooms);
//    }

    public ArrayList<Hotel> getAllHotelsByStars(int stars){

        return null;
    }

    //hotels with free rooms for those dates
    public ArrayList<Hotel> getAllHotelsByDate(ArrayList<Calendar> dates){
        //vika ot RoomsDao vs hoteli , kudeto ima svobodni stai za tezi dni
        return null;
    }

    public ArrayList<Hotel> getAllHotelsByCity(String city){

        return null;
    }

    //all hotels that can carry the guests in a single room
    public ArrayList<Hotel> getAllHotelsByGuests(int guests){
        //vika ot RoomsDao vs hoteli , kudeto ima stai za tozi broy 'guests'
        return null;
    }

    public ArrayList<Hotel> requestHotels(int stars, ArrayList<Calendar> dates, String city, int guests){
        // from daos get all hotels which meet the fields
        return null;
    }

    public void  addReview(User user, Hotel hotel, String pros, String cons, double rating){

    }

}
