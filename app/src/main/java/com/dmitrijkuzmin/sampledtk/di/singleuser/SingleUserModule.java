package com.dmitrijkuzmin.sampledtk.di.singleuser;

import android.content.Context;

import com.dmitrijkuzmin.sampledtk.business.singleuser.SingleUserInteractor;
import com.dmitrijkuzmin.sampledtk.business.singleuser.SingleUserInteractorImpl;
import com.dmitrijkuzmin.sampledtk.data.db.AppDatabase;
import com.dmitrijkuzmin.sampledtk.data.net.UsersApi;

import dagger.Module;
import dagger.Provides;

@Module
public class SingleUserModule {

    @SingleUserScope
    @Provides
    public SingleUserInteractor provideSingleUserInteractor(UsersApi api, AppDatabase appDatabase, Context context) {
        return new SingleUserInteractorImpl(api, appDatabase.postDao(), appDatabase.photoDao(), context);
    }

}
