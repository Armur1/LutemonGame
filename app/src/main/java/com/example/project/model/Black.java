package com.example.project.model;

public class Black extends Lutemon {
    private boolean blockNext = false;

    public Black(String name) {
        super(name, "Black", 4, 6, 40);
    }

    @Override
    public void useUltimate() {
        if (!ultimateUsed) {
            blockNext = true;
            ultimateUsed = true;
        }
    }

    @Override
    public void takeDamage(int damage) {
        if (blockNext) {
            blockNext = false;
        } else {
            super.takeDamage(damage);
        }
    }
}

