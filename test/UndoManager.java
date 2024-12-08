import java.util.*;

public class UndoManager {
    private static Model model;
    private Stack<Command> history; // Use generic types for better type safety
    private Stack<Command> redoStack; 
    private Command currentCommand;

    public UndoManager() {
        history = new Stack<>();
        redoStack = new Stack<>();
    }

    public static void setModel(Model model) {
        UndoManager.model = model;
    }

    /**
     * Executes a command and adds it to the history stack.
     */
    public void executeCommand(Command command) {
        if (command != null) {
            command.execute();
            history.push(command);
            redoStack.clear(); // Clear the redo stack because history has changed
            model.setChanged(); // Notify the model to refresh the view
        }
    }

    /**
     * Begins a command and clears the redo stack.
     */
    public void beginCommand(Command command) {
        if (currentCommand != null) {
            if (currentCommand.end()) {
                history.push(currentCommand);
            }
        }
        currentCommand = command;
        redoStack.clear();
        command.execute();
    }

    /**
     * Ends the current command and pushes it to the history stack.
     */
    public void endCommand(Command command) {
        if (command != null) {
            command.end();
            history.push(command);
            currentCommand = null;
            model.setChanged();
        }
    }

    /**
     * Cancels the current command without adding it to the history stack.
     */
    public void cancelCommand() {
        currentCommand = null;
        model.setChanged();
    }

    /**
     * Undoes the last executed command.
     */
    public void undo() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            if (command.undo()) {
                redoStack.push(command);
            }
            model.setChanged(); // Refresh view after undo
        }
    }

    /**
     * Redoes the last undone command.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            if (command.redo()) {
                history.push(command);
            }
            model.setChanged(); // Refresh view after redo
        }
    }
}
