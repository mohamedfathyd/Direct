package com.khalej.direct.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.direct.Activity.SubCategory;
import com.khalej.direct.R;
import com.khalej.direct.model.Images;
import com.khalej.direct.model.contact_home;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_first_Company extends RecyclerView.Adapter<RecyclerAdapter_first_Company.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<Images> contactslist ;


    public RecyclerAdapter_first_Company(Context context, List<Images> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");


        Glide.with(context).load("http://jamalah.com/montag/direkt/"+contactslist.get(position).getImage()).error(R.drawable.logo).into(holder.image);
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Dialog settingsDialog = new Dialog(context);
                  settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                  settingsDialog.setContentView(R.layout.image_show);
                  ImageView img = (ImageView) settingsDialog.findViewById(R.id.img);
                  Glide.with(context).load("http://jamalah.com/montag/direkt/"+contactslist.get(position).getImage()).error(R.drawable.logo).into(img);
                  settingsDialog.show();
              }
          });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,details;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.name);
            image=(ImageView)itemView.findViewById(R.id.photo);
            details=itemView.findViewById(R.id.details);
        }
    }}