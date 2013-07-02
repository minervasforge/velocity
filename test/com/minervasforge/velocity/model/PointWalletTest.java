package com.minervasforge.velocity.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PointWalletTest {
    @Test
    public void shouldAwardBasicPoints(){
        PointWallet pointWallet = new PointWallet();

        pointWallet.awardPoints();

        assertThat(pointWallet.getTotalPoints(), is(5));
    }

    @Test
    public void shouldIncrementPoints(){
        PointWallet pointWallet = new PointWallet();

        awardTenPoints(pointWallet);

        assertThat(pointWallet.getTotalPoints(), is(10));
    }

    @Test
    public void shouldDecrementPoints(){
        PointWallet pointWallet = new PointWallet();
        awardTenPoints(pointWallet);
        int cost = 8;

        pointWallet.decreasePoints(cost);

        assertThat(pointWallet.getTotalPoints(), is(2));
    }



    private void awardTenPoints(PointWallet pointWallet) {
        pointWallet.awardPoints();
        pointWallet.awardPoints();
    }
}
