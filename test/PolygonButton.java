import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class PolygonButton extends JButton {
    private UndoManager undoManager;
    private View view;
    private JPanel drawingPanel;
    private List<Point> points;  // List of points for the polygon
    private int clickCount;

    public PolygonButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Draw Polygon");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        this.points = new ArrayList<>();  // Initialize the points list
        this.clickCount = 0;

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
        // Debugging: print which button was clicked
        if (button == MouseEvent.BUTTON1) {
            System.out.println("Left button clicked at: " + clickedPoint);
        } else if (button == MouseEvent.BUTTON3) {
            System.out.println("Right button clicked at: " + clickedPoint);
        }

        if (button == MouseEvent.BUTTON1) {
            // Left click behavior
            points.add(clickedPoint);
            view.drawPoint(clickedPoint);  // Draw the point

            if (points.size() > 1) {
                // Draw a line connecting the last point to the current point
                Point lastPoint = points.get(points.size() - 2);
                view.drawLine(lastPoint, clickedPoint);
            }
            clickCount++;
        } else if (button == MouseEvent.BUTTON3) {
            // Right click behavior: Close the polygon
            if (points.size() > 1) {
                Point firstPoint = points.get(0);
                Point lastPoint = points.get(points.size() - 1);
                view.drawLine(firstPoint, lastPoint);  // Close the polygon
                System.out.println("Polygon closed: " + firstPoint + " to " + lastPoint);
            }
            // After closing the polygon, reset the drawing
            drawingPanel.removeMouseListener(drawingPanel.getMouseListeners()[0]);
            points.clear();  // Clear the points for the next polygon
            System.out.println("Polygon reset and ready for next drawing.");
        }
    }
}
