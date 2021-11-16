package com.example.comp2000_exercise_farmbnb;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.comp2000_exercise_farmbnb.databinding.ActivityMainBinding;
import com.example.comp2000_exercise_farmbnb.databinding.BarnAccomodationBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private String AccommodationName;
    private String ArrivalDate;
    private String DepartureDate;
    private String CurrentUser;
    private String ChannelID = "1";

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

    public void LoadEditAccount(View view) {
        setContentView(R.layout.edit_account);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void UpdateAccountDetails(View view) {
        String UpdatedPassword = ((TextView)findViewById(R.id.EditUserInputPassword)).getText().toString();
        String UpdatedEmail = ((TextView)findViewById(R.id.EditUserInputEmail)).getText().toString();

        if(UpdatedPassword!="")
        {
            UserDetails.UserLoginDetails.replace(CurrentUser,UserDetails.UserLoginDetails.get(CurrentUser),UpdatedPassword);
        }
        else
        {
            //ignore
        }

        if(UpdatedEmail!="")
        {
            UserDetails.UserLoginDetails.replace(CurrentUser,CurrentUser,UpdatedEmail);
        }
        else
        {
            //ignore
        }

        setContentView(R.layout.homescreen);

    }

    public void DeleteAccount(View view) {
        setContentView(R.layout.login_screen);
        UserDetails.UserLoginDetails.remove(CurrentUser);
        CurrentUser = "";
    }

    public void LoadBarnDetails(View view) {
        setContentView(R.layout.barn_accomodation);
        AccommodationName = "Self-Catering Farm";
    }

    public void LogOut(View view) {
        setContentView(R.layout.login_screen);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void LoadHomePage(View view) {
        String userPassword;
        String userEmail;
        setContentView(R.layout.homescreen);
        try {

            if (Objects.isNull(((TextView) findViewById(R.id.UserEmailAddress))) && (Objects.isNull((TextView) findViewById(R.id.UserPassword)))) {
                //ignore
            } else {
                userEmail = ((TextView) findViewById(R.id.UserEmailAddress)).getText().toString();
                userPassword = ((TextView) findViewById(R.id.UserPassword)).getText().toString();

                if ((userEmail.equals("")) && (userPassword.equals(""))) {
                    PopUpErrorMessage("User email and password is required");
                } else if (UserDetails.UserLoginDetails.containsKey(userEmail)) {
                    if (userPassword.equals((String) UserDetails.UserLoginDetails.get(userEmail))) {
                        setContentView(R.layout.homescreen);
                    } else {
                        PopUpErrorMessage("User email or password is incorrect");
                    }
                } else {
                    PopUpErrorMessage("User email or password is incorrect");
                }

                BookingDepartureToday();
            }
        }
        catch(Exception e){
            PopUpErrorMessage(e.toString());
        }
    }

    @SuppressLint("ResourceType")
    public void LoadManageBookings(View view) throws ParseException {
        setContentView(R.layout.manage_bookings);

            SimpleDateFormat dateFormatDate = new SimpleDateFormat("dd MMMM yyyy");
            SimpleDateFormat dateFormatText = new SimpleDateFormat("dd MMM yyyy");
            TableLayout tl = (TableLayout) findViewById(R.id.manage_bookings_table);

            TableRow tr_header = new TableRow(this);
            tr_header.setId(10);
            tr_header.setBackgroundColor(Color.parseColor("#A6C1AC"));
            tr_header.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView Accommodation_Name = new TextView(this);
            Accommodation_Name.setId(20);
            Accommodation_Name.setText("Accommodation");
            Accommodation_Name.setPadding(10, 10, 10, 10);
            Accommodation_Name.setTextSize(22);
            Accommodation_Name.setBackgroundColor(Color.parseColor("#97ad9c"));
            Accommodation_Name.setTextColor(Color.WHITE);
            Accommodation_Name.setPadding(5, 5, 5, 5);
            tr_header.addView(Accommodation_Name);

            TextView Arrival_Date = new TextView(this);
            Arrival_Date.setId(21);//
            Arrival_Date.setText("Arrival");
            Arrival_Date.setPadding(10, 10, 10, 10);
            Arrival_Date.setBackgroundColor(Color.parseColor("#97ad9c"));
            Arrival_Date.setTextSize(22);
            Arrival_Date.setTextColor(Color.WHITE);
            Arrival_Date.setPadding(5, 5, 5, 5);
            tr_header.addView(Arrival_Date);

            TextView Departure_Date = new TextView(this);
            Departure_Date.setId(22);
            Departure_Date.setText("Departure");
            Departure_Date.setPadding(10, 10, 10, 10);
            Departure_Date.setTextAlignment(1);
            Departure_Date.setBackgroundColor(Color.parseColor("#97ad9c"));
            Departure_Date.setTextSize(22);
            Departure_Date.setTextColor(Color.WHITE);
            Departure_Date.setPadding(5, 5, 5, 5);
            tr_header.addView(Departure_Date);

            tl.addView(tr_header, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,                    //part4
                    TableRow.LayoutParams.MATCH_PARENT));

        if(UserDetails.UserBookingDetails.equals("")) {

            ArrayList<String> BookingRecord = UserDetails.UserBookingDetails.getStringArrayList(CurrentUser);
            TextView[] textArray = new TextView[((Integer) ((BookingRecord.size()) / 3))];
            TableRow[] tr_head = new TableRow[((Integer) ((BookingRecord.size()) / 3))];

            for (int i = 0; i < ((Integer) ((BookingRecord.size()) / 3)); i++) {
                Date arrival_date_date = dateFormatDate.parse(BookingRecord.get(1).toString());
                Date departure_date_date = dateFormatDate.parse(BookingRecord.get(2).toString());

                String accommodation_name = BookingRecord.get(0);
                String arrival_date = dateFormatText.format(arrival_date_date);
                String departure_date = dateFormatText.format(departure_date_date);

                tr_head[i] = new TableRow(this);
                tr_head[i].setId(i + 1);
                tr_head[i].setBackgroundColor(Color.parseColor("#709277"));
                tr_head[i].setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                textArray[i] = new TextView(this);
                textArray[i].setId(i + 111);
                textArray[i].setWidth(700);
                textArray[i].setText(accommodation_name);
                textArray[i].setTextColor(Color.WHITE);
                textArray[i].setPadding(10, 10, 10, 10);
                tr_head[i].addView(textArray[i]);

                textArray[i] = new TextView(this);
                textArray[i].setId(i + 112);
                textArray[i].setText(arrival_date);
                textArray[i].setTextColor(Color.WHITE);
                textArray[i].setPadding(10, 10, 10, 10);
                tr_head[i].addView(textArray[i]);

                textArray[i] = new TextView(this);
                textArray[i].setId(i + 113);
                textArray[i].setText(departure_date);
                textArray[i].setTextColor(Color.WHITE);
                textArray[i].setPadding(10, 10, 10, 10);
                tr_head[i].addView(textArray[i]);

                tl.addView(tr_head[i], new TableLayout.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

            }
        }
        else{

        }

    }

    public void CreateAccount(View view) {
        String userTitle = ((TextView) findViewById(R.id.UserTitle)).getText().toString();
        String userFirstName = ((TextView) findViewById(R.id.UserFirstName)).getText().toString();
        String userLastName = ((TextView) findViewById(R.id.UserLastName)).getText().toString();
        String userPhoneNumber = ((TextView) findViewById(R.id.UserPhoneNumber)).getText().toString();
        String userHomeAddress = ((TextView) findViewById(R.id.UserHomeAddress)).getText().toString();
        String userEmailAddress = ((TextView) findViewById(R.id.UserInputEmail)).getText().toString();
        String userPassword = ((TextView) findViewById(R.id.UserInputPassword)).getText().toString();

        if (
                (userTitle.equals(""))
                        && (userPassword.equals(""))
                        && (userFirstName.equals(""))
                        && (userLastName.equals(""))
                        && (userPhoneNumber.equals(""))
                        && (userHomeAddress.equals(""))
                        && (userEmailAddress.equals(""))
        ){
            PopUpErrorMessage("All fields are required for sign up");
        }
        else{
            UserDetails.UserLoginDetails.put(userEmailAddress,userPassword);
            setContentView(R.layout.homescreen);
        };
    }

    public void LoadSignUpScreen(View view) {
        setContentView(R.layout.sign_up_screen);
    }

    public void LoadBookingConfirmation(View view) {
        setContentView(R.layout.booking_confirmation);

        ((TextView) findViewById(R.id.accommodation_name_confirm)).setText(AccommodationName);
        ((TextView) findViewById(R.id.arrival_date_confirm)).setText(ArrivalDate);
        ((TextView) findViewById(R.id.departure_date_confirm)).setText(DepartureDate);

        AccommodationName = ((TextView) findViewById(R.id.accommodation_name_confirm)).getText().toString();
    }

    public void PopUpErrorMessage(String errormessage) {
        Toast error_message = Toast.makeText(MainActivity.this, errormessage, Toast.LENGTH_LONG);
        error_message.show();
    }
    public void LoadPaymentDetails(View view) {setContentView(R.layout.payment_screen);}

    public void LoadOrderConfirmation(View view) {

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
            ArrayList<String> BookingRecordInfo = new ArrayList<String>();
            BookingRecordInfo.add(AccommodationName);
            BookingRecordInfo.add(ArrivalDate);
            BookingRecordInfo.add(DepartureDate);

            UserDetails.UserBookingDetails.putStringArrayList(CurrentUser, BookingRecordInfo);

            setContentView(R.layout.order_confirmation);
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
        SimpleDateFormat dateFormatText = new SimpleDateFormat("dd MMMM yyyy");

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        ArrayList<String> SaturdayDates = new ArrayList<String>();

        Calendar calendar = Calendar.getInstance();
        Integer DaysTillSat = 0;


        int  DayofWeek = calendar.get(Calendar.DAY_OF_WEEK);
            switch (DayofWeek) {
                case Calendar.MONDAY:
                    DaysTillSat = 5;
                    break;
                case Calendar.TUESDAY:
                    DaysTillSat = 4;
                    break;
                case Calendar.WEDNESDAY:
                    DaysTillSat = 3;
                    break;
                case Calendar.THURSDAY:
                    DaysTillSat = 2;
                    break;
                case Calendar.FRIDAY:
                    DaysTillSat = 1;
                    break;
                case Calendar.SATURDAY:
                    DaysTillSat = 0;
                    break;
                case Calendar.SUNDAY:
                    DaysTillSat = 6;
                    break;
            }
        calendar.add(Calendar.DATE, DaysTillSat);
        SaturdayDates.add(dateFormat.format(calendar.getTime()));

        String nextSaturday;
        Date nextSaturdayDate;
        Integer DaysMultiply;

        for(int i = 1; i < 52; i++) {
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

    private void NotificationPopUp(String Title, String Content) {
        createNotificationChannel();
        Intent fullScreenIntent = new Intent(this, MainActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,ChannelID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(Title)
                .setContentText(Content)
                .setFullScreenIntent(fullScreenPendingIntent,true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ChannelID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void BookingDepartureToday(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar calendar = Calendar.getInstance();
        String TodayDate = dateFormat.format(calendar.getTime());
        if(UserDetails.UserBookingDetails.equals("")) {
            //ignore
        }
        else{
            ArrayList<String> BookingRecord = UserDetails.UserBookingDetails.getStringArrayList(CurrentUser);
            String arrival_date = BookingRecord.get(1).toString();
            String departure_date = BookingRecord.get(2).toString();

            if(arrival_date == TodayDate){
                NotificationPopUp("Farm BnB","Today is your check in date, we look forward to seeing you.");
            }
            else if(departure_date == TodayDate){
                NotificationPopUp("Farm BnB","Today is your departure date, we hope you enjoyed your stay.");
            }
            else{
                //Ignore
            }
        }
    }

}
