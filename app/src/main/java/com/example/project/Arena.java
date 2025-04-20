package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.databinding.ArenaBinding;
import com.example.project.logic.GameManager;
import com.example.project.model.Lutemon;

import java.util.List;

public class Arena extends AppCompatActivity {

    private ArenaBinding binding;
    private Lutemon selectedOpponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use ViewBinding to inflate the layout
        binding = ArenaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the player's selected champion
        Lutemon champion = GameManager.getChampion();

        // If no opponents left, go to ChampionScreenActivity
        if (GameManager.getOpponents().isEmpty()) {
            Intent intent = new Intent(this, ChampionScreenActivity.class);
            intent.putExtra("name", champion.getName());
            intent.putExtra("type", champion.getType());
            intent.putExtra("attack", champion.getAttack());
            intent.putExtra("defense", champion.getDefense());
            intent.putExtra("health", champion.getMaxHealth());
            intent.putExtra("dealt", GameManager.getDamageDealt());
            intent.putExtra("taken", GameManager.getDamageTaken());
            GameManager.resetStats();
            startActivity(intent);
            finish();
            return;
        }

        // Display champion image and stats if available
        if (champion != null) {
            binding.imgChampionArena.setImageResource(getImageResource(champion.getType()));
            binding.textChampionStats.setText(
                    champion.getName() + " (" + champion.getType() + ")\n" +
                            "ATK: " + champion.getAttack() +
                            "  DEF: " + champion.getDefense() +
                            "  HP: " + champion.getHealth() + "/" + champion.getMaxHealth() +
                            "  XP: " + champion.getExperience()
            );
        }

        // Retrieve remaining opponents from GameManager
        List<Lutemon> opponents = GameManager.getOpponents();
        if (!opponents.isEmpty()) {
            // Display the first available opponent
            selectedOpponent = opponents.get(0);
            binding.imgOpponent.setImageResource(getImageResource(selectedOpponent.getType()));
            binding.textOpponentInfo.setText(
                    selectedOpponent.getName() + " (" + selectedOpponent.getType() + ")\n" +
                            "ATK: " + selectedOpponent.getAttack() +
                            "  DEF: " + selectedOpponent.getDefense() +
                            "  HP: " + selectedOpponent.getHealth() + "/" + selectedOpponent.getMaxHealth()
            );
        } else {
            // No opponents left, hide battle button
            binding.textOpponentInfo.setText("No opponents available.");
            binding.btnEnterBattle.setVisibility(View.GONE);
        }

        // Launch the Battle screen when 'Enter Battle' is clicked
        binding.btnEnterBattle.setOnClickListener(v -> {
            if (selectedOpponent != null) {
                Intent intent = new Intent(this, Battle.class);
                startActivity(intent);
                finish(); // Close Arena screen
            }
        });

        // Quit to champion selection
        binding.btnQuit.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChampionSelection.class);
            startActivity(intent);
            finish();
        });
    }

    // map Lutemon type to a drawable
    private int getImageResource(String type) {
        switch (type) {
            case "Pink": return R.drawable.pink_lutemon;
            case "Black": return R.drawable.black_lutemon;
            case "Orange": return R.drawable.orange_lutemon;
            case "White": return R.drawable.white_lutemon;
            case "Green": return R.drawable.green_lutemon;
            default: return android.R.drawable.ic_menu_help; // fallback icon
        }
    }
}


