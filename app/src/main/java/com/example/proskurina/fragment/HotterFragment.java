package com.example.proskurina.fragment;

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
import com.example.proskurina.database.Gif;

public class HotterFragment extends Fragment {
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

        text.setText("Гифки данной категории отсутствуют на сайте");
        return view;
    }
}