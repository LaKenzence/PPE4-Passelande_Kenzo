package com.example.ppe4_passelande_kenzo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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



import com.example.ppe4_passelande_kenzo.databinding.FragmentSecondBinding;

public class ActImport extends AppCompatActivity {


    private String url;
    private String[] mesparams;
    private Async mthreadImp = null;
    private boolean permissionOverlayok;


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

                alertmsg("fddfd", "ffdsgsgv");

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
                for (JsonElement obj : varray) {
                    Visite visite = gson.fromJson(obj.getAsJsonObject(), Visite.class);
                    visite.setCompte_rendu_infirmiere("");
                    visite.setDate_reelle(visite.getDate_prevue());
                    listeVisite.add(visite);
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
    }



