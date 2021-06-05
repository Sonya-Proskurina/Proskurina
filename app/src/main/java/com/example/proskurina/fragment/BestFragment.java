package com.example.proskurina.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proskurina.App;
import com.example.proskurina.R;
import com.example.proskurina.Repository;
import com.example.proskurina.database.Gif;

public class BestFragment extends Fragment {

    ImageView gifka;
    TextView text;
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

        text.setText("Когда хочешь быстро разобраться с реактивным программированием и архитектурой для тестовго задания смены в Сириусе");
        Glide.with(getContext()).asGif().load(R.drawable.sory).into(gifka);

        newGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gif gif = App.repository.newGif();

                Log.e("listSize ", App.repository.listSize() + "");
                Log.e("Num ", App.repository.getNum() + "");

                text.setText(gif.text);
                Glide.with(getContext()).asGif().load(gif.url).into(gifka);
                oldGif.setBackgroundResource(R.color.design_default_color_primary);
            }
        });

        oldGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.repository.getInd() > 0) {
                    Gif gif = App.repository.oldGif();
                    text.setText(gif.text);
                    Glide.with(getContext()).asGif().load(gif.url).into(gifka);
                }
                if (App.repository.getInd()==0)  oldGif.setBackgroundResource(R.color.gray);
            }
        });

        return view;
    }
}