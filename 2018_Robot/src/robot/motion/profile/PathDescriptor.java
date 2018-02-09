package robot.motion.profile;

public class PathDescriptor {

    Point[] pts;
    int polyOrder;

    public Matrix A, b;

    public PathDescriptor(Point[] pts, int order) {
        this.pts = pts;
        this.polyOrder = order;

        getMatrix();
    }

    private void getMatrix() {

        double[][] A = new double[polyOrder + 1][polyOrder + 1];
        double[] b = new double[polyOrder + 1];

        //for each partial derivative...
        for (int diffOrder = 0; diffOrder <= polyOrder; diffOrder++) {
            //add up the resulting coefficients associated with each point.
            for (int pt = 0; pt < pts.length; pt++) {

                for (int k = 0; k <= polyOrder; k++) {
                    A[diffOrder][k] += 2 * Math.pow(pts[pt].getX(), diffOrder + k);
                }


                b[diffOrder] += 2 * Math.pow(pts[pt].getX(), diffOrder) * pts[pt].getY();
            }
        }

        this.A = new Matrix(A);
        this.b = new Matrix(new double[][]{b}).transpose();

//        this.A.show();
//        System.out.println();
//        this.b.show();;
    }

}
