package com.example.hive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private Toolbar MainToolbar;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

   MainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
   setSupportActionBar(MainToolbar);

    getSupportActionBar().setTitle("Hive");

        //initialize and assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.botom_nav);

        //set Homes selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //performmItem selected

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:

                        return true;

                    case R.id.payment:
                        startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){

            //sending the user to the login page
            sendToLogin();
        }
    }

    //adding menu bar to main activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        //inflating toolbar with the options menu

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;

    }

    //saying when any Item in the menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout_btn:
                //creating logout method in toolbar
                logout();


                return true;


            default:
                return false;
        }


    }

    //making sure the user signs out first and then runs sendToLogin method
    private void logout() {
        mAuth.signOut();
        sendToLogin();
    }

    //creating the method to send users to login page
    private void sendToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

}
