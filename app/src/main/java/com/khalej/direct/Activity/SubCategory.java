package com.khalej.direct.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.khalej.direct.R;
import com.khalej.direct.model.Apiclient_home;
import com.khalej.direct.model.apiinterface_home;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SubCategory extends AppCompatActivity {
Intent intent;
String addressTo,addressFrom,details,name,subDetails;
double latFrom,lngFrom,latTo,lngTo;
int id ,numberofSets=1;
TextView nameofcategory,numOfSets,locationFromAddress,locationToAddress,subName;
EditText detailsEdit,type,numofdays,mark;
    ImageView NA;
    int x=0;
    String imagePath;
    private static final int CAMERA_REQUEST = 1;
    Call<ResponseBody> call = null;
Button cunti;
    ProgressDialog progressDialog;
    ImageView imageView;
    LinearLayout locationFrom,locationTo,numnum;
    private apiinterface_home apiinterface;
    String mediaPath;
ImageView plus,minus,relativelayout1;
    private SharedPreferences sharedpref;
    private static final int MY_CAMERA_PERMISSION_CODE = 1;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        initializer();
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
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        intent=getIntent();
        Glide.with(this).load(intent.getStringExtra("image")).error(R.drawable.logo).into(imageView);

        nameofcategory.setText(intent.getStringExtra("name"));
       addressFrom=intent.getStringExtra("addressFrom");
       addressTo=intent.getStringExtra("addressTo");
       details=intent.getStringExtra("detail");
       id=intent.getIntExtra("id",0);
       latFrom=intent.getDoubleExtra("latFrom",0);
       latTo=intent.getDoubleExtra("latTo",0);
       lngFrom=intent.getDoubleExtra("lngFrom",0);
       lngTo=intent.getDoubleExtra("lngTo",0);
       numberofSets=intent.getIntExtra("numberSets",1);
       name=intent.getStringExtra("name");
        NA=findViewById(R.id.NA);
        subDetails=intent.getStringExtra("subDetails");
        type.setHint("نوع ال" +intent.getStringExtra("name"));
        if(intent.getStringExtra("numofdays")!=null){
        numofdays.setText(intent.getStringExtra("numofdays"));}
        if(intent.getStringExtra("mark")!=null){
        mark.setText(intent.getStringExtra("mark"));}
        if(intent.getStringExtra("type")!=null){
        type.setText(intent.getStringExtra("type"));}
       locationToAddress.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(SubCategory.this,MapsActivityTo.class);
               intent.putExtra("addressFrom",addressFrom);
               intent.putExtra("addressTo",addressTo);
               intent.putExtra("detail",details);
               intent.putExtra("id",id);
               intent.putExtra("name",name);
               intent.putExtra("latFrom",latFrom);
               intent.putExtra("latTo",latTo);
               intent.putExtra("lngFrom",lngFrom);
               intent.putExtra("lngTo",lngTo);
               intent.putExtra("numberSets",numberofSets);
               intent.putExtra("subDetails",subDetails);
               intent.putExtra("mark",mark.getText().toString());
               intent.putExtra("type",type.getText().toString());
               intent.putExtra("numofdays",numofdays.getText().toString());
               intent.putExtra("image",intent.getStringExtra("image"));
               startActivity(intent);
               finish();
           }
       });
        locationFromAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SubCategory.this,MapsActivity.class);
                intent.putExtra("addressFrom",addressFrom);
                intent.putExtra("addressTo",addressTo);
                intent.putExtra("detail",details);
                intent.putExtra("id",id);
                intent.putExtra("latFrom",latFrom);
                intent.putExtra("latTo",latTo);
                intent.putExtra("name",name);
                intent.putExtra("lngFrom",lngFrom);
                intent.putExtra("lngTo",lngTo);
                intent.putExtra("numberSets",numberofSets);
                intent.putExtra("subDetails",subDetails);
                intent.putExtra("mark",mark.getText().toString());
                intent.putExtra("type",type.getText().toString());
                intent.putExtra("numofdays",numofdays.getText().toString());
                intent.putExtra("image",intent.getStringExtra("image"));

                startActivity(intent);
                finish();
            }
        });
        if(intent.getStringExtra("name").equals("تجميع")){
            mark.setHint("ماركة تجميع");
        }
        else if(intent.getStringExtra("name").equals("فك وتركيب")){
            mark.setHint("ماركة الأثاث");
            type.setHint("نوع الأثاث");
            NA.setVisibility(View.VISIBLE);
        }
        else if(intent.getStringExtra("name").equals("نقل الاثاث")){
            mark.setHint("نوع السيارة");
            type.setHint("نوع الأثاث");
        }
        else if (intent.getStringExtra("name").equals("توصيل")){
            mark.setVisibility(View.VISIBLE);
            mark.setHint("نوع السيارة");
        }
        else {
            mark.setVisibility(View.GONE);
        }
        if(intent.getStringExtra("name").equals("توصيل")||intent.getStringExtra("name").equals("نقل الاثاث")){
            locationToAddress.setVisibility(View.VISIBLE);
            locationTo.setVisibility(View.VISIBLE);

        }
        else{
            locationToAddress.setVisibility(View.GONE);
            locationTo.setVisibility(View.GONE);
        }
        if(intent.getStringExtra("name").equals("تخزين")){
            numofdays.setVisibility(View.VISIBLE);
        }
        else{
            numofdays.setVisibility(View.GONE);
        }
        if(intent.getStringExtra("name").equals("صيانه منزل")){
            numnum.setVisibility(View.GONE);
        }
        else{
            numnum.setVisibility(View.VISIBLE);
        }
        if(intent.getIntExtra("id",0)==4){
            mark.setVisibility(View.VISIBLE);
            mark.setHint("نوع الأثاث");
            locationToAddress.setVisibility(View.VISIBLE);
            locationTo.setVisibility(View.VISIBLE);
            numofdays.setVisibility(View.VISIBLE);
            NA.setVisibility(View.VISIBLE);
            numofdays.setHint("اسم المشترك");
            type.setHint("اسم شركة المنتج");

        }
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberofSets++;
                numOfSets.setText(numberofSets+"");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberofSets==1){
                    numberofSets=1;
                }
                else {
                    numberofSets--;
                    numOfSets.setText(numberofSets+"");
                }
            }
        });
        cunti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(locationFromAddress.getText().toString().equals("")||locationFromAddress.getText().toString()==null
                        ){
                    Toast.makeText(SubCategory.this,"قم بأستكمال البيانات المطلوبه",Toast.LENGTH_LONG).show();
                    return;
                }
                if(type.getVisibility()==View.VISIBLE){
                    if(type.getText().toString().equals("")||type.getText().toString()==null){
                        Toast.makeText(SubCategory.this,"قم بأستكمال البيانات المطلوبه",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if(numofdays.getVisibility()==View.VISIBLE){
                    if(numofdays.getText().toString().equals("")||numofdays.getText().toString()==null){
                        Toast.makeText(SubCategory.this,"قم بأستكمال البيانات المطلوبه",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if(mark.getVisibility()==View.VISIBLE){
                    if(mark.getText().toString().equals("")||mark.getText().toString()==null){
                        Toast.makeText(SubCategory.this,"قم بأستكمال البيانات المطلوبه",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if(NA.getVisibility()==View.VISIBLE){
                    if(mediaPath==null){
                        Toast.makeText(SubCategory.this,"قم بأستكمال البيانات المطلوبه",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if(detailsEdit.getVisibility()==View.VISIBLE){
                    if(detailsEdit.getText().toString().equals("")||detailsEdit.getText().toString()==null){
                        Toast.makeText(SubCategory.this,"قم بأستكمال البيانات المطلوبه",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
              if( intent.getStringExtra("name").equals("فك وتركيب")||intent.getIntExtra("id",0)==12){
                fetchInfoImage();}
                else{
                  fetchInfo();
              }
            }
        });
        locationFromAddress.setText(addressFrom);
        locationToAddress.setText(addressTo);
        numOfSets.setText(numberofSets+"");
        detailsEdit.setText(details);
        subName.setText(subDetails);

        NA.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    showPictureDialog();
                }
            }
        });
    }
    private void selectImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        x=0;
        startActivityForResult(galleryIntent, 0);

    }

    private void takePhotoFromCamera() {
        x=1;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",  /* suffix */
                    storageDir     /* directory */
            );
            Uri uri = FileProvider.getUriForFile(SubCategory.this, getPackageName(), image);
            imagePath=image.getAbsolutePath();//Store this path as globe variable

            Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST);
            } catch (IOException e) {
            e.printStackTrace();
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {

            mediaPath = imagePath;
            mediaPath=resizeAndCompressImageBeforeSend(SubCategory.this,mediaPath,"image");

            NA.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

           }
        if(requestCode == 0 && resultCode == RESULT_OK && null != data)
        {
            Uri pathImag = data.getData();
             String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(pathImag, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            mediaPath=resizeAndCompressImageBeforeSend(SubCategory.this,mediaPath,"image");

            NA.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            cursor.close();
 }

    }
    private void initializer(){
     nameofcategory=findViewById(R.id.name);
     numOfSets=findViewById(R.id.numOfSets);
     locationFromAddress=findViewById(R.id.locationFromAddress);
     locationToAddress=findViewById(R.id.locationToAddress);
     detailsEdit=findViewById(R.id.details);
     subName=findViewById(R.id.subName);
     cunti=findViewById(R.id.cunti);
     plus=findViewById(R.id.plus);
     imageView=findViewById(R.id.relativelayout1);
     minus=findViewById(R.id.minus);
     locationFrom=findViewById(R.id.locationFrom);
     locationTo=findViewById(R.id.locationTo);
     mark=findViewById(R.id.mark);
     relativelayout1=findViewById(R.id.relativelayout1);
        numofdays=findViewById(R.id.numofdays);
        numnum=findViewById(R.id.numnum);
     type=findViewById(R.id.type);
    }

    public void fetchInfo() {
       // Toast.makeText(SubCategory.this,latFrom+"",Toast.LENGTH_LONG).show();
        if(!(sharedpref.getString("remember","").equals("yes"))){
            Toast.makeText(SubCategory.this,"قم بتسجيل الدخول أولا " ,Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog = ProgressDialog.show(SubCategory.this, "جاري تسجيل الطلب", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_order(sharedpref.getInt("id",0)
                    ,intent.getIntExtra("id",0),numberofSets,latFrom,lngFrom,latTo,lngTo
                ,locationFromAddress.getText().toString(),locationToAddress.getText().toString(),detailsEdit.getText().toString()
                ,type.getText().toString(),numofdays.getText().toString(),mark.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(SubCategory.this);
                dlgAlert.setMessage("تم تسجيل طلبك بنجاح");
                dlgAlert.setTitle("Direct");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SubCategory.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchInfoImage() {
        String image="";
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        RequestBody a1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(sharedpref.getInt("id",0)));
        RequestBody a111 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(intent.getIntExtra("id",0)));

        RequestBody a2 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(numberofSets));
        RequestBody a3=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latFrom));
        RequestBody a4=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lngFrom));
        RequestBody a5=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latTo));
        RequestBody a6= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lngTo));
        RequestBody a7=RequestBody.create(MediaType.parse("text/plain"),locationFromAddress.getText().toString());
        RequestBody a8= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), locationToAddress.getText().toString());
        RequestBody a9=RequestBody.create(MediaType.parse("text/plain"),detailsEdit.getText().toString());
        RequestBody a10= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), type.getText().toString());
        RequestBody a11=RequestBody.create(MediaType.parse("text/plain"),numofdays.getText().toString());
        RequestBody a12= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), mark.getText().toString());

        // Toast.makeText(SubCategory.this,latFrom+"",Toast.LENGTH_LONG).show();
        if(!(sharedpref.getString("remember","").equals("yes"))){
            Toast.makeText(SubCategory.this,"قم بتسجيل الدخول أولا " ,Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog = ProgressDialog.show(SubCategory.this, "جاري تسجيل الطلب", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_order_Image(fileToUpload,a1,a111,a2,
                a3,a4,a5,a6,a7,a8,a9,a10,a11,a12);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(SubCategory.this);
                dlgAlert.setMessage("تم تسجيل طلبك بنجاح");
                dlgAlert.setTitle("Direct");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SubCategory.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                cameraIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    public static String resizeAndCompressImageBeforeSend(Context context,String filePath,String fileName){
        final int MAX_IMAGE_SIZE = 400 * 1024; // max final file size in kilobytes

        // First decode with inJustDecodeBounds=true to check dimensions of image
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);

        // Calculate inSampleSize(First we are going to resize the image to 800x800 image, in order to not have a big but very low quality image.
        //resizing the image will already reduce the file size, but after resizing we will check the file size and start to compress image
        options.inSampleSize = calculateInSampleSize(options, 800, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig= Bitmap.Config.ARGB_8888;

        Bitmap bmpPic = BitmapFactory.decodeFile(filePath,options);


        int compressQuality = 100; // quality decreasing by 5 every loop.
        int streamLength;
        do{
            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            Log.d("compressBitmap", "Quality: " + compressQuality);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
            compressQuality -= 5;
            Log.d("compressBitmap", "Size: " + streamLength/1024+" kb");
        }while (streamLength >= MAX_IMAGE_SIZE);

        try {
            //save the resized and compressed file to disk cache
            Log.d("compressBitmap","cacheDir: "+context.getCacheDir());
            FileOutputStream bmpFile = new FileOutputStream(context.getCacheDir()+fileName);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
            bmpFile.flush();
            bmpFile.close();
        } catch (Exception e) {
            Log.e("compressBitmap", "Error on saving file");
        }
        //return the path of resized and compressed file
        return  context.getCacheDir()+fileName;
    }



    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        String debugTag = "MemoryInformation";
        // Image nin islenmeden onceki genislik ve yuksekligi
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(debugTag,"image height: "+height+ "---image width: "+ width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(debugTag,"inSampleSize: "+inSampleSize);
        return inSampleSize;
    }
}
