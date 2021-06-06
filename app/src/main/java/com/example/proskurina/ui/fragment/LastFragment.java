package com.example.proskurina.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proskurina.R;
import com.example.proskurina.Repository;


public class LastFragment extends Fragment {
    static ImageView gifka;
    static TextView text;
    Button newGif, oldGif;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last, container, false);

        gifka = view.findViewById(R.id.image);
        text = view.findViewById(R.id.text);
        newGif = view.findViewById(R.id.buttonNew);
        oldGif = view.findViewById(R.id.buttonOld);

        newGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Repository.isOnline(getContext())) {
                    gifka.setImageResource(R.drawable.ic_internet);
                    text.setText("Ошибка! Проверьте свое интернет соединение");
                } else {
                    Repository.newGif2();
                    oldGif.setBackgroundResource(R.color.purple_500);
                }
            }
        });

        oldGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Repository.isOnline(getContext())) {
                    gifka.setImageResource(R.drawable.ic_internet);
                    text.setText("Ошибка! Проверьте свое интернет соединение");
                } else {
                    Repository.oldGif2();
                    if (Repository.ind2 == 0) {
                        oldGif.setBackgroundResource(R.color.gray);
                    }
                }
            }
        });
        return view;
    }

    public static void loading(Context context) {
        if (!Repository.isOnline(context)) {
            gifka.setImageResource(R.drawable.ic_internet);
            text.setText("Ошибка! Проверьте свое интернет соединение");
        } else {
            text.setText(Repository.list2.get(Repository.ind2).text + "");
            Glide.with(context).asGif().load(Repository.list2.get(Repository.ind2).url).into(gifka);
        }
    }
}