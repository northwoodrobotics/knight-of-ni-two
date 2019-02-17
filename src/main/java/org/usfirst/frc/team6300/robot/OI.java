/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6300.robot;

import org.usfirst.frc.team6300.robot.commands.SetClaw;
import org.usfirst.frc.team6300.robot.commands.SwitchCentricMode;
import org.usfirst.frc.team6300.robot.commands.SetClaw.Position;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static final XboxController driveController = new XboxController(0);
	public static final JoystickButton driveX = new JoystickButton(driveController, 3);
	
	public static final XboxController cubeController = new XboxController(1);
	public static final JoystickButton cubeX = new JoystickButton(cubeController, 3);
	public static final JoystickButton cubeB = new JoystickButton(cubeController, 2);
	
	public OI(Robot robot) {
		driveX.whenPressed(new SwitchCentricMode(robot.drivetrain, driveController));
		
		cubeX.whenPressed(new SetClaw(robot.claw, Position.OPEN));
		cubeB.whenPressed(new SetClaw(robot.claw, Position.CLOSED));
	}
	/**
	 * Adds a deadzone to, for example, a joystick input that does not completely
	 * zero itself mechanically.
	 * 
	 * @param input
	 *            the input value (any double between -1 and 1, inclusively).
	 * @param radius
	 *            how far from zero the input can be for the output to still be
	 *            zero. This must be greated than 0 and less than 1.
	 * @return the value after application of the deadzone (between -1 and 1,
	 *         inclusively).
	 */
	public static double deadBand(double input) {
		double output;
		double radius = 0.2;
		double maxOutput = 1;
		assert (-1 < input && input < 1) : "input is less than -1 or greater than 1";
		assert (radius < maxOutput) : "deadband radius is greater than or equal to the maximum output";
		
		if (input > radius) {
			output = ((maxOutput * (input - maxOutput)) / (maxOutput - radius)) + maxOutput;
		} else if (input < -radius) {
			output = ((maxOutput * (input + maxOutput)) / (maxOutput - radius)) - maxOutput;
		} else {
			output = 0;
		}
		
		assert (Math.abs(output) <= maxOutput) : "expected to output a number closer to 0 than " + maxOutput;
		return output;
	}
	
	/**
	 * Exponential axis control, making sure inputs are correct
	 * @param input the value to  apply
	 * @return the value raised to the power of 1.5
	 */
	public static double expControl(double input) {
		assert(Math.abs(input) <= 1);
		return Math.pow(input, 1.5);
	}
	
	/**
	 * Exponential axis control, making sure inputs make sense
	 * @param input the value to  apply
	 * @return the value raised to the power of "exp"
	 */
	public static double expControl(double input, double exp) {
		assert(Math.abs(input) <= 1);
		assert(exp >= 1);
		return Math.pow(input, exp);
	}
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a command in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
