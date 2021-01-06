package com.khalej.direct.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khalej.direct.R;

public class OrdersDetails extends AppCompatActivity {
TextView toolbar_title,name, phone,details;
    CircleImageView image;
Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_details);
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
        intent=getIntent();
        name=findViewById(R.id.username);
        image=findViewById(R.id.image);
        name.setText(intent.getStringExtra("name"));
        if(name.getText().toString().equals("")||name.getText()==null){
            name.setText("لم يتم ربط طلبك بمقدم خدمة  او الموافقه عليه بعد من قبل الأدارة");
            image.setVisibility(View.INVISIBLE);
        }
        phone=findViewById(R.id.phone);
        phone.setText(intent.getStringExtra("phone"));
        details=findViewById(R.id.details);
        details.setText(intent.getStringExtra("details"));

        Glide.with(this).load(intent.getStringExtra("image")).error(R.drawable.profile).into(image);

    }
}
