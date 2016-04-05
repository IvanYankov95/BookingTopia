package bg.ittalents.bookingtopia;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.UserManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import model.Company;
import model.RegisterHelper;
import model.User;
import model.UserSessionManager;
import model.dao.CompanyDAO;
import model.dao.UserDAO;

public class LogIn extends AbstractDrawerActivity {

    UserSessionManager session;

    private static CheckBox logInAsCompany;
    private static Button register;
    private static Button logIn;
    private static EditText email;
    private static EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Log in");

        session = new UserSessionManager(getApplicationContext());
        //manager = UserManager.getInstance(LogIn.this);

        logIn = (Button) findViewById(R.id.login_button);
        register = (Button) findViewById(R.id.login_register_button);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        logInAsCompany = (CheckBox) findViewById(R.id.login_company_checkbox);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
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
                    User user = UserDAO.getInstance(LogIn.this).login(emailText, RegisterHelper.md5(passwordText));
                    if (user == null) {
                        Toast.makeText(LogIn.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LogIn.this, "Login sucssessfull!", Toast.LENGTH_SHORT).show();
                        session.createUserLoginSession(user.getUserId(), "true");
                        startActivity(new Intent(LogIn.this, Home.class));
                    }
                } else {
                    Company company = CompanyDAO.getInstance(LogIn.this).login(emailText, RegisterHelper.md5(passwordText));
                    if (company == null) {
                        Toast.makeText(LogIn.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LogIn.this, "Login sucssessfull!", Toast.LENGTH_SHORT).show();
                        session.createUserLoginSession(company.getCompanyId(), "false");
                        startActivity(new Intent(LogIn.this, Home.class));

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
                                startActivity(new Intent(LogIn.this , RegisterCompanyActivity.class));
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("User", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(LogIn.this, Register.class));
                                dialog.cancel();
                            }
                        }).show();
            }
        });

    }




}
