package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project.databinding.BattleBinding;
import com.example.project.logic.GameManager;
import com.example.project.model.Lutemon;

public class Battle extends AppCompatActivity {

    private BattleBinding binding;
    private Lutemon champion;
    private Lutemon enemy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind the UI using ViewBinding
        binding = BattleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve the current player's champion
        champion = GameManager.getChampion();

        // Ensure both champion and at least one opponent exist
        if (champion == null || GameManager.getOpponents().isEmpty()) {
            Toast.makeText(this, "No opponents available!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Select the first opponent from the list
        enemy = GameManager.getOpponents().get(0);

        // Load images and display initial health
        setImages();
        updateUI();

        // Set up attack button logic
        binding.btnAttack.setOnClickListener(v -> {
            // Champion attacks enemy
            champion.attack(enemy);

            // If enemy is defeated
            if (!enemy.isAlive()) {
                GameManager.getOpponents().remove(enemy);
                GameManager.awardXP("");  // Grant XP only, upgrade comes later
                GameManager.restoreChampion();  // Heal champion and reset ultimate

                // Go to Victory/Defeat screen
                Intent intent = new Intent(this, VictoryDefeatActivity.class);
                intent.putExtra("victory", true);
                intent.putExtra("isFinalBattle", GameManager.getOpponents().isEmpty());
                startActivity(intent);
                finish();
                return;
            }

            // Enemy uses ultimate if HP drops below 50% and not yet used
            if (!enemy.isUltimateUsed() && enemy.getHealth() < enemy.getMaxHealth() / 2) {
                enemy.useUltimate();
                Toast.makeText(this, enemy.getName() + " used their Ultimate!", Toast.LENGTH_SHORT).show();
            }

            // Enemy attacks champion back
            enemy.attack(champion);

            // If champion dies, show defeat screen
            if (!champion.isAlive()) {
                Intent intent = new Intent(this, VictoryDefeatActivity.class);
                intent.putExtra("victory", false);
                intent.putExtra("isFinalBattle", false);
                startActivity(intent);
                finish();
                return;
            }

            // Update the UI with latest HP
            updateUI();
        });

        // Set up ultimate button logic
        binding.btnUltimate.setOnClickListener(v -> {
            if (!champion.isUltimateUsed()) {
                champion.useUltimate();
                Toast.makeText(this, "Ultimate activated!", Toast.LENGTH_SHORT).show();
                updateUI();
            } else {
                Toast.makeText(this, "Ultimate already used!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Update both champion's and enemy's HP display
    private void updateUI() {
        String champStatus = champion.getName() + ": " + champion.getHealth() + "/" + champion.getMaxHealth() + " HP";
        String enemyStatus = enemy.getName() + ": " + enemy.getHealth() + "/" + enemy.getMaxHealth() + " HP";

        binding.textChampionHP.setText(champStatus);
        binding.textEnemyHP.setText(enemyStatus);
    }

    // Assign appropriate image based on Lutemon type
    private void setImages() {
        binding.imgChampion.setImageResource(getImageResource(champion.getType()));
        binding.imgEnemy.setImageResource(getImageResource(enemy.getType()));
    }

    // Helper method to match color type to drawable
    private int getImageResource(String type) {
        switch (type) {
            case "Pink": return R.drawable.pink_lutemon;
            case "Black": return R.drawable.black_lutemon;
            case "Orange": return R.drawable.orange_lutemon;
            case "White": return R.drawable.white_lutemon;
            case "Green": return R.drawable.green_lutemon;
            default: return android.R.drawable.ic_menu_help; // fallback image
        }
    }
}




