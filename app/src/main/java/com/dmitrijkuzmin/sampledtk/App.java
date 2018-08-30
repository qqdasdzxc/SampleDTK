package com.dmitrijkuzmin.sampledtk;

import android.app.Application;

import com.dmitrijkuzmin.sampledtk.di.app.AppComponent;
import com.dmitrijkuzmin.sampledtk.di.app.AppModule;
import com.dmitrijkuzmin.sampledtk.di.app.DaggerAppComponent;

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
