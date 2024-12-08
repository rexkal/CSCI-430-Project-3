import java.awt.*;

public interface UIContext {
    // Draw a line between two points
    public abstract void drawLine(Point point1, Point point2);

    // Draw a label (string) at a specific point
    public abstract void drawLabel(String s, Point p);

    // Draw a triangle given its three vertices
    public abstract void drawTriangle(Point p1, Point p2, Point p3);
}
