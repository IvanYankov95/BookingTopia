package model.dao;

import com.google.gson.Gson;

import model.User;
import model.connectionhelper.ConnectionHelper;

/**
 * Created by user-17 on 4/2/16.
 */
public class UserDAO {

    private static UserDAO instance;

    private UserDAO(){}

    public static UserDAO getInstance(){
        if(instance == null)
            instance = new UserDAO();

        return instance;
    }

    public User registerUser(User user){

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        String returnJson = ConnectionHelper.registerUser(userJson);

        return gson.fromJson(returnJson, User.class);


    }

    public void deleteUser(User user){

    }

    public long changeUserData(User user){
        return 0;
    }

    public boolean checkUsername(String username){
        return true;
    }

    public boolean checkUserEmail(String email){
        return true;
    }

    public boolean checkPassword(long userId, String password){
        return true;
    }

    public User login (String email, String password){
        return null;
    }

    public User getUser (long id){
        return null;
    }
}
