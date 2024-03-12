package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.XboxController;

public class ClimberSubsystem extends SubsystemBase{
 
    TalonFX climber = new TalonFX(40);
    public XboxController driver;
    public ClimberSubsystem(){

    climber.setNeutralMode(NeutralModeValue.Brake);
  
    }

public double moveClimber;

//     public void ClimbCommand() {

//         climber.set(Constants.Climber.ClimberUp);
   
//     }

//    public void ClimbDownCommand() {

//         climber.set(Constants.Climber.ClimberDown);
   
//     }

    public void stopMotors(){

        climber.stopMotor();

    }
    @Override
    public void periodic() {
        moveClimber = XboxController.Axis.kRightY.value;
        //climber.set(moveClimber);
    }
}
