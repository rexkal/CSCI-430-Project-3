import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;



class View extends JFrame {
    private UIContext uiContext;
    private JPanel drawingPanel;
    private JPanel buttonPanel;
    private JPanel filePanel;
    private JButton lineButton;
    private JButton deleteButton;
    private JButton labelButton;
    private JButton selectButton;
    private JButton saveButton;
    private JButton openButton;
    private JButton undoButton;
    private JButton redoButton;
    private JButton moveButton;
    private JButton triangleButton; // New button for triangle drawing
    private JButton polygonButton; // Button for polygon drawing

    private static UndoManager undoManager;
    private String fileName;
    private static Model model;

    public UIContext getUI() {
        return uiContext;
    }

    private void setUI(UIContext uiContext) {
        this.uiContext = uiContext;
    }

    public static void setModel(Model model) {
        View.model = model;
    }

    public static void setUndoManager(UndoManager undoManager) {
        View.undoManager = undoManager;
    }

    public Model getModel() {
        return model;
    }

    private class DrawingPanel extends JPanel {
        private MouseListener currentMouseListener;
        private KeyListener currentKeyListener;
        private FocusListener currentFocusListener;

        public DrawingPanel() {
            setLayout(null);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            (NewSwingUI.getInstance()).setGraphics(g);
            g.setColor(Color.BLUE);
            Enumeration enumeration = model.getItems();
            while (enumeration.hasMoreElements()) {
                ((Item) enumeration.nextElement()).render(uiContext);
            }
            g.setColor(Color.RED);
            enumeration = model.getSelectedItems();
            while (enumeration.hasMoreElements()) {
                ((Item) enumeration.nextElement()).render(uiContext);
            }
        }

        public void addMouseListener(MouseListener newListener) {
            removeMouseListener(currentMouseListener);
            currentMouseListener = newListener;
            super.addMouseListener(newListener);
        }

        public void addKeyListener(KeyListener newListener) {
            removeKeyListener(currentKeyListener);
            currentKeyListener = newListener;
            super.addKeyListener(newListener);
        }

        public void addFocusListener(FocusListener newListener) {
            removeFocusListener(currentFocusListener);
            currentFocusListener = newListener;
            super.addFocusListener(newListener);
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        setTitle("Drawing Program 1.1  " + fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public View() {
        super("Drawing Program 1.1  Untitled");
        fileName = null;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });

        this.setUI(NewSwingUI.getInstance());
        drawingPanel = new DrawingPanel();
        buttonPanel = new JPanel();
        Container contentpane = getContentPane();
        contentpane.add(buttonPanel, "North");
        contentpane.add(drawingPanel);

        // Initialize buttons
        lineButton = new LineButton(undoManager, this, drawingPanel);
        labelButton = new LabelButton(undoManager, this, drawingPanel);
        selectButton = new SelectButton(undoManager, this, drawingPanel);
        deleteButton = new DeleteButton(undoManager);
        saveButton = new SaveButton(undoManager, this);
        openButton = new OpenButton(undoManager, this);
        undoButton = new UndoButton(undoManager);
        redoButton = new RedoButton(undoManager);
        moveButton = new MoveButton(undoManager, this, drawingPanel);
        triangleButton = new TriangleButton(undoManager, this, drawingPanel); // Add triangle button
        polygonButton = new PolygonButton(undoManager, this, drawingPanel); // Polygon button


        // Add buttons to panel
        buttonPanel.add(lineButton);
        buttonPanel.add(labelButton);
        buttonPanel.add(selectButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(openButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(moveButton);
        buttonPanel.add(triangleButton); // Add triangle button to panel
        buttonPanel.add(polygonButton); // Add polygon button to panel


        this.setSize(1000, 800);
    }

    public void refresh() {
        drawingPanel.repaint();
    }

    public void drawPoint(Point p) {
        Graphics g = drawingPanel.getGraphics();
        g.fillOval(p.x - 3, p.y - 3, 6, 6);
    }

    public void drawLine(Point p1, Point p2) {
        Graphics g = drawingPanel.getGraphics();
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    public void drawTriangle(Triangle triangle) {
        Graphics g = drawingPanel.getGraphics();
        Point[] vertices = triangle.getVertices();
        g.drawLine(vertices[0].x, vertices[0].y, vertices[1].x, vertices[1].y);
        g.drawLine(vertices[1].x, vertices[1].y, vertices[2].x, vertices[2].y);
        g.drawLine(vertices[2].x, vertices[2].y, vertices[0].x, vertices[0].y);
    }

    public void drawPolygon(Polygon polygon) {
        Graphics g = drawingPanel.getGraphics();
        List<Point> vertices = polygon.getVertices();
        for (int i = 0; i < vertices.size() - 1; i++) {
            g.drawLine(vertices.get(i).x, vertices.get(i).y, vertices.get(i + 1).x, vertices.get(i + 1).y);
        }
    }
    public static Point mapPoint(Point point) {
        return point;
    }
}
