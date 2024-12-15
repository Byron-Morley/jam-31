package com.byron.systems;

public class MilestoneSystem {
    private int level;
    private int score;
    private int pointsToNextLevel;

    public MilestoneSystem() {
        level = 0;
        score = 0;
        pointsToNextLevel = 1000;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        level++;
    }

    public int getScore() {
        return score;
    }

    public int getPointsToNextLevel() {
        return pointsToNextLevel;
    }
}
