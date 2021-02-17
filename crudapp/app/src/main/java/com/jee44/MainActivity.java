package com.jee44;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jee44.adapter.UserAdapter;
import com.jee44.model.UserModel;
import com.jee44.retrofit.RetrofitConfig;
import com.jee44.service.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Intent i;
    UserService userService;
    ListView userListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userService = RetrofitConfig.createService(UserService.class);
        userListView = findViewById(R.id.userListView);
       // i = new Intent(getApplicationContext(), LoginActivity.class);
        //startActivity(i);
        Call<List<UserModel>> list = userService.getAll();
        list.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                List<UserModel> list = response.body();
                if(list != null && list.size() > 0){
                    showUserList(list);
                }else{
                    System.out.println("user not found");
                }

            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void showUserList(List<UserModel> list){
        UserAdapter adapter = new UserAdapter(this, list);
        userListView.setAdapter(adapter);
    }


    public void edit(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

}