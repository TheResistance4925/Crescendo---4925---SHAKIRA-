package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotContainer;

public class BeamBreak extends SubsystemBase {

  AnalogInput beam = new AnalogInput(0);
  public double beamVolts;
  
  public BeamBreak() {
    
  }
    
  
    
  //@Override
  public void periodic() {
    // This method will be called once per scheduler run
    beamVolts = beam.getAverageVoltage();
    SmartDashboard.putNumber("BeamAverageVoltage", beamVolts);
    SmartDashboard.putBoolean( "Is Beam broken?", isBeamBroken());
  }    
  
    public boolean isBeamBroken() {
    if(beamVolts >1.0){
      return true;
    }else{
      return false;
    }
    }
    public final Trigger hasNote = new Trigger(this::isBeamBroken);
    public final Trigger noNote = new Trigger(this::isBeamBroken);
  }


