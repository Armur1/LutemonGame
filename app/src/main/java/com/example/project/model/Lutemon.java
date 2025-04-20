package com.example.project.model;
import com.example.project.logic.GameManager;

public abstract class Lutemon {
    protected String name;
    protected String type;
    public int attack;
    public int defense;
    public int health;
    public int maxHealth;
    protected int experience;
    protected boolean ultimateUsed;

    public Lutemon(String name, String type, int attack, int defense, int health) {
        this.name = name;
        this.type = type;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.maxHealth = health;
        this.experience = 0;
        this.ultimateUsed = false;
    }

    public void takeDamage(int damage) {
        int reduced = (int)(damage * (100.0 / (100 + defense)));
        health -= Math.max(reduced, 1);
        GameManager.trackTaken(reduced);
    }

    public void attack(Lutemon target) {
        int min = (int)(attack * 0.85);
        int max = (int)(attack * 1.15);
        int randomAttack = min + new java.util.Random().nextInt(max - min + 1);
        target.takeDamage(randomAttack);
        GameManager.trackDealt(randomAttack);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void gainExperience(int amount) {
        experience += amount;
    }

    public void useExperience() {
        if (experience > 0) {
            experience--;
        }
    }
    public void reset() {
        this.health = maxHealth;
        this.ultimateUsed = false;
    }
    public void resetUltimate() {
        this.ultimateUsed = false;
    }
    public void restore() {
        this.health = this.maxHealth;
        this.ultimateUsed = false;
    }





    public abstract void useUltimate();

    public String getName() { return name; }
    public String getType() { return type; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getExperience() { return experience; }
    public boolean isUltimateUsed() { return ultimateUsed; }
}
