package com.example.proskurina;

import android.app.Application;

import androidx.room.Room;

import com.example.proskurina.database.AppDatabase;


public class App extends Application {

    public static App instance;
    public static Repository repository;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database").build();
        repository = new Repository();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}