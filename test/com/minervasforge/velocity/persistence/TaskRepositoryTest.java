package com.minervasforge.velocity.persistence;

import com.minervasforge.velocity.models.Task;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TaskRepositoryTest {
    @Test
    public void shouldStoreAddedTask() {
        TaskRepository taskRepository = taskRepositoryWithTasks("one", "two");

        assertThat(taskRepository.getAll(), is(asList(new Task("one"), new Task("two"))));
    }

    @Test
    public void shouldDeleteCompletedTask() {
        TaskRepository taskRepository = taskRepositoryWithTasks("one", "two");

        taskRepository.complete(new Task("one"));

        assertThat(taskRepository.getAll(), is(asList(new Task("two"))));
    }

    @Test
    public void shouldTestEqualityOfTaskRepositories(){
        TaskRepository taskRepository = taskRepositoryWithTasks("one", "two");
        TaskRepository taskRepository2 = taskRepositoryWithTasks("one", "two");

        assertThat(taskRepository, is(taskRepository2));
    }

    private TaskRepository taskRepositoryWithTasks(String... tasks) {
        TaskRepository taskRepository = new TaskRepository();
        for (String task : tasks) {
            taskRepository.add(new Task(task));
        }
        return taskRepository;
    }
}
