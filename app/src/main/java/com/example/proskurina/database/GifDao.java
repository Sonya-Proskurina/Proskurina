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

    @Query("SELECT * FROM gif WHERE type = 2")
    Single<List<Gif>> getAllHot();

    @Query("SELECT * FROM gif")
    List<Gif> getAll();

    @Query("SELECT * FROM gif WHERE type = :type")
    List<Gif> getByType(long type);

    @Insert
    void insert(Gif gif);

    @Update
    void update(Gif gif);

    @Delete
    void delete(Gif gif);

    @Query("SELECT * FROM gif WHERE id = :id")
    Single<Gif> getById(long id);

    @Query("SELECT * from gif ORDER BY RANDOM() LIMIT 1")
    LiveData<Gif> getRandomVerse();

    @Query("SELECT * from gif ORDER BY RANDOM() LIMIT 1")
    Single<Gif> getRxRandomVerse();
}
