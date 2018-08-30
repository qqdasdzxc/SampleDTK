package com.dmitrijkuzmin.sampledtk.di.users;


import com.dmitrijkuzmin.sampledtk.business.users.UsersInteractor;
import com.dmitrijkuzmin.sampledtk.business.users.UsersInteractorImpl;
import com.dmitrijkuzmin.sampledtk.data.db.AppDatabase;
import com.dmitrijkuzmin.sampledtk.data.net.UsersApi;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersModule {

    @UsersScope
    @Provides
    public UsersInteractor provideUsersInteractor(UsersApi api, AppDatabase appDatabase) {
        return new UsersInteractorImpl(api, appDatabase.userDao());
    }
}