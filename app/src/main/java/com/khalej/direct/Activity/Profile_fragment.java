package com.khalej.direct.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.direct.Adapter.RecyclerAdapter_first;
import com.khalej.direct.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.direct.Adapter.RecyclerAdapter_morder;
import com.khalej.direct.R;
import com.khalej.direct.model.Apiclient_home;
import com.khalej.direct.model.Order;
import com.khalej.direct.model.apiinterface_home;
import com.khalej.direct.model.contact_annonce;
import com.khalej.direct.model.contact_home;

import java.util.ArrayList;
import java.util.List;


public class Profile_fragment extends Fragment {
    CircleImageView image;
    TextView name ,address,phone ;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter_morder recyclerAdapter;
    private List<Order> contactList=new ArrayList<>();
    private apiinterface_home apiinterface;
    ImageView notification;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        image=view.findViewById(R.id.image);
        name=view.findViewById(R.id.username);
        address=view.findViewById(R.id.address);
        phone=view.findViewById(R.id.phone);
        Glide.with(getContext()).load(sharedpref.getString("image","")).error(R.drawable.profile).into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog settingsDialog = new Dialog(getContext());
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.setContentView(R.layout.image_show);
                ImageView img = (ImageView) settingsDialog.findViewById(R.id.img);
                Glide.with(getContext()).load(sharedpref.getString("image","")).error(R.drawable.profile).into(img);
                settingsDialog.show();
            }
        });
        name.setText(sharedpref.getString("name",""));
        address.setText(sharedpref.getString("address",""));
        phone.setText(sharedpref.getString("phone",""));
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        layoutManager = new GridLayoutManager(getContext(), 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetchInfo();
        notification=view.findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Notification.class));
            }
        });
        return view;
    }
    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<Order>> call = apiinterface.getcontacts_MyOrder(sharedpref.getInt("id",0),1);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                progressBar.setVisibility(View.GONE);

                contactList = response.body();
                try{
                if(contactList.size()!=0||!(contactList.isEmpty())) {
                progressBar.setVisibility(View.GONE);
                recyclerAdapter=new RecyclerAdapter_morder(getActivity(),contactList);
                recyclerView.setAdapter(recyclerAdapter);}}
                catch (Exception e){
                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}
