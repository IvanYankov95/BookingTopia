package bg.ittalents.bookingtopia.controller.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import bg.ittalents.bookingtopia.R;

public class SearchResultActivity extends AbstractDrawerActivity {

    Calendar calendar;
    private static String selectMaxGuests;

    private static LinearLayout lm ;
    private static EditText searchField;
    private static EditText checkInDate;
    private static EditText checkOutDate;
    private static Spinner guestsNumber;
    private static Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_drawer);
        onCreateDrawer();
        getSupportActionBar().setTitle("Search");

        ArrayList<Integer> guestsNumberSpinner = new ArrayList<>();
        guestsNumberSpinner.add(1);
        guestsNumberSpinner.add(2);
        guestsNumberSpinner.add(3);
        guestsNumberSpinner.add(4);
        guestsNumberSpinner.add(5);
        guestsNumberSpinner.add(6);

        lm = (LinearLayout) findViewById(R.id.search_layout);
        searchField = (EditText) findViewById(R.id.search_field);

        guestsNumber = (Spinner)  findViewById(R.id.guests_number_spinner);
        searchButton = (Button) findViewById(R.id.search_button);

        checkInDate = (EditText) findViewById(R.id.check_in_date);
        checkOutDate = (EditText) findViewById(R.id.check_out_date);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO

                //search method
            }
        });


        checkInDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    picDate((EditText) v);
            }
        });

        checkOutDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    picDate((EditText) v);
            }
        });

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, guestsNumberSpinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        guestsNumber.setAdapter(dataAdapter);

        guestsNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectMaxGuests = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

}
