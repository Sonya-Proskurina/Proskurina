package com.example.proskurina.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface GifDao {

    @Query("SELECT * FROM gif WHERE type = 0")
    Single<List<Gif>> getAllGif();

    @Query("SELECT * FROM gif WHERE type = 1")
    Single<List<Gif>> getAllLatest();

    @Insert
    void insert(Gif gif);
}
