package com.dmitrijkuzmin.sampledtk.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.dmitrijkuzmin.sampledtk.data.entity.Photo;
import com.dmitrijkuzmin.sampledtk.data.entity.Post;
import com.dmitrijkuzmin.sampledtk.data.entity.User;

@Database(entities = {User.class, Post.class, Photo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract PostDao postDao();
    public abstract PhotoDao photoDao();
}
