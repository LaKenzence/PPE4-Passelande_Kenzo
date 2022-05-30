package com.example.ppe4_passelande_kenzo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ppe4_passelande_kenzo.databinding.FragmentSecondBinding;
import java.util.Calendar;
import java.util.Date;
import android.content.SharedPreferences;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private Async mThreadCon = null;
    private EditText login;
    private EditText pass;
    private String url;
    private String[] mesparams;

    public String getLogin ()
    {
        return this.login.toString();
    }

    public String getpass ()
    {
        return this.pass.toString();
    }

    private Calendar myCalendar = Calendar.getInstance();
    private EditText dateconnexion;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

       // affichelog1();

        //TxtDateHeure =


       /* dateconnexion.setText(df.format("dd/MM/yyyy", dateconnexion);
        String s = f.format(d);
        if(laVisite.getDate_reelle().toString().length()==0)
        {
            ddatereelle = new Date();
        }
        else
        {
            ddatereelle=laVisite.getDate_reelle();
        }
        datereelle.setText(df.format("dd/MM/yyyy", ddatereelle));
    }*/
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);










        binding.bFragCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        affichelog1();
 binding.bFragOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            login = (EditText) getView().findViewById(R.id.etFragId);
            pass = (EditText) getView().findViewById(R.id.etFragPassword);



/*
                url = "https://www.btssio-carcouet.fr/ppe4/public/connect2"+login.getText()+"/"+pass.getText()+"infirmiere" ;
                url = "https://www.btssio-carcouet.fr/ppe4/public/connect2/"
                        .concat(login.getText().toString())
                        .concat("/")
                        .concat(pass.getText().toString())
                        .concat("/")
                        .concat("infirmiere");
                Toast.makeText(getContext(), url
                        , Toast.LENGTH_SHORT).show();

                mesparams=new String[3];
                mesparams[0]="1";
                mesparams[1]=url;
                mesparams[2]="GET";




                mThreadCon = new Async ((MainActivity)getActivity());
                mThreadCon.execute(mesparams);
*/
                ((MainActivity)getActivity()).testMotDePasse(login.getText().toString(), pass.getText().toString());

            }
        });
 /*

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void affichelog1()
    {
    String login=((MainActivity)getActivity()).affichelog();
        if (! login.equals("nothing")) {
            TextView log = (TextView) getView().findViewById(R.id.etFragId);
            log.setText(login);
        }
    }

}
/*
package com.example.ppe4_passelande_kenzo;


import android.content.Context;
        import android.graphics.Color;
        import android.text.format.DateFormat;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.CheckBox;
        import android.widget.TextView;


        import android.content.Context;
        import android.view.LayoutInflater;
        import android.widget.BaseAdapter;

        import java.util.List;

public class SoinAdapter extends BaseAdapter {

    private ViewHolder holder;
    private List<VisiteSoin> listSoin;
    private LayoutInflater layoutInflater; //Cet attribut a pour mission de charger notre fichier XML de la vue pour l'item.
    private DateFormat df = new DateFormat();
    private Modele modele;


    public SoinAdapter(Context context, List<VisiteSoin> vListSoin) {
        super();
        layoutInflater = LayoutInflater.from(context);
        listSoin = vListSoin;
        modele = new Modele(context);

    }


    private class ViewHolder {
        public CheckBox checkRealise;
        public TextView vuesoinslibel;
    }

    @Override
    public int getCount() {
        return listSoin.size();
    }

    @Override
    public Object getItem(int position) {
        return listSoin.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listSoin.get(position).getId_soins();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.vueunevisite, null);
            holder.checkRealise = (CheckBox) view.findViewById(R.id.vuesoinsrealise);
            holder.vuesoinslibel = (TextView) view.findViewById(R.id.vuesoinslibel);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        Soin soins = modele.trouveSoin(listSoin.get(position).getId_categ_soins(),listSoin.get(position).getId_type_soins(), listSoin.get(position).getId_soins());
        if(listSoin.get(position).isRealise()) {
            holder.checkRealise.setChecked(true);
        }
        holder.vuesoinslibel.setText(soins.getLibel());

        holder.checkRealise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb =  v.findViewById(R.id.vuesoinsrealise);
                listSoin.get(position).setRealise(cb.isChecked());
                modele.saveVisiteSoin(listSoin.get(position));
            }
        });
        return view;
    }
}
 */