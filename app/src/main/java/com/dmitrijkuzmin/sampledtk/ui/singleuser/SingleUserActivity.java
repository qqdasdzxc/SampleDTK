package com.dmitrijkuzmin.sampledtk.ui.singleuser;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.dmitrijkuzmin.sampledtk.R;
import com.dmitrijkuzmin.sampledtk.data.entity.Post;
import com.dmitrijkuzmin.sampledtk.data.entity.User;
import com.dmitrijkuzmin.sampledtk.databinding.ActivitySingleUserBinding;
import com.dmitrijkuzmin.sampledtk.ui.singleuser.mvp.SingleUserPhotoPresenter;
import com.dmitrijkuzmin.sampledtk.ui.singleuser.mvp.SingleUserPhotoView;
import com.dmitrijkuzmin.sampledtk.ui.singleuser.mvp.SingleUserPostsPresenter;
import com.dmitrijkuzmin.sampledtk.ui.singleuser.mvp.SingleUserPostsView;

import java.util.List;

public class SingleUserActivity extends MvpAppCompatActivity
        implements SingleUserPhotoView, SingleUserPostsView {

    @InjectPresenter
    SingleUserPhotoPresenter photoPresenter;
    @InjectPresenter
    SingleUserPostsPresenter postsPresenter;

    @ProvidePresenter
    SingleUserPhotoPresenter providePhotoPresenter() {
        return new SingleUserPhotoPresenter(getIntent().getParcelableExtra("user"));
    }

    @ProvidePresenter
    SingleUserPostsPresenter providePostsPresenter() {
        return new SingleUserPostsPresenter(getIntent().getParcelableExtra("user"));
    }

    ActivitySingleUserBinding binding;
    PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = getIntent().getParcelableExtra("user");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_user);
        binding.setUser(user);

        binding.photoErrMsgTv.setOnClickListener(view -> photoPresenter.loadUserPhoto());

        initRecyclerView();
        binding.swipeRefresh.setOnRefreshListener(() -> postsPresenter.loadUserPosts());
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostsAdapter();
        binding.recycler.setAdapter(adapter);
    }

    @Override
    public void showPostsProgress() {
        binding.swipeRefresh.post(() -> binding.swipeRefresh.setRefreshing(true));
    }

    @Override
    public void showPhotoProgress() {
        binding.photoProgress.setVisibility(View.VISIBLE);
        binding.photoErrMsgTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hidePostsProgress() {
        binding.swipeRefresh.post(() -> binding.swipeRefresh.setRefreshing(false));
    }

    @Override
    public void hidePhotoProgress() {
        binding.photoProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPosts(List<Post> posts) {
        adapter.setItems(posts);
    }

    @Override
    public void setPhoto(Drawable photo) {
        binding.photo.setImageDrawable(photo);
    }

    @Override
    public void showPostsLoadingError() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPhotoLoadingError() {
        binding.photoErrMsgTv.setVisibility(View.VISIBLE);
    }
}
