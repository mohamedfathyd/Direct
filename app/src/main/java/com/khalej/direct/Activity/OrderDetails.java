package com.khalej.direct.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.khalej.direct.R;

public class OrderDetails extends AppCompatActivity implements OnMapReadyCallback {
TextView name,phonee,address;
double latfrom,lngfrom;
Intent intent;
    private GoogleMap mMap;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
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
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        phonee=findViewById(R.id.phone);
        intent=getIntent();
        name.setText(intent.getStringExtra("name"));
        address.setText(intent.getStringExtra("address"));
        phonee.setText(intent.getStringExtra("phone"));
        latfrom=intent.getDoubleExtra("latFrom",0);
        lngfrom=intent.getDoubleExtra("lngFrom",0);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(latfrom, lngfrom);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Location").snippet(""));

        // For zooming automatically to the location of the marker
        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(sydney,16);
        mMap.animateCamera(cameraPosition);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void mylocation(final LatLng latLng){


    }
}


