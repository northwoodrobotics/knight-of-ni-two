package org.usfirst.frc.team6300.robot.commands.autonomous;

import org.usfirst.frc.team6300.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoLift extends Command {

	private Lifter lifter;
	private double pwr;
	private double durationMillis;
//	private LifterPos posIntent;
	private long startTime;

	public AutoLift(Lifter lifter, double pwr, double seconds) {
		this.lifter = lifter;
		this.pwr = pwr;
		durationMillis = seconds * 1000;
	}

//	public AutoLift(Lifter lifter, double pwr, LifterPos position) {
//		this.lifter = lifter;
//		posIntent = position;
//		durationMillis = 0;
//
//		int reversePwr;
//		if (posIntent == LifterPos.TOP) {
//			reversePwr = 1;
//		} else {
//			reversePwr = -1;
//		}
//		this.pwr = reversePwr * Math.abs(pwr);
//	}

	// Called just before this Command runs the first time
	protected void initialize() {
		startTime = System.currentTimeMillis();
		lifter.move(pwr);
		System.out.println("Started AutoLift");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		lifter.move(pwr);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
//		if (posIntent == null && System.currentTimeMillis() >= startTime + durationMillis) {
//			return true;
//		} else if (posIntent == LifterPos.TOP && lifter.isAtTop()) {
//			return true;
//		} else if (posIntent == LifterPos.BOTTOM && lifter.isAtBottom()) {
//			return true;
//		} else {
//			return false;
//		}
		return System.currentTimeMillis() > startTime + durationMillis;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Ran AutoLift for " + (System.currentTimeMillis() - startTime) / 1000.0 + "seconds.");
		lifter.move(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		lifter.move(0);
		System.out.println("Ran AutoLift for " + (System.currentTimeMillis() - startTime) / 1000.0 + "seconds, then got interrupted.");
	}
}
