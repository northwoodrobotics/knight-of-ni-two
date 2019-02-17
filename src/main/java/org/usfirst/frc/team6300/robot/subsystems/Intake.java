package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.teleop.TeleIntake;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
	private VictorSP m1;
	private VictorSP m2;
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Intake() {
		m1 = new VictorSP(RobotMap.INTAKE_MOTOR_1);
		m2 = new VictorSP(RobotMap.INTAKE_MOTOR_2);
		m1.setInverted(RobotMap.INTAKE_1_INVERTED);
		m2.setInverted(RobotMap.INTAKE_2_INVERTED);
	}
	
	public void move(double pwr) {
		m1.set(pwr);
		m2.set(pwr);
	}
	
	public void stop() {
		m1.stopMotor();
		m2.stopMotor();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleIntake(this));
	}
}

