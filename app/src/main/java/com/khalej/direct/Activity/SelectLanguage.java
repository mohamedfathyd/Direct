package com.khalej.direct.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.khalej.direct.R;

public class SelectLanguage extends AppCompatActivity {
  Button arabic,english;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        arabic=findViewById(R.id.arabic);
        english=findViewById(R.id.english);
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.putString("language","ar");
                edt.apply();
                startActivity(new Intent(SelectLanguage.this,Mobile_Code.class));
                finish();
            }
        });
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.putString("language","en");
                edt.apply();
                startActivity(new Intent(SelectLanguage.this,Mobile_Code.class));
                finish();
            }
        });
    }
}
