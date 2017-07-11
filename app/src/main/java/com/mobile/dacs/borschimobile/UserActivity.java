package com.mobile.dacs.borschimobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mobile.dacs.borschimobile.model.Cliente;
import com.mobile.dacs.borschimobile.model.Producto;
import com.mobile.dacs.borschimobile.model.Productos;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class UserActivity extends AppCompatActivity {

    private static final String ESPACIO = "      ";
    Button buscarButton;
    EditText buscarEditText;
    ListView buscarLista;
    final Context context = this;
    Productos productos;
    Cliente cliente;
    List <String> productosLista = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
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
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String stringBusqueda = buscarEditText.getText().toString();
                URL endpoint = null;
                Gson gson = new GsonBuilder().create();
                try {
//                    endpoint = new URL("http://192.168.0.104:8080/dacstpi/api/productos?query="+stringBusqueda);
                    endpoint = new URL("http://192.168.0.105:8080/timeGreeting");
                    HttpURLConnection myConnection = (HttpURLConnection) endpoint.openConnection();
                    if (myConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        productos = gson.fromJson(responseBodyReader, Productos.class);
                    } else {
                        // Error handling code goes here
                    }
                    myConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            TimeUnit.SECONDS.sleep(Long.valueOf("5"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Producto producto: productos.getProductos())  {
            productosLista.add(producto.getNombreProducto().substring(0, 25).concat(ESPACIO)
                    .concat(producto.getPrecioUnitarioProducto().toString()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, productosLista);
        buscarLista.setAdapter(adapter);
        buscarLista.setTextFilterEnabled(true);
        buscarLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, final View view,
                                           int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("Comprar..");
                alertDialogBuilder
                        .setMessage("Agregar al carrito?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
////                                listaCarrito.add(((TextView) view).getId());
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
