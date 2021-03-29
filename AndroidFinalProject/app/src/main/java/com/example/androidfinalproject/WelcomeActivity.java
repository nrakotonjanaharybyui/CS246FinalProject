package com.example.androidfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Welcome Activity
 * This is the welcome class which is the first UI displayed to the user
 * Here is where the user enters his/her credentials or create a new account
 */
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView registerLink;
    private EditText emailField, passwordField;
    private Button loginButton;
    private ProgressBar welcomeProgress;

    private FirebaseAuth mAuth;

    /**
     * onCreate
     * Triggered when the page loads itself
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Initializing Views
        registerLink = (TextView) findViewById(R.id.welcome_register_link);
        emailField = (EditText) findViewById(R.id.welcome_email);
        passwordField = (EditText) findViewById(R.id.welcome_password);
        loginButton = (Button) findViewById(R.id.welcome_login_button);
        welcomeProgress = (ProgressBar) findViewById(R.id.welcome_progressBar);
        mAuth = FirebaseAuth.getInstance();

        //Setting on click listener
        registerLink.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        //Hide the default action bar
        getSupportActionBar().hide();


    }


    /**
     * onClick
     * Main Onclick listner
     * Listen to clicks and switches throuhg ids to launch functions
     * @param v (View)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.welcome_register_link:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.welcome_login_button:
                logUser();
                break;
        }
    }

    /**
     * logUser
     * Handles fields' info and logs user
     */
    private void logUser() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if(email.isEmpty()){
            emailField.setError("Please provide an email");
            emailField.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailField.setError("Please enter a valid email");
            emailField.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordField.setError("Password required");
            passwordField.requestFocus();
            return;
        }

        if(password.length() < 6){
            passwordField.setError("Please provide at least 6 characters");
            passwordField.requestFocus();
            return;
        }

        welcomeProgress.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    welcomeProgress.setVisibility(View.GONE);
                    Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                    mainIntent.putExtra("user email", email);
                    startActivity(mainIntent);
                }
                else{
                    Toast.makeText(WelcomeActivity.this, "Login failed. Please try again", Toast.LENGTH_LONG).show();
                    welcomeProgress.setVisibility(View.GONE);
                }
            }
        });
                
                
    }
}