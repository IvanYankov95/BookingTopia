package model.dao;

import model.User;

/**
 * Created by user-20 on 4/6/16.
 */
public interface IUserDAO {

    public boolean checkUsername(String username);

    public boolean checkUserEmail(String email);

    public long registerUser(User user);

    public void deleteUser(User user);

    public long updateUser(User user);

    public User login (String email, String password);
}
