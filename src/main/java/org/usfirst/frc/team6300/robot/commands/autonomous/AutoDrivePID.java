package org.usfirst.frc.team6300.robot.commands.autonomous;

import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class AutoDrivePID extends PIDCommand {

	private Drivetrain drivetrain;
	private double fwd;
	private double strafe;
	private double targetHeading;
	private double durationMillis;
	private long startTime;
	private double pidRotation;

	private static final double P = 0.02;
	private static final double I = 0.00;
	private static final double D = 0.00;
	
	private static final double maxOutput = 0.8;

	/**
	 * Drives the robot around, keeping it pointing in the specified heading, for a given length of time.
	 * @param drivetrain
	 * @param fwd
	 * @param strafe
	 * @param heading
	 * @param seconds
	 */
	public AutoDrivePID(Drivetrain drivetrain, double fwd, double strafe, double heading, double seconds) {
		super(P, I, D);
		getPIDController().setAbsoluteTolerance(2.0);
		getPIDController().setInputRange(0.0, 360.0);
		getPIDController().setContinuous();
		getPIDController().setOutputRange(-maxOutput, maxOutput);
		
		this.drivetrain = drivetrain;
		this.fwd = fwd;
		this.strafe = strafe;
		this.targetHeading = heading;
		durationMillis = seconds * 1000;
		
		requires(drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		startTime = System.currentTimeMillis();
		System.out.println("Started AutoDrivePID");
		setSetpoint(targetHeading);
		getPIDController().enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		drivetrain.drive(fwd, strafe, pidRotation);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return System.currentTimeMillis() > startTime + durationMillis;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Ran " + this.toString() + " for " + (System.currentTimeMillis() - startTime) / 1000.0 + " seconds.");
		getPIDController().disable();
		drivetrain.drive(0, 0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("Ran " + this.toString() + " for " + (System.currentTimeMillis() - startTime) / 1000.0 + " seconds, then got interrupted.");
		getPIDController().disable();
		drivetrain.drive(0, 0, 0);
	}

	@Override
	protected double returnPIDInput() {
		return drivetrain.getHeading();
	}

	@Override
	protected void usePIDOutput(double output) {
		pidRotation = output;
	}
}
