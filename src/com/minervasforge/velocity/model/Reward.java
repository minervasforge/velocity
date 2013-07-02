package com.minervasforge.velocity.model;

import java.io.Serializable;

public class Reward implements Serializable{
    private final int SMALL_REWARD_COST = 8;
    private String title;

    private Reward (String title) {
        this.title = title;
    }

    public int getCost() {
        return SMALL_REWARD_COST;
    }

    public String getTitle() {
        return title;
    }

    public static Reward createSmallReward(String title) {
        return new Reward(title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reward reward = (Reward) o;

        if (!title.equals(reward.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return "Reward{" +
                "title='" + title + '\'' +
                '}';
    }


}
