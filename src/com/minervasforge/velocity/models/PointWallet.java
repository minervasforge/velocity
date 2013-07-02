package com.minervasforge.velocity.models;

import java.io.Serializable;

public class PointWallet implements Serializable {
    private final int BASIC_AWARDED_POINTS = 5;
    private int totalPoints;

    public PointWallet() {
    }

    public void awardPoints() {
        totalPoints += BASIC_AWARDED_POINTS;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void decreasePoints(int cost) {
        totalPoints -= cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointWallet that = (PointWallet) o;

        if (BASIC_AWARDED_POINTS != that.BASIC_AWARDED_POINTS) return false;
        if (totalPoints != that.totalPoints) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = BASIC_AWARDED_POINTS;
        result = 31 * result + totalPoints;
        return result;
    }
}
