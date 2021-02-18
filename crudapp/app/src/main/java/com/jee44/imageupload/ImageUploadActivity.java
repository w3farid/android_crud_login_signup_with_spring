package com.jee44.imageupload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jee44.R;
import com.jee44.retrofit.RetrofitConfig;
import com.jee44.service.ImageUploadService;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploadActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    Uri imageUri;
    ImageManager imageManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        imageManager = new ImageManager(this);
        imageView = (ImageView)findViewById(R.id.imageView);
        button = (Button)findViewById(R.id.buttonLoadPicture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = imageManager.pickImage();
            }
        });

        imageManager.showImage("http://192.168.2.130:8080/downloadFile/logo.jpg", imageView);
    }




    public void upload(View view){
        if(imageUri != null){
            imageManager.uploadImage(imageUri);
        }
        // write here for select image code

    }




}