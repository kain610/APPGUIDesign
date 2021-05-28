package com.example.guidesign;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectStreamClass;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatActivity extends AppCompatActivity implements TextWatcher {

    private static final int IMAGE_REQUEST_ID = 1;
    private String name;
    private String SERVER_PATH = "ws://echo.websocket.org";
    private WebSocket webSocket;

    private EditText messageEdit;
    private View sendBtn, pickImgBtn;
    private RecyclerView recyclerView;

    private MessageAdapter messageAdapter;//先別管我


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
        }
        name = "name";


        initiateSocketConnection();
    }

    public void initiateSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, new SocketListener());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String string = editable.toString().trim();
        if(string.isEmpty()){
            resetMessageEdit();
        }else{
            sendBtn.setVisibility(View.VISIBLE);
            pickImgBtn.setVisibility(View.INVISIBLE);
        }

    }
    private void resetMessageEdit() {
        messageEdit.removeTextChangedListener(this);

        messageEdit.setText("");
        sendBtn.setVisibility(View.INVISIBLE);
        pickImgBtn.setVisibility(View.VISIBLE);

        messageEdit.addTextChangedListener(this);
    }

    public class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            runOnUiThread(() -> {
                Toast.makeText(ChatActivity.this, "Socket Connection Successful",
                        Toast.LENGTH_SHORT).show();
                initializeView();
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);

            runOnUiThread(() -> {
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    jsonObject.put("isSent", false);
                    messageAdapter.addItem(jsonObject);//先別管我
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void initializeView() {

        messageEdit = findViewById(R.id.messageEdit);
        sendBtn = findViewById(R.id.sendBtn);

        recyclerView = findViewById(R.id.recyclerView);

        messageEdit.addTextChangedListener( this);
        messageAdapter = new MessageAdapter(getLayoutInflater());//先別管我
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//先別管我
        recyclerView.setAdapter(messageAdapter);//先別管我



        sendBtn.setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("message", messageEdit.getText().toString());

                webSocket.send(jsonObject.toString());
                jsonObject.put("isSent", true);
                messageAdapter.addItem(jsonObject);//先別管我
                resetMessageEdit();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }




}