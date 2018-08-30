package com.dmitrijkuzmin.sampledtk.data.net;

import com.dmitrijkuzmin.sampledtk.data.entity.Photo;
import com.dmitrijkuzmin.sampledtk.data.entity.Post;
import com.dmitrijkuzmin.sampledtk.data.entity.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsersApi {

    @GET("users")
    Single<List<User>> getAllUsers();

    @GET("posts")
    Single<List<Post>> getUserPosts(@Query("userId") int userId);

    @GET("photos")
    Single<List<Photo>> getUserPhoto(@Query("id") int id);
}
