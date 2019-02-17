package org.usfirst.frc.team6300.robot.commands.autonomous;

import org.usfirst.frc.team6300.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoIntake extends Command {

	private Intake intake;
	private double pwr;
	private double durationMillis;
	private long startTime;

	/**
	 * Sets the intake to pwr for a specified time, then stops it.
	 * 
	 * @param intake
	 * @param pwr
	 * @param seconds
	 */
	public AutoIntake(Intake intake, double pwr, double seconds) {
		this.intake = intake;
		this.pwr = pwr;
		durationMillis = seconds * 1000;
		requires(intake);
	}

	/**
	 * Sets the intake to pwr. Does not stop motors on command finish.
	 * 
	 * @param intake
	 * @param pwr
	 */
	public AutoIntake(Intake intake, double pwr) {
		this.intake = intake;
		this.pwr = pwr;
		durationMillis = 0;
		requires(intake);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		startTime = System.currentTimeMillis();
		intake.move(pwr);
		System.out.println("Started AutoIntake");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		intake.move(pwr);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return System.currentTimeMillis() > startTime + durationMillis;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Ran " + this.toString() + " for " + (System.currentTimeMillis() - startTime) / 1000.0 + " seconds.");
		if (durationMillis != 0) {
			intake.stop();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("Ran " + this.toString() + " for " + (System.currentTimeMillis() - startTime) / 1000.0 + " seconds, then got interrupted.");
	}
}
