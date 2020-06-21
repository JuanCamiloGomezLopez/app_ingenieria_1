package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import model.Inbox;

public class Planear_reunion extends AppCompatActivity {



    Window window;
    TextView fecha_plan, hora_plan;
    int dia, mes, año;
    Calendar mcurrentDate;
    Button botonreunion;
    private RecyclerView recyclerView;
    private AdapterListInbox mAdapter;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;

    private List<Inbox> items = new ArrayList<>();
    private FloatingActionButton fab_Load;

    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    String camilo1="",camilo2="";
  //  String ip="192.168.102.61";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planear_reunion);

        final String ip = getIntent().getExtras().getString("ipwifi");

       // getIP();

        fecha_plan = findViewById(R.id.text_fecha_plan);
        mcurrentDate = Calendar.getInstance();
        dia = mcurrentDate.get(Calendar.MONTH);
        mes = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        año = mcurrentDate.get(Calendar.YEAR);
        mes = mes + 1;
        // fecha_plan.setText(dia + "/"+mes+"/"+año);

        fecha_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Planear_reunion.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        fecha_plan.setText(dayOfMonth + "/" + month + "" + year);
                    }
                }, año, mes, dia);
                datePickerDialog.show();
            }
        });

        // cambiar color a barra inferior del cel
        this.window = getWindow();
        String primary = "#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        botonreunion=(Button)findViewById(R.id.boton_reun);

        botonreunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteInboxes();

            }
        });
        rq= Volley.newRequestQueue(this);

      //  addItems();

        insertarusuario("http://"+ip+"/login/buscar_usuarios.php");
        initComponent();
      //  Toast.makeText(this, "Selecciones los participantes de la reunion", Toast.LENGTH_SHORT).show();



    }



    private void insertarusuario (String URL){
        final String ip = getIntent().getExtras().getString("ipwifi");
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                items.clear();
                for (int i = 1; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);


                        items.add(new Inbox(1,"http://"+ip+"/"+jsonObject.getString("imagen"),jsonObject.getString("name"),jsonObject.getString("user"),jsonObject.getString("area"),"area", R.color.red_500));
                        mAdapter = new AdapterListInbox(Planear_reunion.this, items);
                        recyclerView.setAdapter(mAdapter);


                        mAdapter.setOnClickListener(new AdapterListInbox.OnClickListener() {
                            @Override
                            public void onItemClick(View view, Inbox obj, int pos) {
                                if (mAdapter.getSelectedItemCount() > 0) {
                                    enableActionMode(pos);
                                } else {
                                    // read the inbox which removes bold from the row
                                    Inbox inbox = mAdapter.getItem(pos);
                                    Toast.makeText(getApplicationContext(), "dejar presioando por favor", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onItemLongClick(View view, Inbox obj, int pos) {
                                enableActionMode(pos);
                            }
                        });


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }                }

                mAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error de conexion planear reunion", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);   }



    private void initComponent() {

        recyclerView =  findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter

        actionModeCallback = new ActionModeCallback();

    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.finish();
           // actionMode.setTitle(String.valueOf(count));
           // actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
    // ************************************************ extraemos los nombres seleccionados
    private void deleteInboxes() {
        String camilo[];


        if (mAdapter.getSelectedItems().size() != 0) {

            List<Integer> selectedItemPositions = mAdapter.getSelectedItems();

            camilo = new String[selectedItemPositions.size()];

            for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {

                camilo[i] = (mAdapter.getItem(selectedItemPositions.get(i)).from);
                // mAdapter.removeData(selectedItemPositions.get(i));
            }
            Toast.makeText(this, Arrays.toString(camilo), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Debe ingresar almenos un praticipante", Toast.LENGTH_SHORT).show();
            return;
        }

    }


        public String getIP(){
            List<InetAddress> addrs;
            String address = "";
            try{
                List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for(NetworkInterface intf : interfaces){
                    addrs = Collections.list(intf.getInetAddresses());
                    for(InetAddress addr : addrs){
                        if(!addr.isLoopbackAddress() && addr instanceof Inet4Address){
                            address = addr.getHostAddress().toUpperCase(new Locale("es", "MX"));
                            Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return address;
        }





       // mAdapter.notifyDataSetChanged();

// *******************************************************************











}
