package com.example.project.model;
import com.example.project.logic.GameManager;

public class White extends Lutemon {
    private boolean reflect = false;

    public White(String name) {
        super(name, "White", 6, 3, 35);
    }

    @Override
    public void useUltimate() {
        if (!ultimateUsed) {
            reflect = true;
            ultimateUsed = true;
        }
    }

    @Override
    public void takeDamage(int damage) {
        int reduced = (int)(damage * (100.0 / (100 + defense)));
        health -= Math.max(reduced, 1);
        if (reflect) {
            GameManager.getChampion().takeDamage(reduced);
            reflect = false;
        }
    }
}
