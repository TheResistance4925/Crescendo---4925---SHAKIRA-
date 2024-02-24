// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.*;
// import frc.robot.subsystems.NeoSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsystem. */

    private final CANSparkMax shoulderMotor;
    private final CANSparkMax shoulderFollower;
    private final CANSparkMax wristMotor;
    private final CANSparkMax wristFollower;

    // private final SparkAbsoluteEncoder shoulderEncoder;
    // private final SparkAbsoluteEncoder wristEncoder;
    private final SparkPIDController shoulderPIDController;
    private final SparkPIDController wristPIDController;
    private final RelativeEncoder shoulderEncoder;
    private final RelativeEncoder wristEncoder;
    private final RelativeEncoder shoulderFollowerEncoder;
    private final RelativeEncoder wristFollowerEncoder;

  public ArmSubsystem(int shoulderFollowerId, int wristFollowerId, int shoulderMotorId, int wristMotorId, int shoulderEncoderId, int wristEncoderId) {
    shoulderMotor = new CANSparkMax(24, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
    shoulderFollower = new CANSparkMax(25, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
    wristMotor = new CANSparkMax(26, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
    wristFollower = new CANSparkMax(27, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);

    // Use RelativeEncoder for hall-effect sensor (built into the brushless motor)
    shoulderEncoder = shoulderMotor.getEncoder();
    wristEncoder = wristMotor.getEncoder();
    shoulderFollowerEncoder = shoulderFollower.getEncoder();
    wristFollowerEncoder = wristFollower.getEncoder();

  
            // Get the PID controllers for shoulder and wrist motors
            shoulderPIDController = shoulderMotor.getPIDController();
            wristPIDController = wristMotor.getPIDController();
            // shoulderFollowerPIDController = shoulderFollower.getPIDController();
            // wristFollowerPIDController = wristFollower.getPIDController();

            // Configure PID constants (you need to tune these values based on your system)
           /*  double kP = 0.1; // Placeholder values, tune based on your system
            double kI = 0.0;
            double kD = 0.0;
            double kIz = 0.0;
            double kFF = 0.0;
            double kMaxOutput = 1.0;
            double kMinOutput = -1.0;
    
            shoulderPIDController.setP(kP);
            shoulderPIDController.setI(kI);
            shoulderPIDController.setD(kD);
            shoulderPIDController.setIZone(kIz);
            shoulderPIDController.setFF(kFF);
            shoulderPIDController.setOutputRange(kMinOutput, kMaxOutput);
    
            wristPIDController.setP(kP);
            wristPIDController.setI(kI);
            wristPIDController.setD(kD);
            wristPIDController.setIZone(kIz);
            wristPIDController.setFF(kFF);
            wristPIDController.setOutputRange(kMinOutput, kMaxOutput);
            */
            shoulderFollower.follow(shoulderMotor);
            wristFollower.follow(wristMotor);
  }


  public void setArmPosition(double shoulderAngle, double wristAngle) {
    double targetShoulderPosition = convertToEncoderTicks(shoulderAngle);
    double targetWristPosition = convertToEncoderTicks(wristAngle);

    // DEBUG
    System.out.println("Target Shoulder Position: " + targetShoulderPosition);
    System.out.println("Target Wrist Position: " + targetWristPosition);

    // Use PID control to set the motor positions
    shoulderPIDController.setReference(targetShoulderPosition, ControlType.kPosition);
    wristPIDController.setReference(targetWristPosition, ControlType.kPosition);
}


private double convertToEncoderTicks(double angle) {
        // Replace this with the actual conversion logic
        // The exact conversion depends on the specifications of the encoder
        // For example, if the encoder has 360 ticks per revolution, you might use:
        double ticksPerRevolution = 360.0;
        return angle * (ticksPerRevolution / 360.0);
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
 