package com.example.project.model;

import com.example.project.logic.GameManager;


public class Green extends Lutemon {
    private Lutemon counterTarget;

    public Green(String name) {
        super(name, "Green", 6, 3, 33);
    }

    @Override
    public void useUltimate() {
        if (!ultimateUsed) {
            counterTarget = GameManager.getChampion();
            ultimateUsed = true;
        }
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (counterTarget != null) {
            counterTarget.takeDamage(attack);
            counterTarget = null;
        }
    }
}
