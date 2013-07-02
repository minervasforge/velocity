Feature: User can maintain a list of tasks

Scenario: User can create a task
  Given an empty ask list
  When a user creates a task with the following information:
    | Title                             |
    | grab her favorite Scottish whisky |
  Then the app will say, "Created task: 'grab her favorite Scottish whisky'"
