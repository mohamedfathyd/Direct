package com.khalej.direct.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.direct.Activity.SubCategory;
import com.khalej.direct.R;
import com.khalej.direct.RoundRectCornerImageView;
import com.khalej.direct.model.ChatMessage;
import com.khalej.direct.model.contact_home;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_chat extends RecyclerView.Adapter<RecyclerAdapter_chat.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<ChatMessage> contactslist;


    public RecyclerAdapter_chat(Context context, List<ChatMessage> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_date,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");
   if(contactslist.get(position).getType().equals("1")){
        holder.recive.setText(contactslist.get(position).getMessage());
        holder.recive.setTypeface(myTypeface);
        holder.send.setVisibility(View.GONE);
   }
   else {
       holder.send.setText(contactslist.get(position).getMessage());
       holder.send.setTypeface(myTypeface);
       holder.recive.setVisibility(View.GONE);
   }


    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView send,recive;

        public MyViewHolder(View itemView) {
            super(itemView);
            send=(TextView)itemView.findViewById(R.id.sender);
            recive=itemView.findViewById(R.id.recive);

        }
    }}