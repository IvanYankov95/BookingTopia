package bg.ittalents.bookingtopia.controller.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import bg.ittalents.bookingtopia.R;
import model.Room;
import model.dao.IRoomDAO;
import model.dao.RoomDAO;

public class CreateRoomActivity extends AbstractDrawerActivity implements View.OnClickListener{

    private static IRoomDAO roomDAO;

    protected static final int IMAGE_GALLERY_REQUEST_1 = 21;
    protected static final int IMAGE_GALLERY_REQUEST_2 = 22;
    protected static final int IMAGE_GALLERY_REQUEST_3 = 23;
    protected static final int IMAGE_GALLERY_REQUEST_4 = 24;
    protected static final int IMAGE_GALLERY_REQUEST_5 = 25;
    protected static final int IMAGE_GALLERY_REQUEST_6 = 26;
    protected static final int IMAGE_GALLERY_REQUEST_7 = 27;

    protected static ArrayList<byte[]> pictures;

    protected static boolean mainPictureCheck = false;

    private static ImageButton mainPicture;
    private static ImageButton picture1;
    private static ImageButton picture2;
    private static ImageButton picture3;
    private static ImageButton picture4;
    private static ImageButton picture5;
    private static ImageButton picture6;

    private static EditText pricePerDay;
    private static EditText numberOfSameRoom;
    private static EditText beds;
    private static EditText description;
    private static EditText extras;

    private static CheckBox smoking;

    private static Spinner guests;

    private static Button addButton;

    private static String selectedMaxGuests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Create room");

        Bundle bundle = getIntent().getExtras();

        final long hotelId = (long)bundle.get("hotel_id");

        roomDAO = RoomDAO.getInstance(CreateRoomActivity.this);

        pictures = new ArrayList<>();

        mainPicture = (ImageButton) findViewById(R.id.add_room_main_picture);
        picture1    = (ImageButton) findViewById(R.id.add_room_picture1);
        picture2    = (ImageButton) findViewById(R.id.add_room_picture2);
        picture3    = (ImageButton) findViewById(R.id.add_room_picture3);
        picture4    = (ImageButton) findViewById(R.id.add_room_picture4);
        picture5    = (ImageButton) findViewById(R.id.add_room_picture5);
        picture6    = (ImageButton) findViewById(R.id.add_room_picture6);

        pricePerDay      = (EditText) findViewById(R.id.add_room_price_text);
        beds             = (EditText) findViewById(R.id.add_room_beds_text);
        description      = (EditText) findViewById(R.id.add_room_description_text);
        extras           = (EditText) findViewById(R.id.add_room_extras_text);
        numberOfSameRoom = (EditText) findViewById(R.id.same_room_number);

        guests = (Spinner) findViewById(R.id.add_room_max_guests_spinner);

        addButton = (Button) findViewById(R.id.add_room_add_button);

        smoking = (CheckBox) findViewById(R.id.add_room_smoking_checkbox);

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

        final String[] maxGuest  = {"1" , "2" , "3", "4", "5", "6", "7", "8", "9", "10"};

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, maxGuest);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        guests.setAdapter(dataAdapter);

        guests.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMaxGuests = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean priceCheck = false;
                boolean bedsCheck = false;
                boolean descriptionCheck = false;
                boolean extrasCheck = false;

                String priceTxt = pricePerDay.getText().toString();
                String bedsTxtTxt = beds.getText().toString();
                String descriptionTxt = description.getText().toString();
                String extrasTxt = extras.getText().toString();


                if(!mainPictureCheck){
                    Toast.makeText(CreateRoomActivity.this, "Main picture is required", Toast.LENGTH_SHORT).show();
                }

                if(priceTxt.isEmpty())
                    pricePerDay.setError("This field is required");
                else
                    priceCheck = true;

                if(bedsTxtTxt.isEmpty())
                    beds.setError("This field is required");
                else
                    bedsCheck = true;

                if(descriptionTxt.isEmpty())
                    description.setText("This field is required");
                else
                    descriptionCheck = true;

                if(extrasTxt.isEmpty())
                    extras.setError("This field is required");
                else
                    extrasCheck = true;


                if(priceCheck && bedsCheck && descriptionCheck && extrasCheck && mainPictureCheck){
                    boolean smoker = smoking.isChecked();
                    Room room = new Room(0, hotelId, Double.valueOf(priceTxt), descriptionTxt, Integer.valueOf(selectedMaxGuests), bedsTxtTxt, 0, 0, extrasTxt, smoker, null, pictures);
                    String toValue = numberOfSameRoom.getText().toString();
                    if(toValue.isEmpty())
                        toValue = "1";
                    for(int i = 0; i <  Integer.valueOf(toValue); i++){
                        roomDAO.registerRoom(room);
                    }
                    setResult(ViewHotelActivity.SEND_CODE);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){

            switch (requestCode){
                case IMAGE_GALLERY_REQUEST_1:
                    setPicture(mainPicture,data, pictures);
                    mainPictureCheck = true;
                    break;
                case IMAGE_GALLERY_REQUEST_2:
                    setPicture(picture1,data, pictures);
                    break;
                case IMAGE_GALLERY_REQUEST_3:
                    setPicture(picture2,data, pictures);
                    break;
                case IMAGE_GALLERY_REQUEST_4:
                    setPicture(picture3,data, pictures);
                    break;
                case IMAGE_GALLERY_REQUEST_5:
                    setPicture(picture4,data, pictures);
                    break;
                case IMAGE_GALLERY_REQUEST_6:
                    setPicture(picture5,data, pictures);
                    break;
                case IMAGE_GALLERY_REQUEST_7:
                    setPicture(picture6,data, pictures);
                    break;

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_room_picture1:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_2);
                break;
            case R.id.add_room_picture2:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_3);
                break;
            case R.id.add_room_picture3:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_4);
                break;
            case R.id.add_room_picture4:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_5);
                break;
            case R.id.add_room_picture5:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_6);
                break;
            case R.id.add_room_picture6:
                askForPhotoWithIntent(IMAGE_GALLERY_REQUEST_7);
                break;
        }
    }

}
