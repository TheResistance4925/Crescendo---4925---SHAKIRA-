package frc.robot.autos;

import frc.robot.Constants;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.Constants;


public class shootLeave  extends SequentialCommandGroup{

    public final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
    public final BeamBreak m_BeamBreak = new BeamBreak();
    public final LEDSubsystem m_LedSubsystem = new LEDSubsystem();
    public final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();

    public shootLeave(ArmSubsystem m_ArmSubsystem, IntakeSubsystem intakeSubsystem,LEDSubsystem m_LedSubsystem){
        
        addCommands(
new SequentialCommandGroup(
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.armActive), m_LedSubsystem),
// Run the arm
new InstantCommand(() -> {
System.out.println("STOP INTAKE CHECK");
m_intakeSubsystem.stopMotors();
}, m_intakeSubsystem),
new InstantCommand(() -> {
System.out.println("Intake button activated");
m_intakeSubsystem.runIntake( 0.25, 0, 0);
}, m_intakeSubsystem),
new InstantCommand(() -> {
System.out.println("Shoulder pose 100");
m_ArmSubsystem.poseShoulder(65);
}, m_ArmSubsystem),
new WaitCommand(0.75),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseWrist(-38);
}, m_ArmSubsystem),
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.shooterActive), m_LedSubsystem),
// Run the Shooter
new WaitCommand(3),
new InstantCommand(() -> {
System.out.println("Shooter button pressed");
m_intakeSubsystem.shooterSequence(0.95, 0.25, 0.45, 0.1, -0.8, 0.1, 1, 0.6);
}, m_intakeSubsystem),

new InstantCommand(() -> {
System.out.println("ARM ZERO");
m_ArmSubsystem.homeArm();
}, m_ArmSubsystem),

// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
)
        );
    }
}