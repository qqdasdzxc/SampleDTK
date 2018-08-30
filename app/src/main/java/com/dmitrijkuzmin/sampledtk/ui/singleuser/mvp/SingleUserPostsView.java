package com.dmitrijkuzmin.sampledtk.ui.singleuser.mvp;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.dmitrijkuzmin.sampledtk.data.entity.Post;

import java.util.List;

public interface SingleUserPostsView extends MvpView {
    void showPostsProgress();
    @StateStrategyType(SkipStrategy.class)
    void hidePostsProgress();
    @StateStrategyType(SingleStateStrategy.class)
    void setPosts(List<Post> posts);
    @StateStrategyType(SkipStrategy.class)
    void showPostsLoadingError();
}
