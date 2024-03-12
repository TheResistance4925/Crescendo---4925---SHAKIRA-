// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;
public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsystem. */

  private final CANSparkMax shoulderDriver;
  private final CANSparkMax shoulderFollow;
  private final CANSparkMax wristDriver;
  private final CANSparkMax wristFollow;

  private SparkPIDController wristSparkPIDController;
  private SparkPIDController shoulderSparkPIDController;

  private RelativeEncoder wristEncoder;
  private RelativeEncoder shoulderEncoder;

  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, maxVelWrist, minVel, maxAcc, allowedErr;
  public double shoulderPose;
  public double wristPose;

  public ArmSubsystem() {

    shoulderDriver = new CANSparkMax(25, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
    shoulderFollow = new CANSparkMax(24, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
    wristDriver = new CANSparkMax(27, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
    wristFollow = new CANSparkMax(26, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
  
    shoulderDriver.restoreFactoryDefaults();
    shoulderFollow.restoreFactoryDefaults();
    wristDriver.restoreFactoryDefaults();
    wristFollow.restoreFactoryDefaults();

    shoulderDriver.setIdleMode(IdleMode.kBrake);
    shoulderFollow.setIdleMode(IdleMode.kBrake);
    wristDriver.setIdleMode(IdleMode.kBrake);
    wristFollow.setIdleMode(IdleMode.kBrake);

    shoulderSparkPIDController = shoulderDriver.getPIDController();
    wristSparkPIDController = wristDriver.getPIDController();
    shoulderEncoder = shoulderDriver.getEncoder();
    wristEncoder = wristDriver.getEncoder();

    //PID coefficents
    kP = 0.000002; 
    kFF = 0.0002; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5700;

    // Smart Motion Coefficients
    maxVel = 2000; // rpm
    maxVelWrist = 1300;
    maxAcc = 2000;
    minVel = 0;
    allowedErr = 0;

    int smartMotionSlot = 0;

    shoulderSparkPIDController.setFF(kFF);
    shoulderSparkPIDController.setP(kP);
    shoulderSparkPIDController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
    shoulderSparkPIDController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
    shoulderSparkPIDController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
    shoulderSparkPIDController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

    wristSparkPIDController.setFF(kFF);
    wristSparkPIDController.setP(kP);
    wristSparkPIDController.setSmartMotionMaxVelocity(maxVelWrist, smartMotionSlot);
    wristSparkPIDController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
    wristSparkPIDController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
    wristSparkPIDController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

    shoulderFollow.follow(shoulderDriver, true);
    wristFollow.follow(wristDriver, true);

    shoulderDriver.setSmartCurrentLimit(60);
    shoulderFollow.setSmartCurrentLimit(60);
    wristDriver.setSmartCurrentLimit(60);
    wristFollow.setSmartCurrentLimit(60);

    shoulderDriver.burnFlash();
    shoulderFollow.burnFlash();
    wristDriver.burnFlash();
    wristFollow.burnFlash();

  }

public double wristPublicPosition;
public double shoulderPublicPosition;







   public void poseShoulder(double shoulderPosition) {

    shoulderPose = shoulderPosition;
    shoulderPublicPosition = shoulderPosition;
     shoulderSparkPIDController.setReference(shoulderPose, ControlType.kSmartMotion);
  }

  public void poseWrist(double wristPosition) {
  wristPose = wristPosition;
  wristPublicPosition = wristPosition;

    wristSparkPIDController.setReference(wristPose, ControlType.kSmartMotion);
  }

  public void homeArm(){

    poseShoulder(60);
    new WaitCommand(3);
    poseWrist( 5);
    new WaitCommand(0.25);
    poseShoulder(5);

  }

  public void groundIntakePose(){

    poseShoulder(50);
    new WaitCommand(2);
    poseWrist(-33);
    new WaitCommand(1);
    poseShoulder(21);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
   
   
    SmartDashboard.putNumber("Wrist Pose?", wristPublicPosition);
    SmartDashboard.putNumber( "Shoulder Pose?", shoulderPublicPosition);
    SmartDashboard.putNumber("OutputWrist", wristDriver.getAppliedOutput());
    SmartDashboard.putNumber("OutputShoulder", shoulderDriver.getAppliedOutput());
  }
}
