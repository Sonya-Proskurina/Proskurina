package com.example.proskurina;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

import com.example.proskurina.database.Gif;
import com.example.proskurina.network.BigData;
import com.example.proskurina.network.NetworkService;
import com.example.proskurina.network.ResponseData;
import com.example.proskurina.ui.fragment.BestFragment;
import com.example.proskurina.ui.fragment.LastFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO: разобраться с rx java и MVVM
//TODO: убрать дублирование кода
public class Repository {
    public static boolean first = true;
    public static boolean first2 = true;
    public static List<Gif> list, list2;
    public static int ind = 0, ind2 = 0, num = 0, num2 = 0;
    public static Context cont;

    public static void firstStart(Context context) {
        cont = context;

        //Проверяем наличие гифок в локальной базе
        App.getInstance().getDatabase().gifDao().getAllGif()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                    @Override
                    public void onSuccess(List<Gif> gif) {
                        list = gif;

                        //Если пуст лист заполняем его
                        if (list.size() == 0) {
                            NetworkService.getInstance()
                                    .getJSONApi()
                                    .getPostTop(num)
                                    .enqueue(new Callback<BigData>() {
                                        @Override
                                        public void onResponse(@NonNull Call<BigData> call, @NonNull Response<BigData> response) {
                                            BigData post = response.body();
                                            for (int i = 0; i < 5; i++) {
                                                ResponseData post2 = post.getResult().get(i);
                                                new Thread() {
                                                    @Override
                                                    public void run() {
                                                        App.getInstance().getDatabase().gifDao().insert(new Gif(post2.getDescription(), post2.getGifURL(), 0));

                                                        App.getInstance().getDatabase().gifDao().getAllGif()
                                                                .subscribeOn(Schedulers.io())
                                                                .observeOn(AndroidSchedulers.mainThread())
                                                                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                                                                    @Override
                                                                    public void onSuccess(List<Gif> gif) {
                                                                        list = gif;
                                                                        if (list.size() == 5 && first) {
                                                                            BestFragment.loading(context);
                                                                            first = false;
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {
                                                                        list = new ArrayList<>();
                                                                        list.add(new Gif("bad", "bad", -1));
                                                                    }
                                                                });
                                                    }
                                                }.start();

                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<BigData> call, @NonNull Throwable t) {
                                            t.printStackTrace();
                                            list = new ArrayList<>();
                                            list.add(new Gif("bad", "bad", -1));
                                        }
                                    });
                        } else {
                            if (first) {
                                BestFragment.loading(context);
                                first = false;
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        list = new ArrayList<>();
                        list.add(new Gif("bad", e.getMessage(), -1));
                    }
                });

        //Тоже самое только для другого раздела
        App.getInstance().getDatabase().gifDao().getAllLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                    @Override
                    public void onSuccess(List<Gif> gif) {
                        list2 = gif;

                        if (list2.size() == 0) {
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

                                                        App.getInstance().getDatabase().gifDao().getAllLatest()
                                                                .subscribeOn(Schedulers.io())
                                                                .observeOn(AndroidSchedulers.mainThread())
                                                                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                                                                    @Override
                                                                    public void onSuccess(List<Gif> gif) {
                                                                        list2 = gif;
                                                                        if (list2.size() == 5 && first2) {
                                                                            LastFragment.loading(context);
                                                                            first2 = false;
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {
                                                                        list2 = new ArrayList<>();
                                                                        list2.add(new Gif("bad", "bad", -1));
                                                                    }
                                                                });
                                                    }
                                                }.start();

                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<BigData> call, @NonNull Throwable t) {
                                            t.printStackTrace();
                                            list2 = new ArrayList<>();
                                            list2.add(new Gif("bad", "bad", -1));
                                        }
                                    });
                        } else {
                            if (first2) {
                                LastFragment.loading(context);
                                first2 = false;
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        list2 = new ArrayList<>();
                        list2.add(new Gif("bad", e.getMessage(), -1));
                    }
                });
    }

    public static void newGif() {
        final int[] f = {1};
        int oldSize = list.size();
        ind++;

        if (ind + 2 >= list.size()) {
            num++;
            NetworkService.getInstance()
                    .getJSONApi()
                    .getPostTop(num)
                    .enqueue(new Callback<BigData>() {
                        @Override
                        public void onResponse(@NonNull Call<BigData> call, @NonNull Response<BigData> response) {
                            BigData post = response.body();
                            for (int i = 0; i < 5; i++) {
                                ResponseData post2 = post.getResult().get(i);
                                new Thread() {
                                    @Override
                                    public void run() {
                                        App.getInstance().getDatabase().gifDao().insert(new Gif(post2.getDescription(), post2.getGifURL(), 0));

                                        App.getInstance().getDatabase().gifDao().getAllGif()
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                                                    @Override
                                                    public void onSuccess(List<Gif> gif) {
                                                        list = gif;
                                                        if (list.size() == oldSize + 5 && f[0] == 1) {
                                                            f[0] = 0;
                                                            BestFragment.loading(cont);
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        list = new ArrayList<>();
                                                        list.add(new Gif("bad", "bad", -1));
                                                    }
                                                });
                                    }
                                }.start();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<BigData> call, @NonNull Throwable t) {
                            t.printStackTrace();
                            list = new ArrayList<>();
                            list.add(new Gif("bad", "bad", -1));
                            ind--;
                            num--;
                        }
                    });
        } else {
            BestFragment.loading(cont);
        }
    }

    public static void newGif2() {
        final int[] f = {1};
        int oldSize = list2.size();
        ind2++;

        if (ind2 + 2 >= list2.size()) {
            num2++;
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

                                        App.getInstance().getDatabase().gifDao().getAllLatest()
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new DisposableSingleObserver<List<Gif>>() {
                                                    @Override
                                                    public void onSuccess(List<Gif> gif) {
                                                        list2 = gif;
                                                        if (list2.size() == oldSize + 5 && f[0] == 1) {
                                                            f[0] = 0;
                                                            LastFragment.loading(cont);
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        list2 = new ArrayList<>();
                                                        list2.add(new Gif("bad", "bad", -1));
                                                    }
                                                });
                                    }
                                }.start();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<BigData> call, @NonNull Throwable t) {
                            t.printStackTrace();
                            list2 = new ArrayList<>();
                            list2.add(new Gif("bad", "bad", -1));
                        }
                    });
        } else {
            LastFragment.loading(cont);
        }
    }

    public static void oldGif() {
        if (ind > 0 && list.get(0).type != -1) {
            ind--;
            BestFragment.loading(cont);
        }
    }

    public static void oldGif2() {
        if (ind2 > 0) {
            ind2--;
            LastFragment.loading(cont);
        }
    }

    //Проверка интернет соединения
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
