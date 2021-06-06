package com.example.proskurina;

import android.app.Application;

import androidx.room.Room;

import com.example.proskurina.database.AppDatabase;


public class App extends Application {

    public static App instance;
    private AppDatabase database;
    private Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database").build();

        repository = new Repository();
        repository.firstStart(getBaseContext());
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}