import java.awt.*;

public class Triangle extends Item {
    private Point p1, p2, p3;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Point[] getVertices() {
        return new Point[]{p1, p2, p3};
    }

    
    public void render(UIContext context) {
        context.drawTriangle(p1, p2, p3);
    }

    public boolean includes(Point point) {
        double area = calculateArea(p1, p2, p3);
        double area1 = calculateArea(point, p2, p3);
        double area2 = calculateArea(p1, point, p3);
        double area3 = calculateArea(p1, p2, point);

        return Math.abs((area1 + area2 + area3) - area) < 0.001;
    }

    private double calculateArea(Point a, Point b, Point c) {
        return Math.abs(a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) / 2.0;
    }

    
    public void translate(int dx, int dy) {
        p1.translate(dx, dy);
        p2.translate(dx, dy);
        p3.translate(dx, dy);
    }
}