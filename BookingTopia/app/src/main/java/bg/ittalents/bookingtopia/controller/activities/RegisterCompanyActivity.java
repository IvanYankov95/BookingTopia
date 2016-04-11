package bg.ittalents.bookingtopia.controller.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import bg.ittalents.bookingtopia.R;
import model.Company;
import model.RegisterHelper;
import model.dao.CompanyDAO;
import model.dao.ICompanyDAO;

public class RegisterCompanyActivity extends AbstractDrawerActivity {

    // constants
    protected static final int IMAGE_GALLERY_REQUEST_1 = 21;
    protected static final int REQ_WIDTH = 200;
    protected static final int REQ_HEIGHT = 200;

    private static ICompanyDAO companyDAO;

    // views start
    private static ImageButton avatar;

    private static EditText name;
    private static EditText password;
    private static EditText confirmPassword;
    private static EditText email;
    private static EditText phone;
    private static EditText address;
    private static EditText description;


    private static Button register;

    private static byte[] avatarPic;
    private static boolean avatarCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);
        Bundle bundle = getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.register_company_toolbar_text);
        if (bundle.getBoolean("edit_mode"))
            toolbar.setTitle("Edit company");
        else
            toolbar.setTitle("Register company");
        setSupportActionBar(toolbar);

        register = (Button) findViewById(R.id.register_company_register_button);

        name = (EditText) findViewById(R.id.register_company_name);
        password = (EditText) findViewById(R.id.register_company_password);
        confirmPassword = (EditText) findViewById(R.id.register_company_confirm_password);
        email = (EditText) findViewById(R.id.register_company_email);
        phone = (EditText) findViewById(R.id.register_comapany_phone);
        address = (EditText) findViewById(R.id.register_comappany_address);
        description = (EditText) findViewById(R.id.register_company_description);
        avatar = (ImageButton) findViewById(R.id.register_company_avatar_button);

        companyDAO = CompanyDAO.getInstance(RegisterCompanyActivity.this);

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

            final Company oldCompany = companyDAO.getCompanyById(getLoggedId());
            register.setText("UPDATE");

            email.setVisibility(View.GONE);

            name.setText(oldCompany.getName());

            byte[] image = oldCompany.getAvatar();

            final Bitmap oldAvatarPic = BitmapFactory.decodeByteArray(image, 0, image.length);
            avatar.setImageBitmap(oldAvatarPic);

            password.setText("");
            phone.setText(oldCompany.getPhone());
            address.setText(oldCompany.getAddress());
            description.setText(oldCompany.getDescription());

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RegisterHelper helper = RegisterHelper.getInstance();
                    String companyNameTxt = name.getText().toString();
                    String passwordTxt = password.getText().toString();
                    String confirmPasswordTxt = RegisterCompanyActivity.confirmPassword.getText().toString();
                    String phoneTxt = phone.getText().toString();
                    String addressTxt = address.getText().toString();
                    String descriptionTxt = description.getText().toString();

                    boolean nameCheck = false;
                    boolean passwordCheck = false;
                    boolean isPasswordChanged = false;

                    if (companyNameTxt.isEmpty())
                        name.setError("This field is required");
                    else
                        nameCheck = true;

                    if (!passwordTxt.equalsIgnoreCase("")) {
                        if (!helper.checkPasswordStrength(passwordTxt))
                            password.setError("Password is too weak\n At least 8 symbols\n At least one uppercase\n At least one lowercase\n At least one digit");
                        else if (!passwordTxt.equals(confirmPasswordTxt))
                            confirmPassword.setError("Passwords don't match");
                        else
                            passwordCheck = true;
                        isPasswordChanged = true;
                    } else {
                        isPasswordChanged = false;
                        passwordCheck = true;
                    }

                    if (nameCheck &&  passwordCheck ) {

                        if(!isPasswordChanged)
                            passwordTxt = oldCompany.getPassword();

                        byte[] selectedAvatar;
                        if (avatarCheck)
                            selectedAvatar = avatarPic;
                        else
                            selectedAvatar = oldCompany.getAvatar();


                        Company company = new Company(companyNameTxt, oldCompany.getEmail(), helper.md5(passwordTxt), addressTxt, selectedAvatar, phoneTxt, descriptionTxt);

                        long companyID = companyDAO.changeCompanyData(company);

                        if (company == null)
                            Toast.makeText(RegisterCompanyActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        else {
                            company.setCompanyId(companyID);
                            Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                }
            });

        }


        if (!bundle.getBoolean("edit_mode")){
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RegisterHelper helper = RegisterHelper.getInstance();

                    String usernameTxt = name.getText().toString();
                    String passwordTxt = password.getText().toString();
                    String confirmPasswordTxt = RegisterCompanyActivity.confirmPassword.getText().toString();
                    String emailTxt = email.getText().toString();
                    String phoneTxt = phone.getText().toString();
                    String addressTxt = address.getText().toString();
                    String descriptionTxt = description.getText().toString();

                    boolean nameCheck = false;
                    boolean emailCheck = false;
                    boolean passwordCheck = false;

                    // username check
                    if (usernameTxt.isEmpty())
                        name.setError("This field is required");
                    else
                        nameCheck = true;
                    // username check

                    // email check
                    if (emailTxt.isEmpty())
                        email.setError("This field is required");
                    else if (!helper.isEmailValid(emailTxt))
                        email.setError("Please enter a valid email");
                    else if (companyDAO.checkUserEmail(emailTxt))
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

                    if (!avatarCheck)
                        Toast.makeText(RegisterCompanyActivity.this, "Avatar is required", Toast.LENGTH_SHORT).show();
                    if (nameCheck && emailCheck && passwordCheck && nameCheck && avatarCheck) {

                        //public Company(String name, String email, String password, String address, byte[] avatar, String phone, String description)
                        Company company = new Company(usernameTxt, emailTxt, helper.md5(passwordTxt), addressTxt, avatarPic, phoneTxt, descriptionTxt);

                        long companyID = companyDAO.registerCompany(company);

                        if (company == null)
                            Toast.makeText(RegisterCompanyActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                        else {
                            company.setCompanyId(companyID);
                            Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        //startActivity(new Intent(Register.this, LogIn.class));
                    }

                }
            });

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case IMAGE_GALLERY_REQUEST_1:
                    setPicture(avatar, data);
                    break;
            }
        }
    }

    private void setPicture(ImageButton button, Intent data) {
        Uri imageUrl = data.getData();

        InputStream inputStream = null;
        InputStream inputStream2 = null;
        ByteArrayOutputStream stream = null;
        try {
            inputStream = getContentResolver().openInputStream(imageUrl);
            inputStream2 = getContentResolver().openInputStream(imageUrl);

            Bitmap image = decodeSampledBitmapFromStream(inputStream, inputStream2, REQ_WIDTH, REQ_HEIGHT);

            stream = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            avatarPic = stream.toByteArray();
            avatarCheck = true;

            button.setImageBitmap(image);

        } catch (FileNotFoundException e) {
            Toast.makeText(RegisterCompanyActivity.this, "Unable to open image", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception e) {
            }
            try {
                if (inputStream2 != null)
                    inputStream2.close();
            } catch (Exception e) {
            }
            try {
                if (stream != null)
                    stream.close();
            } catch (Exception e) {
            }
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

    protected static Bitmap decodeSampledBitmapFromStream(InputStream inputStream, InputStream inputStream2, int reqWidth, int reqHeight) {

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
