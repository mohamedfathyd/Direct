package com.khalej.direct.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.direct.R;


public class More_fragment extends Fragment {
TextView logout,terms,whous,callus,language;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_fragment, container, false);
        logout=view.findViewById(R.id.logout);
        terms=view.findViewById(R.id.terms);
        whous=view.findViewById(R.id.whous);
        callus=view.findViewById(R.id.callus);
        language=view.findViewById(R.id.language);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.putInt("id",0);
                edt.putString("name","");
                edt.putString("image","");
                edt.putString("phone","");
                edt.putString("address","");
                edt.putString("password","");
                edt.putString("createdAt","");
                edt.putString("remember","no");
                edt.apply();
                startActivity(new Intent(getActivity(),Login.class));
                getActivity().finish();
            }
        });
        callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try{
//                    String url = "https://api.whatsapp.com/send?phone="+"+97333348098";
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    startActivity(i);}
//                catch( Exception e){
//                    Toast.makeText(getActivity(), "غير متاحه الأن عاود المحاولة لاحقا " ,Toast.LENGTH_LONG).show();
//                }

                startActivity(new Intent(getActivity(),CallUs.class));
            }
        });
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedpref.getString("language","").trim().equals("ar")){
                    edt.putString("language","en");
                    edt.apply();
                    startActivity(new Intent(getActivity(),Main.class));
                    getActivity().finish();
                }
                else
                {
                    edt.putString("language","ar");
                    edt.apply();
                    startActivity(new Intent(getActivity(),Main.class));
                    getActivity().finish();
                }
            }
        });

        whous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),whous.class));
                }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Terms.class));
                 }
        });
        return view;

    }

}
