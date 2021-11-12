package com.example.comp2000_exercise_farmbnb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.comp2000_exercise_farmbnb.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private String AccommodationName;
    private String ArrivalDate;
    private String DepartureDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
    }

    public void LoadFarmhouseDetails(View view) {
        setContentView(R.layout.farmhouse_accomodation);
        AccommodationName = "Farmhouse Accommodation";
    }

    public void LoadShepherdsHutDetails(View view) {
        setContentView(R.layout.shepherds_hut_accomodation);
        AccommodationName = "Luxury Shepherds Hut";
    }

    public void LoadBarnDetails(View view) {
        setContentView(R.layout.barn_accomodation);
        AccommodationName = "Self-Catering Farm";
    }

    public void LoadHomePage(View view) {
        String userEmail = ((TextView) findViewById(R.id.UserEmailAddress)).getText().toString();
        String userPassword = ((TextView) findViewById(R.id.UserPassword)).getText().toString();

        if ((userEmail.equals("")) && (userPassword.equals("")) ){
            PopUpErrorMessage("User email and password is required");
        }
        else{
            setContentView(R.layout.homescreen);
        }
    }

    public void LoadManageBookings(View view) {
        setContentView(R.layout.manage_bookings);
    }

    public void LoadSignUpScreen(View view) {
        String userTitle = ((TextView) findViewById(R.id.UserTitle)).getText().toString();
        String userPassword = ((TextView) findViewById(R.id.UserPassword)).getText().toString();
        String userFirstName = ((TextView) findViewById(R.id.UserFirstName)).getText().toString();
        String userLastName = ((TextView) findViewById(R.id.UserLastName)).getText().toString();
        String userPhoneNumber = ((TextView) findViewById(R.id.UserPhoneNumber)).getText().toString();
        String userHomeAddress = ((TextView) findViewById(R.id.UserHomeAddress)).getText().toString();

        if (
                (userTitle.equals(""))
                        && (userPassword.equals(""))
                        && (userFirstName.equals(""))
                        && (userLastName.equals(""))
                        && (userPhoneNumber.equals(""))
                        && (userHomeAddress.equals(""))
        ){
            PopUpErrorMessage("All fields are required for sign up");
        }
        else{
            setContentView(R.layout.homescreen);
        }
    }

    public void LoadBookingConfirmation(View view) {
        setContentView(R.layout.booking_confirmation);

        ((TextView) findViewById(R.id.accommodation_name_confirm)).setText(AccommodationName);
        ((TextView) findViewById(R.id.arrival_date_confirm)).setText(ArrivalDate);
        ((TextView) findViewById(R.id.departure_date_confirm)).setText(DepartureDate);

        AccommodationName = ((TextView) findViewById(R.id.accommodation_name_confirm)).getText().toString();
    }

    public void LoadOrderConfirmation(View view) {setContentView(R.layout.order_confirmation);}

    public void PopUpErrorMessage(String errormessage) {
        Toast error_message = Toast.makeText(MainActivity.this, errormessage, Toast.LENGTH_LONG);
        error_message.show();
    }

    public void LoadPaymentDetails(View view) {

        String UserCardNumber = ((TextView) findViewById(R.id.UserCardNumber)).getText().toString();
        String UserCardExpiry = ((TextView) findViewById(R.id.UserCardExpiry)).getText().toString();
        String UserCardCVC = ((TextView) findViewById(R.id.UserCardCVC)).getText().toString();

        if (
                (UserCardNumber.equals(""))
                        && (UserCardExpiry.equals(""))
                        && (UserCardCVC.equals(""))
        ){
            PopUpErrorMessage("All fields are required for payment");
        }
        else{
            setContentView(R.layout.payment_screen);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void LoadAvailabiltyChecker(View view) throws ParseException {
        setContentView(R.layout.availability_checker);
        try{
            SetupCalenderDatePicker(view);
        }
        catch(Exception e)
        {
            PopUpErrorMessage(e.toString());
        }

        Spinner arrivaldate = (Spinner) findViewById(R.id.arrival_date);
        TextView departuredate = (TextView) findViewById(R.id.departure_date);
        SimpleDateFormat dateFormatText = new SimpleDateFormat("dd-MMMM-yyyy");

        arrivaldate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            Date d;
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                try {
                    d = dateFormatText.parse(arrivaldate.getSelectedItem().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(d);
                    calendar.add(Calendar.DATE,7);
                    departuredate.setText(dateFormatText.format(calendar.getTime()));

                    ArrivalDate = ((Spinner) findViewById(R.id.arrival_date)).getSelectedItem().toString();
                    DepartureDate = ((TextView) findViewById(R.id.departure_date)).getText().toString();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        TextView AccommodationName_textview = (TextView) findViewById(R.id.accommodation_name);
        AccommodationName_textview.setText(AccommodationName);

        TextView AccommodationName_confirm  = (TextView) findViewById(R.id.accommodation_name);
        Spinner ArrivalDate_confirm = (Spinner) findViewById(R.id.arrival_date);
        TextView DepartureDate_confirm = (TextView) findViewById(R.id.departure_date);

        AccommodationName = AccommodationName_confirm.getText().toString();
        ArrivalDate = ArrivalDate_confirm.getSelectedItem().toString();
        DepartureDate = DepartureDate_confirm.getText().toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SetupCalenderDatePicker(View view) throws ParseException {
        try{
        try
        {
            // do something that throws an exception here
        Spinner dropdown = findViewById(R.id.arrival_date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        ArrayList<String> SaturdayDates = new ArrayList<String>();

        Calendar calendar = Calendar.getInstance();
        Date TodayDate = calendar.getTime();
        Integer DaysTillSat;


        String DayofWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(TodayDate.getTime());
        if (DayofWeek=="Monday"){
            DaysTillSat = 5;
        }
        else if (DayofWeek=="Tuesday"){
            DaysTillSat = 4;
        }
        else if (DayofWeek=="Wednesday"){
            DaysTillSat = 3;
        }
        else if (DayofWeek=="Thursday"){
            DaysTillSat = 2;
        }
        else if (DayofWeek=="Friday"){
            DaysTillSat = 1;
        }
        else if (DayofWeek=="Saturday"){
            DaysTillSat = 0;
        }
        else if (DayofWeek=="Sunday"){
            DaysTillSat = 6;
        }
        else{
            DaysTillSat = 0;
        }

        calendar.add(Calendar.DATE, DaysTillSat);
        SaturdayDates.add(dateFormat.format(calendar.getTime()));

        String nextSaturday;
        Date nextSaturdayDate;
        Integer DaysMultiply;

        for(int i = 1; i < 10; i++) {
            Calendar c = Calendar.getInstance();
            DaysMultiply = (i * 7)+DaysTillSat;
            c.add(Calendar.DATE, DaysMultiply);
            nextSaturdayDate = c.getTime();
            nextSaturday = dateFormat.format(nextSaturdayDate);
            SaturdayDates.add(nextSaturday);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, SaturdayDates);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(arrayAdapter);
        }
        catch(Exception e)
        {
            Log.e("Some Tag", e.getMessage(), e);
        }
        }
        catch(Exception e)
        {
            PopUpErrorMessage(e.toString());
        }
    }

}
