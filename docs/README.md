# TBC - User Guide

**TBC** is a desktop personal assistant chatbot optimized for fast keyboard-based task and place management through a Command Line Interface (CLI) paired with a graphical user interface (GUI).

![TBC Graphical User Interface](Ui.png)

---

## Table of Contents
- [Quick Start](#quick-start)
- [Command Format Conventions](#command-format-conventions)
- [Task Management Features](#task-management-features)
  - [Adding a To-Do: `todo`](#adding-a-to-do-todo)
  - [Adding a Deadline: `deadline`](#adding-a-deadline-deadline)
  - [Adding an Event: `event`](#adding-an-event-event)
  - [Listing All Tasks: `list`](#listing-all-tasks-list)
  - [Marking a Task as Done: `mark`](#marking-a-task-as-done-mark)
  - [Marking a Task as Incomplete: `unmark`](#marking-a-task-as-incomplete-unmark)
  - [Deleting a Task: `delete`](#deleting-a-task-delete)
  - [Searching Tasks by Keyword: `find`](#searching-tasks-by-keyword-find)
  - [Undoing Previous Action: `undo`](#undoing-previous-action-undo)
- [Place Management Features](#place-management-features)
  - [Adding a Place: `place`](#adding-a-place-place)
  - [Listing All Places: `places`](#listing-all-places-places)
  - [Searching Places: `findplace`](#searching-places-findplace)
  - [Deleting a Place: `deleteplace`](#deleting-a-place-deleteplace)
- [General Features](#general-features)
  - [Exiting the Application: `bye`](#exiting-the-application-bye)
  - [Data Persistence](#data-persistence)
- [Command Summary](#command-summary)

---

## Quick Start

1. Ensure that you have **Java 25** installed on your system.
2. Download the latest `duke.jar` from the releases page.
3. Place `duke.jar` into an empty working folder.
4. Open a terminal, navigate to the folder containing the file, and launch the application:
   ```bash
   java -jar duke.jar
   ```
5. Type your command into the text box and press **Enter** (or click **Send**) to execute it.

---

## Command Format Conventions

* Words in `UPPER_CASE` represent parameters supplied by the user.
  * Example: In `todo DESCRIPTION`, `DESCRIPTION` is a parameter such as `read book`.
* Parameters in square brackets `[ ... ]` are optional.
  * Example: `place NAME [/details DETAILS]`
* Date and time arguments can be formatted as:
  * `yyyy-MM-dd` (e.g. `2026-10-15`)
  * `yyyy-MM-dd HHmm` (e.g. `2026-10-15 1800`)
* The pipe character `|` is reserved for data storage and cannot be used in descriptions, details, or keywords.
* Commands and parameters are separated by spaces. Leading and trailing whitespace are automatically trimmed.
* Item indices (`INDEX`) must be positive integers starting from `1` matching the currently displayed list.

---

## Task Management Features

### Adding a To-Do: `todo`

Adds a simple task without a specific deadline or schedule.

* **Format**: `todo DESCRIPTION`
* **Example**: `todo read textbook chapter 4`
* **Expected Output**:
  ```text
  Got it. I've added this task:
    [T][ ] read textbook chapter 4
  Now you have 1 tasks in the list.
  ```

### Adding a Deadline: `deadline`

Adds a task that must be completed by a specific date or time.

* **Format**: `deadline DESCRIPTION /by DATE_TIME`
* **Parameters**:
  * `DATE_TIME`: Accepts `yyyy-MM-dd` or `yyyy-MM-dd HHmm`.
* **Example**: `deadline submit assignment /by 2026-10-15 1800`
* **Expected Output**:
  ```text
  Got it. I've added this task:
    [D][ ] submit assignment (by: Oct 15 2026, 6:00 PM)
  Now you have 2 tasks in the list.
  ```

### Adding an Event: `event`

Adds a task that spans a start and end date/time. The start time must not be later than the end time.

* **Format**: `event DESCRIPTION /from START_DATE_TIME /to END_DATE_TIME`
* **Parameters**:
  * `START_DATE_TIME` and `END_DATE_TIME`: Accept `yyyy-MM-dd` or `yyyy-MM-dd HHmm`.
* **Example**: `event team sprint planning /from 2026-10-16 1400 /to 2026-10-16 1600`
* **Expected Output**:
  ```text
  Got it. I've added this task:
    [E][ ] team sprint planning (from: Oct 16 2026, 2:00 PM to: Oct 16 2026, 4:00 PM)
  Now you have 3 tasks in the list.
  ```

### Listing All Tasks: `list`

Displays all current tasks stored in your list along with their status, type, and chronological details.

* **Format**: `list`
* **Expected Output**:
  ```text
  Here are the tasks in your list:
  1. [T][ ] read textbook chapter 4
  2. [D][ ] submit assignment (by: Oct 15 2026, 6:00 PM)
  3. [E][ ] team sprint planning (from: Oct 16 2026, 2:00 PM to: Oct 16 2026, 4:00 PM)
  ```

### Marking a Task as Done: `mark`

Marks the task at the specified index as completed (`[X]`).

* **Format**: `mark INDEX`
* **Example**: `mark 1`
* **Expected Output**:
  ```text
  Nice! I've marked this task as done:
    [T][X] read textbook chapter 4
  ```

### Marking a Task as Incomplete: `unmark`

Reverts the task at the specified index back to incomplete (`[ ]`).

* **Format**: `unmark INDEX`
* **Example**: `unmark 1`
* **Expected Output**:
  ```text
  OK, I've marked this task as not done yet:
    [T][ ] read textbook chapter 4
  ```

### Deleting a Task: `delete`

Permanently removes the task at the specified index from the task list.

* **Format**: `delete INDEX`
* **Example**: `delete 2`
* **Expected Output**:
  ```text
  Noted. I've removed this task:
    [D][ ] submit assignment (by: Oct 15 2026, 6:00 PM)
  Now you have 2 tasks in the list.
  ```

### Searching Tasks by Keyword: `find`

Finds and lists all tasks whose description contains the specified search keyword (case-sensitive search).

* **Format**: `find KEYWORD`
* **Example**: `find sprint`
* **Expected Output**:
  ```text
  Here are the matching tasks in your list:
  1. [E][ ] team sprint planning (from: Oct 16 2026, 2:00 PM to: Oct 16 2026, 4:00 PM)
  ```

### Undoing Previous Action: `undo`

Reverts the most recent task list or place list mutation (such as an addition, deletion, mark, or unmark).

* **Format**: `undo`
* **Expected Output**:
  ```text
  Got it. I've undone the previous command.
  Now you have 3 tasks in the list.
  ```