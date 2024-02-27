// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Timer;

public class WristSubsystem extends SubsystemBase {
  /** Creates a new ShoulderSubsystem. */

private final CANSparkMax wrist1;
private final CANSparkMax wrist2;
  
public WristSubsystem() {

  wrist1 = new CANSparkMax(26, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
  wrist2 = new CANSparkMax(27, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);

}
 
    // Method to set speed for shoulder
    public void testWrist(double wristSpeed, double runTime ) {
      System.out.print("ACTIVE WRIST");
      wrist1.set(wristSpeed);
      wrist2.set(-wristSpeed);
      Timer.delay(runTime);
    }


    
    public void stopMotors() {
   
      wrist1.stopMotor();
      wrist2.stopMotor();
      
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    stopMotors();
  }
}
