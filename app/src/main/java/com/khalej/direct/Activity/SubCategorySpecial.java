package com.khalej.direct.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.khalej.direct.R;
import com.khalej.direct.model.Apiclient_home;
import com.khalej.direct.model.apiinterface_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategorySpecial extends AppCompatActivity {
Intent intent;
String addressTo,addressFrom,details,name,subDetails;
double latFrom,lngFrom,latTo,lngTo;
int id ,numberofSets=1;
TextView nameofcategory,numOfSets,locationFromAddress,locationToAddress,subName;
EditText detailsEdit,type,numofdays,mark;

Button cunti;
    ProgressDialog progressDialog;
    ImageView imageView;
    LinearLayout locationFrom,locationTo,numnum;
    private apiinterface_home apiinterface;
ImageView plus,minus,relativelayout1;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        initializer();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        this.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        intent=getIntent();
        Glide.with(this).load(intent.getStringExtra("image")).error(R.drawable.logo).into(imageView);

        nameofcategory.setText(intent.getStringExtra("name"));
       addressFrom=intent.getStringExtra("addressFrom");
       addressTo=intent.getStringExtra("addressTo");
       details=intent.getStringExtra("detail");
       id=intent.getIntExtra("id",0);
       latFrom=intent.getDoubleExtra("latFrom",0);
       latTo=intent.getDoubleExtra("latTo",0);
       lngFrom=intent.getDoubleExtra("lngFrom",0);
       lngTo=intent.getDoubleExtra("lngTo",0);
       numberofSets=intent.getIntExtra("numberSets",1);
       name=intent.getStringExtra("name");
        subDetails=intent.getStringExtra("subDetails");
        type.setHint("نوع ال" +intent.getStringExtra("name"));
        if(intent.getStringExtra("name").equals("تجميع")){
            mark.setHint("ماركة تجميع");
        }
        else if(intent.getStringExtra("name").equals("فك وتركيب")){
            mark.setHint("ماركة الأثاث");
            type.setHint("نوع الأثاث");
        }
        else if(intent.getStringExtra("name").equals("نقل الاثاث")){
            mark.setHint("نوع السيارة");
            type.setHint("نوع الأثاث");
        }
        else {
         mark.setVisibility(View.GONE);
        }
        if(intent.getStringExtra("name").equals("توصيل")||intent.getStringExtra("name").equals("نقل الاثاث")){
            locationToAddress.setVisibility(View.VISIBLE);
            locationTo.setVisibility(View.VISIBLE);
        }
        else{
            locationToAddress.setVisibility(View.GONE);
            locationTo.setVisibility(View.GONE);
        }
        if(intent.getStringExtra("name").equals("تخزين")){
            numofdays.setVisibility(View.VISIBLE);
        }
        else{
            numofdays.setVisibility(View.GONE);
        }
        if(intent.getStringExtra("name").equals("صيانه منزل")){
            numnum.setVisibility(View.GONE);
        }
        else{
            numnum.setVisibility(View.VISIBLE);
        }
       locationToAddress.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(SubCategorySpecial.this,MapsActivityTo.class);
               intent.putExtra("addressFrom",addressFrom);
               intent.putExtra("addressTo",addressTo);
               intent.putExtra("detail",details);
               intent.putExtra("id",id);
               intent.putExtra("name",intent.getStringExtra("name"));
               intent.putExtra("latFrom",latFrom);
               intent.putExtra("latTo",latTo);
               intent.putExtra("lngFrom",lngFrom);
               intent.putExtra("lngTo",lngTo);
               intent.putExtra("numberSets",numberofSets);
               intent.putExtra("subDetails",subDetails);
               intent.putExtra("image",intent.getStringExtra("image"));
               startActivity(intent);
               finish();
           }
       });
        locationFromAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SubCategorySpecial.this,MapsActivity.class);
                intent.putExtra("addressFrom",addressFrom);
                intent.putExtra("addressTo",addressTo);
                intent.putExtra("detail",details);
                intent.putExtra("id",id);
                intent.putExtra("latFrom",latFrom);
                intent.putExtra("latTo",latTo);
                intent.putExtra("name",intent.getStringExtra("name"));
                intent.putExtra("lngFrom",lngFrom);
                intent.putExtra("lngTo",lngTo);
                intent.putExtra("numberSets",numberofSets);
                intent.putExtra("subDetails",subDetails);
                intent.putExtra("image",intent.getStringExtra("image"));

                startActivity(intent);
                finish();
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberofSets++;
                numOfSets.setText(numberofSets+"");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberofSets==1){
                    numberofSets=1;
                }
                else {
                    numberofSets--;
                    numOfSets.setText(numberofSets+"");
                }
            }
        });
        cunti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
fetchInfo();
            }
        });
        locationFromAddress.setText(addressFrom);
        locationToAddress.setText(addressTo);
        numOfSets.setText(numberofSets+"");
        detailsEdit.setText(details);
        subName.setText(subDetails);
    }
    private void initializer(){
     nameofcategory=findViewById(R.id.name);
     numOfSets=findViewById(R.id.numOfSets);
     locationFromAddress=findViewById(R.id.locationFromAddress);
     locationToAddress=findViewById(R.id.locationToAddress);
     detailsEdit=findViewById(R.id.details);
     subName=findViewById(R.id.subName);
     cunti=findViewById(R.id.cunti);
     plus=findViewById(R.id.plus);
     imageView=findViewById(R.id.relativelayout1);
     minus=findViewById(R.id.minus);
     locationFrom=findViewById(R.id.locationFrom);
     locationTo=findViewById(R.id.locationTo);
     mark=findViewById(R.id.mark);
     relativelayout1=findViewById(R.id.relativelayout1);
        numofdays=findViewById(R.id.numofdays);
        numnum=findViewById(R.id.numnum);
     type=findViewById(R.id.type);
    }

    public void fetchInfo() {
       // Toast.makeText(SubCategory.this,latFrom+"",Toast.LENGTH_LONG).show();
        if(!(sharedpref.getString("remember","").equals("yes"))){
            Toast.makeText(SubCategorySpecial.this,"قم بتسجيل الدخول أولا " ,Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog = ProgressDialog.show(SubCategorySpecial.this, "جاري تسجيل الطلب", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_order(sharedpref.getInt("id",0)
                    ,intent.getIntExtra("id",0),numberofSets,latFrom,lngFrom,latTo,lngTo
                ,locationFromAddress.getText().toString(),locationToAddress.getText().toString(),detailsEdit.getText().toString()
                ,type.getText().toString(),numofdays.getText().toString(),mark.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(SubCategorySpecial.this);
                dlgAlert.setMessage("تم تسجيل طلبك بنجاح");
                dlgAlert.setTitle("Direct");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SubCategorySpecial.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
