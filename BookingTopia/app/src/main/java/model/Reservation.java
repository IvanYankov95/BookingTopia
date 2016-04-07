package model;


public class Reservation {
    long bookID;
    long roomID;
    long userID;


    public Reservation(long bookID, long roomID, long userID) {
        this.bookID = bookID;
        this.roomID = roomID;
        this.userID = userID;

    }

    public void setBookID(long bookID) {
        this.bookID = bookID;
    }

    public long getBookID() {
        return bookID;
    }

    public long getRoomID() {
        return roomID;
    }

    public long getUserID() {
        return userID;
    }

}