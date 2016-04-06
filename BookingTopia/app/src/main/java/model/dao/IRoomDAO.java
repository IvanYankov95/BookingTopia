package model.dao;

import java.util.ArrayList;

import model.Hotel;
import model.Room;

/**
 * Created by user-20 on 4/6/16.
 */
public interface IRoomDAO {

    public long registerRoom(Hotel hotel, Room room);

    public void deleteRoom(Room room);

    public long changeRoomData(Room room);

    public Room getRoomById (long roomId);

    public ArrayList<Room> getAllRoomsByHotelID(long hotelID);
}
