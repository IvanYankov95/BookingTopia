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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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

import model.User;
import model.UserManager;
import model.UserSessionManager;
import model.dao.UserDAO;

public class Register extends CustomActivityWithMenu {

    protected static final int IMAGE_GALLERY_REQUEST_1 = 21;
    protected static final int REQ_WIDTH = 500;
    protected static final int REQ_HEIGHT = 500;

    private UserManager userManager;

    private static Button register;

    private static ImageButton avatar;

    private static EditText username;
    private static EditText password;
    private static EditText confirmPassword;
    private static EditText email;
    private static EditText firstName;
    private static EditText lastName;
    private static EditText city;
    private static EditText address;
    private static EditText phone;
    private static EditText dateOfBirth;

    private static Spinner  genderSpinner;
    private static CheckBox smokerCheckBox;

    private static String selectedGender;

    private static byte[] avatarPic;

    private static Calendar calendar;

    private boolean usernameCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userManager = UserManager.getInstance();

        register        = (Button)   findViewById(R.id.registerButton);
        username        = (EditText) findViewById(R.id.usernameField);
        password        = (EditText) findViewById(R.id.passwordField);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordField);
        email           = (EditText) findViewById(R.id.emailField);
        firstName       = (EditText) findViewById(R.id.firstNameField);
        lastName        = (EditText) findViewById(R.id.lastNameField);
        city            = (EditText) findViewById(R.id.cityField);
        address         = (EditText) findViewById(R.id.addressField);
        phone           = (EditText) findViewById(R.id.phoneField);
        genderSpinner   = (Spinner)  findViewById(R.id.register_gender_spinner);
        smokerCheckBox  = (CheckBox) findViewById(R.id.register_smoker_checkbox);
        dateOfBirth     = (EditText) findViewById(R.id.register_date_of_birth_text);
        avatar          = (ImageButton) findViewById(R.id.register_image_button);

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
                String usernameTxt = username.getText().toString();
                String passwordTxt = password.getText().toString();
                String emailTxt = email.getText().toString();
                String firstNameTxt = firstName.getText().toString();
                String lastNameTxt = lastName.getText().toString();
                String addressTxt = address.getText().toString();
                String phoneTxt = phone.getText().toString();
                String dateOfBirthTxt = dateOfBirth.getText().toString();


                boolean emailCheck = false;
                boolean passwordCheck = false;
                boolean passwordMatch = false;

//                if (userManager.existUsername(usernameTxt)) {
//                    username.setError("Username already exists");
//                } else if (usernameTxt.isEmpty()) {
//                    username.setError("This field is required");
//                } else
//                    usernameCheck = true;
                new GetRequest().execute(emailTxt, passwordTxt);
//                if (userManager.existEmail(emailTxt)) {
//                    email.setError("Email already exists");
//                } else if (emailTxt.isEmpty()) {
//                    email.setError("This field is required");
//                } else if (!isEmailValid(emailTxt)) {
//                    email.setError("Please enter a valid email");
//                } else
//                    emailCheck = true;
//
//                if (password.getText().toString().isEmpty()) {
//                    password.setError("This field is required.");
//                } else {
//                    if (!userManager.checkPasswordStrength(password.getText().toString())) {
//                        password.setError("Password is too weak\n At least 8 symbols \n At least 1 lowercase and uppercase \n At least 1 number");
//                    } else
//                        passwordCheck = true;
//
//                    if (userManager.checkPasswordStrength(password.getText().toString()) && !password.getText().toString().equals(confirmPassword.getText().toString())) {
//                        confirmPassword.setError("Passwords don't match");
//                    } else
//                        passwordMatch = true;
//                }

                if (usernameCheck && emailCheck && passwordCheck && passwordMatch) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(1995, 7, 4);
                    String names = firstNameTxt + " " + lastNameTxt;
                    User user = new User(names, passwordTxt, avatarPic, emailTxt, usernameTxt, phoneTxt, calendar, selectedGender, addressTxt, smokerCheckBox.isChecked());
                    //User user = new User(emailTxt, passwordTxt, usernameTxt, firstNameTxt, lastNameTxt, phoneTxt, cityTxt, addressTxt);
                    //User regedUser = userManager.register(user);
                    //if(regedUser != null)

                    Toast.makeText(getApplicationContext(), "register successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, LogIn.class));
                }

            }
        });

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picDate((EditText) v);
            }
        });
        Calendar cal = Calendar.getInstance();
        cal.set(1995, 7, 4);
        User user = new User("Gosho", "BahQkataParolaXx95xX", new byte[2], "1 235FUCK YOU 99", "XxN0_SC0P3RxX", "964635953", cal, "male", "Bulgaria", true);


    }

    private class GetRequest extends AsyncTask<String, Void, Boolean> {
//
        @Override
        protected Boolean doInBackground(String... params) {
            //Calendar cal = Calendar.getInstance();
            //cal.set(1995, 7,4);
            //User user = new User(20, "Pesho", "BahQkataParolaXx95xX", new byte[2], "qkmail@qkmail" , "XxN0_SC0P3RxX", "964635953", cal, "male", "UK", false);
            //UserDAO.getInstance().changeUserData(user);
            if(UserDAO.getInstance().login(params[0], params[1]) == null)
              return false;
            else
                return true;
        }

        @Override
        protected void onPostExecute(Boolean b) {
               //email.setText(user.toString());
            makeToast(b);
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

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
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
            //user.set(stream.toByteArray());

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
