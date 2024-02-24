package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.RobotContainer;

public class LEDsSubsystem extends SubsystemBase {
  
  Spark m_blinkin = new Spark(9);
  
  public LEDsSubsystem () {

    
       
        m_blinkin.set(.71); 
    
    }
    @Override
    public void periodic() {  
        

    }
}
    
   
