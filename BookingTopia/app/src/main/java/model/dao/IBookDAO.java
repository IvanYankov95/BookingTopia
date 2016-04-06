package model.dao;

import model.Book;
import model.User;

/**
 * Created by user-20 on 4/6/16.
 */
public interface IBookDAO {

    public long book(Book book);

    public void removeBook(Book book);

    public Book getBooksByUser(User user);

    public Book getBooksByID(long bookID);
}
