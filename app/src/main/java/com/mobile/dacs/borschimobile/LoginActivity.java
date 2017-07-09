package com.mobile.dacs.borschimobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button loginbutton;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginbutton = (Button) findViewById(R.id.login);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonClicked();
            }
        });
    }

    private void onLoginButtonClicked(){
        // Retrieve the text entered from the EditText
        usernametxt = username.getText().toString();
        passwordtxt = password.getText().toString();

        // Send data to WS for verification
        if (true) {
            // If user exist and authenticated, send user to Welcome.class
            Intent intent = new Intent(
                    LoginActivity.this,
                    UserActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),
                    "Successfully Logged in",
                    Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "No such user exist, please signup",
                    Toast.LENGTH_LONG).show();
        }
    }
}
