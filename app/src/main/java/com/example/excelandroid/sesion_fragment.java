package com.example.excelandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class sesion_fragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    Button ingresar1;
    TextView pasar_regstro,email,password;
    RequestQueue rq;
    JsonRequest jrq;
    Context context;
    RadioButton sesion;
    private ProgressBar barraprogreso;
    private ProgressDialog progressDialog;

    private boolean esactivido;

    private static final String STRING_PREFRENCES="com.example.excelandroid";
    private static final String PREFERENCE_ESTADO_SECCION="estado_button";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_sesion_fragment,container,false);


        ingresar1=vista.findViewById(R.id.ingresar);
        pasar_regstro=vista.findViewById(R.id.textView6);
        email=vista.findViewById(R.id.reg_email);
        password=vista.findViewById(R.id.reg_password);
        rq= Volley.newRequestQueue(getContext());
        sesion=vista.findViewById(R.id.radioButton);

        barraprogreso=new ProgressBar(getContext());
        progressDialog = new ProgressDialog(getContext());


      //if (obtenerestado()){
         // Toast.makeText(getContext(), "verdadero", Toast.LENGTH_SHORT).show();
       // Intent intent = new Intent(getActivity(), Main_principal.class);
   // startActivity(intent);
        //  Objects.requireNonNull(getActivity()).finish();


     //  }

        ingresar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //  iniciarseccion();

              Intent intent = new Intent(getActivity(), Main_principal.class);
              startActivity(intent);

            }
        });

        pasar_regstro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Registrar_usuario.class);
                startActivity(intent);
            }
        });

        esactivido=sesion.isChecked();

        sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(esactivido){
                    sesion.setChecked(false);
                }
                esactivido=sesion.isChecked();
            }
        });

        return vista;
    }





    public  void guardarestadobutton (){

        SharedPreferences preferences= Objects.requireNonNull(this.getActivity()).getSharedPreferences(STRING_PREFRENCES,Context.MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_ESTADO_SECCION,sesion.isChecked()).apply();

    }


    public boolean obtenerestado(){
        SharedPreferences preferences= Objects.requireNonNull(this.getActivity()).getSharedPreferences(STRING_PREFRENCES,Context.MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCE_ESTADO_SECCION,false);

    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(), "No se ha enconctrado el usuario: ", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {

        progressDialog.dismiss();


        User usuario = new User();




        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObjectson=null;

        try {
            assert jsonArray != null;
            jsonObjectson=jsonArray.getJSONObject(0);
            usuario.setUser(jsonObjectson.optString("user"));
            usuario.setPwd(jsonObjectson.optString("pwd"));
            usuario.setName(jsonObjectson.optString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }




       // Bundle datosAEnviar = new Bundle();
        //datosAEnviar.putLong("id", 123L);
       // datosAEnviar.putInt("edad", 21);
       // datosAEnviar.putString("nombre", usuario.getName());
       // Fragment fragmento = new HomeFragment();
       // fragmento.setArguments(datosAEnviar);
       // FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
       // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       // fragmentTransaction.add(R.id.content,fragmento);
       // fragmentTransaction.commit();



        if (obtenerestado()) {
            guardarestadobutton();
           // Toast.makeText(getContext(), "verdadero", Toast.LENGTH_SHORT).show();

            String nombre = usuario.getName();
            Toast.makeText(getContext(), "Bienvenido " + nombre + " !!!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), Main_principal.class);
            intent.putExtra("nombre", usuario.getName());
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();

        } else {
            guardarestadobutton();
            //Toast.makeText(getContext(), "falso", Toast.LENGTH_SHORT).show();
            String nombre = usuario.getName();
            Toast.makeText(getContext(), "Bienvenido " + nombre + " !!!", Toast.LENGTH_SHORT).show();



            Intent intent = new Intent(getActivity(), Main_principal.class);
            intent.putExtra("nombre", usuario.getName());
            startActivity(intent);}


    }

    private void iniciarseccion() {

        progressDialog.setMessage("Ingresando");
        progressDialog.show();
        String url="http://192.168.100.195/login/sesion.php?user="+email.getText().toString()+
                "&pwd="+password.getText().toString();
        jrq=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }


}
