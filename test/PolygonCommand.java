public class PolygonCommand extends Command {
    private Polygon polygon;
    private Model model;

    public PolygonCommand(Polygon polygon, Model model) {
        this.polygon = polygon;
        this.model = model;
    }

    @Override
    public void execute() {
        // Add the polygon to the model
        model.addItem(polygon);
    }

    @Override
    public boolean undo() {
        // Remove the polygon from the model
        model.removeItem(polygon);
        return true;
    }

    @Override
    public boolean redo() {
        // Re-add the polygon to the model
        model.addItem(polygon);
        return true;
    }
}
