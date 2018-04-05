package robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {


	//******************************************
	// Speed Controllers (CAN addresses)
	//******************************************
	public static final int LEFT_DRIVE_MOTOR_CAN_ADDRESS;          // TALON_SRX
	public static final int RIGHT_DRIVE_MOTOR_CAN_ADDRESS;         // TALON_SRX

	public static final int LEFT_DRIVE_FOLLOWER_CAN_ADDRESS;       // VICTOR_SPX
	public static final int RIGHT_DRIVE_FOLLOWER_CAN_ADDRESS;      // VICTOR_SPX
	
	public static int ELEVATOR_MOTOR_CAN_ADDRESS            = 4;  // TALON_SRX
	public static int ELEVATOR_MOTOR_FOLLOWER_CAN_ADDRESS  =  5;  // VICTOR_SPX
	
	// Roller: 	to suck in the cube
	// Lift: 	to lift the cube
	public static int INTAKE_ROLLER_MOTOR_CAN_ADDRESS      =  6;  // VICTOR_SPX
	public static int INTAKE_TILT_MOTOR_CAN_ADDRESS        =  7;  // VICTOR_SPX
	
	
	//******************************************
	// DIO Ports
	//******************************************
	public static int ELEVATOR_BOTTOM_LIMIT_DIO_PORT    = 0;
	public static int ELEVATOR_TOP_LIMIT_DIO_PORT       = 1;

	public static int LEFT_CUBE_DETECT_DIO_PORT         = 2;
	public static int RIGHT_CUBE_DETECT_DIO_PORT        = 3;
	
	//******************************************
	// Pneumatics Ports
	//******************************************
	public static int SHIFTER_PNEUMATIC_PORT = 0;
	public static int INTAKE_CLAW_PNEUMATIC_PORT = 3;
	public static int INTAKE_CLAW_PNEUMATIC_PORT2 = 4;
	
	
	static {
		
		switch (RobotConst.robot) {

		case 1310:
			LEFT_DRIVE_MOTOR_CAN_ADDRESS          = 0;  // TALON_SRX
			RIGHT_DRIVE_MOTOR_CAN_ADDRESS         = 1;  // TALON_SRX

			LEFT_DRIVE_FOLLOWER_CAN_ADDRESS       = 2;  // VICTOR_SPX
			RIGHT_DRIVE_FOLLOWER_CAN_ADDRESS      = 3;  // VICTOR_SPX
			break;

		case 1311:
			LEFT_DRIVE_MOTOR_CAN_ADDRESS          = 1;  // TALON_SRX
			RIGHT_DRIVE_MOTOR_CAN_ADDRESS         = 0;  // TALON_SRX

			LEFT_DRIVE_FOLLOWER_CAN_ADDRESS       = 3;  // VICTOR_SPX
			RIGHT_DRIVE_FOLLOWER_CAN_ADDRESS      = 2;  // VICTOR_SPX
			break;
			
		default:
			LEFT_DRIVE_MOTOR_CAN_ADDRESS          = 0;  // TALON_SRX
			RIGHT_DRIVE_MOTOR_CAN_ADDRESS         = 1;  // TALON_SRX

			LEFT_DRIVE_FOLLOWER_CAN_ADDRESS       = 2;  // VICTOR_SPX
			RIGHT_DRIVE_FOLLOWER_CAN_ADDRESS      = 3;  // VICTOR_SPX
			break;

		}
	}
}
