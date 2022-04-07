package com.example.ppe4_passelande_kenzo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ppe4_passelande_kenzo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import android.Manifest;
import android.content.pm.PackageManager;
import java.util.List;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.core.app.ActivityCompat;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;





public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Menu lemenu;

    // Demande et vérification des permissions
    //private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET,Manifest.permission.READ_CONTACTS};
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET,Manifest.permission.READ_CONTACTS,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int MULTIPLE_PERMISSIONS = 10;
    private List<String> listPermissionsNeeded;
    private boolean permissionOverlayAsked=false;
    private boolean permissionOverlay=false;
    private String nom;
    private String prenom;
    private String url;
    private String[] mesparams;
    private Async mThreadCon = null;
    private String login;
    private String pass;
    private Intent i;

    public String getnom()
    {
        return this.nom;
    }
    public String getprenom()
    {
        return this.prenom;
    }




    @Override
    public void onStart() {
        super.onStart();
        super.onResume();
        if(!permissionOverlayAsked) {
            checkPermissionAlert();
        }
        checkPermissions();
    }

    private boolean permissionOK=false;

    private  void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result;
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String p : permissions) {
                result = checkSelfPermission(p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            } else {
                // Toutes les permissions sont ok
                permissionOK = true;
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    int posi=0;
                    for (String per : permissionsList) {
                        if(grantResults[posi] == PackageManager.PERMISSION_DENIED){
                            permissionsDenied += "\n" + per;
                        }
                        posi++;
                    }
                    // Show permissionsDenied
                    if(permissionsDenied.length()>0) {
                        Toast.makeText(getApplicationContext(),    "Nous ne pouvons pas continuer l'application car ces permissions sont nécéssaires : \n"+permissionsDenied,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        permissionOK=true;
                    }
                }
                return;
            }
        }
    }
    // Alert Message

    public void alertmsg(String title, String msg) {
        if (permissionOverlay) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage(msg)
                    .setTitle(title);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            });

            AlertDialog dialog = builder.create();
            dialog.getWindow().setType(WindowManager.LayoutParams.
                    TYPE_APPLICATION_OVERLAY);
            dialog.show();
        }
        else {
            Toast.makeText(getApplicationContext(), title.concat(" -->").concat(msg), Toast.LENGTH_SHORT).show();
        }
    }



    // Autorisation des alert Message
    public static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;
    public void checkPermissionAlert() {
        permissionOverlayAsked=true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
            else {
                permissionOverlay  = true ;
            }
        }
        else{
            permissionOverlay = true ;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // on regarde quelle Activity a répondu
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(this)) {
                        permissionOverlay = true ;
                        alertmsg("Permission ALERT","Permission OK");
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Pbs demande de permissions "
                                , Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    permissionOverlay = true ;
                }
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        lemenu=menu;
        return true;
    }
    public void menuConnect()
    {
        lemenu.findItem(R.id.menu_connect).setVisible(false);
        lemenu.findItem(R.id.menu_list).setVisible(true);
        lemenu.findItem(R.id.menu_import).setVisible(true);
        lemenu.findItem(R.id.menu_export).setVisible(true);
        lemenu.findItem(R.id.menu_deconnect).setVisible(true);


    }

    public void menuDeconnect()
    {
        lemenu.findItem(R.id.menu_connect).setVisible(true);
        lemenu.findItem(R.id.menu_list).setVisible(false);
        lemenu.findItem(R.id.menu_import).setVisible(false);
        lemenu.findItem(R.id.menu_export).setVisible(false);
        lemenu.findItem(R.id.menu_deconnect).setVisible(false);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                Toast.makeText(getApplicationContext(), "click sur connect", Toast.LENGTH_SHORT).show();

                boolean firstFragActive=(Navigation.findNavController(this,R.id.nav_host_fragment_content_main).getCurrentDestination().getId()==R.id.FirstFragment);
                if (firstFragActive && permissionOK)
                {
                    Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_FirstFragment_to_SecondFragment);
                }
                else
                {
                    checkPermissions();
                    Toast.makeText(getApplicationContext(), "Veuillez accepter les permissions", Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.menu_list:
                Toast.makeText(getApplicationContext(), "click sur list", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_import:
                i = new Intent(getApplicationContext(), ActImport.class);

                i.putExtra("permissionOverlay", permissionOverlay);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "click sur import", Toast.LENGTH_SHORT).show();
                return true;
                case R.id.menu_export:
                Toast.makeText(getApplicationContext(), "click sur export", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_deconnect:
                Toast.makeText(getApplicationContext(), "click sur disconnect", Toast.LENGTH_SHORT).show();
                boolean thirdFragActive=(Navigation.findNavController(this,R.id.nav_host_fragment_content_main).getCurrentDestination().getId()==R.id.troisiemeFragment);
                boolean secondFragActive=(Navigation.findNavController(this,R.id.nav_host_fragment_content_main).getCurrentDestination().getId()==R.id.SecondFragment);
                if(thirdFragActive){


                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_troisiemeFragment_to_FirstFragment);
                }
                else if (secondFragActive)
                {
                    Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.action_SecondFragment_to_FirstFragment);
                }


                this.menuDeconnect();
                return true;

            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void retourConnexion(StringBuilder sb)
    {

        alertmsg("retour Connexion", sb.toString());
        try {
            JSONObject vJsonObject = new JSONObject(sb.toString());
            if(vJsonObject.has("status"))
            {
                alertmsg("Erreur connexion", "Identifiant ou mot de passe incorrect");
            }
            else
            {
                alertmsg("Connexion OK", vJsonObject.getString("nom"));
                Navigation.findNavController(this , R.id.nav_host_fragment_content_main).navigate(R.id.action_SecondFragment_to_troisiemeFragment);
                menuConnect();
                nom = vJsonObject.getString("nom");
                prenom = vJsonObject.getString("prenom");

                SharedPreferences myPrefs = this.getSharedPreferences("mesvariablesglobales", 0);
                SharedPreferences.Editor prefsEditor = myPrefs.edit();
                prefsEditor.putString("prefLogin", login);
                prefsEditor.putString("prefMdp",MD5.getMd5(pass));
                prefsEditor.commit();
            }
        } catch (JSONException e) {
            alertmsg("Erreur connexion", "Les données reçues sont dans un format incorrect.".concat(e.getMessage()));
        }

    }
    public void testMotDePasse(String vlogin,String vmdp) {
        try {

        this.login = vlogin;
        this.pass = vmdp;

        SharedPreferences  myPrefs = this.getSharedPreferences("mesvariablesglobales", 0);
        String prefLog = myPrefs.getString("prefLogin","nothing");
        String prefPass = myPrefs.getString("prefMdp", "nothing");
        if(login.equals(prefLog) && MD5.getMd5(pass).equals(prefPass))
        {
            menuConnect();
            alertmsg("Connexion", "Connecté en local.");
            Navigation.findNavController(this , R.id.nav_host_fragment_content_main).navigate(R.id.action_SecondFragment_to_troisiemeFragment);
        }
        else {
            //url = "https://www.btssio-carcouet.fr/ppe4/public/connect2"+login.getText()+"/"+pass.getText()+"infirmiere" ;
            url = "https://www.btssio-carcouet.fr/ppe4/public/connect2/"
                    .concat(vlogin)
                    .concat("/")
                    .concat(vmdp)
                    .concat("/")
                    .concat("infirmiere");


            mesparams = new String[3];
            mesparams[0] = "1";
            mesparams[1] = url;
            mesparams[2] = "GET";


            mThreadCon = new Async(this);
            mThreadCon.execute(mesparams);
        }
            } catch (Exception e) {
                alertmsg("Erreur", "Erreur ");
            }
        }
    }


