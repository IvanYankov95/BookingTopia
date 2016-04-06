package bg.ittalents.bookingtopia;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import model.dao.HotelDAO;
import model.dao.IHotelDAO;

public class CreateHotelActivity extends AbstractDrawerActivity implements View.OnClickListener {

    private static IHotelDAO hotelDAO;

    protected static final int IMAGE_GALLERY_REQUEST_1 = 21;
    protected static final int IMAGE_GALLERY_REQUEST_2 = 22;
    protected static final int IMAGE_GALLERY_REQUEST_3 = 23;
    protected static final int IMAGE_GALLERY_REQUEST_4 = 24;
    protected static final int IMAGE_GALLERY_REQUEST_5 = 25;
    protected static final int IMAGE_GALLERY_REQUEST_6 = 26;
    protected static final int IMAGE_GALLERY_REQUEST_7 = 27;
    protected static final int REQ_WIDTH = 500;
    protected static final int REQ_HEIGHT = 500;

    protected static ArrayList<byte[]> pictures;

    protected static boolean mainPictureCheck = false;

    private static ImageButton mainPicture;
    private static ImageButton picture1;
    private static ImageButton picture2;
    private static ImageButton picture3;
    private static ImageButton picture4;
    private static ImageButton picture5;
    private static ImageButton picture6;

    private static EditText hotelName;
    private static EditText address;
    private static EditText city;
    private static EditText hotelDdescription;
    private static EditText extras;
    private static EditText webPage;
    private static EditText facebookPage;
    private static EditText policies;

    private static Spinner stars;
    private static Spinner fromDay;
    private static Spinner fromMonth;
    private static Spinner toDay;
    private static Spinner toMonth;

    private static Button addButton;

    private static String selectedStars;
    private static String selectedFromDate;
    private static String selectedFromMonth;
    private static String selectedToDate;
    private static String selectedToMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hotel_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Create hotel");

        hotelDAO = HotelDAO.getInstance(CreateHotelActivity.this);

        pictures = new ArrayList<>();

        mainPicture = (ImageButton) findViewById(R.id.add_hotel_main_picture);
        picture1    = (ImageButton) findViewById(R.id.add_hotel_picture1);
        picture2    = (ImageButton) findViewById(R.id.add_hotel_picture2);
        picture3    = (ImageButton) findViewById(R.id.add_hotel_picture3);
        picture4    = (ImageButton) findViewById(R.id.add_hotel_picture4);
        picture5    = (ImageButton) findViewById(R.id.add_hotel_picture5);
        picture6    = (ImageButton) findViewById(R.id.add_hotel_picture6);

        hotelName           = (EditText) findViewById(R.id.add_hotel_name_text);
        address             = (EditText) findViewById(R.id.add_hotel_name_text);
        city                = (EditText) findViewById(R.id.add_hotel_name_text);
        hotelDdescription   = (EditText) findViewById(R.id.add_hotel_name_text);
        extras              = (EditText) findViewById(R.id.add_hotel_name_text);
        webPage             = (EditText) findViewById(R.id.add_hotel_name_text);
        facebookPage        = (EditText) findViewById(R.id.add_hotel_name_text);
        policies            = (EditText) findViewById(R.id.add_hotel_name_text);

        stars       = (Spinner) findViewById(R.id.add_hotel_stars_spinner);
        fromDay     = (Spinner) findViewById(R.id.add_hotel_from_day);
        fromMonth   = (Spinner) findViewById(R.id.add_hotel_from_month);
        toDay       = (Spinner) findViewById(R.id.add_hotel_to_day);
        toMonth     = (Spinner) findViewById(R.id.add_hotel_to_month);

        addButton = (Button) findViewById(R.id.add_hotel_add_button);

        mainPicture.setOnClickListener(new View.OnClickListener() {
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

        picture1.setOnClickListener(this);
        picture2.setOnClickListener(this);
        picture3.setOnClickListener(this);
        picture4.setOnClickListener(this);
        picture5.setOnClickListener(this);
        picture6.setOnClickListener(this);

        String[] fromDays  = {"Work from day" , "1" , "2" , "3", "4", "5", "6", "7", "8", "9", "10", "11" , "12" , "13", "14", "15", "16", "17", "18", "19", "20", "21" , "22" , "23", "24", "25", "26", "27", "28", "29", "30" , "31"};
        String[] month     = {"Month", "January" , "February" , "March", "April", "May", "June", "Jully", "August", "September", "October", "November" , "December"};
        String[] toDays    = {"Work to day" , "1" , "2" , "3", "4", "5", "6", "7", "8", "9", "10", "11" , "12" , "13", "14", "15", "16", "17", "18", "19", "20", "21" , "22" , "23", "24", "25", "26", "27", "28", "29", "30" , "31"};


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fromDays);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromDay.setAdapter(dataAdapter);

        fromDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFromDate = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, month);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromMonth.setAdapter(dataAdapter2);

        fromMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFromMonth = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toDays);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toDay.setAdapter(dataAdapter3);

        toDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedToDate = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, month);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toMonth.setAdapter(dataAdapter4);

        toMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedToMonth = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] star = {"1", "2", "3", "4", "5", "6", "7"};

        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, month);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stars.setAdapter(dataAdapter5);

        stars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStars = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){

            switch (requestCode){
                case IMAGE_GALLERY_REQUEST_1:
                    setPicture(mainPicture,data);
                    mainPictureCheck = true;
                    break;
                case IMAGE_GALLERY_REQUEST_2:
                    setPicture(picture1,data);
                    break;
                case IMAGE_GALLERY_REQUEST_3:
                    setPicture(picture2,data);
                    break;
                case IMAGE_GALLERY_REQUEST_4:
                    setPicture(picture3,data);
                    break;
                case IMAGE_GALLERY_REQUEST_5:
                    setPicture(picture4,data);
                    break;
                case IMAGE_GALLERY_REQUEST_6:
                    setPicture(picture5,data);
                    break;
                case IMAGE_GALLERY_REQUEST_7:
                    setPicture(picture6,data);
                    break;

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_hotel_picture1:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_2);
                break;
            case R.id.add_hotel_picture2:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_3);
                break;
            case R.id.add_hotel_picture3:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_4);
                break;
            case R.id.add_hotel_picture4:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_5);
                break;
            case R.id.add_hotel_picture5:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_6);
                break;
            case R.id.add_hotel_picture6:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_7);
                break;
        }
    }

    protected void askForPhotoWithIntent(int request){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);

        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent, request);
    }

    protected void setPicture(ImageButton button, Intent data){
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

            pictures.add(stream.toByteArray());

            button.setImageBitmap(image);

        } catch (FileNotFoundException e) {
            Toast.makeText(CreateHotelActivity.this, "Unable to open image", Toast.LENGTH_SHORT).show();
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
