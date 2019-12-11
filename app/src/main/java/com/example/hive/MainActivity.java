package com.example.hive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.Menu;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;




import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private Toolbar MainToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   MainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
   setSupportActionBar(MainToolbar);

    getSupportActionBar().setTitle("Hive");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){

            Intent loginIntent = new Intent( MainActivity.this,LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }

    //adding menu bar to main activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        //inflating toolbar with the options menu

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;

    }
}
