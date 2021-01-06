package com.khalej.direct.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.khalej.direct.model.Apiclient_home;
import com.khalej.direct.model.apiinterface_home;
import com.khalej.direct.model.contact_userinfo;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_ {
    private contact_userinfo contactList;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    ProgressDialog progressDialog;
    public void fetchInfo(final Context context, String phone, String password){
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();

        progressDialog = ProgressDialog.show(context,"جاري تسجيل الدخول","Please wait...",false,false);
        progressDialog.show();
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_userinfo> call= apiinterface.getcontacts_login(phone,
               password);
        call.enqueue(new Callback<contact_userinfo>() {
            @Override
            public void onResponse(Call<contact_userinfo> call, Response<contact_userinfo> response) {
                contactList = response.body();
                if (response.code() == 404) {
                    Toast.makeText(context,"هناك خطأ فى الهاتف او الرقم السري ",Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            try{
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                dlgAlert.setMessage("تم تسجيل الدخول بنجاح");
                dlgAlert.setTitle("Direct");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                edt.putInt("id",contactList.getId());
                edt.putString("name",contactList.getName());
                edt.putString("image","http://jamalah.com/montag/direkt/"+contactList.getImage());
                edt.putString("phone",contactList.getPhone());
                edt.putString("address",contactList.getMaddress());
                edt.putString("password",contactList.getPassword());
                edt.putString("createdAt",contactList.getCreatedAt());
                edt.putString("remember","yes");
                edt.apply();

            }
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<contact_userinfo> call, Throwable t) {
                Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
