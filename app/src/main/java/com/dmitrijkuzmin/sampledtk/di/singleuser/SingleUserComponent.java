package com.dmitrijkuzmin.sampledtk.di.singleuser;

import com.dmitrijkuzmin.sampledtk.ui.singleuser.mvp.SingleUserPhotoPresenter;
import com.dmitrijkuzmin.sampledtk.ui.singleuser.mvp.SingleUserPostsPresenter;

import dagger.Subcomponent;

@SingleUserScope
@Subcomponent(modules = SingleUserModule.class)
public interface SingleUserComponent {
    void inject(SingleUserPhotoPresenter presenter);
    void inject(SingleUserPostsPresenter presenter);
}
