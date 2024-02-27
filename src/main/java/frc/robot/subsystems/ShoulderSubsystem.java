// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Timer;

public class ShoulderSubsystem extends SubsystemBase {
  /** Creates a new ShoulderSubsystem. */

private final CANSparkMax shoulder1;
private final CANSparkMax shoulder2;
  
public ShoulderSubsystem() {

  shoulder1 = new CANSparkMax(24, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
  shoulder2 = new CANSparkMax(25, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);

}
 
    // Method to set speed for shoulder
    public void testShoulder(double shoulderSpeeed, double runTime ) {
      System.out.print("ACTIVE SHOULDER");
      shoulder1.set(shoulderSpeeed);
      shoulder2.set(-shoulderSpeeed);
      Timer.delay(runTime);
    }

    public void stopMotors() {
   
      shoulder1.stopMotor();
      shoulder2.stopMotor();
      
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    stopMotors();
  }
}
