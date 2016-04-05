package model;


public class Book {
    long bookID;
    long roomID;
    long userID;


    public Book(long bookID, long roomID, long userID) {
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

    public void setRoomID(long roomID) {
        this.roomID = roomID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }


}