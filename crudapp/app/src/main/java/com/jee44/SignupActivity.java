package com.jee44;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jee44.model.UserModel;
import com.jee44.retrofit.RetrofitConfig;
import com.jee44.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private Intent i;
    EditText eName;
    EditText eUsername;
    EditText ePassword;
    EditText eAddress;
    Context mtx;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mtx = this;
        userService = RetrofitConfig.createService(UserService.class);
        eName = findViewById(R.id.name);
        eUsername = findViewById(R.id.username);
        ePassword = findViewById(R.id.password);
        eAddress = findViewById(R.id.address);
    }

    public void navigateLogin(View view){
        i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    public void add(View view){
        String name = eName.getText().toString();
        String username = eUsername.getText().toString();
        String password = ePassword.getText().toString();
        String address = eAddress.getText().toString();
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setUsername(username);
        userModel.setPassword(password);
        userModel.setAddress(address);
        Call<UserModel> res = userService.add(userModel);
        res.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel user =  response.body();
                startActivity(new Intent(mtx, LoginActivity.class));
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}