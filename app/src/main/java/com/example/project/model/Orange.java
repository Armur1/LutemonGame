package com.example.project.model;


public class Orange extends Lutemon {
    private boolean critical = false;

    public Orange(String name) {
        super(name, "Orange", 8, 2, 30);
    }

    @Override
    public void useUltimate() {
        if (!ultimateUsed) {
            critical = true;
            ultimateUsed = true;
        }
    }

    @Override
    public void attack(Lutemon enemy) {
        if (critical) {
            enemy.takeDamage(attack * 2);
            critical = false;
        } else {
            super.attack(enemy);
        }
    }
}
