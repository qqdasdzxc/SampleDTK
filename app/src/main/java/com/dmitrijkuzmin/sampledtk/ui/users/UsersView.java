package com.dmitrijkuzmin.sampledtk.ui.users;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.dmitrijkuzmin.sampledtk.data.entity.User;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface UsersView extends MvpView {

    void onStartLoading();

    void onFinishLoading();

    void showProgress();

    @StateStrategyType(SkipStrategy.class)
    void hideProgress();

    @StateStrategyType(SingleStateStrategy.class)
    void setUsers(List<User> users);

    @StateStrategyType(SkipStrategy.class)
    void showError();

    void filterUsers(String query);

}
