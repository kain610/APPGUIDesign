package com.example.guidesign;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.guidesign.ui.home.HomeFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Upload extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar progressBar;
    private String filePath = null;
    private TextView txtPercentage;
    //private ImageView imgPreview;
    private VideoView vidPreview;
    private Button btnUpload;
    long totalSize = 0;

    private int act;
    private String username,direction, time , password;
    @SuppressLint("ResourceType")

    public Upload() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_upload, container, false);
         txtPercentage = (TextView) root.findViewById(R.id.txtPercentage);
        btnUpload = (Button) root.findViewById(R.id.btnUpload);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        //imgPreview = (ImageView) findViewById(R.id.imgPreview);
        vidPreview = (VideoView) root.findViewById(R.id.videoPreview);


        // image or video path that is captured in previous activity
        filePath = (String)getArguments().get("filePath");
        username = (String)getArguments().get("username");

        act = (int)getArguments().get("act");
        direction = (String)getArguments().get("direction");
        time=(String)getArguments().get("time");

        // boolean flag to identify the media type, image or video
        boolean isImage = getArguments().getBoolean("isImage");

        if (filePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage);
            Toast.makeText(getActivity(),
                    "錄製成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(),
                    "檔案遺失!", Toast.LENGTH_LONG).show();
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // uploading the file to server




                //progressBar.setProgress(50);

                //txtPercentage.setText("50" + "%");
                progressBar.setVisibility(View.VISIBLE);

                new UploadFileToServer().execute();



            }
        });
        return root;
    }


    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {
            /*
            imgPreview.setVisibility(View.VISIBLE);
            vidPreview.setVisibility(View.GONE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            imgPreview.setImageBitmap(bitmap);
            */

        } else {
            //imgPreview.setVisibility(View.GONE);
            vidPreview.setVisibility(View.VISIBLE);
            vidPreview.setVideoPath(filePath);
            // start playing
            vidPreview.start();
        }
    }

    /**
     * Uploading the file to server
     * */

    @SuppressLint("StaticFieldLeak")
    public class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        AlertDialog any;
        @Override
        protected void onPreExecute() {

            // setting progress bar to zero
            progressBar.setProgress(0);

            super.onPreExecute();


        }

        @Override
        protected void onProgressUpdate(Integer... progress) {


            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
            if (progress[0] == 100){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("分析中....")
                        .setCancelable(false);

                 any = builder.create();
               any.show();



            }




        }

        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();

        }


        private String uploadFile() {

            String responseString = null;


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);



            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {

                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(filePath);

                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
                entity.addPart("username",new StringBody(username));
                // Extra parameters if you want to pass to server
                entity.addPart("act",
                        new StringBody(String.valueOf(act)));
                entity.addPart("direction", new StringBody(direction));
                entity.addPart("time",new StringBody(time));

                totalSize = entity.getContentLength();

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            httpclient.getConnectionManager().shutdown();

            return responseString;


        }


        @Override
        protected void onPostExecute(String result) {

            Log.e(TAG, "Response from server: " + result);
            any.dismiss();


            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }

    }



    /**
     * Method to show alert dialog
     * */
    private void showAlert(String message) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message).setTitle("提醒")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeFragment fragment =  new HomeFragment();

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        // do nothing
                    }
                });
       /* builder.setNegativeButton("重新錄製", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent is = new Intent(UploadActivity.this, ActionActivity.class);
                startActivity(is);
            }
        });*/

        AlertDialog alert = builder.create();
        alert.show();
    }
    private void closeDialog(AlertDialog dialog) {
        try {
            java.lang.reflect.Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}

