package com.example.proskurina;

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
    private List<Gif> list,list2,list3;
    private int ind, ind2,ind3, num,num2,num3;

    public Repository() {
        list = new ArrayList<>();
        list.add(new Gif("Костыль!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 0));
        list.add(new Gif("Но все идет совсем не по плану и тебе очень неловко, что эксперты будут смотреть вот такое тестовое \uD83D\uDC49\uD83C\uDFFB\uD83D\uDC48\uD83C\uDFFB", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 0));
        list.add(new Gif("Костыль!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 0));
        ind = 1;
        num = 0;

        list2 = new ArrayList<>();
        list2.add(new Gif("Костыль!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 1));
        list2.add(new Gif("Но все идет совсем не по плану и тебе очень неловко, что эксперты будут смотреть вот такое тестовое \uD83D\uDC49\uD83C\uDFFB\uD83D\uDC48\uD83C\uDFFB", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 1));
        list2.add(new Gif("Костыль!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 1));
        ind2 = 1;
        num2 = 0;

        list3 = new ArrayList<>();
        list3.add(new Gif("Костыль!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 2));
        list3.add(new Gif("Но все идет совсем не по плану и тебе очень неловко, что эксперты будут смотреть вот такое тестовое \uD83D\uDC49\uD83C\uDFFB\uD83D\uDC48\uD83C\uDFFB", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 2));
        list3.add(new Gif("Костыль!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 2));
        ind3 = 1;
        num3 = 0;
    }

    public int listSize() {
        return list.size();
    }

    public int listSize2() {
        return list2.size();
    }

    public int listSize3() {
        return list3.size();
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
                                        // ...
                                    }
                                });
                        num = num + 1;
                    }

                    @Override
                    public void onFailure(@NonNull Call<BigData> call, @NonNull Throwable t) {
                        t.printStackTrace();
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

//                            Observable.just(new Gif(post2.getDescription(), post2.getGifURL(), 0))
//                                    .subscribeOn(Schedulers.io())
//                                    .subscribe(App.getInstance().getDatabase().gifDao()::insert)
//                                    .dispose();
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
                                        // ...
                                    }
                                });

                        num2 = num2 + 1;
                    }

                    @Override
                    public void onFailure(@NonNull Call<BigData> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void newList3() {
        NetworkService.getInstance()
                .getJSONApi()
                .getPostHot(num3)
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
                                    App.getInstance().getDatabase().gifDao().insert(new Gif(post2.getDescription(), post2.getGifURL(), 2));
                                }
                            }.start();
                        }

                        App.getInstance().getDatabase().gifDao().getAllLatest()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                                    @Override
                                    public void onSuccess(List<Gif> gif) {
                                        list3 = gif;
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        // ...
                                    }
                                });

                        num3 = num3 + 1;
                    }

                    @Override
                    public void onFailure(@NonNull Call<BigData> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public Gif newGif() {
        Gif temp = new Gif("Добро пожаловать!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 0);

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
                        // ...
                    }
                });

        if (ind + 2 >= list.size() || list.size() == 0) newList();
        if (list.size() > ind) {
            temp = list.get(ind);
        }
        ind++;
        return temp;
    }

    public Gif newGif2() {
        Gif temp = new Gif("Добро пожаловать!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 1);

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
                        // ...
                    }
                });

        if (ind2 + 2 >= list2.size() || list2.size() == 0) newList2();
        if (list2.size() > ind2) {
            temp = list2.get(ind2);
        }
        ind2++;
        return temp;
    }

    public Gif newGif3() {
        Gif temp = new Gif("Добро пожаловать!", "http://static.devli.ru/public/images/gifs/201303/02926671-82c7-4cf5-a011-4440422458c9.gif", 2);

        App.getInstance().getDatabase().gifDao().getAllLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                    @Override
                    public void onSuccess(List<Gif> gif) {
                        list3 = gif;
                    }

                    @Override
                    public void onError(Throwable e) {
                        // ...
                    }
                });

        if (ind3 + 2 >= list3.size() || list3.size() == 0) newList3();
        if (list3.size() > ind3) {
            temp = list3.get(ind3);
        }
        ind3++;
        return temp;
    }

    public Gif oldGif(){
        ind--;
        Gif temp = list.get(ind);
        return temp;
    }

    public Gif oldGif2(){
        ind2--;
        Gif temp = list2.get(ind2);
        return temp;
    }

    public Gif oldGif3(){
        ind3--;
        Gif temp = list3.get(ind3);
        return temp;
    }

    public int getInd() {
        return ind;
    }

    public int getInd2() {
        return ind2;
    }

    public int getInd3() {
        return ind3;
    }

    public int getNum() {
        return num;
    }

    public int getNum2() {
        return num2;
    }

    public int getNum3() {
        return num3;
    }
}
