package com.minervasforge.velocity;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

public class CommandLineInterface {
    private boolean quitFlag = false;
    private PrintWriter out;
    private CommandReader commandReader;
    private TaskRepository taskRepository;
    private PointWallet pointWallet;
    private Persister persister;
    private SkillRepository skillRepository;
    private RewardRepository rewardRepository;

    public CommandLineInterface(CommandReader commandReader,
                                PrintWriter out,
                                TaskRepository taskRepository,
                                SkillRepository skillRepository,
                                RewardRepository rewardRepository,
                                PointWallet pointWallet,
                                Persister persister) {
        this.out = out;
        this.commandReader = commandReader;
        this.taskRepository = taskRepository;
        this.skillRepository = skillRepository;
        this.rewardRepository = rewardRepository;
        this.pointWallet = pointWallet;
        this.persister = persister;
    }

    public void processNextCommand() throws IOException {
        Command command = commandReader.nextCommand();

        if ("help".equals(command.getCommandName()))    {displayListOfValidCommands();}
        else if ("add task".equals(command.getCommandName())){addTaskCommand(command);}
        else if ("ts".equals(command.getCommandName())) {displayAllTasksCommand();}
        else if ("c".equals(command.getCommandName()))  {completeTaskCommand(command);}
        else if ("add skill".equals(command.getCommandName())) {addSkillCommand(command);}
        else if ("ss".equals(command.getCommandName())) {displayListOfSkillsCommand();}
        else if ("add reward".equals(command.getCommandName())) {addRewardCommand(command);}
        else if ("rs".equals(command.getCommandName())) {displayListOfRewardsCommand();}
        else if ("get".equals(command.getCommandName())) {awardUserCommand(command);}
        else if ("ps".equals(command.getCommandName()))  {displayTotalPointsCommand();}
        else if ("q".equals(command.getCommandName()))  {quitCommand();}
        else {askUserToEnterValidCommand();}

    }

    private void awardUserCommand(Command command) {
        Reward reward = rewardRepository.getAll().get(parseInt(command.getArguments()) - 1);
        if (rewardRepository.canAward(reward, pointWallet)) {
            rewardRepository.award(reward, pointWallet);
        } else {
            out.append("You do not have enough points for this award. :(\n");
            out.flush();
        }
    }

    private void addRewardCommand(Command command) {
        String arguments = command.getArguments();
        rewardRepository.add(Reward.createSmallReward(arguments));
    }

    private void addSkillCommand(Command command) {
        String arguments = command.getArguments();
        skillRepository.add(new Skill(arguments));
    }

    private void displayListOfSkillsCommand() {
        out.append("Skills:\n");
        for(Skill skill : skillRepository.getAll()) {
            out.append(skill.getName() + "\n");
        }
        out.flush();
    }

    private void displayListOfValidCommands() {
        out.append("Valid Commands:\n"
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
        out.flush();
    }

    private void askUserToEnterValidCommand() {
        out.append("Please use a valid command. (enter \"help\" to see the list of commands)\n");
        out.flush();
    }

    private void displayAllTasksCommand() {
        int taskNumber = 1;

        out.append("Tasks:\n");
        for (Task task : taskRepository.getAll()) {
            out.append(format("%d. %s\n", taskNumber++, task.getTitle(), "\n"));
        }
        out.flush();
    }

    private void displayListOfRewardsCommand() {
        int rewardNumber = 1;

        out.append("Rewards:\n");
        for (Reward reward : rewardRepository.getAll()) {
            out.append(format("%d. %s\n", rewardNumber++, reward.getTitle(), "\n"));
        }
        out.flush();
    }

    private void displayTotalPointsCommand() {
        out.append("You have " + pointWallet.getTotalPoints() + " points\n");
        out.flush();
    }

    private void quitCommand() throws IOException {
        storeRepositories();
        quitFlag = true;
    }

    private void completeTaskCommand(Command command) {
        Task task = taskRepository.getAll().get(parseInt(command.getArguments()) - 1);
        taskRepository.complete(task);
        pointWallet.awardPoints();
        out.append("You are awarded 5 points\n");
        out.flush();
    }

    private void addTaskCommand(Command command) {
        String arguments = command.getArguments();
        taskRepository.add(new Task(arguments));
    }

    public void loadRepositories(ObjectInputStream dataForLoading) {
        try {
            taskRepository = (TaskRepository) persister.load(dataForLoading);
            pointWallet = (PointWallet) persister.load(dataForLoading);
            rewardRepository = (RewardRepository) persister.load(dataForLoading);
            skillRepository = (SkillRepository) persister.load(dataForLoading);
        } catch (IOException e) {
            initiateNewRepositories();
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found" + e.getMessage());
            initiateNewRepositories();
        } catch (IllegalAccessException e) {
            System.out.println("Illegal Access Exception" + e.getMessage());
            initiateNewRepositories();
        } catch (InstantiationException e) {
            System.out.println("Instantiation Exception" + e.getMessage());
            initiateNewRepositories();
        }
    }


    private void initiateNewRepositories() {
        taskRepository = new TaskRepository();
        pointWallet = new PointWallet();
        rewardRepository = new RewardRepository();
        skillRepository = new SkillRepository();
    }

    private void storeRepositories() throws IOException {
        persister.store(taskRepository, pointWallet, rewardRepository, skillRepository);
    }

    public boolean userWantsToQuit() {
        return quitFlag;
    }
}
