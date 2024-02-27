// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Timer;

public class MotorTestSubsystem extends SubsystemBase {

 
   
    private final CANSparkMax shooter1;
    private final CANSparkMax shooter2;
    private final CANSparkMax intake1;
    
    
    public MotorTestSubsystem() {
        // Initialize NEO motors with device IDs
     
     
        shooter1 = new CANSparkMax(29, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
        shooter2 = new CANSparkMax(30, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
        intake1 = new CANSparkMax(28, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
        
        // Set default command if needed
        //setDefaultCommand(new YourDefaultCommand(this));
    }

   // Method to set speed for intake
   public void testIntake(double intakeSpeed, double shooterSpeed, double runTime) {

    System.out.print("ACTIVE INTAKE");
    shooter1.set(shooterSpeed);
    shooter2.set(-shooterSpeed);
    intake1.set(-intakeSpeed);
    Timer.delay(runTime);
  }


    // Method to stop all motors
    public void stopMotors() {

        shooter1.stopMotor();
        shooter2.stopMotor();
        intake1.stopMotor();
        
    }

    // Additional methods for other testing functionalities can be added

    // Ensure motors are stopped when the robot is disabled
    @Override
    public void periodic() {
        stopMotors();
    }
}