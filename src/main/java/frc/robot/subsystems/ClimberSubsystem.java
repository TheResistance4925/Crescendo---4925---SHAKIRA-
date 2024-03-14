package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;


import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.XboxController;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClimberSubsystem extends SubsystemBase{
 
    TalonFX climber = new TalonFX(40);
    // private final PositionVoltage m_voltagePosition = new PositionalVoltage(0,0,true,0,0,false,false,false);
    // public XboxController driver;

    public ClimberSubsystem(){

    climber.setNeutralMode(NeutralModeValue.Brake);
    //climber.setControl();
    }

public double moveClimber;



    public void ClimbCommand() {
        // climber.setSelectedSensorPosition(0);
         climber.set(1);
// new WaitCommand(1);
//         stopMotors();
     }



     public void RetractCommand() {
        // climber.se(0);
             climber.set(-1);
// new WaitCommand(1);
//         stopMotors();
     }



    public void stopMotors(){

        climber.stopMotor();

    }
    // @Override
    // public void periodic() {
    //     moveClimber = XboxController.Axis.kRightY.value;
    //     //climber.set(moveClimber);
    // }
}
