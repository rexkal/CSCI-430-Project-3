import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Polygon extends Item {
    private List<Point> points;

    public Polygon() {
        this.points = new ArrayList<>();
    }

    // Add a point to the polygon
    public void addPoint(Point point) {
        points.add(point);
    }

    // Close the polygon by connecting the last point to the first
    public void completePolygon() {
        if (points.size() > 2) {
            points.add(points.get(0)); // Close the shape by connecting last to first
        }
    }

    public List<Point> getVertices() {
        return points;
    }

    @Override
    public void render(UIContext context) {
        if (points.size() < 2) return;

        for (int i = 0; i < points.size() - 1; i++) {
            context.drawLine(points.get(i), points.get(i + 1));
        }
    }

    @Override
    public boolean includes(Point point) {
        int crossings = 0;

        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);

            if (isEdgeCrossing(p1, p2, point)) {
                crossings++;
            }
        }

        // If crossings are odd, the point is inside the polygon
        return crossings % 2 == 1;
    }

    private boolean isEdgeCrossing(Point p1, Point p2, Point point) {
        if (p1.y > p2.y) {
            Point temp = p1;
            p1 = p2;
            p2 = temp;
        }

        if (point.y <= p1.y || point.y > p2.y) return false;

        double slope = (double) (p2.x - p1.x) / (p2.y - p1.y);
        double xCross = p1.x + (point.y - p1.y) * slope;

        return point.x < xCross;
    }

    @Override
    public void translate(int dx, int dy) {
        for (Point point : points) {
            point.translate(dx, dy);
        }
    }
}
