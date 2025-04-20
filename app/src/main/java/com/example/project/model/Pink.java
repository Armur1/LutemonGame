package com.example.project.model;
public class Pink extends Lutemon {
    public Pink(String name) {
        super(name, "Pink", 5, 4, 35);
    }

    @Override
    public void useUltimate() {
        if (!ultimateUsed) {
            health = Math.min(maxHealth, health + (maxHealth * 40 / 100));
            ultimateUsed = true;
        }
    }
}
