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
	public static int LEFT_DRIVE_MOTOR_CAN_ADDRESS          = 0;  // TALON_SRX
	public static int RIGHT_DRIVE_MOTOR_CAN_ADDRESS         = 1;  // TALON_SRX

	public static int LEFT_DRIVE_FOLLOWER_CAN_ADDRESS       = 2;  // VICTOR_SPX
	public static int RIGHT_DRIVE_FOLLOWER_CAN_ADDRESS      = 3;  // VICTOR_SPX
	
	public static int ELEVATOR_MOTOR_CAN_ADDRESS            = 4;  // TALON_SRX
	
	public static int LEFT_FRONT_RAMP_MOTOR_CAN_ADDRESS    =  5;  // VICTOR_SPX
	public static int LEFT_REAR_RAMP_MOTOR_CAN_ADDRESS     =  6;  // VICTOR_SPX
	public static int RIGHT_FRONT_RAMP_MOTOR_CAN_ADDRESS   =  7;  // VICTOR_SPX
	public static int RIGHT_REAR_RAMP_MOTOR_CAN_ADDRESS    =  8;  // VICTOR_SPX
	
	// The motor that pivots to open/close the claw
	public static int LEFT_INTAKE_ARM_MOTOR_CAN_ADDRESS    =  9;  // VICTOR_SPX
	public static int RIGHT_INTAKE_ARM_MOTOR_CAN_ADDRESS   = 10;  // VICTOR_SPX
	
	// The claw that pulls the cube in using the arms
	public static int RIGHT_INTAKE_CLAW_WHEELS_CAN_ADDRESS = 12;  // VICTOR_SPX
	public static int LEFT_INTAKE_CLAW_WHEELS_CAN_ADDRESS  = 13;  // VICTOR_SPX
	
	// The motor that slides the cube into the elevator
	public static int LEFT_INTAKE_RAIL_WHEELS_CAN_ADDRESS  = 11;  // VICTOR_SPX
	public static int RIGHT_INTAKE_RAIL_WHEELS_CAN_ADDRESS = 14;  // VICTOR_SPX

	
	//******************************************
	// DIO Ports
	//******************************************
	public static int LEFT_BOTTOM_LIMIT_DIO_PORT        = 0;
	public static int RIGHT_BOTTOM_LIMIT_DIO_PORT       = 1;
	
	public static int ELEVATOR_BOTTOM_LIMIT_DIO_PORT    = 2;
	public static int ELEVATOR_TOP_LIMIT_DIO_PORT       = 3;

	public static int LEFT_CLAW_RETRACT_LIMIT_DIO_PORT  = 4;
	public static int RIGHT_CLAW_RETRACT_LIMIT_DIO_PORT = 5;
	
	public static int CUBE_DETECT_LIMIT_DIO_PORT        = 6;
	public static int CUBE_INSIDE_LIMIT_DIO_PORT        = 7;
	public static int CUBE_LOADED_LIMIT_DIO_PORT        = 8;
	
	public static int LEFT_CLAW_ENCODER_A_DIO_PORT      = 9; 
	public static int LEFT_CLAW_ENCODER_B_DIO_PORT      = 10;
	public static int RIGHT_CLAW_ENCODER_A_DIO_PORT     = 11;
	public static int RIGHT_CLAW_ENCODER_B_DIO_PORT     = 12;
	
	//******************************************
	// Analog Input Ports
	//******************************************
	public static int ULTRASONIC_SENSOR_ANALOG_PORT = 1;
	
	//******************************************
	// Pneumatics Ports
	//******************************************
	public static int SHIFTER_PNEUMATIC_PORT = 0;
	public static int RAMP_RELEASE = 1;
}
