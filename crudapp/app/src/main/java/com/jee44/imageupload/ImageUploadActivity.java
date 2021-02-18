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
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageUploadService imageUploadService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        imageUploadService = RetrofitConfig.createService(ImageUploadService.class);
        imageView = (ImageView)findViewById(R.id.imageView);
        button = (Button)findViewById(R.id.buttonLoadPicture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            // Verify permissions
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //request for access
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {
                    // Here is the logic of their own to open after permission to operate
                }
            }
        }
        Picasso.get().load("http://192.168.2.130:8080/downloadFile/logo.jpg").into(imageView);

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    public void upload(View view){

        File file = new File(getPath(imageUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageFile = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Call<ImageUploadResponse> res = imageUploadService.imageUpload(imageFile);
        res.enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                ImageUploadResponse body = response.body();
                System.out.println(body.toString());
                productSave(body.getFileName());
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    private boolean isPermissionGranted(String permission) {
        // проверяем разрешение - есть ли оно у нашего приложения
        int permissionCheck = ActivityCompat.checkSelfPermission(this, permission);
        // true - если есть, false - если нет
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    public  String productSave(String imagename){

        return null;
    }

}