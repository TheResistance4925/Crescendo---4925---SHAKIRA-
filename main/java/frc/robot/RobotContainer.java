package frc.robot;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

/**
* This class is where the bulk of the robot should be declared. Since Command-based is a
* "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
* periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
* subsystems, commands, and button mappings) should be declared here.
*/

public class RobotContainer {

//subsystems
public final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
public final BeamBreak m_BeamBreak = new BeamBreak();
public final LEDSubsystem m_LedSubsystem = new LEDSubsystem();
public final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();

/*Controler Configuration*/
private final XboxController driver = new XboxController(0);
private final XboxController driver2 = new XboxController(1);

/* Drive Controls */
private final int translationAxis = XboxController.Axis.kLeftY.value;
private final int strafeAxis = XboxController.Axis.kLeftX.value;
private final int rotationAxis = XboxController.Axis.kRightX.value;

/* Driver Buttons */
private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
private final JoystickButton aprilAlign = new JoystickButton(driver, XboxController.Button.kB.value);
/* Subsystems */
private final Swerve s_Swerve = new Swerve();

/* The container for the robot. Contains subsystems, OI devices, and commands. */
public RobotContainer() {

// Schedule command to move arm to zeroed position when robot is enabled
// Command zeroArmCommand = new MoveArmToAngle(m_armSubsystem, m_armSubsystem2, 0.0, 0.0);
// zeroArmCommand.schedule();

ControlLEDs controlLEDsCommand = new ControlLEDs(m_BeamBreak, m_LedSubsystem);
scheduleCommand(controlLEDsCommand);

configureButtonBindings();

s_Swerve.setDefaultCommand(new TeleopSwerve(s_Swerve, () -> -driver.getRawAxis(translationAxis), () -> -driver.getRawAxis(strafeAxis), () -> -driver.getRawAxis(rotationAxis), () -> robotCentric.getAsBoolean()));
}


// Configure shoulder control using Xbox controller input



/*
* Use this method to define your button->command mappings. Buttons can be created by
* instantiating a {@link GenericHID} or one of its subclasses ({@link
* edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
* edu.wpi.first.wpilibj2.command.button.JoystickButton}.
*/

private void scheduleCommand(Command command) {
// Schedule command to run periodically
command.schedule();
}

private void configureButtonBindings() {


    
//zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));     



Trigger shooterBasicScore = new JoystickButton(driver2, XboxController.Button.kLeftBumper.value);
shooterBasicScore.onTrue(new SequentialCommandGroup(
// Change LED color to newBlinkinValue
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
m_ArmSubsystem.poseShoulder(85);
}, m_ArmSubsystem),
new WaitCommand(0.75),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseWrist(-44);
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
));



Trigger ampScore = new JoystickButton(driver2, XboxController.Button.kRightBumper.value);
ampScore.onTrue(new SequentialCommandGroup(
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.armActive), m_LedSubsystem),
// Run the arm
new InstantCommand(() -> {
System.out.println("STOP INTAKE CHECK");
m_intakeSubsystem.stopMotors();
}, m_intakeSubsystem),
new InstantCommand(() -> {
System.out.println("Shoulder pose 100");
m_ArmSubsystem.poseShoulder(20);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseWrist(-10);
}, m_ArmSubsystem),
new WaitCommand(1),
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.shooterActive), m_LedSubsystem),
new InstantCommand(() -> {
System.out.println("Intake button activated");
m_intakeSubsystem.runIntake(-0.9, -0.9, 0.5);
}, m_intakeSubsystem),
new InstantCommand(() -> {
System.out.println("ARM ZERO");
m_ArmSubsystem.homeArm();
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("STOP INTAKE CHECK");
m_intakeSubsystem.stopMotors();
}, m_intakeSubsystem),
// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));



Trigger staticIntakeNote = new JoystickButton(driver2, XboxController.Button.kA.value);
staticIntakeNote.onTrue(new SequentialCommandGroup(
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.intakeActive), m_LedSubsystem),
// Run the intake sequence
new InstantCommand(() -> {
System.out.println("STOP INTAKE CHECK");
m_intakeSubsystem.stopMotors();
}, m_intakeSubsystem),
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.intakeActive), m_LedSubsystem),
new InstantCommand(() -> {
System.out.println("Intake button activated");
m_intakeSubsystem.runIntake(0.6, 0.2, 0);
}, m_intakeSubsystem),
// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));



Trigger home = new JoystickButton(driver2, XboxController.Button.kY.value);
home.onTrue(new SequentialCommandGroup(
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.armActive), m_LedSubsystem),
// Run the arm
new InstantCommand(() -> {
System.out.println("STOP INTAKE CHECK");
m_intakeSubsystem.stopMotors();
}, m_intakeSubsystem),

new InstantCommand(() -> {
System.out.println("ARM ZERO");
m_ArmSubsystem.homeArm();
}, m_ArmSubsystem),

new WaitCommand(1),

new InstantCommand(() -> {
System.out.println("Shooter button pressed");
m_intakeSubsystem.shooterSequence(0.95, 0.25, 0.1, 0, 0, 0, 0, 0);
}, m_intakeSubsystem),

new WaitCommand(1),

new InstantCommand(() -> {
System.out.println("STOP INTAKE CHECK");
m_intakeSubsystem.stopMotors();
}, m_intakeSubsystem),

// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));



Trigger groundIntakePose = new JoystickButton(driver2, XboxController.Button.kX.value);
groundIntakePose.onTrue(new SequentialCommandGroup(
// Change LED color to newBlinkinValue

new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.armActive), m_LedSubsystem),
// Run the arm
new InstantCommand(() -> {
System.out.println("GroundIntake");
m_ArmSubsystem.groundIntakePose();
}, m_ArmSubsystem),
// Run the Intake
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.intakeActive), m_LedSubsystem),
new WaitCommand(1),
new InstantCommand(() -> {
System.out.println("Intake button activated");
m_intakeSubsystem.runIntake( 0.9, 0.2, 0);
}, m_intakeSubsystem),
// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));



Trigger climb = new JoystickButton(driver2, XboxController.Button.kB.value);
climb.onTrue(new SequentialCommandGroup(    
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.armActive), m_LedSubsystem),
// Run the arm
// new InstantCommand(() -> {
// System.out.println("STOP INTAKE CHECK");
// m_intakeSubsystem.stopMotors();
// }, m_intakeSubsystem),
// new InstantCommand(() -> {
// System.out.println("Shoulder CLIMB PREP");
// m_ArmSubsystem.poseShoulder(120);
// }, m_ArmSubsystem),
// new InstantCommand(() -> {
// System.out.println("Wrist pose TUCKED");
// m_ArmSubsystem.poseWrist(5);
// }, m_ArmSubsystem),
// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));



}

/**
* Use this to pass the autonomous command to the main {@link Robot} class.
*
* @return the command to run in autonomous
*/
public Command getAutonomousCommand() {
// An ExampleCommand will run in autonomous
return new exampleAuto(s_Swerve);
}
}
