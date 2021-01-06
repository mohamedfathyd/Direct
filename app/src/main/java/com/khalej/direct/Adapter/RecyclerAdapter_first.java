package com.khalej.direct.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.direct.Activity.SubCategory;
import com.khalej.direct.R;
import com.khalej.direct.RoundRectCornerImageView;
import com.khalej.direct.model.contact_home;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_first extends RecyclerView.Adapter<RecyclerAdapter_first.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_home> contactslist;


    public RecyclerAdapter_first(Context context, List<contact_home> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_circle_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");

        holder.Name.setText(contactslist.get(position).getname());
        holder.Name.setTypeface(myTypeface);
        holder.details.setText(contactslist.get(position).getDetails());
        holder.details.setTypeface(myTypeface);
        Glide.with(context).load("http://jamalah.com/montag/direkt/"+contactslist.get(position).getImg()).error(R.drawable.logo).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SubCategory.class);
                intent.putExtra("name",contactslist.get(position).getname());
                intent.putExtra("subDetails",contactslist.get(position).getDetails());
                intent.putExtra("image","http://jamalah.com/montag/direkt/"+contactslist.get(position).getImg());
                intent.putExtra("id",contactslist.get(position).getId());
                context.startActivity(intent);
            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,details;
        RoundRectCornerImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.photo);
            details=itemView.findViewById(R.id.details);
        }
    }}