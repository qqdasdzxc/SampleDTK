package com.dmitrijkuzmin.sampledtk.di.app;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.dmitrijkuzmin.sampledtk.data.db.AppDatabase;
import com.dmitrijkuzmin.sampledtk.data.db.UserDao;
import com.dmitrijkuzmin.sampledtk.data.net.UsersApi;
import com.dmitrijkuzmin.sampledtk.utils.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public UsersApi provideUsersApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(UsersApi.class);
    }

    @Singleton
    @Provides
    public AppDatabase provideAppDatabase() {
        return Room.databaseBuilder(context, AppDatabase.class, "db-sampleDTK").build();
    }
}
