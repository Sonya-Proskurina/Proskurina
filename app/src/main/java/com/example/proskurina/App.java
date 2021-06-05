package com.example.proskurina;

import android.app.Application;

import androidx.room.Room;

import com.example.proskurina.database.AppDatabase;
import com.example.proskurina.database.Gif;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class App extends Application {

    public static App instance;
    public static Repository repository;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .build();
        repository = new Repository();

//        App.getInstance().getDatabase().gifDao().getAll2()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableSingleObserver<List<Gif>>() {
//                    @Override
//                    public void onSuccess(List<Gif> gif) {
//                       repository.newList();
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        // ...
//                    }
//                });
//
//        database.gifDao().insert(new Gif("d","dd",0));
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}