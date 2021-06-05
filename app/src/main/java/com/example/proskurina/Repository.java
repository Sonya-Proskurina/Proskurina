package com.example.proskurina;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.proskurina.database.Gif;
import com.example.proskurina.network.BigData;
import com.example.proskurina.network.NetworkService;
import com.example.proskurina.network.ResponseData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private List<Gif> list, list2;
    private int ind, ind2, num, num2;

    public Repository() {
        list = new ArrayList<>();
        list.add(new Gif("Но все идет совсем не по плану и тебе очень неловко, что эксперты будут смотреть вот такое тестовое \uD83D\uDC49\uD83C\uDFFB\uD83D\uDC48\uD83C\uDFFB", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 0));
        list.add(new Gif("Костыль1!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 0));
        list.add(new Gif("Костыль2!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 0));

        ind = 3;
        num = 0;

        list2 = new ArrayList<>();
        list2.add(new Gif("Но все идет совсем не по плану и тебе очень неловко, что эксперты будут смотреть вот такое тестовое \uD83D\uDC49\uD83C\uDFFB\uD83D\uDC48\uD83C\uDFFB", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 1));
        list2.add(new Gif("Костыль!1", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 1));
        list2.add(new Gif("Костыль!2", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 1));

        ind2 = 3;
        num2 = 0;
    }

    public void newList() {
        NetworkService.getInstance()
                .getJSONApi()
                .getPostTop(num)
                .enqueue(new Callback<BigData>() {
                    @Override
                    public void onResponse(@NonNull Call<BigData> call, @NonNull Response<BigData> response) {
                        BigData post = response.body();
                        for (int i = 0; i < 5; i++) {
                            ResponseData post2 = post.getResult().get(i);

//                            Observable.just(new Gif(post2.getDescription(), post2.getGifURL(), 0))
//                                    .subscribeOn(Schedulers.io())
//                                    .subscribe(App.getInstance().getDatabase().gifDao()::insert)
//                                    .dispose();
                            new Thread() {
                                @Override
                                public void run() {
                                    App.getInstance().getDatabase().gifDao().insert(new Gif(post2.getDescription(), post2.getGifURL(), 0));
                                }
                            }.start();
                        }

                        App.getInstance().getDatabase().gifDao().getAllGif()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                                    @Override
                                    public void onSuccess(List<Gif> gif) {
                                        list = gif;
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        list = new ArrayList<>();
                                        list.add(new Gif("bad", "bad", -1));
                                    }
                                });

                        num = num + 1;
                    }

                    @Override
                    public void onFailure(@NonNull Call<BigData> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        list2 = new ArrayList<>();
                        list2.add(new Gif("bad", "bad", -1));
                    }
                });
    }

    public void newList2() {
        NetworkService.getInstance()
                .getJSONApi()
                .getPostLatest(num2)
                .enqueue(new Callback<BigData>() {
                    @Override
                    public void onResponse(@NonNull Call<BigData> call, @NonNull Response<BigData> response) {
                        BigData post = response.body();
                        for (int i = 0; i < 5; i++) {
                            ResponseData post2 = post.getResult().get(i);
                            new Thread() {
                                @Override
                                public void run() {
                                    App.getInstance().getDatabase().gifDao().insert(new Gif(post2.getDescription(), post2.getGifURL(), 1));
                                }
                            }.start();
                        }

                        App.getInstance().getDatabase().gifDao().getAllLatest()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                                    @Override
                                    public void onSuccess(List<Gif> gif) {
                                        list2 = gif;
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        list2 = new ArrayList<>();
                                        list2.add(new Gif("bad", "bad", -1));
                                    }
                                });

                        num2 = num2 + 1;
                    }
                    @Override
                    public void onFailure(@NonNull Call<BigData> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        list2 = new ArrayList<>();
                        list2.add(new Gif("bad", "bad", -1));
                    }
                });
    }

    public Gif newGif() {
        Gif temp = new Gif("Костыль!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 0);

        App.getInstance().getDatabase().gifDao().getAllGif()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                    @Override
                    public void onSuccess(List<Gif> gif) {
                        list = gif;
                    }
                    @Override
                    public void onError(Throwable e) {
                        list = new ArrayList<>();
                        list.add(new Gif("bad", "bad", -1));
                        Log.e("onError", e.getMessage());
                    }
                });

        if (ind + 2 >= list.size() || list.size() == 0) newList();
        if (list.size() > ind) {
            if (list2.get(0).type != -1)
                temp = list.get(ind);
        }

        if (!list2.isEmpty()&&list2.get(0).type==-1){
            temp=list2.get(0);
        }

        ind++;

        return temp;
    }

    public Gif newGif2() {
        Gif temp = new Gif("Костыль!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 1);

        App.getInstance().getDatabase().gifDao().getAllLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                    @Override
                    public void onSuccess(List<Gif> gif) {
                        list2 = gif;
                    }

                    @Override
                    public void onError(Throwable e) {
                        list2 = new ArrayList<>();
                        list2.add(new Gif("bad", "bad", -1));
                        Log.e("onError", e.getMessage());
                    }
                });

        if (ind2 + 2 >= list2.size() || list2.size() == 0) newList2();
        if (list2.size() > ind2) {
            if (list2.get(0).type != -1)
                temp = list2.get(ind2);
        }

        if (!list2.isEmpty()&&list2.get(0).type==-1){
            temp=list2.get(0);
        }

        ind2++;

        return temp;
    }

    public Gif oldGif() {
        ind--;
        return list.get(ind);
    }

    public Gif oldGif2() {
        ind2--;
        return list2.get(ind2);
    }

    public int getInd() {
        return ind;
    }

    public int getInd2() {
        return ind2;
    }

    public int getNum() {
        return num;
    }

    public int getNum2() {
        return num2;
    }

    public int listSize() {
        return list.size();
    }

    public int listSize2() {
        return list2.size();
    }
}
