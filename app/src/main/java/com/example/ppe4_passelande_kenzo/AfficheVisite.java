package com.example.ppe4_passelande_kenzo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.DatePicker;
import android.text.format.DateFormat;

import java.util.List;
import java.util.Date;
import java.util.Calendar;




public class AfficheVisite extends AppCompatActivity {

    private ListView listView;
    private Date ddatereelle;
    private List<VisiteSoin> listeSoin;
    private Visite laVisite;
    private Modele modele;
    private int idvisite;
    private DateFormat df = new DateFormat();
    private Calendar myCalendar = Calendar.getInstance();
    private EditText datereelle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_visite);

       Bundle b = getIntent().getExtras();
       idvisite =b.getInt("idVisite");
       modele = new Modele(this);
       laVisite = modele.trouveVisite(idvisite);
       Patient patient = modele.trouvePatient(laVisite.getPatient());

       TextView  VisiteDate = (TextView)findViewById(R.id.visiteDatePrevue);
       TextView  VisitePrenom = (TextView)findViewById(R.id.visitePrenom);
       TextView  VisiteNom = (TextView)findViewById(R.id.visiteNom);
       TextView  Adresse = (TextView)findViewById(R.id.visitead1);
       TextView  Cp = (TextView)findViewById(R.id.visitecp);
       TextView  Ville = (TextView)findViewById(R.id.visiteville);
       TextView  Nump = (TextView)findViewById(R.id.visitenumport);
       TextView  Numfixe = (TextView)findViewById(R.id.visitenumfixe);

       VisitePrenom.setText(patient.getPrenom());
       VisiteNom.setText(patient.getNom());
       Adresse.setText(patient.getAd1());
       Cp.setText(String.valueOf(patient.getCp()));
       Ville.setText(patient.getVille());
       Nump.setText("Port : "+ String.valueOf(patient.getTel_port()));
       Numfixe.setText("Fixe" + String.valueOf(patient.getTel_fixe()));
       VisiteDate.setText(df.format("dd/MM/yyyy",laVisite.getDate_prevue()));



        Button bsauv = (Button) findViewById(R.id.visitesave);
        bsauv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                laVisite.setDate_reelle(myCalendar.getTime());
                EditText commentaire = (EditText)findViewById(R.id.visitecommentaire);
                laVisite.setCompte_rendu_infirmiere(commentaire.getText().toString());
                modele.saveVisite(laVisite);

            }
        });

       //TextView textViewPrenom = (TextView) getView().findViewById(R.id.textViewPrenom);


        datereelle=(EditText) findViewById(R.id.visiteDateReelle);
        if(laVisite.getDate_reelle().toString().length()==0)
        {
            ddatereelle = new Date();
        }
        else
        {
            ddatereelle=laVisite.getDate_reelle();
        }
        datereelle.setText(df.format("dd/MM/yyyy", ddatereelle));

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                datereelle.setText(df.format("dd/MM/yyyy", myCalendar.getTime()));
                ddatereelle=myCalendar.getTime();
            }

        };
        datereelle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AfficheVisite.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

}