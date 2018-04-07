package com.torontocodingcollective.subsystem;

import com.torontocodingcollective.pid.TGyroPID;
import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.gyro.TGyro;
import com.torontocodingcollective.speedcontroller.TSpeedController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class TGryoDriveSubsystem extends TDriveSubsystem {

	protected TGyro gyro;

	private TGyroPID gyroPid;

	/**
	 * Drive subsystem with left/right drive and gyro.
	 * <p>
	 * The GyroDrive subsystem extends the basic DriveSubsystem and adds has a 
	 * gyro.  To enable a GyroPid controller use {@link #setGyroPidGain(double, double)}
	 * and set the PidGain to a non-zero value, and then use {@link #enableGyroPid()}
	 * 
	 * @param gyro that extends {@link TGyro}
	 * @param leftMotor that extends the {@link TSpeedController}
	 * @param rightMotor that extends {@link TSpeedController}
	 */
	public TGryoDriveSubsystem(
			TGyro gyro,
			TSpeedController leftMotor, 
			TSpeedController rightMotor 
			) {

		this(gyro, leftMotor, rightMotor, 0.0, 0.0);
	}


	/**
	 * Drive subsystem with left/right drive and gyro.
	 * <p>
	 * The GyroDrive subsystem extends the DriveSubsystem and adds has a 
	 * gyro and PID controller to control the angle heading of the robot.
	 * The GyroPID is initialized to disabled.  Use the {@link #enableGyroPid()} 
	 * and {@link #disableGyroPid()} to enable and disable the angle PID.
	 * <p>
	 * @param gyro that extends {@link TGyro}
	 * @param leftMotor that extends the {@link TSpeedController}
	 * @param rightMotor that extends {@link TSpeedController}
	 * @param gyroKP Default Proportional gain for the gyro angle pid.  The 
	 * gyro PID is displayed on the SmartDashboard and can be 
	 * adjusted through that interface
	 * @param gyroKI Default Integral gain for the gyro angle pid.  The 
	 * gyro PID is displayed on the SmartDashboard and can be 
	 * adjusted through that interface
	 */
	public TGryoDriveSubsystem(
			TGyro gyro,
			TSpeedController leftMotor, 
			TSpeedController rightMotor, 
			double gyroKP, double gyroKI
			) {
		super(  leftMotor, 
				rightMotor); 
		
		gyroPid = new TGyroPID(gyroKP, gyroKI);
		this.gyro = gyro;
	}

	/**
	 * Drive subsystem with left/right drive and gyro.
	 * <p>
	 * The GyroDrive subsystem extends the DriveSubsystem and adds has a 
	 * gyro and PID controller to control the angle heading of the robot.
	 * The GyroPID is initialized to disabled.  Use the {@link #enableGyroPid()} 
	 * and {@link #disableGyroPid()} to enable and disable the angle PID.
	 * <p>
	 * The drive subsystem has pids that are initialized to 
	 * disabled.  Use the {@link #enableSpeedPids()} and {@link #disableSpeedPids()}
	 * routines to set the PIDs on and off
	 * 
	 * @param gyro that extends {@link TGyro}
	 * @param leftMotor that extends the {@link TSpeedController}
	 * @param rightMotor that extends {@link TSpeedController}
	 * @param leftEncoder encoder for the left motor
	 * @param rightEncoder encoder for the right motor
	 * @param speedKP Default Proportional gain for the motor speed pid.  The 
	 * speed PIDs are displayed on the SmartDashboard and can be 
	 * adjusted through that interface
	 * @param maxEncoderSpeed the max loaded robot encoder rate used
	 * to normalize the PID input encoder feedback.
	 * @param gyroKP Default Proportional gain for the gyro angle pid.  The 
	 * gyro PID is displayed on the SmartDashboard and can be 
	 * adjusted through that interface
	 * @param gyroKI Default Integral gain for the gyro angle pid.  The 
	 * gyro PID is displayed on the SmartDashboard and can be 
	 * adjusted through that interface
	 */
	public TGryoDriveSubsystem(
			TGyro gyroSensor,
			TSpeedController leftMotor, 
			TSpeedController rightMotor, 
			TEncoder leftEncoder,   
			TEncoder rightEncoder,	   
			double speedKP, double maxEncoderSpeed,
			double gyroKP, double gyroKI
			) {

		super(
				leftMotor, 
				rightMotor, 
				leftEncoder, 
				rightEncoder, 
				speedKP, maxEncoderSpeed);
		
		gyroPid = new TGyroPID(gyroKP, gyroKI);
		this.gyro = gyroSensor;
	}

	/**
	 * Disable the angle PID for the Drive subsystem.
	 * <p>
	 * NOTE: If the angle PIDs is not currently enabled,
	 * this routine has no effect
	 */
	public void disableGyroPid() {
		gyroPid.disable();
	}
	
	/**
	 * Enable the angle PIDs for the Drive subsystem.
	 * <p>
	 * NOTE: If the angle PID is already enabled,
	 * this routine has no effect.
	 */
	public void enableGyroPid() {
		
		// If the gain is set to zero, the pid cannot be enabled
		if (gyroPid.getP() == 0 && gyroPid.getI() == 0) {
			System.out.println("The GyroPid cannot be enabled until"
					+ " the PID Kp value is set.");
			return;
		}
		gyroPid.enable();
		
	}

	/**
	 * Get the current gyro angle
	 * <p>
	 * NOTE: This routine will always return a positive
	 * angle >= 0 and < 360 degrees.
	 * @return gyro angle in degrees.
	 */
	public double getGryoAngle() {

		// Normalize the angle
		double angle = gyro.getAngle() % 360;

		if (angle < 0) {
			angle = angle + 360;
		}

		return angle;
	}

	/** 
	 * Get the GyroPid Steering
	 */
	public double getGyroPidSteering() {
		
		if (gyroPid.isEnabled()) {
			return gyroPid.get();
		}
		
		return 0.0;
	}

	/**
	 * Set the current gyro heading to zero.
	 */
	public void resetGyroAngle() {
		setGyroAngle(0);
	}

	/**
	 * Set the Heading for the Gyro PID
	 */
	public void setDirection(double direction) {
		gyroPid.setSetpoint(direction);
	}

	/**
	 * Reset the gyro angle to a known heading angle.
	 * <p>
	 * This routine is useful when start autonomous
	 * to set the gyro angle to a known configuration
	 * at the start of the match 
	 * (ie pointed right = 90 degrees)
	 * @param angle new angle reading for the gyro
	 */
	public void setGyroAngle(double angle) {
		gyro.setGyroAngle(angle);
	}

	public void setGyroPidGain(double kP, double kI) {
		
		this.gyroPid.setP(kP);
		this.gyroPid.setI(kI);
		
		// If the gain is set to zero, the pid cannot be enabled
		if (kP == 0 && kI == 0) {
			disableGyroPid();
		}
	}

	@Override
	public void updatePeriodic() {

		super.updatePeriodic();

		// Update all PIDs
		if (gyroPid.isEnabled()) {
			gyroPid.calculate(gyro.getAngle());
		}
		
		// Update all SmartDashboard values
		SmartDashboard.putData("Gyro", gyro);
		SmartDashboard.putNumber("Gyro Angle", getGryoAngle());

		SmartDashboard.putData("Gyro PID", gyroPid);
		SmartDashboard.putNumber("Gyro Steering", getGyroPidSteering());

		if (gyro.supportsPitch()) {
			SmartDashboard.putNumber("Gyro Pitch", gyro.getPitch());
		}

	}

}
