package com.dmitrijkuzmin.sampledtk.ui.users;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dmitrijkuzmin.sampledtk.R;
import com.dmitrijkuzmin.sampledtk.data.entity.User;
import com.dmitrijkuzmin.sampledtk.ui.singleuser.SingleUserActivity;

import java.util.List;

public class UsersActivity extends MvpAppCompatActivity
        implements UsersView, UsersAdapter.UsersAdapterListener {

    @InjectPresenter
    UsersPresenter presenter;

    SwipeRefreshLayout refreshLayout;
    RecyclerView usersRecyclerView;
    UsersAdapter usersAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecyclerView();

        refreshLayout = findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(() -> presenter.loadUsersFromApi());
    }

    private void initRecyclerView() {
        usersRecyclerView = findViewById(R.id.recycler);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(this);
        usersRecyclerView.setAdapter(usersAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //presenter.onQueryTextChanged(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                presenter.onQueryTextChanged(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void showProgress() {
        refreshLayout.post(() -> refreshLayout.setRefreshing(true));
    }

    @Override
    public void hideProgress() {
        refreshLayout.post(() -> refreshLayout.setRefreshing(false));
    }

    @Override
    public void setUsers(List<User> users) {
        usersAdapter.setItems(users);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartLoading() {
        refreshLayout.setEnabled(false);
    }

    @Override
    public void onFinishLoading() {
        refreshLayout.setEnabled(true);
    }

    @Override
    public void filterUsers(String query) {
        usersAdapter.getFilter().filter(query);
    }

    @Override
    public void onUserSelected(User user) {
        Intent intent = new Intent(this, SingleUserActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
