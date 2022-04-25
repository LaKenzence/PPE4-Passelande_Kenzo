package com.example.ppe4_passelande_kenzo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;
import java.util.ArrayList;

import java.util.Date;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.reflect.Type;


import com.example.ppe4_passelande_kenzo.databinding.FragmentSecondBinding;

public class ActImport extends AppCompatActivity {


    private String url;
    private String[] mesparams;
    private Async mthreadImp = null;
    private boolean permissionOverlayok;


    private String urlP;
    private String[] mesparamsP;
    private Async mthreadImpP = null;


    private String urlVS;
    private String[] mesparamsVS;
    private Async mthreadImpVS = null;


    private String urlS;
    private String[] mesparamsS;
    private Async mthreadImpS = null;


    private class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
            String date = element.getAsString();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_import);
        Bundle b = getIntent().getExtras();
        permissionOverlayok = b.getBoolean("permissionOverlay", false);
        url = "https://www.btssio-carcouet.fr/ppe4/public/mesvisites/3";
        //Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

        mesparams=new String[3];
        mesparams[0]="2";
        mesparams[1]=url;
        mesparams[2]="GET";
        mthreadImp = new Async (this);





        Button imp = (Button) findViewById(R.id.bImport);
        imp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                mthreadImp.execute(mesparams);


            }
        });

    }

    public void alertmsg(String title, String msg) {


        if (permissionOverlayok) {
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
    public void retourImport(StringBuilder sb)
    {


            //alertmsg("retour Connexion", sb.toString());
            try {
                Modele vmodel = new Modele(this);
                JsonElement json = new JsonParser().parse(sb.toString());
                JsonArray varray = json.getAsJsonArray();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                ArrayList<Visite> listeVisite = new ArrayList<Visite>();
                ArrayList<Integer> lesPatients = new ArrayList<Integer>();
                for (JsonElement obj : varray) {
                    Visite visite = gson.fromJson(obj.getAsJsonObject(), Visite.class);
                    visite.setCompte_rendu_infirmiere("");
                    visite.setDate_reelle(visite.getDate_prevue());
                    Integer p = visite.getPatient();
                    Integer v = visite.getId();
                    lesPatients.add(p);
                    listeVisite.add(visite);

                   /* urlP = "https://www.btssio-carcouet.fr/ppe4/public/personne/".concat(p.toString());
                    //Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

                    mesparamsP=new String[3];
                    mesparamsP[0]="3";
                    mesparamsP[1]=url;
                    mesparamsP[2]="GET";
                    mthreadImpP = new Async (this);


                    urlVS = "https://www.btssio-carcouet.fr/ppe4/public/visitesoins/".concat(v.toString());
                    //Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

                    mesparams=new String[3];
                    mesparamsVS[0]="4";
                    mesparamsVS[1]=url;
                    mesparamsVS[2]="GET";
                    mthreadImpVS = new Async (this);

                    urlS = "https://www.btssio-carcouet.fr/ppe4/public/soins/";
                    //Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

                    mesparamsS=new String[3];
                    mesparamsS[0]="5";
                    mesparamsS[1]=url;
                    mesparamsS[2]="GET";
                    mthreadImpS = new Async (this);

                    mthreadImpP.execute(mesparamsP);
                    mthreadImpP.execute(mesparamsP);
                    mthreadImpP.execute(mesparamsP);*/
                }
                vmodel.deleteVisite();
                vmodel.addVisite(listeVisite);
                alertmsg("Retour", "Vos informations ont bien été importé avec succès !");
                alertmsg("retour import",sb.toString());
            }
            catch (Exception e) {
                alertmsg("Erreur retour import", e.getMessage());
            }
        }
        public void retourImportPatient (StringBuilder sb)
        {

            try {
                Modele vmodel = new Modele(this);
                JsonElement json = new JsonParser().parse(sb.toString());
                JsonArray varray = json.getAsJsonArray();
                Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).serializeNulls().create();
                ArrayList<Patient> listePatient = new ArrayList<Patient>();
                for (JsonElement obj : varray) {
                    Patient unpatient = gson.fromJson(obj.getAsJsonObject(), Patient.class);
                    listePatient.add(unpatient);
                }
                vmodel.deletePatient();
                vmodel.addPatient(listePatient);
                alertmsg("Retour", "Vos informations ont bien été importé avec succès !");
                alertmsg("retour import",sb.toString());
            }
            catch (JsonParseException e) {
                Log.d("Patient", "erreur json" + e.getMessage());
            }
        }

    public void retourImportVisiteSoin (StringBuilder sb)
    {

        try {
            Modele vmodel = new Modele(this);
            JsonElement json = new JsonParser().parse(sb.toString());
            JsonArray varray = json.getAsJsonArray();
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).serializeNulls().create();
            ArrayList<VisiteSoin> listeVisiteSoin = new ArrayList<VisiteSoin>();
            for (JsonElement obj : varray) {
                VisiteSoin uneVisiteSoin = gson.fromJson(obj.getAsJsonObject(), VisiteSoin.class);
                listeVisiteSoin.add(uneVisiteSoin);
            }
            vmodel.deleteVisiteSoin();
            vmodel.addVisiteSoin(listeVisiteSoin);
            alertmsg("Retour", "Vos informations ont bien été importé avec succès !");
            alertmsg("retour import",sb.toString());
        }
        catch (JsonParseException e) {
            Log.d("Patient", "erreur json" + e.getMessage());
        }
    }

    public void retourImportSoin (StringBuilder sb)
    {

        try {
            Modele vmodel = new Modele(this);
            JsonElement json = new JsonParser().parse(sb.toString());
            JsonArray varray = json.getAsJsonArray();
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).serializeNulls().create();
            ArrayList<Soin> listeSoin = new ArrayList<Soin>();
            for (JsonElement obj : varray) {
                Soin unSoin = gson.fromJson(obj.getAsJsonObject(), Soin.class);
                listeSoin.add(unSoin);
            }
            vmodel.deleteSoin();
            vmodel.addSoin(listeSoin);
            alertmsg("Retour", "Vos informations ont bien été importé avec succès !");
            alertmsg("retour import",sb.toString());
        }
        catch (JsonParseException e) {
            Log.d("Patient", "erreur json" + e.getMessage());
        }
    }

}



