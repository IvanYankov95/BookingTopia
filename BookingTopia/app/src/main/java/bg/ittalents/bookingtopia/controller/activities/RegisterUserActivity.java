package bg.ittalents.bookingtopia.controller.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import bg.ittalents.bookingtopia.R;
import model.RegisterHelper;
import model.User;
import model.dao.IUserDAO;
import model.dao.UserDAO;

public class RegisterUserActivity extends AbstractDrawerActivity {

    // constants
    protected static final int IMAGE_GALLERY_REQUEST_1 = 21;

    // views start
    private static ImageButton avatar;

    private static IUserDAO userDAO;

    private static EditText username;
    private static EditText password;
    private static EditText confirmPassword;
    private static EditText email;
    private static EditText firstName;
    private static EditText lastName;
    private static EditText country;
    private static EditText phone;
    private static EditText dateOfBirth;

    private static Spinner genderSpinner;
    private static CheckBox smokerCheckBox;

    private static Button register;
    private static ProgressBar progressBar;
    // views end

    // helpers
    private static String selectedGender;

    private static ArrayList<byte[]> avatarPic;
    private static boolean avatarCheck;

    boolean dateCheck = false;
    private static LocalDate localDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Bundle bundle = getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.register_user_toolbar_text);
        if (bundle.getBoolean("edit_mode"))
            toolbar.setTitle("Edit user");
        else
            toolbar.setTitle("Register user");
        setSupportActionBar(toolbar);

        avatarPic = new ArrayList<>();

        register = (Button) findViewById(R.id.register_user_register_button);
        username = (EditText) findViewById(R.id.register_user_username);
        password = (EditText) findViewById(R.id.register_user_password);
        confirmPassword = (EditText) findViewById(R.id.register_user_confirm_password);
        email = (EditText) findViewById(R.id.register_user_email);
        firstName = (EditText) findViewById(R.id.register_user_first_name);
        lastName = (EditText) findViewById(R.id.register_user_last_name);
        country = (EditText) findViewById(R.id.register_user_country);
        phone = (EditText) findViewById(R.id.register_user_phone);
        genderSpinner = (Spinner) findViewById(R.id.register_user_gender_spinner);
        smokerCheckBox = (CheckBox) findViewById(R.id.register_user_smoker_checkbox);
        dateOfBirth = (EditText) findViewById(R.id.register_user_date_of_birth_text);
        avatar = (ImageButton) findViewById(R.id.register_user_avatar_button);

        userDAO = UserDAO.getInstance(RegisterUserActivity.this);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Prefer not to say");
        categories.add("Male");
        categories.add("Female");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(dataAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);

                photoPickerIntent.setDataAndType(data, "image/*");

                startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST_1);

            }
        });

        if (bundle.getBoolean("edit_mode")) {
            username.setVisibility(View.GONE);
            email.setVisibility(View.GONE);

            long userId = getLoggedId();

            final User user = userDAO.getUserById(userId);

            byte[] image = user.getAvatar();

            final Bitmap oldAvatarPic = BitmapFactory.decodeByteArray(image, 0, image.length);
            avatar.setImageBitmap(oldAvatarPic);

            if (!user.getNames().isEmpty()) {
                String[] names = user.getNames().split(" ");
                firstName.setText(names[0]);
                if (names.length > 1)
                    lastName.setText(names[1]);
            }

            password.setText("");

            dateOfBirth.setText(user.getDateOfBirth().toString());

            country.setText(user.getCountry());

            phone.setText(user.getMobilePhone());

            if (user.getGender().equalsIgnoreCase("male")) {
                genderSpinner.setSelection(1);
                selectedGender = "Male";
            }
            if (user.getGender().equalsIgnoreCase("female")) {
                genderSpinner.setSelection(2);
                selectedGender = "Female";
            }

            smokerCheckBox.setChecked(user.isSmoking());


            register.setText("UPDATE");

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RegisterHelper helper = RegisterHelper.getInstance();

                    String passwordTxt = password.getText().toString();
                    String confirmPasswordTxt = RegisterUserActivity.confirmPassword.getText().toString();
                    String firstNameTxt = firstName.getText().toString();
                    String lastNameTxt = lastName.getText().toString();
                    String phoneTxt = phone.getText().toString();
                    String countryTxt = country.getText().toString();

                    boolean passwordCheck = false;
                    boolean isPassChanging = false;
                    boolean nameCheck = false;

                    if (!passwordTxt.isEmpty()) {
                        if (!helper.checkPasswordStrength(passwordTxt))
                            password.setError("Password is too weak\n At least 8 symbols\n At least one uppercase\n At least one lowercase\n At least one digit");
                        else if (!passwordTxt.equals(confirmPasswordTxt))
                            confirmPassword.setError("Passwords don't match");
                        else
                            passwordCheck = true;
                        isPassChanging = true;
                    } else {
                        isPassChanging = false;
                        passwordCheck = true;
                    }

                    // names check
                    if (firstNameTxt.isEmpty())
                        firstName.setError("This field is required");
                    else if (lastNameTxt.isEmpty())
                        lastName.setText("This field is required");
                    else
                        nameCheck = true;
                    // names check


                    if (passwordCheck && nameCheck) {

                        String names = firstNameTxt + " " + lastNameTxt;

                        if (!isPassChanging) {
                            passwordTxt = user.getPassword();
                        }

                        byte[] selectedAvatar;
                        if (avatarCheck)
                            selectedAvatar = avatarPic.get(0);
                        else
                            selectedAvatar = user.getAvatar();
                        User user2 = new User(names, helper.md5(passwordTxt), selectedAvatar, user.getEmail(), user.getUsername(), phoneTxt, localDate, selectedGender, countryTxt, smokerCheckBox.isChecked());

                        long userId = userDAO.updateUser(user2);

                        if (user == null)
                            Toast.makeText(RegisterUserActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        else {
                            user.setUserId(userId);
                            Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            });

        }

        if (!bundle.getBoolean("edit_mode"))
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RegisterHelper helper = RegisterHelper.getInstance();

                    String usernameTxt = username.getText().toString();
                    String passwordTxt = password.getText().toString();
                    String confirmPasswordTxt = RegisterUserActivity.confirmPassword.getText().toString();
                    String emailTxt = email.getText().toString();
                    String firstNameTxt = firstName.getText().toString();
                    String lastNameTxt = lastName.getText().toString();
                    String phoneTxt = phone.getText().toString();
                    String countryTxt = country.getText().toString();
                    String date = dateOfBirth.getText().toString();

                    boolean usernameCheck = false;
                    boolean emailCheck = false;
                    boolean passwordCheck = false;
                    boolean nameCheck = false;

                    if(date.isEmpty())
                        dateOfBirth.setError("This field is required");
                    else
                        dateCheck = true;

                    // username check
                    if (usernameTxt.isEmpty())
                        username.setError("This field is required");
                    else if (userDAO.checkUsername(usernameTxt))
                        username.setError("Username is already taken");
                    else
                        usernameCheck = true;
                    // username check

                    // email check
                    if (emailTxt.isEmpty())
                        email.setError("This field is required");
                    else if (!helper.isEmailValid(emailTxt))
                        email.setError("Please enter a valid email");
                    else if (userDAO.checkUserEmail(emailTxt))
                        email.setError("Email is already in use");
                    else
                        emailCheck = true;
                    // email check

                    // password check
                    if (passwordTxt.isEmpty())
                        password.setError("This field is required");
                    else if (!helper.checkPasswordStrength(passwordTxt))
                        password.setError("Password is too weak\n At least 8 symbols\n At least one uppercase\n At least one lowercase\n At least one digit");
                    else if (!passwordTxt.equals(confirmPasswordTxt))
                        confirmPassword.setError("Passwords don't match");
                    else
                        passwordCheck = true;
                    // password check

                    // names check
                    if (firstNameTxt.isEmpty())
                        firstName.setError("This field is required");
                    else if (lastNameTxt.isEmpty())
                        lastName.setText("This field is required");
                    else
                        nameCheck = true;
                    // names check

                    if (!avatarCheck)
                        Toast.makeText(RegisterUserActivity.this, "Avatar is required", Toast.LENGTH_SHORT).show();
                    if (usernameCheck && emailCheck && passwordCheck && nameCheck && avatarCheck && dateCheck) {


                        String names = firstNameTxt + " " + lastNameTxt;
                        User user = new User(names, helper.md5(passwordTxt), avatarPic.get(0), emailTxt, usernameTxt, phoneTxt, localDate, selectedGender, countryTxt, smokerCheckBox.isChecked());

                        long userId = userDAO.registerUser(user);

                        if (user == null)
                            Toast.makeText(RegisterUserActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                        else {
                            user.setUserId(userId);
                            Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        //startActivity(new Intent(Register.this, LogIn.class));
                    }

                }
            });

        dateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    picDate((EditText) v);
            }
        });

    }

    public boolean areAllFieldsFilled() {
        return (!username.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty() &&
                !confirmPassword.getText().toString().isEmpty() &&
                !email.getText().toString().isEmpty() &&
                !firstName.getText().toString().isEmpty() &&
                !lastName.getText().toString().isEmpty() &&
                !country.getText().toString().isEmpty() &&
                !phone.getText().toString().isEmpty());

    }

    public void makeToast(Boolean b) {
        Toast.makeText(RegisterUserActivity.this, "RESULT " + b, Toast.LENGTH_SHORT).show();
    }

    private void picDate(final EditText edt) {
        class FragmentDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
            public FragmentDatePicker() {
                // Required empty public constructor
            }

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                return new DatePickerDialog(getActivity(), this, year, month, day);
            }

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String date = year + "-" + ((monthOfYear < 10) ? "0" : "") + monthOfYear + "-" + ((dayOfMonth < 10) ? "0" : "") + dayOfMonth;
                dateCheck = true;
                localDate = new LocalDate(year, monthOfYear, dayOfMonth);
                edt.setText(date);
            }
        }
        edt.setKeyListener(null);
        edt.setVisibility(View.VISIBLE);
        edt.requestFocus();
        if (edt.isFocused()) {
            DialogFragment dateFragment = new FragmentDatePicker();
            dateFragment.show(getSupportFragmentManager(), "datePicker");

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case IMAGE_GALLERY_REQUEST_1:
                    setPicture(avatar, data, avatarPic);
                    avatarCheck = true;
                    break;
            }
        }
    }

}
