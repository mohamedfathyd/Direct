package com.khalej.direct.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.khalej.direct.Adapter.RecyclerAdapter_first;
import com.khalej.direct.Adapter.RecyclerAdapter_first_Company;
import com.khalej.direct.Adapter.RecyclerAdapter_first_CompanyLogo;
import com.khalej.direct.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.direct.R;
import com.khalej.direct.model.Apiclient_home;
import com.khalej.direct.model.Companys;
import com.khalej.direct.model.Images;
import com.khalej.direct.model.apiinterface_home;
import com.khalej.direct.model.contact_annonce;

import java.util.ArrayList;
import java.util.List;


public class Offers_Fragment extends Fragment {

    private RecyclerView recyclerView,recyclerView2;
    private RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    private List<Companys> contactList_annonce=new ArrayList<>();
    private List<Images> ImageList=new ArrayList<>();
    private RecyclerAdapter_first_Company recyclerAdapter;
    private RecyclerAdapter_first_CompanyLogo recyclerAdapter_annonce;
    private apiinterface_home apiinterface;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_main_fragment, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        layoutManager = new GridLayoutManager(getContext(), 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        2, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView2=(RecyclerView)view.findViewById(R.id.recyclerview2);
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView2.setHasFixedSize(true);


        fetchInfo_annonce();
        return view;
    }

public void scroll(int h,int i){
   // Toast.makeText(getContext(),h+"",Toast.LENGTH_LONG).show();
    for(int x=0;x<h;x++){
       //  Toast.makeText(getContext(),x+"",Toast.LENGTH_LONG).show();

        ImageList.add(contactList_annonce.get(i).getImages().get(x));
    }
    //  Toast.makeText(getContext(),ImageList+"",Toast.LENGTH_LONG).show();

}
    public void fetchInfo_annonce(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<Companys>> call = apiinterface.getcontacts_Companys();
        call.enqueue(new Callback<List<Companys>>() {
            @Override
            public void onResponse(Call<List<Companys>> call, Response<List<Companys>> response) {
                contactList_annonce = response.body();
                progressBar.setVisibility(View.GONE);

                  try{
                 if(contactList_annonce.size()!=0){
                recyclerAdapter_annonce=new RecyclerAdapter_first_CompanyLogo(getActivity(),contactList_annonce,recyclerView);
                recyclerView2.setAdapter(recyclerAdapter_annonce);
//               ImageList=contactList_annonce.get(0).getImages();
//                recyclerAdapter=new RecyclerAdapter_first_Company(getActivity(),ImageList);
     //           recyclerView.setAdapter(recyclerAdapter);
                     }

                      }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<List<Companys>> call, Throwable t) {

            }
        });
    }

}
