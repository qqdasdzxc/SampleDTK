package com.dmitrijkuzmin.sampledtk.business.singleuser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dmitrijkuzmin.sampledtk.data.db.PhotoDao;
import com.dmitrijkuzmin.sampledtk.data.db.PostDao;
import com.dmitrijkuzmin.sampledtk.data.entity.Photo;
import com.dmitrijkuzmin.sampledtk.data.entity.Post;
import com.dmitrijkuzmin.sampledtk.data.net.UsersApi;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SingleUserInteractorImpl implements SingleUserInteractor {

    private UsersApi api;
    private PostDao postDao;
    private PhotoDao photoDao;
    private Context context;

    public SingleUserInteractorImpl(UsersApi api, PostDao postDao, PhotoDao photoDao, Context context) {
        this.api = api;
        this.postDao = postDao;
        this.photoDao = photoDao;
        this.context = context;
    }

    @Override
    public Single<List<Post>> getUserPostsFromApi(int userId) {
        return api.getUserPosts(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<Post>> getUserPostsFromDb(int userId) {
        return postDao.loadAllPostByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void savePosts(List<Post> posts) {
        Completable.fromAction(() -> postDao.insertAll(posts))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public Single<List<Photo>> getUserPhotoFromApi(int userId) {
        return api.getUserPhoto(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<Photo>> getUserPhotoFromDb(int userId) {
        return photoDao.loadPhotoByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void savePhoto(Photo photo) {
        Completable.fromAction(() -> photoDao.insert(photo))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public Single<Drawable> downloadPhoto(String url){
        return Single.fromCallable(() -> Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(200, 200)
                .get());
    }
}
