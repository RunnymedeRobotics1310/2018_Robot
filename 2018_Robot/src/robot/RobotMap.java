package robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	public static int LEFT_MOTOR_PWM_PORT  = 0;
	public static int RIGHT_MOTOR_PWM_PORT = 1;
	public static int LEFT_FOLLOWER_PWM_PORT = 2;
	public static int RIGHT_FOLLOWER_PWM_PORT = 3;

	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	public static int LEFT_MOTOR_CAN_ADDRESS  = 0;
	public static int RIGHT_MOTOR_CAN_ADDRESS = 1;
	public static int LEFT_FOLLOWER_CAN_ADDRESS = 2;
	public static int RIGHT_FOLLOWER_CAN_ADDRESS = 3;
	
	public static int ULTRASONIC_SENSOR_ANALOG_PORT = 1;
}
