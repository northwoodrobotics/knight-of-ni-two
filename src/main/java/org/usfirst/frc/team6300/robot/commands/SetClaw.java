package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetClaw extends Command {

	public enum Position {
		OPEN, CLOSED
	}

	private Claw claw;
	private Position pos;

	public SetClaw(Claw claw, Position pos) {
		this.claw = claw;
		this.pos = pos;
		requires(claw);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		switch(pos) {
		case OPEN:
			claw.open();
			break;
		case CLOSED:
			claw.close();
			break;
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
