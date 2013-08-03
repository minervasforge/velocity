package com.minervasforge.velocity.persistence;

import com.minervasforge.velocity.model.PointWallet;
import com.minervasforge.velocity.model.Reward;
import com.minervasforge.velocity.model.Skill;
import com.minervasforge.velocity.model.Task;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PersisterTest {

    private TaskRepository taskRepository;
    private PointWallet pointWallet;
    private SkillRepository skillRepository;
    private RewardRepository rewardRepository;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Persister persister;

    public void setUpRepositories() throws Exception {
        taskRepository = new TaskRepository();
        pointWallet = new PointWallet();
        skillRepository = new SkillRepository();
        rewardRepository = new RewardRepository();

        taskRepository.add(new Task("task"));
        pointWallet.awardPoints();
        skillRepository.add(new Skill("skill"));
        rewardRepository.add(Reward.createSmallReward("reward"));
    }

    private void setupInputAndOutputStreams() throws IOException {
        objectOutputStream = new ObjectOutputStream(new FileOutputStream("/tmp/TaskArcade.dat"));
        objectInputStream = new ObjectInputStream(new FileInputStream("/tmp/TaskArcade.dat"));
        persister = new Persister(objectOutputStream);
    }

    @Test
    public void shouldStoreAndLoadAllRepositories() throws Exception {
        setUpRepositories();
        setupInputAndOutputStreams();

        persister.store(taskRepository, pointWallet, rewardRepository,
                skillRepository);

        TaskRepository loadedTaskRepository = (TaskRepository) persister
                .load(objectInputStream);
        PointWallet loadedPointWallet = (PointWallet) persister
                .load(objectInputStream);
        RewardRepository loadedRewardRepository = (RewardRepository) persister
                .load(objectInputStream);
        SkillRepository loadedSkillRepository = (SkillRepository) persister
                .load(objectInputStream);

        assertThat(loadedTaskRepository, is(taskRepository));
        assertThat(loadedPointWallet, is(pointWallet));
        assertThat(loadedSkillRepository, is(skillRepository));
        assertThat(loadedRewardRepository, is(rewardRepository));
    }

}
