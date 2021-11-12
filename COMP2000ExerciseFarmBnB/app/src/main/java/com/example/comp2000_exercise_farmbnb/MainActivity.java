package com.example.comp2000_exercise_farmbnb;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.comp2000_exercise_farmbnb.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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
    }

    public void LoadShepherdsHutDetails(View view) {setContentView(R.layout.shepherds_hut_accomodation);}

    public void LoadBarnDetails(View view) {
        setContentView(R.layout.barn_accomodation);
    }

    public void LoadHomePage(View view) {
        setContentView(R.layout.homescreen);
    }

    public void LoadManageBookings(View view) {
        setContentView(R.layout.manage_bookings);
    }

    public void LoadSignUpScreen(View view) {
        setContentView(R.layout.sign_up_screen);
    }

    public void LoadBookingConfirmation(View view) {
        setContentView(R.layout.booking_confirmation);

        ((TextView) findViewById(R.id.accommodation_name_confirm)).setText(AccommodationName);
        ((TextView) findViewById(R.id.arrival_date_confirm)).setText(ArrivalDate);
        ((TextView) findViewById(R.id.departure_date_confirm)).setText(DepartureDate);
    }

    public void LoadOrderConfirmation(View view) {setContentView(R.layout.order_confirmation);}

    public void LoadPaymentDetails(View view) {
        setContentView(R.layout.payment_screen);
    }

    public void LoadAvailabiltyChecker(View view) {
        setContentView(R.layout.availability_checker);

        TextView AccommodationName_textview = (TextView) findViewById(R.id.accommodation_name);
        AccommodationName_textview.setText(view.getTag().toString());

        TextView AccommodationName_confirm  = (TextView) findViewById(R.id.accommodation_name);
        TextView ArrivalDate_confirm = (TextView) findViewById(R.id.arrival_date);
        TextView DepartureDate_confirm = (TextView) findViewById(R.id.departure_date);

        AccommodationName = AccommodationName_confirm.getText().toString();
        ArrivalDate = ArrivalDate_confirm.getText().toString();
        DepartureDate = DepartureDate_confirm.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}