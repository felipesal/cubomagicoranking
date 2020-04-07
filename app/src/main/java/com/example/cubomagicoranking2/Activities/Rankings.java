package com.example.cubomagicoranking2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.cubomagicoranking2.Fragments.MediaDosCentraisFragment;
import com.example.cubomagicoranking2.Fragments.MelhorDeTresFragment;
import com.example.cubomagicoranking2.Fragments.SimplesFragment;
import com.example.cubomagicoranking2.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class Rankings extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);

        Bundle dados = getIntent().getExtras();

        smartTabLayout = findViewById(R.id.viewPagerTab);
        viewPager = findViewById(R.id.viewPager);

        getSupportActionBar().setElevation(0);


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Simples", SimplesFragment.class)
                .add("Melhor", MelhorDeTresFragment.class)
                .add("Média", MediaDosCentraisFragment.class)
                .create());


        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pag_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
    }

