package com.minervasforge.velocity.view;

import com.minervasforge.velocity.model.PointWallet;
import com.minervasforge.velocity.model.Reward;
import com.minervasforge.velocity.model.Skill;
import com.minervasforge.velocity.model.Task;
import com.minervasforge.velocity.persistence.Persister;
import com.minervasforge.velocity.persistence.RewardRepository;
import com.minervasforge.velocity.persistence.SkillRepository;
import com.minervasforge.velocity.persistence.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommandLineInterfaceTest {
    @Mock
    private PrintWriter out;
    @Mock
    private PointWallet pointWallet;
    @Mock
    private CommandReader commandReader;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private SkillRepository skillRepository;
    @Mock
    private Persister persister;
    @Mock
    private RewardRepository rewardRepository;

    @InjectMocks
    private CommandLineInterface commandLineInterface;

    private final List<Reward> rewards = asList(Reward.createSmallReward("3 hour nap"),
            Reward.createSmallReward("a piece of chocolate"),
            Reward.createSmallReward("a kiss from my girlfriend"));

    @Before
    public void setup() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldAddTaskThroughCommandLine() throws IOException {
        when(commandReader.nextCommand()).thenReturn(
                new Command("add task", "create unit test for grinder"));

        commandLineInterface.processNextCommand();

        verify(taskRepository).add(new Task("create unit test for grinder"));
    }

    @Test
    public void shouldGetAllTasksFromTaskRepository() throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("ts"));

        commandLineInterface.processNextCommand();

        verify(taskRepository).getAll();
    }

    @Test
    public void shouldOutputListOfTasksToTheConsole() throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("ts"));
        when(taskRepository.getAll()).thenReturn(
                asList(new Task("create unit test for grinder"), new Task(
                        "create feature test for grinder")));

        commandLineInterface.processNextCommand();

        verify(out).append("Tasks:\n");
        verify(out).append("1. create unit test for grinder\n");
        verify(out).append("2. create feature test for grinder\n");
        verify(out).flush();
    }

    @Test
    public void shouldAwardPointsWhenTaskIsCompleted() throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("c", "1"));
        when(taskRepository.getAll()).thenReturn(
                asList(new Task("create unit test for grinder"), new Task(
                        "create feature test for grinder")));

        commandLineInterface.processNextCommand();

        verify(taskRepository).complete(
                new Task("create unit test for grinder"));
        verify(pointWallet).awardPoints();
        verify(out).append("You are awarded 5 points\n");
        verify(out).flush();
    }

    @Test
    public void shouldQuitWhenPromptedByUser() throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("q"));

        commandLineInterface.processNextCommand();

        verify(persister).store(taskRepository, pointWallet, rewardRepository, skillRepository);
    }

