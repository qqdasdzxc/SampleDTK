package com.dmitrijkuzmin.sampledtk.business.singleuser;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.dmitrijkuzmin.sampledtk.data.entity.Photo;
import com.dmitrijkuzmin.sampledtk.data.entity.Post;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Single;

public interface SingleUserInteractor {
    Single<List<Post>> getUserPostsFromApi(int userId);
    Single<List<Photo>> getUserPhotoFromApi(int userId);

    Single<List<Post>> getUserPostsFromDb(int userId);
    Single<List<Photo>> getUserPhotoFromDb(int userId);
    Single<Drawable> downloadPhoto(String url) throws InterruptedException, ExecutionException;

    void savePosts(List<Post> posts);
    void savePhoto(Photo photo);
}
