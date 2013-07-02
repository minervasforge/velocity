package com.minervasforge.velocity.persistence;

import com.minervasforge.velocity.models.PointWallet;
import com.minervasforge.velocity.models.Reward;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RewardRepository implements Serializable{
    private List<Reward> rewards = new ArrayList<Reward>();

    public void add(Reward reward) {
        rewards.add(reward);
    }

    public List<Reward> getAll() {
        return rewards;
    }

    public void award(Reward reward, PointWallet pointWallet) {
        pointWallet.decreasePoints(reward.getCost());
    }

    public boolean canAward(Reward reward, PointWallet pointWallet) {
        return reward.getCost() < pointWallet.getTotalPoints();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RewardRepository that = (RewardRepository) o;

        if (!rewards.equals(that.rewards)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return rewards.hashCode();
    }
}
