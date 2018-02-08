package robot.motion.profile;

public class Main {

    public static void main(String[] args) {
        
        //Now we’ll try some ‘noisy’ data
        double[] xPts = new double[]{0, 0.5, 1, 1.5, 2, 2.5};
        double[] yPts = {0.0674, -0.9156, 1.6253, 3.0377, 3.3535, 7.9409};


        PathDescriptor testPath = new PathDescriptor(Point.getPointSet(6, xPts, yPts), 10);
        System.out.println();
        Path path = new Path(testPath);
        System.out.println(path);
    }
}
