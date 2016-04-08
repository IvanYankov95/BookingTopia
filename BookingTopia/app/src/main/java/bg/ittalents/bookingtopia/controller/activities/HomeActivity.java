package bg.ittalents.bookingtopia.controller.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;

import bg.ittalents.bookingtopia.R;
import model.CalendarHelper;

public class HomeActivity extends AbstractDrawerActivity {

    Calendar calendar;
    private static String selectStars;

    boolean areStarsSellected;

    private static LinearLayout lm ;
    private static EditText searchField;
    private static EditText checkInDate;
    private static EditText checkOutDate;
    private static Spinner starsNumber;
    private static Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Home");

        ArrayList<Integer> starsNumber = new ArrayList<>();
        starsNumber.add(1);
        starsNumber.add(2);
        starsNumber.add(3);
        starsNumber.add(4);
        starsNumber.add(5);
        starsNumber.add(6);

        Log.e("DATE ON CREATE", CalendarHelper.fromDate.toString() + " " + CalendarHelper.toDate.toString());

        lm = (LinearLayout) findViewById(R.id.search_layout);
        searchField = (EditText) findViewById(R.id.search_field);

        HomeActivity.starsNumber = (Spinner)  findViewById(R.id.stars_number_spinner);
        searchButton = (Button) findViewById(R.id.search_button);

        checkInDate = (EditText) findViewById(R.id.check_in_date);
        checkOutDate = (EditText) findViewById(R.id.check_out_date);

        checkInDate.setText(CalendarHelper.fromDate.toString());
        checkOutDate.setText(CalendarHelper.toDate.toString());

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HotelListActivity.class);
                intent.putExtra("search_name", searchField.getText().toString());
                intent.putExtra("search_stars", selectStars);
                startActivity(intent);
            }
        });


        checkInDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    picDate((EditText) v, true);
            }
        });

        checkOutDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    picDate((EditText) v, false);

            }
        });

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, starsNumber);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HomeActivity.starsNumber.setAdapter(dataAdapter);

        HomeActivity.starsNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectStars = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectStars = "nothing";
            }
        });

    }


    private void picDate(final EditText edt, final boolean isFromDate) {
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

                if(isFromDate)
                    CalendarHelper.fromDate = new LocalDate(year, monthOfYear, dayOfMonth);
                else
                    CalendarHelper.toDate = new LocalDate(year, monthOfYear, dayOfMonth);

                Log.e("DATE", CalendarHelper.fromDate.toString() + " " + CalendarHelper.toDate.toString());

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

}
