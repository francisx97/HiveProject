package com.example.hive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private EditText LoginEmailNametxt;
    private EditText LoginPasswordtxt;
    private Button LoginButton;
    private Button LoginRegButton;

    private FirebaseAuth mAuth;

    private ProgressBar LoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        LoginEmailNametxt = (EditText) findViewById(R.id.Login_Email);
        LoginPasswordtxt = (EditText) findViewById(R.id.Login_Password);
        LoginButton = (Button) findViewById(R.id.Login_Button);
        LoginRegButton = (Button) findViewById(R.id.Login_Reg_Button);
        LoginProgress = (ProgressBar) findViewById(R.id.Login_progress_Bar);

// giving the login button its function, once the sure types in email and password text we use it for the login.
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String loginEmail = LoginEmailNametxt.getText().toString();
                String loginPass = LoginPasswordtxt.getText().toString();

                //checcking to see if the feilds are not emtpy
                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)){
                    //  setting the progress bar to visible.
                    LoginProgress.setVisibility(View.VISIBLE);

                    //grabbing the details and checking if the task worked or not.
                    mAuth.signInWithEmailAndPassword(loginEmail,loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                                sendToMain();

                            }else {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this,"Error : " + errorMessage,Toast.LENGTH_LONG).show();


                            }
                            LoginProgress.setVisibility(View.INVISIBLE);
                        }

                    });
                }
            }
        });


    }
    // checks if the user is signed in or not

    @Override
    protected void onStart() {
        super.onStart();
        //checking if the user is signed in first
        FirebaseUser currentUser  = mAuth.getCurrentUser();

        if (currentUser != null){
            //new method to send user to main Page
            sendToMain();



        }
    }
// sending users to the main activity
    private void sendToMain() {
        Intent mainIntent = new Intent( LoginActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
