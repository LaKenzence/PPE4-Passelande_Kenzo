package com.example.ppe4_passelande_kenzo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.util.Calendar;


public class AfficheVisite extends AppCompatActivity {

    private ListView listView;
    private Date ddatereelle;
    private List<VisiteSoin> listeSoin;
    private Visite laVisite;
    private Modele vmodel;
    //private DateFormat df = new DateFormat();
    private Calendar myCalendar = Calendar.getInstance();
    private EditText datereelle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_visite);


    }

}