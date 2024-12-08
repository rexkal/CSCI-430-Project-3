import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TriangleButton extends JButton {
    private UndoManager undoManager;
    private View view;
    private JPanel drawingPanel;
    private Point p1, p2, p3;
    private int clickCount;

    public TriangleButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Draw Triangle");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        this.clickCount = 0;

        addActionListener(e -> enableTriangleDrawing());
    }

    private void enableTriangleDrawing() {
        MouseAdapter triangleMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getPoint());
            }
        };
        drawingPanel.addMouseListener(triangleMouseAdapter);
    }

    private void handleMouseClick(Point clickedPoint) {
        if (clickCount == 0) {
            p1 = clickedPoint;
            view.drawPoint(p1);
            clickCount++;
        } else if (clickCount == 1) {
            p2 = clickedPoint;
            view.drawLine(p1, p2);
            clickCount++;
        } else if (clickCount == 2) {
            p3 = clickedPoint;
            Triangle triangle = new Triangle(p1, p2, p3);
            view.drawTriangle(triangle);
            undoManager.executeCommand(new TriangleCommand(triangle, view.getModel()));
            clickCount = 0;
        }
    }
}