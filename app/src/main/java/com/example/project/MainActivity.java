package com.example.project;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the view using ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set listener on Start Game button
        binding.btnStartGame.setOnClickListener(v -> {
            // Navigate to Champion Selection screen
            Intent intent = new Intent(MainActivity.this, ChampionSelection.class);
            startActivity(intent);
        });
    }
}
