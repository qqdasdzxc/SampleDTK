package com.dmitrijkuzmin.sampledtk.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.dmitrijkuzmin.sampledtk.data.entity.Photo;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PhotoDao {

    @Query("SELECT * FROM photo WHERE id LIKE :userId LIMIT 1")
    Single<List<Photo>> loadPhotoByUserId(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Photo photo);
}
