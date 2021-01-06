package com.khalej.direct.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.direct.Adapter.RecyclerAdapter_morder;
import com.khalej.direct.Adapter.RecyclerAdapter_morder_car;
import com.khalej.direct.R;
import com.khalej.direct.model.Apiclient_home;
import com.khalej.direct.model.ChatMessage;
import com.khalej.direct.model.Orders;
import com.khalej.direct.model.Orders;
import com.khalej.direct.model.apiinterface_home;

import java.util.ArrayList;
import java.util.List;

public class CarProfile extends AppCompatActivity {
    CircleImageView image;
    TextView name ,address,phone ;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter_morder_car recyclerAdapter;
    private List<Orders> contactList=new ArrayList<>();
    private apiinterface_home apiinterface;
    ImageView logout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_profile);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        image=findViewById(R.id.image);
        name=findViewById(R.id.username);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        logout=findViewById(R.id.logout);
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
                startActivity(new Intent(CarProfile.this,Login.class));
                finish();
            }
        });
        Glide.with(this).load(sharedpref.getString("image","")).error(R.drawable.profile).into(image);
        name.setText(sharedpref.getString("name",""));
        address.setText(sharedpref.getString("address",""));
        phone.setText(sharedpref.getString("phone",""));
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        layoutManager = new GridLayoutManager(this, 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetchInfo();
    }
    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<Orders>> call = apiinterface.getcontacts_MyOrders(sharedpref.getInt("id",0),sharedpref.getInt("type",0));
        call.enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                progressBar.setVisibility(View.GONE);

                contactList = response.body();
                try{
                if(contactList.size()!=0||!(contactList.isEmpty())) {

                    progressBar.setVisibility(View.GONE);
                    recyclerAdapter = new RecyclerAdapter_morder_car(CarProfile.this, contactList);
                    recyclerView.setAdapter(recyclerAdapter);
                }}
                catch (Exception e){}
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}
