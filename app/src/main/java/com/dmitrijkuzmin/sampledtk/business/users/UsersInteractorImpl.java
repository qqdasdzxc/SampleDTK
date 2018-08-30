package com.dmitrijkuzmin.sampledtk.business.users;

import com.dmitrijkuzmin.sampledtk.data.db.UserDao;
import com.dmitrijkuzmin.sampledtk.data.entity.User;
import com.dmitrijkuzmin.sampledtk.data.net.UsersApi;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UsersInteractorImpl implements UsersInteractor {

    private UsersApi api;
    private UserDao dao;

    public UsersInteractorImpl(UsersApi api, UserDao dao) {
        this.api = api;
        this.dao = dao;
    }

    @Override
    public Single<List<User>> getUsersFromApi() {
        return api.getAllUsers()
                //для теста смены ориентации экрана, потом убрать
                .delay(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<User>> getUsersFromDb() {
        return dao.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveUsers(List<User> users) {
        Completable.fromAction(() -> dao.insertAll(users))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
