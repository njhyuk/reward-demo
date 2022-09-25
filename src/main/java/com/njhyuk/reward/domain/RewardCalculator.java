package com.njhyuk.reward.domain;

public class RewardCalculator {
    static public int calculatePoint(int consecutiveCount) {
        int validCount = consecutiveCount % 10;

        return switch (validCount) {
            case 3 -> 300;
            case 5 -> 500;
            case 0 -> 1000;
            default -> 100;
        };
    }
}