//	@Test
//	public void shouldLoadTaskAndPointRepositories()
//			throws ClassNotFoundException, InstantiationException,
//			IllegalAccessException, IOException {
//		ObjectInputStream objectInputStream = Mockito.mock(ObjectInputStream.class);
//		commandLineInterface.loadRepositories(objectInputStream);
//
//		//TODO: VERIFY ORDERING TASK-POINT-REWARD-SKILL
//		verify(persister).load(objectInputStream, TaskRepository.class);
//		verify(persister).load(objectInputStream, PointWallet.class);
//		verify(persister).load(objectInputStream, SkillRepository.class);
//		verify(persister).load(objectInputStream, RewardRepository.class);
//	}

    @Test
    public void shouldCheckThatTheQuitFlagIsFalseAtStartup() {
        assertThat(commandLineInterface.userWantsToQuit(), is(false));
    }

    @Test
    public void shouldActivateQuitFlagIfUserSendsQuitCommand()
            throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("q"));

        commandLineInterface.processNextCommand();

        assertThat(commandLineInterface.userWantsToQuit(), is(true));
    }

    @Test
    public void shouldOutputNoPointsForNewUser() throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("ps"));

        commandLineInterface.processNextCommand();

        verify(out).append("You have 0 points\n");
        verify(out).flush();
    }

    @Test
    public void shouldOutputCurrentTotalPoints() throws IOException {
        when(pointWallet.getTotalPoints()).thenReturn(5);
        when(commandReader.nextCommand()).thenReturn(new Command("ps"));

        commandLineInterface.processNextCommand();

        verify(out).append("You have 5 points\n");
        verify(out).flush();
    }

    @Test
    public void shouldOutputBadCommandMessageWhenUserEntersBadCommand()
            throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("garbage"));

        commandLineInterface.processNextCommand();

        verify(out)
                .append("Please use a valid command. (enter \"help\" to see the list of commands)\n");
        verify(out).flush();
    }

    @Test
    public void shouldOutputAListOfCommandsWhenTheHelpCommandIsCalled()
            throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("help"));

        commandLineInterface.processNextCommand();

        verify(out)
                .append("Valid Commands:\n"
                        + "add task (<task name>) - Add a new task to your task list.\n"
                        + "ts - List the tasks you are currently working on.\n"
                        + "c (<number of a task in your task list>) - Mark a certain task as completed.\n\n"
                        + "add skill (<skill name>) - Add a new skill to your skill list.\n"
                        + "ss - List all your skills.\n\n"
                        + "add reward (<reward name>) - Add a new reward to your reward list.\n"
                        + "rs - List all your rewards.\n"
                        + "get (<number of reward in your reward list>) - Get a reward.\n\n"
                        + "ps - Display your total points.\n"
                        + "q - Save your progress and quit the game.\n"
                        + "help - Displays the list of valid commands.\n");
        verify(out).flush();
    }

    @Test
    public void shouldAllowUserToAddASkillToTheSkillRepository()
            throws IOException {
        when(commandReader.nextCommand()).thenReturn(
                new Command("add skill", "new skill"));

        commandLineInterface.processNextCommand();

        verify(skillRepository).add(new Skill("new skill"));
    }

    @Test
    public void shouldAllowUserToListAllSkills() throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("ss"));
        when(skillRepository.getAll()).thenReturn(
                asList(new Skill("one"), new Skill("two")));

        commandLineInterface.processNextCommand();

        verify(out).append("Skills:\n");
        verify(out).append("one\n");
        verify(out).append("two\n");
        verify(out).flush();
    }

    @Test
    public void shouldAllowUserToAddRewards() throws IOException {
        when(commandReader.nextCommand()).thenReturn(
                new Command("add reward", "one piece of chocolate"));

        commandLineInterface.processNextCommand();

        verify(rewardRepository).add(Reward.createSmallReward("one piece of chocolate"));
    }

    @Test
    public void shouldAllowUserToListAllRewards() throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("rs"));
        when(rewardRepository.getAll()).thenReturn(
                asList(Reward.createSmallReward("one"), Reward.createSmallReward("two")));

        commandLineInterface.processNextCommand();

        verify(out).append("Rewards:\n");
        verify(out).append("1. one\n");
        verify(out).append("2. two\n");
        verify(out).flush();
    }

    @Test
    public void shouldAllowUserToAnotherListOfAllRewards() throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("rs"));
        when(rewardRepository.getAll()).thenReturn(
                asList(Reward.createSmallReward("3 hour nap"),
                        Reward.createSmallReward("a kiss from my girlfriend")));

        commandLineInterface.processNextCommand();

        verify(out).append("Rewards:\n");
        verify(out).append("1. 3 hour nap\n");
        verify(out).append("2. a kiss from my girlfriend\n");
        verify(out).flush();
    }

    @Test
    public void shouldAllowUserToGetRewardIfTheyHaveEnoughPoints()
            throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("get", "2"));
        when(rewardRepository.getAll()).thenReturn(rewards);
        when(rewardRepository.canAward(Reward.createSmallReward("a piece of chocolate"),
                pointWallet)).thenReturn(true);

        commandLineInterface.processNextCommand();

        verify(rewardRepository).canAward(Reward.createSmallReward("a piece of chocolate"),
                pointWallet);
        verify(rewardRepository).award(Reward.createSmallReward("a piece of chocolate"),
                pointWallet);
    }

    @Test
    public void shouldNotAllowUserToGetAnotherRewardIfTheyDontHaveEnoughPoints()
            throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("get", "1"));
        when(rewardRepository.getAll()).thenReturn(
                rewards);
        when(pointWallet.getTotalPoints()).thenReturn(5);

        commandLineInterface.processNextCommand();

        verify(rewardRepository)
                .canAward(Reward.createSmallReward("3 hour nap"), pointWallet);
        verify(out)
                .append("You do not have enough points for this award. :(\n");
        verify(out).flush();
    }
}