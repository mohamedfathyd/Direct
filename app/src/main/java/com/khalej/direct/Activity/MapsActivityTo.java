package com.khalej.direct.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.khalej.direct.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;


public class MapsActivityTo extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String lati, longg, image;
    Intent intent;
    Handler mHandler;
    String detail, addressFrom, subDetails;
    double lat, latFrom;
    String name, numofdays, mark, type;
    double lng, lngFrom;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    int getCategory_id, gender_id;
    Button cunti;
    LatLng latLngValue = null;
    TextView address;
     SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        address = findViewById(R.id.address);
        intent = getIntent();
        getCategory_id = intent.getIntExtra("id", 0);
        gender_id = intent.getIntExtra("numberSets", 0);
        addressFrom = intent.getStringExtra("addressFrom");
        detail = intent.getStringExtra("details");
        latFrom = intent.getDoubleExtra("latFrom", 0);
        lngFrom = intent.getDoubleExtra("lngFrom", 0);
        subDetails = intent.getStringExtra("subDetails");
        image = intent.getStringExtra("image");
        name = intent.getStringExtra("name");
        numofdays = intent.getStringExtra("numofdays");
        mark = intent.getStringExtra("mark");
        type = intent.getStringExtra("type");
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
                        Intent intent = new Intent(MapsActivityTo.this, SubCategory.class);
                        intent.putExtra("latTo", 0.0);
                        intent.putExtra("lngTo", 0.0);
                        intent.putExtra("id", getCategory_id);
                        intent.putExtra("latFrom", latFrom);
                        intent.putExtra("lngFrom", lngFrom);
                        intent.putExtra("numberSets", gender_id);
                        intent.putExtra("addressFrom", addressFrom);
                        intent.putExtra("detail", detail);
                        intent.putExtra("addressTo", "");
                        intent.putExtra("subDetails", subDetails);
                        intent.putExtra("image", image);
                        intent.putExtra("name", name);
                        intent.putExtra("mark", mark);
                        intent.putExtra("type", type);
                        intent.putExtra("numofdays", numofdays);

                        startActivity(intent);
                        finish();
                    }
                }
        );
        searchView=findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String Location=searchView.getQuery().toString();
                List<Address>addressList=null;
                if(Location !=null||!Location.equals("")){
                    Geocoder geocoder=new Geocoder(MapsActivityTo.this);
                    try{
                        addressList=geocoder.getFromLocationName(Location,1);
                    }catch (Exception e){}
                }
                Address addresss =null;
                try{
                    addresss =addressList.get(0);}
                catch (Exception e){}
                LatLng latLng= new LatLng(addresss.getLatitude(),addresss.getLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Direct").icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
                CameraUpdate location= CameraUpdateFactory.newLatLngZoom(latLng,14);
                mMap.animateCamera(location); Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(MapsActivityTo.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }
                address.setText(addresses.get(0).getAddressLine(0));
                latLngValue=latLng;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMap.clear();
                return false;
            }
        });
        cunti = findViewById(R.id.cunti);

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
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try{
            boolean success =googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map));
            if(success){}
        }catch (Exception e){}
        LatLng sydney;
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        if(sharedpref.getFloat("latlocation",0)==0){
            sydney = new LatLng(23.6587778, 43.0343392);
            Toast.makeText(MapsActivityTo.this,"من فضلك تأكد ان الخريطة تحديد الموقع يعمل بشكل جيد عن طريق فتح جوجل ماب وغلقه لكي يمكننا تحديد موقعك الحالي",Toast.LENGTH_LONG).show();

        }
        else{
            sydney = new LatLng(sharedpref.getFloat("latlocation",0), sharedpref.getFloat("lnglocation",0));
        }
        // mMap.addMarker(new MarkerOptions().position(sydney).title("HandMade"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate location= CameraUpdateFactory.newLatLngZoom(sydney,18);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Direct").icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(MapsActivityTo.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }
                address.setText(addresses.get(0).getAddressLine(0));
                latLngValue=latLng;
            }
        });
        mMap.animateCamera(location);
        cunti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latLngValue!=null){
                mylocation(latLngValue);}
            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void mylocation(final LatLng latLng){

        new AlertDialog.Builder(MapsActivityTo.this)
                .setTitle("direct App")
                .setMessage("هل هذا هو موقع نهاية رحلة النقل ؟")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Geocoder geocoder;
                        List<Address> addresses = null;
                        geocoder = new Geocoder(MapsActivityTo.this, Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Intent intent=new Intent(MapsActivityTo.this,SubCategory.class);
                        intent.putExtra("latTo",latLng.latitude);
                        intent.putExtra("lngTo",latLng.longitude);
                        intent.putExtra("id",getCategory_id);
                        intent.putExtra("latFrom",latFrom);
                        intent.putExtra("lngFrom",lngFrom);
                        intent.putExtra("numberSets",gender_id);
                        intent.putExtra("addressFrom",addressFrom);
                        intent.putExtra("detail",detail);
                        intent.putExtra("addressTo",addresses.get(0).getAddressLine(0));
                        intent.putExtra("subDetails",subDetails);
                        intent.putExtra("image",image);
                        intent.putExtra("name",name);
                        intent.putExtra("mark",mark);
                        intent.putExtra("type",type);
                        intent.putExtra("numofdays",numofdays);

                        startActivity(intent);
                        finish();


                    }})
                .setNegativeButton(android.R.string.no,  new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mMap.clear();
                    }}).show();

    }

}

