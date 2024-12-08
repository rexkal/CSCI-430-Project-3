import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PolygonButton extends JButton {
    private UndoManager undoManager;
    private View view;
    private JPanel drawingPanel;
    private List<Point> points;  // List of points for the polygon

    public PolygonButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Draw Polygon");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        this.points = new ArrayList<>();  // Initialize the points list

        addActionListener(e -> enablePolygonDrawing());
    }

    private void enablePolygonDrawing() {
        MouseAdapter polygonMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getPoint(), e.getButton());
            }
        };
        drawingPanel.addMouseListener(polygonMouseAdapter);
    }

    private void handleMouseClick(Point clickedPoint, int button) {
        if (button == MouseEvent.BUTTON1) {
            points.add(clickedPoint);
            view.drawPoint(clickedPoint);  // Draw the point

            if (points.size() > 1) {
                // Draw a line connecting the last point to the current point
                Point lastPoint = points.get(points.size() - 2);
                view.drawLine(lastPoint, clickedPoint);
            }
        } else if (button == MouseEvent.BUTTON3) {
            // Right click behavior: Close the polygon
            if (points.size() > 1) {
                Point firstPoint = points.get(0);
                Point lastPoint = points.get(points.size() - 1);
                view.drawLine(firstPoint, lastPoint);  // Close the polygon

                // Create a new Polygon object and add it to the model
                Polygon polygon = new Polygon();
                for (Point point : points) {
                    polygon.addPoint(point);
                }
                polygon.completePolygon(); // Ensure the polygon is closed
                undoManager.executeCommand(new PolygonCommand(polygon, view.getModel()));
            }
            // Do not clear the points; keep the polygon visible
        }
    }
}