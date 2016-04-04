package bg.ittalents.bookingtopia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.RegisterHelper;
import model.User;
import model.dao.UserDAO;

public class Register extends CustomActivityWithMenu {

    // constants
    protected static final int IMAGE_GALLERY_REQUEST_1 = 21;
    protected static final int REQ_WIDTH = 500;
    protected static final int REQ_HEIGHT = 500;

    // views start
    private static ImageButton avatar;

    private static EditText username;
    private static EditText password;
    private static EditText confirmPassword;
    private static EditText email;
    private static EditText firstName;
    private static EditText lastName;
    private static EditText country;
    private static EditText phone;
    private static EditText dateOfBirth;

    private static Spinner  genderSpinner;
    private static CheckBox smokerCheckBox;

    private static Button register;
    private static ProgressBar progressBar;
    // views end

    // helpers
    private static String selectedGender;

    private static byte[]  avatarPic;
    private static boolean avatarCheck;

    private static Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register        = (Button)   findViewById(R.id.register_user_register_button);
        username        = (EditText) findViewById(R.id.register_user_username);
        password        = (EditText) findViewById(R.id.register_user_password);
        confirmPassword = (EditText) findViewById(R.id.register_user_confirm_password);
        email           = (EditText) findViewById(R.id.register_user_email);
        firstName       = (EditText) findViewById(R.id.register_user_first_name);
        lastName        = (EditText) findViewById(R.id.register_user_last_name);
        country         = (EditText) findViewById(R.id.register_user_country);
        phone           = (EditText) findViewById(R.id.register_user_phone);
        genderSpinner   = (Spinner)  findViewById(R.id.register_user_gender_spinner);
        smokerCheckBox  = (CheckBox) findViewById(R.id.register_user_smoker_checkbox);
        dateOfBirth     = (EditText) findViewById(R.id.register_user_date_of_birth_text);
        avatar          = (ImageButton) findViewById(R.id.register_user_avatar_button);
        progressBar     = (ProgressBar) findViewById(R.id.register_user_progress_bar);

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



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterHelper helper = RegisterHelper.getInstance();
                UserDAO dao = UserDAO.getInstance();

                String usernameTxt = username.getText().toString();
                String passwordTxt = password.getText().toString();
                String confirmPasswordTxt = Register.confirmPassword.getText().toString();
                String emailTxt = email.getText().toString();
                String firstNameTxt = firstName.getText().toString();
                String lastNameTxt = lastName.getText().toString();
                String phoneTxt = phone.getText().toString();
                String countryTxt = country.getText().toString();

                boolean usernameCheck = false;
                boolean emailCheck = false;
                boolean passwordCheck = false;
                boolean nameCheck = false;

                // username check
                if(usernameTxt.isEmpty())
                    username.setError("This field is required");
                else
                    usernameCheck = true;
                // username check

                // email check
                if(emailTxt.isEmpty())
                    email.setError("This field is required");
                else if(!helper.isEmailValid(emailTxt))
                    email.setError("Please enter a valid email");
                else
                    emailCheck = true;
                // email check

                // password check
                if(passwordTxt.isEmpty())
                    password.setError("This field is required");
                else if (!helper.checkPasswordStrength(passwordTxt))
                    password.setError("Password is too weak\n At least 8 symbols\n At least one uppercase\n At least one lowercase\n At least one digit");
                else if(!passwordTxt.equals(confirmPasswordTxt))
                    confirmPassword.setError("Passwords don't match");
                else
                    passwordCheck = true;
                // password check

                // names check
                if(firstNameTxt.isEmpty())
                    firstName.setError("This field is required");
                else if(lastNameTxt.isEmpty())
                    lastName.setText("This field is required");
                else
                    nameCheck = true;
                // names check

                if(!avatarCheck)
                    Toast.makeText(Register.this, "Avatar is required", Toast.LENGTH_SHORT).show();
                if (usernameCheck && emailCheck && passwordCheck && nameCheck && avatarCheck) {

                    String names = firstNameTxt + " " + lastNameTxt;
                    User user = new User(names, helper.md5(passwordTxt), avatarPic, emailTxt, usernameTxt, phoneTxt, calendar, selectedGender, countryTxt , smokerCheckBox.isChecked());

                    String[] strs = dao.registerUser(user);


                    if(user != null)
                        Toast.makeText(Register.this, "Register failed", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(Register.this, LogIn.class));
                }

            }
        });

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picDate((EditText) v);
            }
        });

    }

    private class SendRegister extends AsyncTask<User, Void, String[]> {

        @Override
        protected void onPreExecute() {
            register.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(User... params) {
            UserDAO dao = UserDAO.getInstance();

            String[] strings = dao.registerUser(params[0]);

            return strings;
        }

        @Override
        protected void onPostExecute(String[] b) {
            if(b[0].equalsIgnoreCase("true"))
                Toast.makeText(Register.this, "Register sucssefull", Toast.LENGTH_SHORT).show();
            if(b[1].equalsIgnoreCase("false"))
                username.setError("Username is already taken");
            if(b[2].equalsIgnoreCase("false"))
                email.setError("Email already in use");
            if(!b[3].isEmpty()) {
                //TODO startactivity with session in login
            }
        }

    }

    public void makeToast(Boolean b){
        Toast.makeText(Register.this, "RESULT " + b, Toast.LENGTH_SHORT).show();
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
                calendar = c;

                return new DatePickerDialog(getActivity(), this, year, month, day);
            }

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String date = year + "-" + ((monthOfYear < 10) ? "0" : "") + monthOfYear + "-" + ((dayOfMonth < 10) ? "0" : "") + dayOfMonth;
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
        if(resultCode == RESULT_OK){

            switch (requestCode){
                case IMAGE_GALLERY_REQUEST_1:
                    setPicture(avatar,data);
                    break;
            }
        }
    }

    private void setPicture(ImageButton button, Intent data){
        Uri imageUrl = data.getData();

        InputStream inputStream = null;
        InputStream inputStream2 = null;
        ByteArrayOutputStream stream = null;
        try {
            inputStream = getContentResolver().openInputStream(imageUrl);
            inputStream2 = getContentResolver().openInputStream(imageUrl);

            Bitmap image = decodeSampledBitmapFromStream(inputStream,inputStream2, REQ_WIDTH, REQ_HEIGHT);

            stream = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            avatarPic = stream.toByteArray();
            avatarCheck = true;

            button.setImageBitmap(image);

        } catch (FileNotFoundException e) {
            Toast.makeText(Register.this, "Unable to open image", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception e){}
            try {
                if (inputStream2 != null)
                    inputStream2.close();
            } catch (Exception e){}
            try {
                if (stream != null)
                    stream.close();
            } catch (Exception e){}
        }
    }

    protected static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    protected static Bitmap decodeSampledBitmapFromStream(InputStream inputStream, InputStream inputStream2,int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream2, null, options);
    }

}
