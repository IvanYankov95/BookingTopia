package model.dao;

import model.User;

/**
 * Created by user-17 on 4/2/16.
 */
public class CompanyDAO {

    public long registerCompany(User user){
        return 0;
    }

    public void deleteCompany(User user){

    }

    public long changeCompanyData(User user){
        return 0;
    }

    boolean checkUsername(String username){
        return true;
    }

    boolean checkCompanyEmail(String email){
        return true;
    }

    boolean checkPassword(long userId, String password){
        return true;
    }

    User login (String email, String password){
        return null;
    }
}
