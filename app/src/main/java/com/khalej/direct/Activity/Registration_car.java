package com.khalej.direct.Activity;

import android.Manifest;
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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.khalej.direct.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.direct.R;
import com.khalej.direct.model.Apiclient_home;
import com.khalej.direct.model.Citys;
import com.khalej.direct.model.apiinterface_home;
import com.khalej.direct.model.contact_annonce;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration_car extends AppCompatActivity {
    private  static final int IMAGEUser = 100;
    Bitmap bitmapUser;
    private  static final int IMAGEId = 99;
    Bitmap bitmapId;
    private  static final int IMAGECarId = 98;
    Bitmap bitmapCarId;
    String mediaPath,mediaPathCar,mediaPathId;
    TextInputLayout textInputLayoutname,textInputLayoutaddress,textInputLayoutphone,
            textInputLayoutpassword,textInputLayoutconfirmpassword;
    TextInputEditText textInputEditTextname,textInputEditTextaddress,textInputEditTextphone,textInputEditTextPasswordd,
            textInputEditTextpassword,textInputEditTextconfirmpassword,textInputEditTextcarmodel;
    AppCompatButton regesiter;
    AppCompatTextView openlogin;
    TextView textInputEditTextlocation;
    Call<ResponseBody> call = null;
    String code,mVerificationId;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog1;
    login_ login_;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    String codee =null;
    List<Citys> citys =new ArrayList<>();
    int MY_CAMERA_PERMISSION_CODE=1;
    double lat,lng;
    Spinner textInputEditTextcity;
    private apiinterface_home apiinterface;
    Intent intent;
ImageView imagecar,imageid,useradd;
    String  imagePathuser,imagePathId,imagePathShop;
    int CAMERA_REQUEST_USER=5;
    int CAMERA_REQUEST_ID=6;
    int CAMERA_REQUEST_SHOP=7;
    int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_car);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);

        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();

        inisialize();
        intent=getIntent();
        textInputEditTextname.setText(intent.getStringExtra("name"));
        textInputEditTextaddress.setText(intent.getStringExtra("address"));
        textInputEditTextlocation.setText(intent.getStringExtra("addressFrom"));
        textInputEditTextpassword.setText(intent.getStringExtra("carNumber"));
        textInputEditTextcarmodel.setText(intent.getStringExtra("carModel"));

        textInputEditTextPasswordd.setText(intent.getStringExtra("password"));
        textInputEditTextphone.setText(intent.getStringExtra("phone"));
        lat=intent.getDoubleExtra("latyou",0);
        lng=intent.getDoubleExtra("lngyou",0);
        mediaPath=intent.getStringExtra("mediapath");
        textInputEditTextlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Registration_car.this,LocationCar.class);
                intent.putExtra("name",textInputEditTextname.getText().toString());
                intent.putExtra("address",textInputEditTextaddress.getText().toString());
                intent.putExtra("phone",textInputEditTextphone.getText().toString());
                intent.putExtra("carNumber",textInputEditTextpassword.getText().toString());
                intent.putExtra("carModel",textInputEditTextcarmodel.getText().toString());
                intent.putExtra("password",textInputEditTextPasswordd.getText().toString());
                intent.putExtra("mediapath",mediaPath);
                try{
                    useradd.setImageBitmap(BitmapFactory.decodeFile(mediaPath));}
                catch (Exception e){}
                //  intent.putExtra("Image",bitmapUser.toString());
                intent.putExtra("latyou","");
                intent.putExtra("lngyou","");
                startActivity(intent);
                finish();

            }
        });
        fetchInfoCity();

        regesiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textInputEditTextname.getText().toString().equals("") || textInputEditTextname.getText().toString() == null) {

                    textInputLayoutname.setError("أدخل اسم المستخدم");

                } else if (textInputEditTextphone.getText().toString().equals("") || textInputEditTextphone.getText().toString() == null) {

                    textInputLayoutphone.setError("أدخل رقم الموبيل");

                } else if (textInputEditTextpassword.getText().toString().equals("") || textInputEditTextpassword.getText().toString() == null) {

                    textInputLayoutpassword.setError("أدخل رقم السيارة");

                } else if (textInputEditTextcarmodel.getText().toString().equals("") || textInputEditTextcarmodel.getText().toString() == null) {

                    textInputLayoutconfirmpassword.setError("أدخل  نوع السيارة");

                }else {


                    fetchInfo();
                }
            }
        });
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(Registration_car.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Registration_car.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        useradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialogUser();
            }
        });
        imagecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialogShop();
            }
        });
        imageid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialogID();
            }
        });
    }

    private void selectImageUser() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGEUser);
    }
    private void showPictureDialogUser(){
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
            Uri uri = FileProvider.getUriForFile(Registration_car.this, getPackageName(), image);
            imagePathuser=image.getAbsolutePath();//Store this path as globe variable

            Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_USER);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    private void selectImageCar() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGECarId);
    }
    private void showPictureDialogShop(){
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
                                choosePhotoFromGallaryShop();
                                break;
                            case 1:
                                takePhotoFromCameraShop();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallaryShop() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        x=0;
        startActivityForResult(galleryIntent, 2);

    }

    private void takePhotoFromCameraShop() {
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
            Uri uri = FileProvider.getUriForFile(Registration_car.this, getPackageName(), image);
            imagePathShop=image.getAbsolutePath();//Store this path as globe variable

            Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_SHOP);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    private void selectImageId() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGEId);
    }
    private void showPictureDialogID(){
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
                                choosePhotoFromGallaryID();
                                break;
                            case 1:
                                takePhotoFromCameraID();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallaryID() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        x=0;
        startActivityForResult(galleryIntent, 1);

    }

    private void takePhotoFromCameraID() {
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
            Uri uri = FileProvider.getUriForFile(Registration_car.this, getPackageName(), image);
            imagePathId=image.getAbsolutePath();//Store this path as globe variable

            Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGEUser && resultCode == RESULT_OK && null != data)
        {

            Uri pathImag = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(pathImag, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Toast.makeText(Registration.this,mediaPath,Toast.LENGTH_LONG).show();
            mediaPath=resizeAndCompressImageBeforeSend(Registration_car.this,mediaPath,"image");

            useradd.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();


        }
        if (requestCode == CAMERA_REQUEST_USER && resultCode == Activity.RESULT_OK)
        {

            mediaPath = imagePathuser;
            mediaPath=resizeAndCompressImageBeforeSend(Registration_car.this,mediaPath,"image");

            useradd.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

        }
        if(requestCode== IMAGECarId && resultCode == RESULT_OK && null != data)
        {
            Uri pathImag = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(pathImag, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPathCar = cursor.getString(columnIndex);
            // Toast.makeText(Registration.this,mediaPath,Toast.LENGTH_LONG).show();
            mediaPathCar=resizeAndCompressImageBeforeSend(Registration_car.this,mediaPathCar,"imageCar");

            imagecar.setImageBitmap(BitmapFactory.decodeFile(mediaPathCar));
            cursor.close();
        }
        if (requestCode == CAMERA_REQUEST_SHOP&& resultCode == Activity.RESULT_OK)
        {

            mediaPathCar = imagePathShop;
            mediaPathCar=resizeAndCompressImageBeforeSend(Registration_car.this,mediaPathCar,"imageCar");

            imagecar.setImageBitmap(BitmapFactory.decodeFile(mediaPathCar));

        }
        if(requestCode== IMAGEId && resultCode == RESULT_OK && null != data)
        {
            Uri pathImag = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(pathImag, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPathId = cursor.getString(columnIndex);
            // Toast.makeText(Registration.this,mediaPath,Toast.LENGTH_LONG).show();
                mediaPathId=resizeAndCompressImageBeforeSend(Registration_car.this,mediaPathId,"imageId");

            imageid.setImageBitmap(BitmapFactory.decodeFile(mediaPathId));
            cursor.close();
        }
        if (requestCode == CAMERA_REQUEST_ID&& resultCode == Activity.RESULT_OK)
        {

            mediaPathId = imagePathId;
            mediaPathId=resizeAndCompressImageBeforeSend(Registration_car.this,mediaPathId,"imageId");

            imageid.setImageBitmap(BitmapFactory.decodeFile(mediaPathId));

        }


    }



    public void fetchInfo() {
        if(mediaPath==null||mediaPathId==null||mediaPathCar==null){
            Toast.makeText(Registration_car.this,  "من فضلك ادخل الصور الفارغة" ,Toast.LENGTH_LONG).show();
            return;
        }
        String image="";
        MultipartBody.Part fileToUpload,fileToUploadid,fileToUploadcar;
        try{
           // image = convertToStringUser();
            File file = new File(mediaPath);

            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);


        }
        catch (Exception e){
            Toast.makeText(Registration_car.this,"من فضلك اختر صورة شخصية لك" , Toast.LENGTH_LONG).show();
            return;

        }
        String imageCar="";
        try{
           // imageCar = convertToStringCar();
            File file = new File(mediaPathCar);

            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
             fileToUploadcar = MultipartBody.Part.createFormData("car_image", file.getName(), requestBody);

        }
        catch (Exception e){
            Toast.makeText(Registration_car.this,"من فضلك اختر صورة رخصة القيادة" , Toast.LENGTH_LONG).show();
            return;

        }
        String imageId="";
        try{
          //  imageId = convertToStringId();
            File file = new File(mediaPathId);

            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
             fileToUploadid = MultipartBody.Part.createFormData("id_image", file.getName(), requestBody);

        }
        catch (Exception e){
            Toast.makeText(Registration_car.this,"من فضلك اختر صورة البطاقة الشخصية" , Toast.LENGTH_LONG).show();
            return;

        }
        progressDialog = ProgressDialog.show(Registration_car.this, "جاري تسجيل طلبك للأنضمام كسائق جديد", "Please wait...", false, false);
        progressDialog.show();
        String phone=textInputEditTextphone.getText().toString();
        RequestBody a = RequestBody.create(MediaType.parse("text/plain"), textInputEditTextname.getText().toString());
        RequestBody b = RequestBody.create(MediaType.parse("text/plain"),  textInputEditTextPasswordd.getText().toString());
        RequestBody c=RequestBody.create(MediaType.parse("text/plain"),textInputEditTextaddress.getText().toString());
        RequestBody d=RequestBody.create(MediaType.parse("text/plain"),phone);
        RequestBody e=RequestBody.create(MediaType.parse("text/plain"),textInputEditTextpassword.getText().toString());
        RequestBody f= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), textInputEditTextcarmodel.getText().toString());
        RequestBody g= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(textInputEditTextcity.getSelectedItemPosition()+1));
        RequestBody h= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(2));
        RequestBody latt= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lat));
        RequestBody lngg= (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(lng));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_newaccountCar(fileToUpload,a,
                b,c,d,
              e,f,fileToUploadid,fileToUploadcar
               ,g,h,latt,lngg
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 422) {
                    Toast.makeText(Registration_car.this,"من فضلك أدخل البيانات صحيحة ... أو ان احد هذه البيانات مسجلة من قبل",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Registration_car.this);
                dlgAlert.setMessage("تم تسجيل طلبك كا سائق سنتواصل معك قريبا شكرا على ثقتك فينا ");
                dlgAlert.setTitle("Direct");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Registration_car.this, "من فضلك قم بأختيار صورة اقل فى الحجم ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void inisialize() {
        textInputLayoutname = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutaddress = (TextInputLayout) findViewById(R.id.textInputLayoutaddress);
        textInputLayoutphone = (TextInputLayout) findViewById(R.id.textInputLayoutphone);
        textInputLayoutpassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputEditTextcarmodel=(TextInputEditText)findViewById(R.id.textInputEditTextcarmodel);
        textInputLayoutconfirmpassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        textInputEditTextname = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextphone = (TextInputEditText) findViewById(R.id.textInputEditTextphone);
        textInputEditTextaddress = (TextInputEditText) findViewById(R.id.textInputEditTextaddress);
        textInputEditTextpassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextlocation =  findViewById(R.id.textInputEditTextlocation);
        textInputEditTextPasswordd=findViewById(R.id.textInputEditTextPasswordd);

        textInputEditTextcity=findViewById(R.id.textInputEditTextcity);
        imagecar=findViewById(R.id.imagecar);
        imageid=findViewById(R.id.imageid);
        useradd=findViewById(R.id.adduser);
        textInputEditTextconfirmpassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);
        regesiter = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        //  openlogin = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

    }
    public void fetchInfoCity(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<Citys>> call = apiinterface.Citys();
        call.enqueue(new Callback<List<Citys>>() {
            @Override
            public void onResponse(Call<List<Citys>> call, Response<List<Citys>> response) {
                try {


                    citys = response.body();
                    if(citys.size()!=0){
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (int i = 0; i < citys.size(); i++) {
                            if(sharedpref.getString("language","").trim().equals("ar")){
                            arrayList.add(citys.get(i).getAr_title());
                            }
                            else{
                                arrayList.add(citys.get(i).getEn_title());
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                Registration_car.this,
                                android.R.layout.simple_spinner_item,
                                arrayList
                        );
                        textInputEditTextcity.setAdapter(adapter);}
                }
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<List<Citys>> call, Throwable t) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                try {
                    if (!(grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(Registration_car.this, "Permission denied to access your location.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){}
            }
        }
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                cameraIntent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
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