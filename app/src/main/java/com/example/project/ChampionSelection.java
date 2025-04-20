package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.databinding.ChampionSelectionBinding;
import com.example.project.logic.GameManager;

public class ChampionSelection extends AppCompatActivity {

    private String selectedColor = null;
    private ChampionSelectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using ViewBinding
        binding = ChampionSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set click listeners for each Lutemon image
        binding.imgPink.setOnClickListener(v -> setSelected("Pink"));
        binding.imgBlack.setOnClickListener(v -> setSelected("Black"));
        binding.imgOrange.setOnClickListener(v -> setSelected("Orange"));
        binding.imgWhite.setOnClickListener(v -> setSelected("White"));
        binding.imgGreen.setOnClickListener(v -> setSelected("Green"));

        // Start button to create and enter game
        binding.btnStart.setOnClickListener(v -> {
            String name = binding.editTextName.getText().toString().trim();

            // Make sure both name and color are selected
            if (name.isEmpty() || selectedColor == null) {
                Toast.makeText(this, "Please enter a name and select a Lutemon.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Start the game logic and pass to Arena screen
            GameManager.startGameRound(name, selectedColor);
            Intent intent = new Intent(this, Arena.class); // Launch the Arena
            startActivity(intent);
            finish();
        });
    }

    // Handle color selection and feedback
    private void setSelected(String color) {
        selectedColor = color;
        Toast.makeText(this, color + " Lutemon selected!", Toast.LENGTH_SHORT).show();
    }
}

