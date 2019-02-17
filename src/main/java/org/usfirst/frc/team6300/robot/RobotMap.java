/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6300.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	/*       --- DRIVETRAIN LAYOUT ---
	 *
	 *                 Front
	 *      Wheel 2 ------------ Wheel 1    ---
	 *          |                 |           |
	 *          |                 |           |
	 *          |                 |           |
	 *    Left  |                 |  Right    |-- Length
	 *          |                 |           |
	 *          |                 |           |
	 *          |                 |           |
	 *      Wheel 3 ------------ Wheel 4    ---
	 *                 Back
	 *
	 *          |                 |
	 *          |----- Width -----|
	 */
	
	public static final int DRIVETRAIN_DRIVE_1 = 3;
	public static final int DRIVETRAIN_DRIVE_2 = 2;
	public static final int DRIVETRAIN_DRIVE_3 = 6;
	public static final int DRIVETRAIN_DRIVE_4 = 7;
	
	public static final boolean DRIVETRAIN_DRIVE_1_INV = true;
	public static final boolean DRIVETRAIN_DRIVE_2_INV = false;
	public static final boolean DRIVETRAIN_DRIVE_3_INV = false;
	public static final boolean DRIVETRAIN_DRIVE_4_INV = true;
	
	public static final int DRIVETRAIN_STEER_1 = 4;
	public static final int DRIVETRAIN_STEER_2 = 1;
	public static final int DRIVETRAIN_STEER_3 = 5;
	public static final int DRIVETRAIN_STEER_4 = 8;
	
	public static final boolean DRIVETRAIN_STEER_1_INV = false;
	public static final boolean DRIVETRAIN_STEER_2_INV = false;
	public static final boolean DRIVETRAIN_STEER_3_INV = false;
	public static final boolean DRIVETRAIN_STEER_4_INV = false;
	
	// LIFTER
	public static final int LIFTER_MOTOR_1 = 9;
	public static final int LIFTER_MOTOR_2 = 10;
	public static final int LIFTER_LIMIT_TOP = 0;
	public static final int LIFTER_LIMIT_BOTTOM = 1;
	public static final boolean LIFTER_INVERTED = true;
	
	// INTAKE
	public static final int INTAKE_MOTOR_1 = 0;
	public static final int INTAKE_MOTOR_2 = 1;
	public static final boolean INTAKE_1_INVERTED = false;
	public static final boolean INTAKE_2_INVERTED = true;
	public static final int clawSol0 = 0;
	public static final int clawSol1 = 1;
}
