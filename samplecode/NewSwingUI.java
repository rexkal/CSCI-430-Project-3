import java.awt.*;

public class NewSwingUI implements UIContext {
    private Graphics graphics;
    private static NewSwingUI swingUI;

    private NewSwingUI() {
        // Singleton constructor
    }

    public static NewSwingUI getInstance() {
        if (swingUI == null) {
            swingUI = new NewSwingUI();
        }
        return swingUI;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void drawLabel(String s, Point p) {
        if (p != null) {
            if (s != null) {
                graphics.drawString(s, (int) p.getX(), (int) p.getY());
            }
        }
        int length = graphics.getFontMetrics().stringWidth(s);
        graphics.drawString("_", (int) p.getX() + length, (int) p.getY());
    }

    @Override
    public void drawLine(Point point1, Point point2) {
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        if (point1 != null) {
            i1 = Math.round((float) (point1.getX()));
            i2 = Math.round((float) (point1.getY()));
            if (point2 != null) {
                i3 = Math.round((float) (point2.getX()));
                i4 = Math.round((float) (point2.getY()));
            } else {
                i3 = i1;
                i4 = i2;
            }
            graphics.drawLine(i1, i2, i3, i4);
        }
    }

    @Override
    public void drawTriangle(Point p1, Point p2, Point p3) {
        if (graphics != null && p1 != null && p2 != null && p3 != null) {
            graphics.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
            graphics.drawLine((int) p2.getX(), (int) p2.getY(), (int) p3.getX(), (int) p3.getY());
            graphics.drawLine((int) p3.getX(), (int) p3.getY(), (int) p1.getX(), (int) p1.getY());
        }
    }
}
