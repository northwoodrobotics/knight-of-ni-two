package org.usfirst.frc.team6300.robot.commands.autonomous;

import org.usfirst.frc.team6300.robot.Robot;
import org.usfirst.frc.team6300.robot.commands.SetClaw;
import org.usfirst.frc.team6300.robot.commands.SwitchCentricMode;
import org.usfirst.frc.team6300.robot.commands.SetClaw.Position;
import org.usfirst.frc4048.swerve.math.CentricMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RCenter extends CommandGroup {

	public RCenter(Robot robot) {
		addSequential(new SetClaw(robot.claw, Position.CLOSED));

		// Hold the cube for the whole auto period  (until interrupted)
		addParallel(new AutoIntake(robot.intake, 0.1, 15.0));

		// Jerk forward and stop to flop the cube down
		addSequential(new SwitchCentricMode(robot.drivetrain, CentricMode.ROBOT));
		addSequential(new AutoDrive(robot.drivetrain, 0.7, 0.0, 0.0, 0.3));
		addSequential(new AutoDrive(robot.drivetrain, -0.7, 0.0, 0.0, 0.3));

		// Wait for things to stop moving
		addSequential(new AutoDrive(robot.drivetrain, 0.0, 0.0, 0.0, 1.0));

		// Switch to Field-Centric mode
		addSequential(new SwitchCentricMode(robot.drivetrain, CentricMode.FIELD));

		// Lift the cube
		addParallel(new AutoLift(robot.lifter, 0.7, 2.0));

		// Drive to the switch
		addSequential(new AutoDrive(robot.drivetrain, 0.4, 0.28, 0.0, 1.6));
		addSequential(new AutoDrive(robot.drivetrain, 0.0, 0.0, 0.0, 1.0));
		addSequential(new AutoDrivePID(robot.drivetrain, 0.3, 0.0, 0.0, 2.0));

		// Release the cube
		addSequential(new AutoIntake(robot.intake, -0.4, 2.0));
		addSequential(new SetClaw(robot.claw, Position.OPEN));
		addSequential(new SwitchCentricMode(robot.drivetrain, CentricMode.ROBOT));
	}
}
