package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ClimberSubsystem extends SubsystemBase{
 
    TalonFX climber = new TalonFX(40);
    public ClimberSubsystem(){

    climber.setNeutralMode(NeutralModeValue.Brake);
    }
    public void RaiseCommand() {
            
        climber.set(0.70);
    }

    public void RetractCommand() {
        
        climber.set(-.70);
    }
}
