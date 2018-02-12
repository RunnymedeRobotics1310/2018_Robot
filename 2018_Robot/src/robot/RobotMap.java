package robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {


	// Speed Controllers (CAN addresses)
	public static int LEFT_DRIVE_MOTOR_CAN_ADDRESS     = 0;
	public static int RIGHT_DRIVE_MOTOR_CAN_ADDRESS    = 1;

	public static int LEFT_DRIVE_FOLLOWER_CAN_ADDRESS  = 2;
	public static int RIGHT_DRIVE_FOLLOWER_CAN_ADDRESS = 3;
	
	public static int ELEVATOR_MOTOR_CAN_ADDRESS = 0;
	
	public static int LEFT_INTAKE_ARM_MOTOR_CAN_ADDRESS    = 5;
	public static int RIGHT_INTAKE_ARM_MOTOR_CAN_ADDRESS   = 6;
	public static int LEFT_INTAKE_WHEEL_MOTOR_CAN_ADDRESS  = 7;
	public static int RIGHT_INTAKE_WHEEL_MOTOR_CAN_ADDRESS = 8;
			
	public static int LEFT_FRONT_RAMP_MOTOR_CAN_ADDRESS  =  9;
	public static int LEFT_REAR_RAMP_MOTOR_CAN_ADDRESS   = 10;
	public static int RIGHT_FRONT_RAMP_MOTOR_CAN_ADDRESS = 11;
	public static int RIGHT_REAR_RAMP_MOTOR_CAN_ADDRESS  = 12;
	
	// Limit Switches (RoboRio DIO ports)
	public static int LEFT_TOP_LIMIT_DIO_PORT     = 9;
	public static int LEFT_BOTTOM_LIMIT_DIO_PORT  = 1;
	public static int RIGHT_TOP_LIMIT_DIO_PORT    = 2;
	public static int RIGHT_BOTTOM_LIMIT_DIO_PORT = 3;
	
	public static int ELEVATOR_BOTTOM_LIMIT_DIO_PORT = 4;
	public static int ELEVATOR_TOP_LIMIT_DIO_PORT = 5;
	public static int ELEVATOR_MAXHEIGHT_LIMIT_DIO_PORT = 6;

	// For Intake
	public static int CUBE_DETECT_LIMIT_DIO_PORT = 0;
	public static int CUBE_INSIDE_LIMIT_DIO_PORT = 1;
	public static int CUBE_LOADED_LIMIT_DIO_PORT = 2;
	
	// Ultrasonic Sensor Analog Port
	public static int ULTRASONIC_SENSOR_ANALOG_PORT = 1;
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	public static int LEFT_MOTOR_CAN_ADDRESS  = 0;
	public static int RIGHT_MOTOR_CAN_ADDRESS = 1;
	public static int LEFT_FOLLOWER_CAN_ADDRESS = 2;
	public static int RIGHT_FOLLOWER_CAN_ADDRESS = 3;
}
