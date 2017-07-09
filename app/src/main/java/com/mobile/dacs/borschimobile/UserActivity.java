package com.mobile.dacs.borschimobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class UserActivity extends AppCompatActivity {

    Button buscarButton;
    EditText buscarEditText;
    ListView buscarLista;
    final Context context = this;
    List productosLista = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        buscarEditText = (EditText) findViewById(R.id.buscarEditText);
        buscarButton = (Button) findViewById(R.id.buscarButton);
        buscarLista = (ListView) findViewById(R.id.productosLista);

        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarButtonClicked();
            }
        });
    }

    private void buscarButtonClicked() {
        String buscarString = buscarEditText.getText().toString();
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                // Create URL
//                URL endpoint = null;
//                try {
//                    endpoint = new URL("http://192.168.1.50:8080/dacstpi-g3/api/tarjeta");
//                    HttpURLConnection myConnection =
//                            (HttpURLConnection) endpoint.openConnection();
//
//                    if (myConnection.getResponseCode() == 200) {
//                        InputStream responseBody = myConnection.getInputStream();
//                        InputStreamReader responseBodyReader =
//                                new InputStreamReader(responseBody, "UTF-8");
//                        JsonReader jsonReader = new JsonReader(responseBodyReader);
//                        jsonReader.beginArray();
//                        jsonReader.beginObject();
//                        while (jsonReader.hasNext()) {
//                            String key = jsonReader.toString();
//                            System.out.print(key);
//                        }
//                    } else {
//                        // Error handling code goes here
//                    }
//                    myConnection.disconnect();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        String[] FRUITS = new String[] { "Apple", "Avocado", "Banana",
                "Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
                "Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, FRUITS);
        buscarLista.setAdapter(adapter);
        buscarLista.setTextFilterEnabled(true);
        buscarLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, final View view,
                                           int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(
//                        UserActivity.this,
//                        PopUpCarritoActivity.class);
//                startActivity(intent);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("Comprar..");
                alertDialogBuilder
                        .setMessage("Agregar al carrito?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                productosLista.add(((TextView) view).getText());
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return false;
            }
        });
    }
}
