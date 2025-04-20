package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ChampionScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion_screen);

        // Bind the TextView and ImageView that will show the champion's stats and portrait
        TextView stats = findViewById(R.id.textChampionStats);
        ImageView portrait = findViewById(R.id.imgChampionPortrait);

        // Retrieve data passed through intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String type = intent.getStringExtra("type");
        int atk = intent.getIntExtra("attack", 0);
        int def = intent.getIntExtra("defense", 0);
        int hp = intent.getIntExtra("health", 0);
        int dealt = intent.getIntExtra("dealt", 0);
        int taken = intent.getIntExtra("taken", 0);

        // Format and display the champion's stats
        stats.setText(
                "Champion: " + name + "\n" +
                        "Type: " + type + "\n" +
                        "Attack: " + atk + "\n" +
                        "Defense: " + def + "\n" +
                        "Health: " + hp + "\n\n" +
                        "Damage Dealt: " + dealt + "\n" +
                        "Damage Taken: " + taken
        );

        // Set the image resource for the champion's type
        portrait.setImageResource(getImageResource(type));
    }

    // Match type string to drawable resource
    private int getImageResource(String type) {
        switch (type) {
            case "Pink": return R.drawable.pink_lutemon;
            case "Black": return R.drawable.black_lutemon;
            case "Orange": return R.drawable.orange_lutemon;
            case "White": return R.drawable.white_lutemon;
            case "Green": return R.drawable.green_lutemon;
            default: return android.R.drawable.ic_menu_help;
        }
    }
}


