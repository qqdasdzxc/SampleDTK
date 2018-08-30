package com.dmitrijkuzmin.sampledtk.ui.singleuser.mvp;

import com.arellomobile.mvp.InjectViewState;
import com.dmitrijkuzmin.sampledtk.App;
import com.dmitrijkuzmin.sampledtk.business.singleuser.SingleUserInteractor;
import com.dmitrijkuzmin.sampledtk.data.entity.Post;
import com.dmitrijkuzmin.sampledtk.data.entity.User;
import com.dmitrijkuzmin.sampledtk.di.singleuser.SingleUserModule;
import com.dmitrijkuzmin.sampledtk.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class SingleUserPostsPresenter extends BasePresenter<SingleUserPostsView> {

    @Inject
    SingleUserInteractor interactor;
    private boolean postsLoading;

    private User user;

    public SingleUserPostsPresenter(User user) {
        App.getAppComponent().plus(new SingleUserModule()).inject(this);
        this.user = user;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadUserPosts();
    }

    public void loadUserPosts() {
        loadPostsFromApi(user.getId());
    }

    private void loadPostsFromApi(Integer userId) {
        if (postsLoading) {
            return;
        }

        postsLoading = true;

        getViewState().showPostsProgress();

        final Single<List<Post>> singlePostsFromApi = interactor.getUserPostsFromApi(userId);

        Disposable disposable = singlePostsFromApi
                .subscribe(posts -> {
                    if (posts.size() == 0) {
                        loadPostsFromDb(userId);
                    } else {
                        interactor.savePosts(posts);
                        onPostsLoadingFinish();
                        onPostsLoadingSuccess(posts);
                    }
                }, error -> {
                    loadPostsFromDb(userId);
                });

        unsubscribeOnDestroy(disposable);
    }


    private void loadPostsFromDb(Integer userId) {
        final Single<List<Post>> singlePostsFromDb = interactor.getUserPostsFromDb(userId);

        Disposable disposable = singlePostsFromDb
                .doOnEvent((consumer, event) -> onPostsLoadingFinish())
                .subscribe(posts -> {
                    if (posts.size() == 0) {
                        onPostsLoadingError();
                    } else {
                        onPostsLoadingSuccess(posts);
                    }
                }, error -> {
                    onPostsLoadingError();
                });

        unsubscribeOnDestroy(disposable);
    }

    private void onPostsLoadingSuccess(List<Post> posts) {
        getViewState().setPosts(posts);
    }

    private void onPostsLoadingError() {
        getViewState().showPostsLoadingError();
    }

    private void onPostsLoadingFinish() {
        postsLoading = false;
        getViewState().hidePostsProgress();
    }

}
