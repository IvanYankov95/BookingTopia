package model.dao;

import model.User;

/**
 * Created by user-17 on 4/2/16.
 */
public class UserDAO {

    public long registerUser(User user){
        return 0;
    }

    public void deleteUser(User user){

    }

    public long changeUserData(User user){
        return 0;
    }

    boolean checkUsername(String username){
        return true;
    }

    boolean checkUserEmail(String email){
        return true;
    }

    boolean checkPassword(long userId, String password){
        return true;
    }

    User login (String email, String password){
        return null;
    }
}
