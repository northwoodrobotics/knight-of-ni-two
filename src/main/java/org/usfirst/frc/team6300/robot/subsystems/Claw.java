package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

	
public class Claw extends Subsystem {
	private DoubleSolenoid sol = new DoubleSolenoid(RobotMap.clawSol0, RobotMap.clawSol1);
	
	public void open() {
		sol.set(Value.kForward);
	}
	
	public void close() {
		sol.set(Value.kReverse);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
}