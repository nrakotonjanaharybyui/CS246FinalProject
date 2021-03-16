package com.example.androidfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText fullNameField, addressField, emailField, passwordField;
    private Button registerButton;
    private ProgressBar registerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        fullNameField = (EditText) findViewById(R.id.register_fullName);
        addressField = (EditText) findViewById(R.id.register_address);
        emailField = (EditText) findViewById(R.id.register_email);
        passwordField = (EditText) findViewById(R.id.register_password);
        registerButton = (Button) findViewById(R.id.register_button);
        registerProgress = (ProgressBar) findViewById(R.id.register_progressBar);

        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register_button:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String fullName = fullNameField.getText().toString().trim();
        String address = addressField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();


        //Check that all fields are filled correctly and fits the correct pattern
        if(fullName.isEmpty()){
            fullNameField.setError("Full name is required!");
            fullNameField.requestFocus();
            return;
        }
        if(address.isEmpty()){
            addressField.setError("Address is required!");
            addressField.requestFocus();
            return;
        }
        if(email.isEmpty()){
            emailField.setError("Email is required!");
            emailField.requestFocus();
            return;
        }
        //checks if the email fits the right pattern
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailField.setError("Please provide a valid email address");
            emailField.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordField.setError("Password is required");
            passwordField.requestFocus();
            return;
        }
        //Checks if password is less than 6 characters
        if(password.length() < 6){
            passwordField.setError("Please provide at least 6 characters");
            passwordField.requestFocus();
            return;
        }

        registerProgress.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User currentUser = new User(fullName, address, email);

                    //Send user to database
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                registerProgress.setVisibility(View.GONE);

                                //Redirect user to login Layout
                                startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Registration failure. Please try again", Toast.LENGTH_LONG).show();
                                registerProgress.setVisibility(View.GONE);
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(RegisterActivity.this, "Registration failure. Please try again", Toast.LENGTH_LONG).show();
                    registerProgress.setVisibility(View.GONE);
                }
            }
        });

    }
}