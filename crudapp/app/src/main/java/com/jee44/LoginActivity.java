package com.jee44;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jee44.model.UserModel;
import com.jee44.retrofit.RetrofitConfig;
import com.jee44.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Intent i;
    UserService userService;
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService = RetrofitConfig.createService(UserService.class);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void navigateSignup(View view){
        i = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(i);
    }

    public void login(View view){
        String uName = username.getText().toString();
        String uPass = password.getText().toString();
        UserModel userModel  = new UserModel();
        userModel.setUsername(uName);
        userModel.setPassword(uPass);
        Call<UserModel> user = userService.login(userModel);
        user.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel userModel1 = response.body();
                if(userModel1 != null){
                    if(userModel1.getUsername() != null){
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "Please check usrname/password", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}