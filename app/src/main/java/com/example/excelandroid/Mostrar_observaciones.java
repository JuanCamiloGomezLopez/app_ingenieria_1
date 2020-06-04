package com.example.excelandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class Mostrar_observaciones extends AppCompatActivity {

    ContentValues val = new ContentValues();
    SQLiteDatabase db;

    public static final int REQUEST_CODE = 105;
    ImageView imagen_1;
    Button tomar_foto,galeria;
    String currentPhotoPath;
    Button botonobs;
    EditText resumen_reunion;
    String arrobs1[],camilo[];
    ListView listView;
    Window window;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    String ip="192.168.101.185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_observaciones);

        // cambiar color a barra inferior del cel
        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        // inicializamos variables para la camara y galeria
        imagen_1 = findViewById(R.id.imagen_1);
        tomar_foto = findViewById(R.id.tomar_foto);
        galeria = findViewById(R.id.button2);
        // finaliza variables para la camara y galeria

        botonobs=findViewById(R.id.boton_obs);
        resumen_reunion=findViewById(R.id.resumen_reunion);
        listView=findViewById(R.id.list_view);

        rq= Volley.newRequestQueue(this);
// llamamos al metodo para actualizar la activity



        final String ident_obs = getIntent().getExtras().getString("identificador2");
        final String recuperar = getIntent().getExtras().getString("plano1");



        buscarobservaciones("http://"+ip+"/login/buscar_observaciones_plano.php?identificador="+ident_obs+"");

        // REGISTRAMOS NUESTROS DATOS
        botonobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


          registrarobservaciones("http://"+ip+"/login/registrar_observacion.php");


            }
        });






















        // empieza codigo para llamar la camara y abrir la galeria *********************************************************************************

       galeria.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent galeria=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(galeria,REQUEST_CODE);
           }
       });


        tomar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // abrir camera
                asKCameraPermission();
            }
        });
    }



    // metodo para registrar observaciones

    private void registrarobservaciones(String URL) {


        final int id100 = (int) (Math.random() * 1000000 + 1000);
        final String id10 = String.valueOf(id100);



        if (resumen_reunion.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar una observacion", Toast.LENGTH_SHORT).show();
        } else {

            final String ident_obs = getIntent().getExtras().getString("identificador2");
            final String recuperar = getIntent().getExtras().getString("plano1");

             StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                   resumen_reunion.setText("");
                   buscarobservaciones("http://"+ip+"/login/buscar_observaciones_plano.php?identificador="+ident_obs+"");

                    Toast.makeText(getApplicationContext(), "exito!! se cargo correctamente", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("id",id10);
                    parametros.put("identificador", ident_obs);
                    parametros.put("observacion",ident_obs+" USUARIO "+resumen_reunion.getText().toString());
                    parametros.put("codigo","");
                    return parametros;
                }
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }



    private void buscarobservaciones (String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                arrobs1=new String[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String camilo1=(jsonObject.getString("observacion"));

                        arrobs1[i]=camilo1;
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                ArrayAdapter<String> add=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,arrobs1);
                listView.setAdapter(add);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(getApplicationContext(), "error de conexion observaciones", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }




    private void asKCameraPermission() {
            if (ContextCompat.checkSelfPermission(Mostrar_observaciones.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Mostrar_observaciones.this,
                        new String[]{
                                Manifest.permission.CAMERA
                        }, 100);
            } else {
                dispatchTakePictureIntent();
            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else {
                Toast.makeText(this, "se requiere el permiso para acceder a la camara", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if(resultCode==Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                imagen_1.setImageURI(Uri.fromFile(f));

                Log.d("tag", "ABsolute Url of Image is"+Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
            }

        }
        if (requestCode == REQUEST_CODE) {
            if(resultCode==Activity.RESULT_OK){
                Uri contentUri = data.getData();
                @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_"+getFileExt(contentUri);
                Log.d("tag", "on ActivityResult: Gallery Image Uri"+imageFileName);

              imagen_1.setImageURI(contentUri);
            }

        }


    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c= getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";


       File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
     // File storageDir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 100);
            }
        }
    }

}
