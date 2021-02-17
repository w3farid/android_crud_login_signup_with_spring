package com.jee44.service;

import com.jee44.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("/user/add")
    public Call<UserModel> add(@Body UserModel userModel);

    @GET("/user/list")
    public Call<List<UserModel>> getAll();

    @GET("/user/one/{id}")
    public Call<UserModel> getById(@Path("id") long id);

    @GET("/user/delete/{id}")
    public Call<UserModel> deleteById(@Path("id") long id);

    @POST("/user/login")
    public Call<UserModel> login(@Body UserModel userModel);
}
