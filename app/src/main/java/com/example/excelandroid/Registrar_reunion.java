package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class Registrar_reunion extends AppCompatActivity {

    Spinner asistente_1,asistente_2,asistente_3,asistente_4,asistente_5;
    EditText plano_1,plano_2,plano_3,plano_4,plano_5;
    EditText textasistente_1,textasistente_2,textasistente_3,textasistente_4,textasistente_5;
    EditText resumen,lugar_reunion,hora_reunion;
    Button registrar_reunion,seleccion,subir;
    String arrbuscar[];
    Window window;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    ImageView imagen4;
   // String ip="192.168.102.61";


    Button insertar_imagen;
    ImageView ShowSelectedImage;
    EditText imageName;
    Bitmap FixBitmap;
    String ImageTag = "image_tag" ;
    String ImageName = "image_data" ;
    ProgressDialog progressDialog ;
    ByteArrayOutputStream byteArrayOutputStream ;
    byte[] byteArray ;
    String ConvertImage ;
    String GetImageNameFromEditText;
    HttpURLConnection httpURLConnection ;
    URL url;
    OutputStream outputStream;
    BufferedWriter bufferedWriter ;
    int RC ;
    BufferedReader bufferedReader ;
    StringBuilder stringBuilder;
    boolean check = true;
    private int GALLERY = 1, CAMERA = 2;

    String id,id9;
    int id1,id8;

    int consecutivo=0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_reunion);

        final String ip = getIntent().getExtras().getString("ipwifi");

       // int aleatorio= (int) Math.floor(Math.random()*(10-2)+2);

        //Toast.makeText(Registrar_reunion.this, aleatorio, Toast.LENGTH_SHORT).show();

        // cambiar color a barra inferior del cel
        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        registrar_reunion=findViewById(R.id.boton_reun);

        asistente_1=(Spinner)findViewById(R.id.spinner_asistente_1);
        asistente_2=(Spinner)findViewById(R.id.spinner_asistente_2);
        asistente_3=(Spinner)findViewById(R.id.spinner_asistente_3);
        asistente_4=(Spinner)findViewById(R.id.spinner_asistente_4);
        asistente_5=(Spinner)findViewById(R.id.spinner_asistente_5);

        String[] opciones = {"EMPRESA","CONSORCIO CCC", "INTERVENTORIA", "ASESORIA", "EPM","TERCERO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        asistente_1.setAdapter(adapter);
        asistente_2.setAdapter(adapter);
        asistente_3.setAdapter(adapter);
        asistente_4.setAdapter(adapter);
        asistente_5.setAdapter(adapter);

        plano_1=findViewById(R.id.plano_1);
        plano_2=findViewById(R.id.plano_2);
        plano_3=findViewById(R.id.plano_3);
       // plano_4=findViewById(R.id.plano_4);
        //plano_5=findViewById(R.id.plano_5);

        textasistente_1=findViewById(R.id.asistente_1);
        textasistente_2=findViewById(R.id.asistente_2);
        textasistente_3=findViewById(R.id.asistente_3);
        textasistente_4=findViewById(R.id.asistente_4);
        textasistente_5=findViewById(R.id.asistente_5);

        resumen=findViewById(R.id.resumen_reunion);
        hora_reunion=findViewById(R.id.hora_reunion);
        lugar_reunion=findViewById(R.id.tema_reunion);

        rq= Volley.newRequestQueue(this);

        registrar_reunion.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          registrarreunion("http://"+ip+"/login/registrar_reuniones.php");
            }




        });


        id1 = (int) (Math.random() * 9000 + 1000);
        id = String.valueOf(id1);




        imagen4=findViewById(R.id.imagen4);
        seleccion=findViewById(R.id.boton_reun2);
        subir=findViewById(R.id.boton_reun3);



        seleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byteArrayOutputStream = new ByteArrayOutputStream();

           // Intent intent=new Intent(Registrar_reunion.this,Main_Imagenes_reunion.class);
           // startActivity(intent);

                showPictureDialog();

            }
        });

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                consecutivo=consecutivo+1;

                if (plano_1.getText().toString().isEmpty()|textasistente_1.getText().toString().isEmpty()|resumen.getText().toString().isEmpty()
                |hora_reunion.getText().toString().isEmpty()|lugar_reunion.getText().toString().isEmpty()){

                    Toast.makeText(Registrar_reunion.this, "Debes ingresar todos los datos", Toast.LENGTH_SHORT).show();

                }else {


                    GetImageNameFromEditText = id+consecutivo;
                    UploadImageToServer();

                    imagen4.setImageResource(R.drawable.ic_menu_camera);

                }


            }
        });


    }



    private void registrarreunion(String URL) {

        if (plano_1.getText().toString().isEmpty()&plano_2.getText().toString().isEmpty()&plano_3.getText().toString().isEmpty()) {

            Toast.makeText(Registrar_reunion.this, "Debe ingresar almenos un codigo de plano", Toast.LENGTH_SHORT).show();
            return;
        } else{

            // BUSCAMOS NUMERO DE

            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat fecha = new SimpleDateFormat("d' de ' MMM 'de ' yyyy");
            final String sfecha = fecha.format(date);



            final int id2 = (int) (Math.random() * 100000 + 10000);
            final String id3 = String.valueOf(id2);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(Registrar_reunion.this, "exito!! se cargo correctamente", Toast.LENGTH_SHORT).show();

                   onBackPressed();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registrar_reunion.this, "Error de conexión con la base de datos"+error.toString(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("id", id3);
                    parametros.put("identificador", id);
                    parametros.put("plano_1",plano_1.getText().toString());
                    parametros.put("plano_2",plano_2.getText().toString());
                    parametros.put("plano_3",plano_3.getText().toString());
                   // parametros.put("plano_4",plano_3.getText().toString());
                   // parametros.put("plano_5",plano_3.getText().toString());
                    parametros.put("asistente_1",textasistente_1.getText().toString());
                    parametros.put("asistente_2",textasistente_2.getText().toString());
                    parametros.put("asistente_3",textasistente_3.getText().toString());
                    parametros.put("asistente_4",textasistente_4.getText().toString());
                    parametros.put("asistente_5",textasistente_5.getText().toString());
                    parametros.put("resumen",resumen.getText().toString());
                    parametros.put("fecha",sfecha);
                    parametros.put("lugar",lugar_reunion.getText().toString());
                    parametros.put("hora",hora_reunion.getText().toString());

                    return parametros;
                }
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }

    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Seleccione una opcion");
        String[] pictureDialogItems = {
                "Elegir de la galería",
                "Tomar foto de la camara" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    imagen4.setImageBitmap(FixBitmap);
                    imagen4.setVisibility(View.VISIBLE);



                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Registrar_reunion.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");


            imagen4.setImageBitmap(FixBitmap);


            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }




    public void UploadImageToServer(){

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {


            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(Registrar_reunion.this,"La imagen se esta subiendo al servidor","Por favor espere",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                Toast.makeText(Registrar_reunion.this,string1,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                Registrar_reunion.ImageProcessClass imageProcessClass = new Registrar_reunion.ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put("image_data",ConvertImage);
                HashMapParams.put("image_tag", GetImageNameFromEditText);

                String FinalData = imageProcessClass.ImageHttpRequest("http://192.168.1.56/login/guardar_imagen_reunion.php", HashMapParams);

                // onBackPressed();

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{
        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                url = new URL(requestURL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(20000);
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(bufferedWriterDataFN(PData));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;
                    while ((RC2 = bufferedReader.readLine()) != null){
                        stringBuilder.append(RC2);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");
                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }
            return stringBuilder.toString();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
            }
            else {
                Toast.makeText(Registrar_reunion.this, "No podemos acceder a la camara..Por favor permita el acceso", Toast.LENGTH_LONG).show();
            }
        }
    }
}

