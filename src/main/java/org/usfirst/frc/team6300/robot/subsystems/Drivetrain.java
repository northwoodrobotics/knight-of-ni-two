package org.usfirst.frc.team6300.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4048.swerve.drive.SwerveDrive;
import org.usfirst.frc4048.swerve.math.CentricMode;
import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.teleop.TeleDrive;
import org.usfirst.frc4048.swerve.drive.CanTalonSwerveEnclosure;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


/*            --- LAYOUT ---
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

/**
 * Knight Of Ni's Swerve Drive
 */
public class Drivetrain extends Subsystem {
	private CanTalonSwerveEnclosure swerveEnclosure1;
	private CanTalonSwerveEnclosure swerveEnclosure2;
	private CanTalonSwerveEnclosure swerveEnclosure3;
	private CanTalonSwerveEnclosure swerveEnclosure4;
	private SwerveDrive swerveDrive;
	
	public static final double GEAR_RATIO = (1024d);
	private static final double L = 21.0;
	private static final double W = 23.5;
	
	private static final double P = 10.0;
	private static final double I = 0.0;
	private static final double D = 0.0;
	private static final double F = 0.0;
	
	private WPI_TalonSRX driveMotor1;
	private WPI_TalonSRX driveMotor2;
	private WPI_TalonSRX driveMotor3;
	private WPI_TalonSRX driveMotor4;
	
	private WPI_TalonSRX steerMotor1;
	private WPI_TalonSRX steerMotor2;
	private WPI_TalonSRX steerMotor3;
	private WPI_TalonSRX steerMotor4;
	
	double[] wheelAngles = new double[4];
	
	private Gyro gyro = new ADXRS450_Gyro();
	private CentricMode centricMode = CentricMode.ROBOT;
	
	public Drivetrain() {
		
	}
	
	public void init() {
		driveMotor1 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_DRIVE_1);
		driveMotor2 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_DRIVE_2);
		driveMotor3 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_DRIVE_3);
		driveMotor4 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_DRIVE_4);
		
		driveMotor1.setInverted(RobotMap.DRIVETRAIN_DRIVE_1_INV);
		driveMotor2.setInverted(RobotMap.DRIVETRAIN_DRIVE_2_INV);
		driveMotor3.setInverted(RobotMap.DRIVETRAIN_DRIVE_3_INV);
		driveMotor4.setInverted(RobotMap.DRIVETRAIN_DRIVE_4_INV);
		
		driveMotor1.setNeutralMode(NeutralMode.Brake);
		driveMotor2.setNeutralMode(NeutralMode.Brake);
		driveMotor3.setNeutralMode(NeutralMode.Brake);
		driveMotor4.setNeutralMode(NeutralMode.Brake);
		
		steerMotor1 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_STEER_1);
		steerMotor2 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_STEER_2);
		steerMotor3 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_STEER_3);
		steerMotor4 = new WPI_TalonSRX(RobotMap.DRIVETRAIN_STEER_4);
		
		steerMotor1.setInverted(RobotMap.DRIVETRAIN_STEER_1_INV);
		steerMotor2.setInverted(RobotMap.DRIVETRAIN_STEER_2_INV);
		steerMotor3.setInverted(RobotMap.DRIVETRAIN_STEER_3_INV);
		steerMotor4.setInverted(RobotMap.DRIVETRAIN_STEER_4_INV);
		
		steerMotor1.configSelectedFeedbackSensor(FeedbackDevice.Analog);
		steerMotor2.configSelectedFeedbackSensor(FeedbackDevice.Analog);
		steerMotor3.configSelectedFeedbackSensor(FeedbackDevice.Analog);
		steerMotor4.configSelectedFeedbackSensor(FeedbackDevice.Analog);
		
		steerMotor1.selectProfileSlot(0,0);
		steerMotor2.selectProfileSlot(0,0);
		steerMotor3.selectProfileSlot(0,0);
		steerMotor4.selectProfileSlot(0,0);
		
		steerMotor1.config_kP(0, P);
		steerMotor2.config_kP(0, P);
		steerMotor3.config_kP(0, P);
		steerMotor4.config_kP(0, P);
		
		steerMotor1.config_kI(0, I);
		steerMotor2.config_kI(0, I);
		steerMotor3.config_kI(0, I);
		steerMotor4.config_kI(0, I);
		
		steerMotor1.config_kD(0, D);
		steerMotor2.config_kD(0, D);
		steerMotor3.config_kD(0, D);
		steerMotor4.config_kD(0, D);
		
		steerMotor1.config_kF(0, F);
		steerMotor2.config_kF(0, F);
		steerMotor3.config_kF(0, F);
		steerMotor4.config_kF(0, F);
		
		swerveEnclosure1 = new CanTalonSwerveEnclosure("enc 1", driveMotor1, steerMotor1, GEAR_RATIO);
		swerveEnclosure2 = new CanTalonSwerveEnclosure("enc 2", driveMotor2, steerMotor2, GEAR_RATIO);
		swerveEnclosure3 = new CanTalonSwerveEnclosure("enc 3", driveMotor3, steerMotor3, GEAR_RATIO);
		swerveEnclosure4 = new CanTalonSwerveEnclosure("enc 4", driveMotor4, steerMotor4, GEAR_RATIO);
		
		swerveEnclosure1.setReverseSteerMotor(true);
		swerveEnclosure2.setReverseSteerMotor(true);
		swerveEnclosure3.setReverseSteerMotor(true);
		swerveEnclosure4.setReverseSteerMotor(true);
		
		swerveEnclosure1.setReverseEncoder(true);
		swerveEnclosure2.setReverseEncoder(true);
		swerveEnclosure3.setReverseEncoder(true);
		swerveEnclosure4.setReverseEncoder(true);
		
		swerveDrive = new SwerveDrive(swerveEnclosure1, swerveEnclosure2, swerveEnclosure3, swerveEnclosure4, W, L);
		swerveDrive.setCentricMode(centricMode);
	}
	
	public void drive(double fwd, double strafe, double rotateCW) {
		swerveDrive.move(fwd, strafe, rotateCW, gyro.getAngle());
	}
	
	public double[] getWheelAngles() {
		wheelAngles[0] = steerMotor1.getSelectedSensorPosition();
		wheelAngles[1] = steerMotor2.getSelectedSensorPosition();
		wheelAngles[2] = steerMotor3.getSelectedSensorPosition();
		wheelAngles[3] = steerMotor4.getSelectedSensorPosition();
		return wheelAngles;
	}
	
	public double getHeading() {
		return gyro.getAngle() % 360;
	}
	
	public void calibrateGyro() {
		System.out.println("Calibrating the gyro...");
		gyro.calibrate();
		System.out.println("Done calibrating the gyro.");
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleDrive(this));
	}
	
	public void resetEncoders() {
		swerveEnclosure1.setEncPosition(0);
		swerveEnclosure2.setEncPosition(0);
		swerveEnclosure3.setEncPosition(0);
		swerveEnclosure4.setEncPosition(0);
	}
	
	public void setCentricMode(CentricMode mode) {
		swerveDrive.setCentricMode(mode);
		centricMode = mode;
	}
	
	public CentricMode getCentricMode() {
		return centricMode;
	}
}

