package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.teleop.TeleLift;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lifter extends Subsystem {
	private WPI_TalonSRX m1;
	private WPI_TalonSRX m2;
	private DigitalInput limitSwitchTop;
	private DigitalInput limitSwitchBottom;
	private double neutralPwr = 0.1;

	public Lifter() {
		m1 = new WPI_TalonSRX(RobotMap.LIFTER_MOTOR_1);
		m2 = new WPI_TalonSRX(RobotMap.LIFTER_MOTOR_2);
		m1.setInverted(RobotMap.LIFTER_INVERTED);
		m2.setInverted(RobotMap.LIFTER_INVERTED);
		m1.configOpenloopRamp(0.5);
		m2.configOpenloopRamp(0.5);
		limitSwitchTop = new DigitalInput(RobotMap.LIFTER_LIMIT_TOP);
		limitSwitchBottom = new DigitalInput(RobotMap.LIFTER_LIMIT_BOTTOM);
	}
	
	public void move(double pwr) {
		if (isAtTop() && pwr > 0) {
			m1.set(neutralPwr);
			m2.set(neutralPwr);
			System.out.println("Lifter at top; not going up.");
		}
		else if (isAtBottom() && pwr < 0) {
			m1.set(neutralPwr);
			m2.set(neutralPwr);
			System.out.println("Lifter at bottom; not going down.");
		}
		else {
			m1.set(pwr + neutralPwr);
			m2.set(pwr + neutralPwr);
		}
	}
	
	public void goLimp() {
		m1.stopMotor();
		m2.stopMotor();
	}
	
	public boolean isAtTop() {
		return !limitSwitchTop.get();
	}
	
	public boolean isInMiddle() {
		return !isAtTop() && !isAtBottom();
	}
	
	public boolean isAtBottom() {
		return !limitSwitchBottom.get();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleLift(this));
	}
}

