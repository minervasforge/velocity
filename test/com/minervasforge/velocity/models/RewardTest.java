package com.minervasforge.velocity.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RewardTest {
    private final int expectedCostOfSmallReward = 8;
    private Reward reward = Reward.createSmallReward("reward");

    @Test
    public void shouldTestRewardEquality(){
        assertThat(reward, is(Reward.createSmallReward("reward")));
    }

    @Test
    public void shouldGetRewardCost(){
        assertThat(reward.getCost(), is(expectedCostOfSmallReward));
    }

    @Test
    public void shouldCreateSmallReward() throws Exception {
        reward = Reward.createSmallReward("title");
        assertSmallReward("title", expectedCostOfSmallReward, reward);

        reward = Reward.createSmallReward("second title");
        assertSmallReward("second title", expectedCostOfSmallReward, reward);
    }

    private void assertSmallReward(String expectedTitle, int expectedCost, Reward actualReward) {
        assertThat(actualReward.getCost(), is(expectedCost));
        assertThat(actualReward.getTitle(), is(expectedTitle));
    }
}
