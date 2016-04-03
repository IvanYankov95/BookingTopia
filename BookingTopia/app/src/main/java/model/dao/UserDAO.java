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
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        ConnectionHelper.deleteUser(userJson);
    }

    public void changeUserData(User user){
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        ConnectionHelper.updateUser(userJson);
    }

    public boolean checkUsername(String username){
        return ConnectionHelper.checkUsername(username);
    }

    public boolean checkUserEmail(String email){
        return ConnectionHelper.checkEmail(email);
    }

    public User login (String email, String password){
        User user = new User(0, null,password,null,email,null,null,null,null,null,false);
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        if(ConnectionHelper.login(email, password)) {
            String returnJson = ConnectionHelper.getUserByEmail(userJson);
            return gson.fromJson(returnJson, User.class);
        }
        else
            return null;
    }

    public User getUser (long id){
        User user = new User(id, null,null,null,null,null,null,null,null,null,false);
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        return gson.fromJson(ConnectionHelper.getUserById(userJson), User.class);
    }
}
