/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6300.robot;

import org.usfirst.frc.team6300.robot.commands.ResetDrivetrainEncoders;
import org.usfirst.frc.team6300.robot.commands.autonomous.AutoDrive;
import org.usfirst.frc.team6300.robot.commands.autonomous.AutoDrivePID;
import org.usfirst.frc.team6300.robot.commands.autonomous.LCenter;
import org.usfirst.frc.team6300.robot.commands.autonomous.LLeft;
import org.usfirst.frc.team6300.robot.commands.autonomous.RCenter;
import org.usfirst.frc.team6300.robot.commands.autonomous.RRight;
import org.usfirst.frc.team6300.robot.subsystems.Claw;
import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;
import org.usfirst.frc.team6300.robot.subsystems.Intake;
import org.usfirst.frc.team6300.robot.subsystems.Lifter;
import org.usfirst.frc4048.swerve.math.CentricMode;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static OI m_oi;

	public final Drivetrain drivetrain = new Drivetrain();
	public final Lifter lifter = new Lifter();
	public final Intake intake = new Intake();
	public final Claw claw = new Claw();
	
	private final Compressor compressor = new Compressor();

	private enum StartingSide {
		LEFT, RIGHT, CENTER
	}

	Command autonomousCommand;
	SendableChooser<StartingSide> sideChooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI(this);
		drivetrain.init();
		drivetrain.resetEncoders();
		drivetrain.calibrateGyro();

		sideChooser.addDefault("Starting from Center", StartingSide.CENTER);
		sideChooser.addObject("Starting from Left", StartingSide.LEFT);
		sideChooser.addObject("Starting from Right", StartingSide.RIGHT);
		SmartDashboard.putData("Starting Side", sideChooser);
		SmartDashboard.putData("Reset Encoders", new ResetDrivetrainEncoders(drivetrain));
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		compressor.start();
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		StartingSide startingSide = sideChooser.getSelected();

		if (gameData.charAt(0) == 'L') {
			switch (startingSide) {
			case LEFT:
				autonomousCommand = new LLeft(this);
				System.out.println("LLeft");
				break;
			case RIGHT:
//				autonomousCommand = new LRight(this);
//				System.out.println("LRight");
				break;
			case CENTER:
				autonomousCommand = new LCenter(this);
				System.out.println("LCenter");
				break;
			default:
				System.out.println("Invalid starting side!");
				break;
			}
		} else if (gameData.charAt(0) == 'R') {
			switch (startingSide) {
			case LEFT:
//				autonomousCommand = new RLeft(this);
//				System.out.println("RLeft");
//				autonomousCommand = new AutoDrivePID(drivetrain, 0.0, 0.0, 0.0, 10.0);
				break;
			case RIGHT:
				autonomousCommand = new RRight(this);
				System.out.println("RRight");
				break;
			case CENTER:
				autonomousCommand = new RCenter(this);
				System.out.println("RCenter");
				break;
			default:
				System.out.println("Invalid starting side!");
				break;
			}
		} else {
			System.out.println("Invalid game data!");
		}

		drivetrain.resetEncoders();

		// schedule the autonomous command
		if (autonomousCommand == null) {
			System.out.println("autonomousCommand is null! Running auto line code.");
			drivetrain.setCentricMode(CentricMode.FIELD);
			autonomousCommand = new AutoDrive(drivetrain, 0.5, 0.0, 0.0, 2.0);
		}
		autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		compressor.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	@Override
	public void robotPeriodic() {
		SmartDashboard.putString("Centric Mode: ", drivetrain.getCentricMode().toString() + "-CENTRIC");
		SmartDashboard.putNumberArray("Wheel Angles", drivetrain.getWheelAngles());
		SmartDashboard.putBoolean("Lifter at Top?", lifter.isAtTop());
		SmartDashboard.putBoolean("Lifter at Bottom?", lifter.isAtBottom());
	}
}
