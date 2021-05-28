package com.example.guidesign;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    private final int TYPE_MESSAGE_SENT = 0;
    private final int TYPE_MESSAGE_RECEIVED = 1;


    private LayoutInflater inflater;
    private List<JSONObject> messages = new ArrayList<>();

    public MessageAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch(viewType){
            case TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.item_send_message, parent, false);
                return new SentMessageHolder(view);
            case TYPE_MESSAGE_RECEIVED:
                view = inflater.inflate(R.layout.item_received_message, parent, false);
                return new ReceivedMessageHolder(view);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        JSONObject message = messages.get(position);

        try {
            if(message.getBoolean("isSent")){
                if(message.has("message")){
                    SentMessageHolder messageHolder = (SentMessageHolder) holder;
                    messageHolder.messageTxt.setText(message.getString("message"));
                }else{
                    SentImageHolder imageHolder = (SentImageHolder) holder;
                    Bitmap bitmap = getBitmapFromString(message.getString("image"));

                    imageHolder.imageView.setImageBitmap(bitmap);
                }
            }else{
                if(message.has("message")){
                    ReceivedMessageHolder messageHolder = (ReceivedMessageHolder) holder;
                    messageHolder.nameTxt.setText(message.getString("name"));
                    messageHolder.messageTxt.setText(message.getString("message"));
                }else{
                    ReceivedImageHolder imageHolder = (ReceivedImageHolder) holder;
                    imageHolder.nameTxt.setText(message.getString("name"));

                    Bitmap bitmap = getBitmapFromString(message.getString("image"));
                    imageHolder.imageView.setImageBitmap(bitmap);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromString(String image) {
        byte[] bytes = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItem(JSONObject jsonObject) {
        messages.add(jsonObject);
        notifyDataSetChanged();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageTxt;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);

            messageTxt = itemView.findViewById(R.id.sentTxt);
        }
    }

    private class SentImageHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public SentImageHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder{

        TextView nameTxt, messageTxt;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.nameTxt);
            messageTxt = itemView.findViewById(R.id.receivedTxt);
        }
    }

    private class ReceivedImageHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView nameTxt;

        public ReceivedImageHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
        }
    }

    public int getItemViewType(int position) {

        JSONObject message = messages.get(position);

        try {
            if(message.getBoolean("isSent")){
                if(message.has("message")){
                    return TYPE_MESSAGE_SENT;
                }else{

                }
            }else{
                if(message.has("message")){
                    return TYPE_MESSAGE_RECEIVED;
                }else{

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }
}

