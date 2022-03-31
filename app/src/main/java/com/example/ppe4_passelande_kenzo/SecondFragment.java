package com.example.ppe4_passelande_kenzo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ppe4_passelande_kenzo.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private Async mThreadCon = null;
    private EditText login;
    private EditText pass;
    private String url;
    private String[] mesparams;




    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();




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

 binding.bFragOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_troisiemeFragment);
                ((MainActivity)getActivity()).menuConnect();


                login=(EditText)getView().findViewById(R.id.etFragId);
                pass=(EditText)getView().findViewById(R.id.etFragPassword);

                //url = "https://www.btssio-carcouet.fr/ppe4/public/connect2"+login.getText()+"/"+pass.getText()+"infirmiere" ;
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
//btnok


                mThreadCon = new Async ((MainActivity)getActivity());
                mThreadCon.execute(mesparams);

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

}