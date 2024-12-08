public class TriangleCommand extends Command {
    private Triangle triangle;
    private Model model;

    public TriangleCommand(Triangle triangle, Model model) {
        this.triangle = triangle;
        this.model = model;
    }

    @Override
    public void execute() {
        model.addItem(triangle);
    }

    @Override
    public boolean undo() {
        model.removeItem(triangle);
        return true;
    }

    @Override
    public boolean redo() {
        model.addItem(triangle);
        return true;
    }
}