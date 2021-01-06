package com.khalej.direct.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khalej.direct.R;
import com.khalej.direct.RoundRectCornerImageView;
import com.khalej.direct.model.notificationData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_notification extends RecyclerView.Adapter<RecyclerAdapter_notification.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<notificationData> contactslist;

    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    public RecyclerAdapter_notification(Context context, List<notificationData> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationlist,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        if(sharedpref.getString("language","").trim().equals("ar")){
            if(contactslist.get(position).getBody_ar()==null){
        holder.details.setText(contactslist.get(position).getBody_en());}
        else{
                holder.details.setText(contactslist.get(position).getBody_ar());
            }
            if(contactslist.get(position).getName_ar()==null){
                holder.Name.setText(contactslist.get(position).getName_ar());}
            else{
                holder.Name.setText(contactslist.get(position).getName_ar());
            }
        }
        else{
            if(contactslist.get(position).getBody_en()==null){
                holder.details.setText(contactslist.get(position).getBody_ar());}
            else{
                holder.details.setText(contactslist.get(position).getBody_en());
            }
            if(contactslist.get(position).getName_en()==null){
                holder.Name.setText(contactslist.get(position).getName_ar());}
            else{
                holder.Name.setText(contactslist.get(position).getName_en());
            }
        }
        holder.Name.setTypeface(myTypeface);
        holder.Date.setText(contactslist.get(position).getDate());
        holder.Date.setTypeface(myTypeface);

        holder.details.setTypeface(myTypeface);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,details,Date;
        RoundRectCornerImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            Date=itemView.findViewById(R.id.txt_fish_title);
            Name=(TextView)itemView.findViewById(R.id.txt_title);
            details=itemView.findViewById(R.id.txt_);
        }
    }}