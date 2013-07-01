package com.minervasforge.velocity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements Serializable {
    private List<Task> taskLists = new ArrayList<Task>();

    public TaskRepository() {
    }

    public void add(Task task) {
        taskLists.add(task);
    }

    public List<Task> getAll() {
        return taskLists;
    }

    public void complete(Task task) {
        taskLists.remove(task);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
