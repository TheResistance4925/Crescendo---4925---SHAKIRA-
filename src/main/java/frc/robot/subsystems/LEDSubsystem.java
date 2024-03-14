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
import frc.robot.Constants;
import frc.robot.subsystems.BeamBreak;
public class LEDSubsystem extends SubsystemBase {
  
  /** Creates a new LEDSubsystem. */

  Spark m_blinkin = new Spark(9);
  // public final BeamBreak m_BeamBreak = new BeamBreak();
  public LEDSubsystem() {
  
       Optional<Alliance> ally = DriverStation.getAlliance();
if (ally.isPresent()) {
    if (ally.get() == Alliance.Red) {
      m_blinkin.set(Constants.LEDs.redTeam);
    }
    if (ally.get() == Alliance.Blue) {
      m_blinkin.set(Constants.LEDs.blueTeam);
    }
}
else {
  m_blinkin.set(Constants.LEDs.noTeam);
} 

  }

  public void customColor(double blinkinValue) {

    m_blinkin.set(blinkinValue);
  }


  public void baseColor() {
   
       Optional<Alliance> ally = DriverStation.getAlliance();
if (ally.isPresent()) {
    if (ally.get() == Alliance.Red) {
      m_blinkin.set(Constants.LEDs.redTeam);
    }
    if (ally.get() == Alliance.Blue) {
      m_blinkin.set(Constants.LEDs.blueTeam);
    }
}
else {
  m_blinkin.set(Constants.LEDs.noTeam);
} 

}

public void noteCheck(){




}


@Override
  public void periodic() {
    // This method will be called once per scheduler run
   // noteCheck();
  }
}
