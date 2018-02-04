package robot;

public class RobotConst {
	
	// The TorontoCodingCollective framework was developed to run on different
	// robots.
	// Supported robots are 1311 and 1321.
	public static int robot = 1321;

	
	public static final boolean INVERTED = true;
	public static final boolean NOT_INVERTED = false;
	public static final char LEFT = 'L';
	public static final char RIGHT = 'R';

	//*********************************************************
	// Drive Constants
	//*********************************************************
	public static final boolean RIGHT_MOTOR_ORIENTATION;
	public static final boolean LEFT_MOTOR_ORIENTATION;
	
	public static final boolean RIGHT_ENCODER_ORIENTATION;
	public static final boolean LEFT_ENCODER_ORIENTATION;
	
	public static final double MAX_LOW_GEAR_SPEED;
	public static final double MAX_HIGH_GEAR_SPEED;
	
	public static final double DRIVE_GYRO_PID_KP;
	public static final double DRIVE_GYRO_PID_KI;
	
	public static final double DRIVE_SPEED_PID_KP;
	public static final double ENCODER_COUNTS_PER_INCH;

	static {
		
		switch (robot) {

		case 1311:
			RIGHT_MOTOR_ORIENTATION = INVERTED;
			LEFT_MOTOR_ORIENTATION = NOT_INVERTED;
			
			RIGHT_ENCODER_ORIENTATION = NOT_INVERTED;
			LEFT_ENCODER_ORIENTATION = INVERTED;
			
			MAX_LOW_GEAR_SPEED = 365.0;    // Encoder counts/sec
			MAX_HIGH_GEAR_SPEED = 830.0;
			
			DRIVE_GYRO_PID_KP = .1;
			DRIVE_GYRO_PID_KI = 0;
			
			DRIVE_SPEED_PID_KP = 0.75;
			ENCODER_COUNTS_PER_INCH = 51.5;
			break;
			
		case 1321:
		default:
			RIGHT_MOTOR_ORIENTATION = NOT_INVERTED;
			LEFT_MOTOR_ORIENTATION = INVERTED;
			
			RIGHT_ENCODER_ORIENTATION = NOT_INVERTED;
			LEFT_ENCODER_ORIENTATION = INVERTED;
			
			MAX_LOW_GEAR_SPEED = 365.0;    // Encoder counts/sec
			MAX_HIGH_GEAR_SPEED = 830.0;
			
			DRIVE_GYRO_PID_KP = 0.05;
			DRIVE_GYRO_PID_KI = 0.001;
			
			DRIVE_SPEED_PID_KP = 0.75;
			ENCODER_COUNTS_PER_INCH = 51.5;
		
			break;
		}
	}
}
