package bg.ittalents.bookingtopia.controller.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import bg.ittalents.bookingtopia.R;
import model.Company;
import model.RegisterHelper;
import model.User;
import model.UserSessionManager;
import model.dao.CompanyDAO;
import model.dao.ICompanyDAO;
import model.dao.IUserDAO;
import model.dao.UserDAO;

public class LogInActivity extends AbstractDrawerActivity {

    UserSessionManager session;

    private static IUserDAO    userDAO;
    private static ICompanyDAO companyDAO;

    private static CheckBox logInAsCompany;
    private static Button register;
    private static Button logIn;

    private static EditText email;
    private static EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.log_in_toolbar_text);
        toolbar.setTitle("Log in");
        setSupportActionBar(toolbar);

        userDAO    = UserDAO.getInstance(LogInActivity.this);
        companyDAO = CompanyDAO.getInstance(LogInActivity.this);

        session = new UserSessionManager(getApplicationContext());

        logIn = (Button) findViewById(R.id.login_button);
       // keepMeLoggedIn = (CheckBox) findViewById(R.id.keep_me_logged_in);
        //keepMeLoggedIn.setChecked(true);
        register = (Button) findViewById(R.id.login_register_button);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        logInAsCompany = (CheckBox) findViewById(R.id.login_company_checkbox);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText    = email.getText().toString();
                String passwordText = password.getText().toString();
                if (emailText.isEmpty()) {
                    email.setError("This field is required.");
                    return;
                }
                if (passwordText.isEmpty()) {
                    password.setError("This field is required.");
                    return;
                }

                if (!logInAsCompany.isChecked()) {
                    User user = userDAO.login(emailText, RegisterHelper.md5(passwordText));
                    if (user == null) {
                        Toast.makeText(LogInActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(" checking reg --- ", "" + emailText + " " + passwordText + " " + RegisterHelper.md5(passwordText));
                        Toast.makeText(LogInActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                      //  if(keepMeLoggedIn.isChecked()){
                             session.createUserLoginSession(user.getUserId(), "true");
                       // }
                        startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                    }
                } else {

                    Company company = companyDAO.login(emailText, RegisterHelper.md5(passwordText));
                    if (company == null) {
                        Toast.makeText(LogInActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LogInActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                      //  if(keepMeLoggedIn.isChecked()){
                            session.createUserLoginSession(company.getCompanyId(), "false");
                     //   }
                        startActivity(new Intent(LogInActivity.this, HomeActivity.class));

                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("What are you")
                        .setNegativeButton("Company", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(LogInActivity.this , RegisterCompanyActivity.class);
                                intent.putExtra("edit_mode", false);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("User", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(LogInActivity.this, RegisterUserActivity.class);
                                intent.putExtra("edit_mode", false);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        }).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
