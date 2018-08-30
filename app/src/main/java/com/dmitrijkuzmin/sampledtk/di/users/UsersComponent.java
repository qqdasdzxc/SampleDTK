package com.dmitrijkuzmin.sampledtk.di.users;

import com.dmitrijkuzmin.sampledtk.ui.users.UsersPresenter;

import dagger.Subcomponent;

@UsersScope
@Subcomponent(modules = UsersModule.class)
public interface UsersComponent {
    void inject(UsersPresenter presenter);
}
