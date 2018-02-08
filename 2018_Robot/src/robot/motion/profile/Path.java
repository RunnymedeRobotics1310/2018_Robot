package robot.motion.profile;

public class Path {

	public enum Turn {
		LEFT, RIGHT, STRAIGHT;
	}

	double[] coeff;
	double[] firstDir;
	double[] secondDir;
	public double timeMax;

	// all in feet (ft)
	final double MAX_ROBOT_VELOCITY = 6; // ft/s
	final double RADIUS_WHEEL = 0.5;

	private double robotWidth; // width of the robot

	public Path(PathDescriptor pd) {
		coeff = pd.A.solve(pd.b).transpose().getData()[0];

		firstDir = getDerivative(coeff);
		secondDir = getDerivative(firstDir);
		timeMax = pd.pts[pd.pts.length - 1].getX();

//		System.out.println(Arrays.toString(coeff));
	}
	
	
	public Point getPoint(double time) {
		double y = 0;

		for (int degree = coeff.length - 1; degree >= 0; degree--) {
			y += coeff[coeff.length - 1 - degree] * Math.pow(time, degree);
		}

		return new Point(time, y);
	}

	public String toString() {
		String s = "y = ";

		for (int i = 0; i < coeff.length; i++) {
			s += coeff[i] + "x^" + i + " + ";
		}

		return s;
	}

	public double getRadius(double time) {
		return Math.pow(Math.sqrt(1 + Math.pow(getValueAt(firstDir, time), 2)), 3) / getValueAt(secondDir, time);

	}

	// [ 3, 9, 2, 4]
	// 3x^3 9x^2 2x, 4
	private double getValueAt(double[] coeff, double time) {

		double sum = 0.0;

		for (int i = 0; i < coeff.length - 1; i++) {
			sum += Math.pow(time, coeff.length - i - 1) * coeff[i];
		}

		return sum;
	}

	private double[] getDerivative(double[] coeff) {
		// 3rd 2nd 1st none
		// [3, 0, 5, 9]
		double derivative[] = new double[coeff.length - 1];
		for (int i = 0; i < coeff.length - 1; i++) {
			derivative[i] = coeff[i] * (coeff.length - i - 1);
		}

		return derivative;
	}

	/**
	 * Get the outer radius
	 * 
	 * @return
	 */
	public double getOuterRadius(double time) {
		return getRadius(time) + 1 / 2 * robotWidth;
	}

	/**
	 * Get the inner radius
	 * 
	 * @return
	 */
	public double getInnerRadius(double time) {
		return getRadius(time) - 1 / 2 * robotWidth;
	}

	/**
	 * Calculate the inner velocity given the velocity
	 * 
	 * @param velocity
	 * @return
	 */
	public double getInnerVelocity(double time, double velocity) {

		// x = inner velocity
		// r1 = inner radius
		// r2 = outer radius
		// V = 1/2 x + 1/2 (r1/r2 * x);
		// x = 2V / (1 + r1/r2)
		double innerRadius = getInnerRadius(time);
		double outerRadius = getOuterRadius(time);

		return (2 * velocity * (innerRadius / outerRadius)) / (1 + innerRadius / outerRadius);
	}

	/**
	 * Calculate the outer velocity
	 * 
	 * @param velocity
	 * @return
	 */
	public double getOuterVelocity(double time, double velocity) {
		double innerRadius = getInnerRadius(time);
		double outerRadius = getOuterRadius(time);

		return (2 * velocity * (outerRadius / innerRadius)) / (1 + outerRadius / innerRadius);
	}

	public double getOmega(double velocity) {
		return Math.round(velocity / (2 * Math.PI * RADIUS_WHEEL));
	}

	public Turn getTurnDirection(double time) {
		double dir = getValueAt(secondDir, time);

		if (dir > 0) {
			return Turn.LEFT;
		} else if (dir < 0) {
			return Turn.RIGHT;
		}

		return Turn.STRAIGHT;

	}

	public double getLeftMotorSpeed(double time, double velocity) {
		Turn t = getTurnDirection(time);

		System.out.print("Turn direction L: " + t);
		
		switch (t) {
		case LEFT:
			return getMotorSpeed(getInnerVelocity(time, velocity));
		case RIGHT:
			return getMotorSpeed(getOuterVelocity(time, velocity));
		case STRAIGHT:
			return getMotorSpeed(getInnerVelocity(time, velocity));
		default:
			return 0.0;
		}
		
	}
	
	public double getRightMotorSpeed(double time, double velocity) {
		Turn t = getTurnDirection(time);
		System.out.print("Turn direction R: " + t);

		switch (t) {
		case LEFT:
			return getMotorSpeed(getOuterVelocity(time, velocity));
		case RIGHT:
			return getMotorSpeed(getInnerVelocity(time, velocity));
		case STRAIGHT:
			return getMotorSpeed(getInnerVelocity(time, velocity));
		default:
			return 0.0;
		}
		
	}

	public double getMotorSpeed(double velocity) {
		return getOmega(velocity) / getOmega(MAX_ROBOT_VELOCITY);
	}
}
