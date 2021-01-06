package com.khalej.direct.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.direct.Adapter.RecyclerAdapter_chat;
import com.khalej.direct.Adapter.RecyclerAdapter_first;
import com.khalej.direct.Adapter.RecyclerAdapter_morder;
import com.khalej.direct.R;
import com.khalej.direct.model.Apiclient_home;
import com.khalej.direct.model.ChatMessage;
import com.khalej.direct.model.Order;
import com.khalej.direct.model.apiinterface_home;
import com.khalej.direct.model.ChatMessage;
import com.khalej.direct.model.chatRoom;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    EditText message;
    ImageView send;
    ChatMessage messageData = new ChatMessage();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter_chat recyclerAdapter;
    private List<ChatMessage> contactList = new ArrayList<>();
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
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
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager = new GridLayoutManager(this, 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        message=findViewById(R.id.textchat);
        send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(sharedpref.getString("remember","").equals("yes"))){
                    Toast.makeText(ChatActivity.this,"قم بتسجيل الدخول أولا " ,Toast.LENGTH_LONG).show();
                    return;
                }
                if (message.getText().toString().equals("")||message.getText().toString()==null){

                }
                else{

                viewMessage();
                fetchInfoSend();

                }
            }
        });
        fetchInfo();
    }
    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<ChatMessage>> call = apiinterface.getcontacts_chat(sharedpref.getInt("room_id",0),sharedpref.getInt("id",0));
        call.enqueue(new Callback<List<ChatMessage>>() {
            @Override
            public void onResponse(Call<List<ChatMessage>> call, Response<List<ChatMessage>> response) {

   try {


       contactList = response.body();
       if (response.code() == 404) {
           contactList=new ArrayList<>();
           return;
       }
       if(contactList.isEmpty()){
           contactList=new ArrayList<>();
       }
       else {
         //  Toast.makeText(ChatActivity.this, "22", Toast.LENGTH_LONG).show();
          recyclerAdapter = new RecyclerAdapter_chat(ChatActivity.this, contactList);
     recyclerView.setAdapter(recyclerAdapter);
          recyclerView.scrollToPosition(contactList.size() - 1);
       }
   }
   catch (Exception e){
     //  Toast.makeText(ChatActivity.this,e+"",Toast.LENGTH_LONG).show();
       contactList=new ArrayList<>();
   }

            }

            @Override
            public void onFailure(Call<List<ChatMessage>> call, Throwable t) {
                contactList=new ArrayList<>();
            }
        });
    }
    public void fetchInfoSend() {

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<chatRoom> call = apiinterface.getcontacts_SendMessage(1,sharedpref.getInt("id",0),message.getText().toString());
        call.enqueue(new Callback<chatRoom>() {
            @Override
            public void onResponse(Call<chatRoom> call, Response<chatRoom> response) {
                message.setText("");
                chatRoom getRoomId=response.body();
                edt.putInt("room_id",getRoomId.getData().getRoom_id());
                edt.apply();

            }

            @Override
            public void onFailure(Call<chatRoom> call, Throwable t) {
                Toast.makeText(ChatActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void viewMessage(){
        ChatMessage messageDaa = new ChatMessage();
        messageDaa.setMessage(message.getText().toString());
        messageDaa.setType("2");
        contactList.add(messageDaa);
        recyclerAdapter=new RecyclerAdapter_chat(ChatActivity.this,contactList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.scrollToPosition(contactList.size()-1);


    }
}
