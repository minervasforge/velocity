package com.minervasforge.velocity.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SkillTest {
    @Test
    public void shouldShowTwoSkillsAreEqual(){
        assertThat(new Skill("one"), is(new Skill("one")));
    }
}
