package com.dmitrijkuzmin.sampledtk.business.users;

import com.dmitrijkuzmin.sampledtk.data.entity.User;

import java.util.List;

import io.reactivex.Single;

public interface UsersInteractor {
    Single<List<User>> getUsersFromApi();
    Single<List<User>> getUsersFromDb();
    void saveUsers(List<User> users);
}
