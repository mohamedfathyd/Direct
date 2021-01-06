package com.khalej.direct.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.direct.Activity.OrdersDetails;
import com.khalej.direct.Activity.SubCategory;
import com.khalej.direct.R;
import com.khalej.direct.RoundRectCornerImageView;
import com.khalej.direct.model.Order;
import com.khalej.direct.model.contact_home;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_morder extends RecyclerView.Adapter<RecyclerAdapter_morder.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<Order> contactslist;


    public RecyclerAdapter_morder(Context context, List<Order> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recervation,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");

        holder.Name.setText(contactslist.get(position).getName());
        holder.Name.setTypeface(myTypeface);
        holder.details.setText(contactslist.get(position).getDetails());
        holder.details.setTypeface(myTypeface);
        holder.price.setText( "السعر :" +contactslist.get(position).getPrice());
        holder.price.setTypeface(myTypeface);
        holder.date.setText("التاريخ :"+contactslist.get(position).getDate());
        holder.date.setTypeface(myTypeface);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrdersDetails.class);
                intent.putExtra("details",contactslist.get(position).getDetails());
                intent.putExtra("name",contactslist.get(position).getRepresentative_name());
                intent.putExtra("phone",contactslist.get(position).getRepresentative_phone());
                intent.putExtra("image","http://jamalah.com/montag/direkt/"+contactslist.get(position).getRepresentative_image());
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,details,date,price;
        RoundRectCornerImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.txt_fish_title);
            date=itemView.findViewById(R.id.txt_title);
            details=itemView.findViewById(R.id.txt_);
            price=itemView.findViewById(R.id.idd);
        }
    }}