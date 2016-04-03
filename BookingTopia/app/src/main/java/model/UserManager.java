package model;

import model.dao.UserDAO;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class UserManager {
    private static UserManager ourInstance;

    private static UserDAO userDAO;

    private UserManager() {
        userDAO = UserDAO.getInstance();
    }

    public static UserManager getInstance() {
        if(ourInstance == null)
            ourInstance = new UserManager();
        return ourInstance;
    }

    public boolean existUsername(String username){

        return userDAO.checkUsername(username);
    }

    public boolean existEmail(String email){

        return userDAO.checkUserEmail(email);
    }


    public boolean checkPasswordStrength(String password) {
        char[] test = password.toCharArray();

        boolean lowercase = false;
        boolean uppercase = false;
        boolean number = false;

        if(test.length < 8)
            return false;

        for (char aTest : test) {
            if (aTest >= 65 && aTest <= 90)
                lowercase = true;
            if (aTest >= 97 && aTest <= 122)
                uppercase = true;
            if (aTest >= 48 && aTest <= 57)
                number = true;
        }

        return !(!lowercase || !uppercase || !number);

    }

    public User register(User user){
        User user1 = userDAO.registerUser(user);

        return user1;
    }

    public User login(String email, String password){
        password = md5(password);

        return userDAO.login(email, password);
    }

    public boolean checkPassword(long userId, String password){
        password = md5(password);
        return userDAO.checkPassword(userId, password);
    }

    private static String md5(String password) {
        try {

            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public User getUser(long id){
        return userDAO.getUser(id);
    }

    public long updateUser(User user){
        return userDAO.changeUserData(user);
    }

}