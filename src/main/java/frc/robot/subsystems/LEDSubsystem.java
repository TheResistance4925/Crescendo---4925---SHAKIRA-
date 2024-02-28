// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Optional;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class LEDSubsystem extends SubsystemBase {
  /** Creates a new LEDSubsystem. */

  Spark m_blinkin = new Spark(9);

  public LEDSubsystem() {
  
    m_blinkin.set(0.71);

  }

  
  public void orange() {

    m_blinkin.set(0.65);
    
  }

  public void hotPink() {

    m_blinkin.set(0.57);
    
  }

  public void lime() {

    m_blinkin.set(0.73);
    
  }

  public void gold() {

    m_blinkin.set(0.67);
  }

  public void custonColor(double blinkinValue) {

    m_blinkin.set(blinkinValue);
  }


  public void baseColor() {
   
    Optional<Alliance> ally = DriverStation.getAlliance();
if (ally.isPresent()) {
    if (ally.get() == Alliance.Red) {
      m_blinkin.set(0.61);
    }
    if (ally.get() == Alliance.Blue) {
      m_blinkin.set(0.85);
    }
}
else {
  m_blinkin.set(0.93);
}

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run



  }
}
