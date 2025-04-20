package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.logic.GameManager;
import com.example.project.model.Lutemon;

public class VictoryDefeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory_defeat);

        boolean victory = getIntent().getBooleanExtra("victory", false);
        boolean isFinal = getIntent().getBooleanExtra("isFinalBattle", false);

        TextView resultText = findViewById(R.id.textResult);
        TextView xpText = findViewById(R.id.textXpInfo);

        Button btnAttack = findViewById(R.id.btnAddAttack);
        Button btnDefense = findViewById(R.id.btnAddDefense);
        Button btnHealth = findViewById(R.id.btnAddHealth);
        Button btnNext = findViewById(R.id.btnContinue);

        // Show result message
        if (victory) {
            resultText.setText("🎉 Victory!");
        } else {
            resultText.setText("💀 Defeat! Your champion has fallen.");
        }

        Lutemon champ = GameManager.getChampion();
        if (champ != null) {
            xpText.setText("XP Available: " + champ.getExperience());
        }

        // Show/hide upgrade buttons
        if (victory) {
            if (!isFinal) {
                btnAttack.setVisibility(View.VISIBLE);
                btnDefense.setVisibility(View.VISIBLE);
                btnHealth.setVisibility(View.VISIBLE);
                btnNext.setText("Continue to Arena");

                btnAttack.setOnClickListener(v -> {
                    GameManager.useExperience("Attack");
                    xpText.setText("XP Available: " + champ.getExperience());
                });
                btnDefense.setOnClickListener(v -> {
                    GameManager.useExperience("Defense");
                    xpText.setText("XP Available: " + champ.getExperience());
                });
                btnHealth.setOnClickListener(v -> {
                    GameManager.useExperience("Health");
                    xpText.setText("XP Available: " + champ.getExperience());
                });
            } else {
                btnAttack.setVisibility(View.GONE);
                btnDefense.setVisibility(View.GONE);
                btnHealth.setVisibility(View.GONE);
                btnNext.setText("Show Champion");
            }
        } else {
            // No champion
            btnAttack.setVisibility(View.GONE);
            btnDefense.setVisibility(View.GONE);
            btnHealth.setVisibility(View.GONE);
        }

        if (!victory) {
            // Defeated
            btnAttack.setVisibility(View.GONE);
            btnDefense.setVisibility(View.GONE);
            btnHealth.setVisibility(View.GONE);
            btnNext.setText("Return to Main Menu");
        }

        // Handle navigation
        btnNext.setOnClickListener(v -> {
            Intent intent;

            if (!victory) {
                // Defeated → reset and go to main
                GameManager.resetStats();
                intent = new Intent(this, MainActivity.class);
            } else if (isFinal) {
                // Final victory → show champion summary
                if (champ != null) {
                    intent = new Intent(this, ChampionScreenActivity.class);
                    intent.putExtra("name", champ.getName());
                    intent.putExtra("type", champ.getType());
                    intent.putExtra("attack", champ.getAttack());
                    intent.putExtra("defense", champ.getDefense());
                    intent.putExtra("health", champ.getMaxHealth());
                    intent.putExtra("dealt", GameManager.getDamageDealt());
                    intent.putExtra("taken", GameManager.getDamageTaken());
                    GameManager.resetStats();
                } else {
                    // fallback to main if champ is null
                    intent = new Intent(this, MainActivity.class);
                }
            } else {
                // Mid-round victory → restore and return to arena
                GameManager.restoreChampion();
                intent = new Intent(this, Arena.class); // change to ArenaActivity if that’s the correct name
            }

            startActivity(intent);
            finish();
        });
    }
}


