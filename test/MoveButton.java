import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MoveButton extends JButton implements ActionListener {
    private UndoManager undoManager;
    private View view;
    private JPanel drawingPanel; // Add this line to define drawingPanel

    public MoveButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Move");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel; // Initialize drawingPanel
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent event) { 
        view.setCursor(new Cursor(Cursor.MOVE_CURSOR)); 
        drawingPanel.addMouseListener(new MouseAdapter() {
            private Point startPoint;

            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                Point endPoint = e.getPoint();
                int deltaX = endPoint.x - startPoint.x;
                int deltaY = endPoint.y - startPoint.y;
                MoveCommand moveCommand = new MoveCommand(deltaX, deltaY);
                undoManager.beginCommand(moveCommand);
                undoManager.endCommand(moveCommand);
                drawingPanel.removeMouseListener(this);
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
}