package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
     
   //Double Check forwardChannel andreverseChannel
    DoubleSolenoid leftClaw = new DoubleSolenoid(18,PneumaticsModuleType.REVPH, 0, 1);
    DoubleSolenoid rightClaw = new DoubleSolenoid(18,PneumaticsModuleType.REVPH, 14, 15);


//Raises Claw
public Command raiseClaw() {
    leftClaw.set(DoubleSolenoid.Value.kForward);
    rightClaw.set(DoubleSolenoid.Value.kForward);
    return runOnce(
        () -> {

        });
    
}

public Command lowerClaw() {
    leftClaw.set(DoubleSolenoid.Value.kReverse);
    rightClaw.set(DoubleSolenoid.Value.kReverse);
    return runOnce(
        () -> {

        });
    
}
/* 
@Override
public void initSendable(SendableBuilder builder) {
    super .initSendable(builder);
    builder.addBooleanProperty("extended", this::leftClaw.kForward, null);
}
*/
}
