package com.minervasforge.velocity;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PersisterTest {
    @Test
    public void shouldStoreAndLoadRepositories() throws Exception {
        TaskRepository taskRepository = new TaskRepository();
        taskRepository.add(new Task("task"));
        PointWallet pointWallet = new PointWallet();
        pointWallet.awardPoints();
        SkillRepository skillRepository = new SkillRepository();
        skillRepository.add(new Skill("skill"));
        RewardRepository rewardRepository = new RewardRepository();
        rewardRepository.add(Reward.createSmallReward("reward"));

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("/tmp/TaskArcade.dat"));
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/tmp/TaskArcade.dat"));
        Persister persister = new Persister(objectOutputStream);

        persister.store(taskRepository, pointWallet, rewardRepository,
                skillRepository);

        TaskRepository loadedTaskRepository = (TaskRepository) persister
                .load(objectInputStream, TaskRepository.class);
        PointWallet loadedPointWallet = (PointWallet) persister
                .load(objectInputStream, PointWallet.class);
        RewardRepository loadedRewardRepository = (RewardRepository) persister
                .load(objectInputStream, RewardRepository.class);
        SkillRepository loadedSkillRepository = (SkillRepository) persister
                .load(objectInputStream, SkillRepository.class);

        assertThat(loadedTaskRepository, is(taskRepository));
        assertThat(loadedPointWallet, is(pointWallet));
        assertThat(loadedSkillRepository, is(skillRepository));
        assertThat(loadedRewardRepository, is(rewardRepository));
    }
}
