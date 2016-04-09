package model.dao;

import model.Company;

/**
 * Created by user-20 on 4/6/16.
 */
public interface ICompanyDAO {

    public long registerCompany(Company company);

    public void deleteCompany(Company company);

    public long changeCompanyData(Company company);

    public boolean checkCompanyName(String name);

    public Company getCompanyById(long id);

    public boolean checkUserEmail(String email);

    public Company login (String email, String password);
}
