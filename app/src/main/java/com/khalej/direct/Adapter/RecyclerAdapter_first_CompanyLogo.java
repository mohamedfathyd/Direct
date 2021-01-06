package com.khalej.direct.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.khalej.direct.R;
import com.khalej.direct.model.Companys;
import com.khalej.direct.model.Images;
import com.khalej.direct.model.contact_annonce;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_first_CompanyLogo extends RecyclerView.Adapter<RecyclerAdapter_first_CompanyLogo.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<Companys> contactslist;
    RecyclerView recyclerView;
    List<Images>images=new ArrayList<>();

    public RecyclerAdapter_first_CompanyLogo(Context context, List<Companys> contactslist, RecyclerView recyclerView){
        this.contactslist=contactslist;
        this.context=context;
        this.recyclerView=recyclerView;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.annonce_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {


            Glide.with(context).load("http://jamalah.com/montag/direkt/"+contactslist.get(position).getLogo()).error(R.drawable.logo).into(holder.image);
        }
        catch (Exception e){}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  Dialog settingsDialog = new Dialog(context);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.setContentView(R.layout.image_show);
                ImageView img = (ImageView) settingsDialog.findViewById(R.id.img);
                Glide.with(context).load(contactslist.get(position).getLogo()).error(R.drawable.logo).into(img);
                settingsDialog.show();*/
                images=contactslist.get(position).getImages();
                RecyclerAdapter_first_Company  recyclerAdapter=new RecyclerAdapter_first_Company(context,images);
                recyclerView.setAdapter(recyclerAdapter);
            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.photo);

        }
    }}