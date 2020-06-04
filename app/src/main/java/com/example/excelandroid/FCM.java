package com.example.excelandroid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class FCM  extends FirebaseMessagingService {

    RequestQueue rq;
    JsonRequest jrq;
    RequestQueue requestQueue;
    String ip="192.168.101.185";

   public void onNewToken(@NonNull String token) {
    super.onNewToken(token);

    guardartoken(token,"http://"+ip+"/login/registrar_token.php");

    }
   private void guardartoken(final String token, String URL) {

       rq= Volley.newRequestQueue(this);

       final String usuario="camilo1";

           final int id100 = (int) (Math.random() * 1000000 + 1000);
           final String id10 = String.valueOf(id100);

           StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {

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
                   parametros.put("id", id10);
                   parametros.put("usuario", usuario);
                   parametros.put("token",token);

                   return parametros;
               }
           };
           requestQueue = Volley.newRequestQueue(this);
           requestQueue.add(stringRequest);

   }



    public void onMessageReceived (@NonNull RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();

        if (remoteMessage.getNotification()!=null){

            String titulo = remoteMessage.getNotification().getTitle();
            String detalle = remoteMessage.getNotification().getBody();
            mayorqueoreo(titulo,detalle);
        }

        if(remoteMessage.getData().size()>0){
            String titulo = remoteMessage.getData().get("titulo");
            String detalle = remoteMessage.getData().get("detalle");
            mayorqueoreo(titulo,detalle);
        }

    }

    private  void mayorqueoreo(String titulo, String detalle){

        String id= "mensaje";
        NotificationManager mn = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder buider =new  NotificationCompat.Builder(this,id);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(id, "nuevo",NotificationManager.IMPORTANCE_DEFAULT);
            nc.setShowBadge(true);
            assert mn != null;
            mn.createNotificationChannel(nc);
        }
        buider.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(detalle)
                .setContentIntent(clicknotify())
                .setContentInfo("nuevo");
        Random random = new Random();
        int id_notify = random.nextInt(8000);

        assert mn != null;
        mn.notify(id_notify,buider.build());


    }

    public PendingIntent clicknotify(){
        Intent intent=new Intent(getApplicationContext(),Planear_reunion.class);
        intent.putExtra("color","rojo");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return  PendingIntent.getActivity(this,0,intent,0);
    }


}
