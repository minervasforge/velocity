package com.minervasforge.velocity;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RewardRepositoryTest {
    private final Reward expectedReward = Reward.createSmallReward("one");
    private RewardRepository rewardRepository = new RewardRepository();

    private RewardRepository getRewardRepository(Reward reward) {
        rewardRepository.add(reward);
        return rewardRepository;
    }

    @Test
    public void shouldAddRewardToRepository() {
        rewardRepository = getRewardRepository(expectedReward);

        assertThat(rewardRepository.getAll(), is(Arrays.asList(expectedReward)));
    }

    @Test
    public void shouldAddSeveralRewardsToRepository() {
        rewardRepository = getRewardRepository(expectedReward);
        rewardRepository.add(Reward.createSmallReward("two"));

        assertThat(rewardRepository.getAll(), is(Arrays.asList(expectedReward, Reward.createSmallReward("two"))));
    }

    @Test
    public void shouldShowThatYouCannotGetAnAwardWithoutEnoughPointsToPayForIt() {
        rewardRepository = getRewardRepository(expectedReward);
        PointWallet pointWallet = new PointWallet();

        assertFalse(rewardRepository.canAward(expectedReward, pointWallet));
    }

    @Test
    public void shouldShowThatYouCanGetAnAwardWithEnoughPointsToPayForIt() {
        rewardRepository = getRewardRepository(expectedReward);
        PointWallet pointWallet = new PointWallet();
        awardTenPoints(pointWallet);

        assertTrue(rewardRepository.canAward(expectedReward, pointWallet));
    }

    @Test
    public void shouldAwardUserByDecrementingPointCount(){
        rewardRepository = getRewardRepository(expectedReward);
        PointWallet pointWallet = new PointWallet();
        awardTenPoints(pointWallet);

        rewardRepository.award(expectedReward, pointWallet);

        assertThat(pointWallet.getTotalPoints(), is(2));
    }

    @Test
    public void shouldTestRewardRepositoryEquality() throws Exception {
        RewardRepository rewardRepository = new RewardRepository();
        rewardRepository.add(expectedReward);
        RewardRepository rewardRepository2 = new RewardRepository();
        rewardRepository2.add(expectedReward);

        assertThat(rewardRepository, is(rewardRepository2));
    }

    private void awardTenPoints(PointWallet pointWallet) {
        pointWallet.awardPoints();
        pointWallet.awardPoints();
    }
}
