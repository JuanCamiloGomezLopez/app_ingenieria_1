package com.example.excelandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.excelandroid.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class Registrar_usuario extends AppCompatActivity  {

    Button registrar1,seleccion;
    Window window;
    EditText email,name1,password,password_repeat;
    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;

    ImageView imagen_usuario;
    String urlimagen;


    Button GetImageFromGalleryButton, UploadImageOnServerButton;
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

    String rutaimagen="";
    String urlimagen1 ="http://192.168.1.56/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);






        final String ip = getIntent().getExtras().getString("ipwifi");

        // cambiar color a barra inferior del cel
        this.window=getWindow();
        String primary="#000000";
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setNavigationBarColor(Color.parseColor(primary));
        window.setStatusBarColor(Color.parseColor(primary));
        // rtermina cambiar color

        rq= Volley.newRequestQueue(this);
        registrar1= findViewById(R.id.registrar_usuario);

        name1=findViewById(R.id.reg_name);
        email=findViewById(R.id.reg_email);
        password=findViewById(R.id.reg_password);
        password_repeat=findViewById(R.id.area);

        seleccion = (Button)findViewById(R.id.seleccion_imagen);

        // UploadImageOnServerButton = (Button)findViewById(R.id.buttonUpload);

        ShowSelectedImage = (ImageView)findViewById(R.id.imageView6);



        //buscarimagen("http://192.168.1.56/login/buscar_imagen_usuario.php?image_name=casa");


        //Picasso.get().load("http://192.168.1.56/probando/upload/camilo.jpg").into(ShowSelectedImage);

        registrar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetImageNameFromEditText = name1.getText().toString();

                if(password.getText().toString().isEmpty()|password_repeat.getText().toString().isEmpty()|name1.getText().toString().isEmpty()|email.getText().toString().isEmpty()){
                    Toast.makeText(Registrar_usuario.this, "Debe llenar todos los datos", Toast.LENGTH_SHORT).show();
                    return;
                }

                String emailinput=email.getText().toString();

                if (!emailinput.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()){



                    UploadImageToServer();


                   // registrarusuario("http://"+ip+"/login/registrar_usuarios.php");



                } else {
                    Toast.makeText(getApplication(), "El email es invalido", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });


        byteArrayOutputStream = new ByteArrayOutputStream();
        seleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });


       // UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
      //      @Override
       //     public void onClick(View view) {
             //   GetImageNameFromEditText = name1.getText().toString();
             //   UploadImageToServer();
          //  }
     //   });

        if (ContextCompat.checkSelfPermission(Registrar_usuario.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }


    }



    private void limpiarcajas() {

        name1.setText("");
        email.setText("");
        password.setText("");
        password_repeat.setText("");
    }


    private void registrarusuario(String URL) {

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        final int id100 = (int) (Math.random() * 1000 + 10);
        final String id10 = String.valueOf(id100);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    Toast.makeText(getApplicationContext(), "Bienvenido: "+name1.getText().toString()+" ya puedes iniciar seccion.", Toast.LENGTH_SHORT).show();
                    limpiarcajas();

                    onBackPressed();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registrar_usuario.this, error.toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "No se pudo registrar el usuario : "+error.toString()+name1.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            })
                {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("id", id10);
                    parametros.put("imagen", ConvertImage);
                    parametros.put("name", name1.getText().toString());
                    parametros.put("user",email.getText().toString());
                    parametros.put("pwd", password.getText().toString());
                    parametros.put("area", password_repeat.getText().toString());


                    return parametros;
                }
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
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
                    // String path = saveImage(bitmap);
                    //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    ShowSelectedImage.setImageBitmap(FixBitmap);
                    registrar1.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Registrar_usuario.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            ShowSelectedImage.setImageBitmap(FixBitmap);
            registrar1.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public void UploadImageToServer(){

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            final int id100 = (int) (Math.random() * 1000 + 10);
            final String id10 = String.valueOf(id100);



            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(Registrar_usuario.this,"La imagen se esta subiendo al servidor","Por favor espere",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                Toast.makeText(Registrar_usuario.this,string1,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();



                HashMapParams.put("id", id10);
                HashMapParams.put("image_data",ConvertImage);
                HashMapParams.put("image_tag", GetImageNameFromEditText);
                HashMapParams.put("user", email.getText().toString());
                HashMapParams.put("pwd", password.getText().toString());
                HashMapParams.put("area", password_repeat.getText().toString());


                String FinalData = imageProcessClass.ImageHttpRequest("http://192.168.1.56/login/registrar_usuario_image.php", HashMapParams);

                limpiarcajas();

                Intent intent= new Intent(Registrar_usuario.this,Activity_login.class);
                startActivity(intent);

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
                Toast.makeText(Registrar_usuario.this, "No podemos acceder a la camara..Por favor permita el acceso", Toast.LENGTH_LONG).show();
            }
        }
    }




    private void buscarimagen(String URL1) {
        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        rutaimagen=(jsonObject.getString("image_path"));
                       // urlimagen = "http://192.168.1.56/";

                       } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error de conexion plano 1", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }







}
