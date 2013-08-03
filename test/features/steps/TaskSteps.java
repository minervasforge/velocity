package features.steps;

import com.minervasforge.velocity.persistence.TaskRepository;
import com.minervasforge.velocity.view.Command;
import com.minervasforge.velocity.view.CommandLineInterface;
import com.minervasforge.velocity.view.CommandReader;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.mockito.Mock;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TaskSteps {

    @Mock
    private CommandReader commandReader;

    @Mock
    private PrintWriter out;
    private CommandLineInterface commandLineInterface;

    @Before
    public void setup() {
        initMocks(this);
        commandLineInterface = new CommandLineInterface(commandReader, out, new TaskRepository(), null, null, null, null);
    }

    @Given("^I create a task$")
    public void I_create_a_task() throws IOException {
        when(commandReader.nextCommand()).thenReturn(new Command("add task", "grab some jeans"));

        commandLineInterface.processNextCommand();
    }

    @Then("^I should see this message: \\\"([^\\\"]*)\\\"$")
    public void I_should_see_this_message(String message) throws Throwable {
        verify(out).append(message);
        verify(out).flush();
    }

    @When("^I use the incomplete command: \\\"([^\\\"]*)\\\"$")
    public void I_use_the_incomplete_command(String incompleteCommand) throws Throwable {
        when(commandReader.nextCommand()).thenReturn(new Command (incompleteCommand, ""));
        commandLineInterface.processNextCommand();
    }
}
