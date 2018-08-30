package com.dmitrijkuzmin.sampledtk.ui.singleuser.mvp;

import android.graphics.drawable.Drawable;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface SingleUserPhotoView extends MvpView {
    void showPhotoProgress();
    @StateStrategyType(SkipStrategy.class)
    void hidePhotoProgress();
    @StateStrategyType(SingleStateStrategy.class)
    void setPhoto(Drawable photo);
    void showPhotoLoadingError();
}
