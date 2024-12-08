public class MoveCommand extends Command {
    private int deltaX;
    private int deltaY;

    public MoveCommand(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public void execute() {
        model.moveSelectedItems(deltaX, deltaY);
    }

    public boolean undo() {
        model.moveSelectedItems(-deltaX, -deltaY);
        return true;
    }

    public boolean redo() {
        execute();
        return true;
    }
}