package com.jee44.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jee44.EditUserActivity;
import com.jee44.LoginActivity;
import com.jee44.R;
import com.jee44.model.UserModel;
import com.jee44.retrofit.RetrofitConfig;
import com.jee44.service.UserService;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends ArrayAdapter<UserModel> {
    Activity context;
    List<UserModel> list;
    UserService userService;
    public UserAdapter(Activity context, List<UserModel> list) {
        super(context, R.layout.my_user_list, list);
        userService = RetrofitConfig.createService(UserService.class);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        UserModel user = list.get(position);

        View rowView = inflater.inflate(R.layout.my_user_list, null, true);
        TextView name = rowView.findViewById(R.id.name);
        TextView username = rowView.findViewById(R.id.username);
        name.setText(user.getName());
        username.setText(user.getUsername());

        Button btnEdit = rowView.findViewById(R.id.btnEdit);
        Button btnDelete = rowView.findViewById(R.id.btnDelete);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel user =  list.get(position);
                Intent i = new Intent(context, EditUserActivity.class);
                i.putExtra("id", user.getId());
                context.startActivity(i);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel user =  list.get(position);
                Call<UserModel> rs = userService.deleteById(user.getId());
                rs.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        list.remove(position);
                        notifyDataSetChanged();
                        setNotifyOnChange(true);
                        Toast.makeText(context, "Delete Successful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });


            }
        });
        return rowView;
    }
}
