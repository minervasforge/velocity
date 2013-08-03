Feature: Manage Tasks

  Scenario: As a user, I want to see a success message after creating a task
    When I create a task
    Then I should see this message: "Task successfully created!"

  Scenario: As a user, I want to see an error message after using an incomplete command
    When I use the incomplete command: "add task"
    Then I should see this message: "Your command was incomplete."

#  Scenario: As a user, I want to see a success message after completing a task
#    Given I have a task called "Eat some chocolate"
#    When I complete the task "Eat some chocolate"
#    Then I should see this success message: "You have completed the task: Eat some chocolate"
