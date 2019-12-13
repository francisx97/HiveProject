package com.example.hive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText RegEmailNametxt;
    private EditText RegPasswordtxt;
    private EditText RegConfirmPasstxt;
    private Button RegCreateAccButton;
    private Button RegHaveAccButton;


    private FirebaseAuth mAuth;

    private ProgressBar RegProgressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        //linking variables names to find desgin

        RegEmailNametxt = (EditText) findViewById(R.id.reg_email_txt);
        RegPasswordtxt = (EditText) findViewById(R.id.reg_pass_txt);
        RegConfirmPasstxt = (EditText) findViewById(R.id.reg_confirm_pass_txt);
        RegCreateAccButton = (Button) findViewById(R.id.reg_create_btn);
        RegHaveAccButton = (Button) findViewById(R.id.reg_have_acc_btn);
        RegProgressbar = (ProgressBar) findViewById(R.id.reg_progressBar);


        RegHaveAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendToLoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(sendToLoginIntent);
            }
        });


        //creating click listener for creating account btn
        RegCreateAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = RegEmailNametxt.getText().toString();
                String pass = RegPasswordtxt.getText().toString();
                String confirmPass = RegConfirmPasstxt.getText().toString();

                //checking to see if any field is empty first
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirmPass)) {


                    //checks if the pass is equaled to confirm password
                    if (pass.equals(confirmPass)) {


                        //creates account with details provided
                        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                //setting the progress bar to visible
                                RegProgressbar.setVisibility(View.VISIBLE);


                                if (task.isSuccessful()) {
                                    sendToMain();

                                } else {

                                    //showing error Message from Toast
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                                }
                                RegProgressbar.setVisibility(View.INVISIBLE);

                            }
                        });

                    } else {
                        Toast.makeText(RegisterActivity.this, "Confirm Password and Password Feilds dont match please try again", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }


    //checking if the user is logged in or not

    @Override
    protected void onStart() {

        super.onStart();

        //sending user to Main page if they are not signed in

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            sendToMain();
        }

    }

    private void sendToMain() {

        //creating method to send user to Main page


        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
