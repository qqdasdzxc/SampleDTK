package com.dmitrijkuzmin.sampledtk.ui.singleuser.mvp;

import android.graphics.drawable.Drawable;

import com.arellomobile.mvp.InjectViewState;
import com.dmitrijkuzmin.sampledtk.App;
import com.dmitrijkuzmin.sampledtk.business.singleuser.SingleUserInteractor;
import com.dmitrijkuzmin.sampledtk.data.entity.Photo;
import com.dmitrijkuzmin.sampledtk.data.entity.User;
import com.dmitrijkuzmin.sampledtk.di.singleuser.SingleUserModule;
import com.dmitrijkuzmin.sampledtk.ui.base.BasePresenter;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SingleUserPhotoPresenter extends BasePresenter<SingleUserPhotoView> {

    @Inject
    SingleUserInteractor interactor;
    private boolean photoLoading;

    private User user;

    public SingleUserPhotoPresenter(User user) {
        App.getAppComponent().plus(new SingleUserModule()).inject(this);
        this.user = user;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadUserPhoto();
    }

    public void loadUserPhoto() {
        loadPhotoFromApi(user.getId());
    }

    private void loadPhotoFromApi(Integer userId) {
        if (photoLoading) {
            return;
        }

        photoLoading = true;

        getViewState().showPhotoProgress();

        final Single<List<Photo>> singlePhotoFromApi = interactor.getUserPhotoFromApi(userId);

        Disposable disposable = singlePhotoFromApi
                .subscribe(photos -> {
                    if (photos.size() == 0) {
                        loadPhotoFromDb(userId);
                    } else {
                        Photo photo = photos.get(0);
                        interactor.savePhoto(photo);
                        onPhotoLoadingSuccess(photo);
                    }
                }, error -> {
                    loadPhotoFromDb(userId);
                });

        unsubscribeOnDestroy(disposable);
    }

    private void loadPhotoFromDb(Integer userId) {
        final Single<List<Photo>> singlePhotoFromDb = interactor.getUserPhotoFromDb(userId);

        Disposable disposable = singlePhotoFromDb
                .subscribe(photos -> {
                    if (photos.size() == 0) {
                        onPhotoLoadingError();
                        onPhotoLoadingFinish();
                    } else {
                        onPhotoLoadingSuccess(photos.get(0));
                    }
                }, error -> {
                    onPhotoLoadingError();
                    onPhotoLoadingFinish();
                });

        unsubscribeOnDestroy(disposable);
    }

    private void onPhotoLoadingSuccess(Photo photo) {
        try {
            final Single<Drawable> photoAsDrawableSingle = interactor.downloadPhoto(photo.getUrl());
            Disposable disposable = photoAsDrawableSingle
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(photoAsDrawable -> {
                        getViewState().setPhoto(photoAsDrawable);
                        onPhotoLoadingFinish();
                    }, error -> {
                        onPhotoLoadingError();
                        onPhotoLoadingFinish();
                    });

            unsubscribeOnDestroy(disposable);
        } catch (InterruptedException | ExecutionException e) {
            onPhotoLoadingError();
            onPhotoLoadingFinish();
        }
    }

    private void onPhotoLoadingError() {
        getViewState().showPhotoLoadingError();
    }

    private void onPhotoLoadingFinish() {
        photoLoading = false;
        getViewState().hidePhotoProgress();
    }


}
