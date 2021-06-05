package com.example.proskurina.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Gif.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GifDao gifDao();

}