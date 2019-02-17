package org.usfirst.frc.team6300.robot.commands.autonomous;

import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDrive extends Command {

	private Drivetrain drivetrain;
	private double fwd;
	private double strafe;
	private double rotateCW;
	private double durationMillis;
	private long startTime;

	public AutoDrive(Drivetrain drivetrain, double fwd, double strafe, double rotateCW, double seconds) {
		this.drivetrain = drivetrain;
		this.fwd = fwd;
		this.strafe = strafe;
		this.rotateCW = rotateCW;
		durationMillis = seconds * 1000;
		requires(drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		startTime = System.currentTimeMillis();
		System.out.println("Started AutoDrive");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		drivetrain.drive(fwd, strafe, rotateCW);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return System.currentTimeMillis() > startTime + durationMillis;
	}

	// Called once after isFinished returns true
	protected void end() {
		drivetrain.drive(0, 0, 0);
		System.out.println("Ran " + this.toString() + " for " + (System.currentTimeMillis() - startTime) / 1000.0 + " seconds.");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("Ran " + this.toString() + " for " + (System.currentTimeMillis() - startTime) / 1000.0 + " seconds, then got interrupted.");
		drivetrain.drive(0, 0, 0);
	}
}
