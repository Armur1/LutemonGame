package com.example.project.logic;

import com.example.project.model.*;
import java.util.*;

public class GameManager {
    private static Lutemon champion;
    private static List<Lutemon> opponents = new ArrayList<>();

    private static int damageDealt = 0;
    private static int damageTaken = 0;

    // Start New Game Round
    public static void startGameRound(String name, String color) {
        switch (color) {
            case "Pink": champion = new Pink(name); break;
            case "Black": champion = new Black(name); break;
            case "Orange": champion = new Orange(name); break;
            case "White": champion = new White(name); break;
            case "Green": champion = new Green(name); break;
        }

        opponents.clear();
        if (!color.equals("Pink")) opponents.add(new Pink("BotPink"));
        if (!color.equals("Black")) opponents.add(new Black("BotBlack"));
        if (!color.equals("Orange")) opponents.add(new Orange("BotOrange"));
        if (!color.equals("White")) opponents.add(new White("BotWhite"));
        if (!color.equals("Green")) opponents.add(new Green("BotGreen"));
    }

    // Getters
    public static Lutemon getChampion() { return champion; }
    public static List<Lutemon> getOpponents() { return opponents; }

    // Damage Tracking
    public static void trackDealt(int amount) { damageDealt += amount; }
    public static void trackTaken(int amount) { damageTaken += amount; }
    public static int getDamageDealt() { return damageDealt; }
    public static int getDamageTaken() { return damageTaken; }

    // XP & Stat Logic
    public static void awardXP(String stat) {
        if (champion == null) return;

        if (stat != null && !stat.isEmpty()) {
            switch (stat) {
                case "Attack": champion.attack++; break;
                case "Defense": champion.defense++; break;
                case "Health":
                    champion.maxHealth += 5;
                    champion.health = champion.maxHealth;
                    break;
            }
        }

        champion.gainExperience(1);

        for (Lutemon l : opponents) {
            l.attack++;
            l.defense++;
            l.maxHealth += 2;
            l.health = l.maxHealth;
        }
    }

    public static void useExperience(String stat) {
        if (champion != null && champion.getExperience() > 0) {
            switch (stat) {
                case "Attack": champion.attack++; break;
                case "Defense": champion.defense++; break;
                case "Health":
                    champion.maxHealth += 5;
                    champion.health = champion.maxHealth;
                    break;
            }
            champion.useExperience();
        }
    }

    // Heal champion after each battle
    public static void restoreChampion() {
        if (champion != null) {
            champion.restore();
        }
    }

    // Reset game data ===
    public static void resetStats() {
        damageDealt = 0;
        damageTaken = 0;
        champion = null;
        opponents.clear();
    }

    // Get champion stats as string
    public static String getChampionStats() {
        if (champion == null) return "No champion selected.";
        return champion.getName() + " (" + champion.getType() + ")\n"
                + "Attack: " + champion.getAttack() + "\n"
                + "Defense: " + champion.getDefense() + "\n"
                + "Health: " + champion.getHealth() + "/" + champion.getMaxHealth() + "\n"
                + "XP: " + champion.getExperience();
    }
}

