package com.mobile.dacs.borschimobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobile.dacs.borschimobile.model.Cliente;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    Button loginbutton;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;
    Cliente cliente;

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
        usernametxt = username.getText().toString();
        passwordtxt = password.getText().toString();

            AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                URL endpoint = null;
                Gson gson = new GsonBuilder().create();
                HttpURLConnection myConnection = null;
                try {
//                    endpoint = new URL("https://api.github.com/users/mralexgray/repos");

//                    usernametxt = "pablord14";
                    usernametxt = "alanfiz92";
//                    passwordtxt = "rdpjgs07";
                    passwordtxt = "34gw4ef34";
//                    endpoint = new URL("http://192.168.0.104:8080/dacstpi/api/login?user="+usernametxt+"&pass="+passwordtxt);
                    endpoint = new URL("http://192.168.0.105:8080/greeting");
//                    endpoint = new URL("http://192.168.0.104:8080/dacstpi/api/login?user=alanfiz92&pass=34gw4ef34");
//                    endpoint = new URL("http://192.168.43.252:8080/dacstpi/api/tarjeta");
                    myConnection = (HttpURLConnection) endpoint.openConnection();

                    if (myConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader =
                                new InputStreamReader(responseBody, "UTF-8");
                        cliente = gson.fromJson(responseBodyReader, Cliente.class);
                    } else {
                        cliente = null;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    myConnection.disconnect();
                }
            }
        });

        try {
            TimeUnit.SECONDS.sleep(Long.valueOf("4"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (cliente != null) {
            Intent intent = new Intent(
                    LoginActivity.this,
                    UserActivity.class);
            intent.putExtra("cliente", cliente);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),
                    "Logeo Exitoso",
                    Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Usuario inexistente",
                    Toast.LENGTH_LONG).show();
        }
    }
}
