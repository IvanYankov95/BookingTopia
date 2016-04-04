package model;


import java.util.ArrayList;

public class Book {

    Hotel hotel;
    ArrayList<Room> rooms = new ArrayList<>();

    Book(Hotel hotel, ArrayList<Room> rooms) {
        this.hotel = hotel;
        this.rooms = rooms;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public Hotel getHotel() {
        return hotel;
    }
}