package com.dmitrijkuzmin.sampledtk.di.app;

import com.dmitrijkuzmin.sampledtk.di.singleuser.SingleUserComponent;
import com.dmitrijkuzmin.sampledtk.di.singleuser.SingleUserModule;
import com.dmitrijkuzmin.sampledtk.di.users.UsersComponent;
import com.dmitrijkuzmin.sampledtk.di.users.UsersModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    UsersComponent plus(UsersModule module);
    SingleUserComponent plus(SingleUserModule module);
}
