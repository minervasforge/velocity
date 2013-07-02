package com.minervasforge.velocity.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.BufferedReader;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommandReaderTest{
    @Mock
    private BufferedReader bufferedReader;

    @InjectMocks
    private CommandReader commandReader;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldCreateAddCommand() throws IOException {
        when(bufferedReader.readLine()).thenReturn("add task new task");

        Command command = commandReader.nextCommand();

        assertThat(command, is(new Command("add task", "new task")));
    }

    @Test
    public void shouldParseNonAddCommand() throws IOException {
        when(bufferedReader.readLine()).thenReturn("ts");

        Command command = commandReader.nextCommand();

        assertThat(command, is(new Command("ts")));
    }

    @Test
    public void shouldReturnEmptyCommandIfReaderThrowsException() throws IOException {
        when(bufferedReader.readLine()).thenThrow(IOException.class);

        assertThat(commandReader.nextCommand(), is(new Command("")));
    }
}
