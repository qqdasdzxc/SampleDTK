package com.dmitrijkuzmin.sampledtk.ui.users;

import android.support.annotation.VisibleForTesting;

import com.arellomobile.mvp.InjectViewState;
import com.dmitrijkuzmin.sampledtk.App;
import com.dmitrijkuzmin.sampledtk.business.users.UsersInteractor;
import com.dmitrijkuzmin.sampledtk.data.entity.User;
import com.dmitrijkuzmin.sampledtk.di.users.UsersModule;
import com.dmitrijkuzmin.sampledtk.ui.base.BasePresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class UsersPresenter extends BasePresenter<UsersView> {

    @Inject
    UsersInteractor interactor;
    private boolean isInLoading;

    public UsersPresenter() {
        App.getAppComponent().plus(new UsersModule()).inject(this);
    }

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadUsersFromApi();
    }

    public void loadUsersFromApi() {
        if (isInLoading) {
            return;
        }

        isInLoading = true;
        getViewState().onStartLoading();
        getViewState().showProgress();

        final Single<List<User>> singleUsersFromApi = interactor.getUsersFromApi();

        Disposable disposable = singleUsersFromApi
                .subscribe(users -> {
                    if (users.size() == 0) {
                        loadUsersFromDb();
                    } else {
                        interactor.saveUsers(users);
                        onLoadingFinish();
                        onLoadingSuccess(users);
                    }
                }, error -> {
                    loadUsersFromDb();
                });

        unsubscribeOnDestroy(disposable);
    }

    private void loadUsersFromDb() {
        final Single<List<User>> singleUsersFromDb = interactor.getUsersFromDb();

        Disposable disposable = singleUsersFromDb
                .doOnEvent((consumer, event) -> onLoadingFinish())
                .subscribe(users -> {
                    if (users.size() == 0) {
                        onLoadingError();
                    } else {
                        onLoadingSuccess(users);
                    }
                }, error -> {
                    onLoadingError();
                });

        unsubscribeOnDestroy(disposable);
    }

    private void onLoadingFinish() {
        isInLoading = false;
        getViewState().onFinishLoading();
        getViewState().hideProgress();
    }

    private void onLoadingSuccess(List<User> users) {
        getViewState().setUsers(users);
    }

    private void onLoadingError() {
        getViewState().showError();
    }

    public void onQueryTextChanged(String query) {
        getViewState().filterUsers(query);
    }

    @VisibleForTesting
    public void attachInteractor(UsersInteractor interactor) {
        this.interactor = interactor;
    }

}
