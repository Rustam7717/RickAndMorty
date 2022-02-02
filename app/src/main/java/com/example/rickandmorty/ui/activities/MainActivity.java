package com.example.rickandmorty.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.ActivityMainBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


    }
}