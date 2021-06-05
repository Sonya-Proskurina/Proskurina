package com.example.proskurina.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.proskurina.R;
import com.example.proskurina.databinding.ActivityMainBinding;
import com.example.proskurina.ui.fragment.BestFragment;
import com.example.proskurina.ui.fragment.HotterFragment;
import com.example.proskurina.ui.fragment.LastFragment;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpWithViewPager(binding.mainViewPager);

        binding.mainTabsLayout.setupWithViewPager(binding.mainViewPager);
        setSupportActionBar(binding.toolbar);
    }

    //Создание разделов в viewPager
    private void setUpWithViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(new LastFragment(), "Последние");
        adapter.addFragments(new BestFragment(), "Лучшие");
        adapter.addFragments(new HotterFragment(), "Горячие");
        viewPager.setAdapter(adapter);
    }
}