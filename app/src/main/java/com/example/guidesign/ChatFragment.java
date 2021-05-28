package com.example.guidesign;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


public class ChatFragment extends Fragment implements TextWatcher {


    private MessageAdapter messageAdapter;
    private String name;
    private String SERVER_PATH = "ws://echo.websocket.org";
    private EditText messageEdit;
    private View sendBtn, pickImgBtn;
    private RecyclerView recyclerView;
    private WebSocket webSocket;

    public ChatFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initiateSocketConnection();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        name = " ";
        initializeView(root);



        return root;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //recycler
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        SpacesItemDecoration decoration = new SpacesItemDecoration(6);
        recyclerView.addItemDecoration(decoration);

        // define an adapter
        messageAdapter = new MessageAdapter(getLayoutInflater());
        recyclerView.setAdapter(messageAdapter);
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

        }

    }
    private void resetMessageEdit() {
        messageEdit.removeTextChangedListener(this);

        messageEdit.setText("");



        messageEdit.addTextChangedListener(this);
    }

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            getActivity().runOnUiThread(() -> {
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

    public void initializeView(View root) {

        messageEdit = root.findViewById(R.id.messageEdit);
        sendBtn = root.findViewById(R.id.sendBtn);

        recyclerView = root.findViewById(R.id.recyclerView);

        messageEdit.addTextChangedListener( this);
        messageAdapter = new MessageAdapter(getLayoutInflater());//先別管我
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//先別管我
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