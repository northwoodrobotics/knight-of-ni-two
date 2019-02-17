package org.usfirst.frc.team6300.robot.commands.teleop;

import edu.wpi.first.wpilibj.GenericHID.Hand;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TeleDrive extends Command {
	private Drivetrain drivetrain;
	private XboxController controller = OI.driveController;
	
	private double fwd;
	private double strafe;
	private double rotateCW;
	
	public TeleDrive(Drivetrain drivetrain) {
		this.drivetrain = drivetrain;
		requires(drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		drivetrain.init();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		fwd = OI.deadBand(-controller.getY(Hand.kLeft));
		strafe = OI.deadBand(controller.getX(Hand.kLeft));
		rotateCW = OI.deadBand(controller.getX(Hand.kRight) * 0.6);
		
		SmartDashboard.putNumber("fwd", fwd);
		SmartDashboard.putNumber("strafe", strafe);
		SmartDashboard.putNumber("rotateCW", rotateCW);
		
		drivetrain.drive(fwd, strafe, rotateCW);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
