package robot.motion.profile;

public class Point {

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public String toString() {
        return "(" + this.x + " " + this.y + ")";
    }

    public static Point[] getPointSet(int n, double[] xPts, double[] yPts) {
        Point[] ptSet = new Point[n];

        for (int i = 0; i < n; i++) {
            ptSet[i] = new Point(xPts[i], yPts[i]);
        }

        return ptSet;
    }
}
