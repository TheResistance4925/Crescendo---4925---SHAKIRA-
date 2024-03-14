package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
import edu.wpi.first.wpilibj.AnalogInput;
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
public final ClimberSubsystem m_ClimberSubsystem = new ClimberSubsystem();
/*Controler Configuration*/
private final XboxController driver = new XboxController(0);
private final XboxController driver2 = new XboxController(1);

/* Drive Controls */
private final int translationAxis = XboxController.Axis.kLeftY.value;
private final int strafeAxis = XboxController.Axis.kLeftX.value;
private final int rotationAxis = XboxController.Axis.kRightX.value;
private final int climbStick = XboxController.Axis.kRightY.value;

/* Driver Buttons */
private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
private final JoystickButton aprilAlign = new JoystickButton(driver, XboxController.Button.kB.value);

/* Subsystems */
private final Swerve s_Swerve = new Swerve();

// Sendable Choose
private final SendableChooser<Command> autoChooser;


 // simple proportional turning control with Limelight.
  // "proportional control" is a control algorithm in which the output is proportional to the error.
  // in this case, we are going to return an angular velocity that is proportional to the 
  // "tx" value from the Limelight.

  double limelight_aim_proportional()
  {    
    // kP (constant of proportionality)
    // this is a hand-tuned number that determines the aggressiveness of our proportional control loop
    // if it is too high, the robot will oscillate.
    // if it is too low, the robot will never reach its target
    // if the robot never turns in the correct direction, kP should be inverted.
    double kP = .035;

    // tx ranges from (-hfov/2) to (hfov/2) in degrees. If your target is on the rightmost edge of 
    // your limelight 3 feed, tx should return roughly 31 degrees.
    double targetingAngularVelocity = LimelightHelpers.getTX("limelight") * kP;

    // convert to radians per second for our drive method
    targetingAngularVelocity *= Constants.Swerve.maxAngularVelocity;

    //invert since tx is positive when the target is to the right of the crosshair
    targetingAngularVelocity *= -1.0;

    return targetingAngularVelocity;
  }

  // simple proportional ranging control with Limelight's "ty" value
  // this works best if your Limelight's mount height and target mount height are different.
  // if your limelight and target are mounted at the same or similar heights, use "ta" (area) for target ranging rather than "ty"
  double limelight_range_proportional()
  {    
    double kP = .1;
    double targetingForwardSpeed = LimelightHelpers.getTY("limelight") * kP;
    targetingForwardSpeed *= Constants.Swerve.maxSpeed;
    targetingForwardSpeed *= -1.0;
    return targetingForwardSpeed;
  }

  /* The container for the robot. Contains subsystems, OI devices, and commands. */

public RobotContainer() {

configureButtonBindings();

s_Swerve.setDefaultCommand(new TeleopSwerve(s_Swerve, () -> driver.getRawAxis(translationAxis), () -> driver.getRawAxis(strafeAxis), () -> -driver.getRawAxis(rotationAxis), () -> robotCentric.getAsBoolean()));

// if(driver.getBButton()) {
//     final var rot_limelight = limelight_aim_proportional();
//     strafeAxis = rot_limelight;

        // final var forward_limelight = limelight_range_proportional();
        // translationAxis = forward_limelight;
// }
    // Build an auto chooser. This will use Commands.none() as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();

    // Another option that allows you to specify the default auto by its name
    // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

    SmartDashboard.putData("Auto Chooser", autoChooser);

//NAMED COMMANDS LIST - does what their names say.
// NamedCommands.registerCommand("AmpScore", );
// NamedCommands.registerCommand("SpeakerShort", );
// NamedCommands.registerCommand("SpeakerLong", );
// NamedCommands.registerCommand("GroundIntake", );
// NamedCommands.registerCommand("HumanPlayerIntake", );
// NamedCommands.registerCommand("ClimbUp", );
// NamedCommands.registerCommand("ClimbDown", );
// NamedCommands.registerCommand("HomeArm", );

NamedCommands.registerCommand("AmpScore", new SequentialCommandGroup(
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
new WaitCommand(1.5),
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
NamedCommands.registerCommand("SpeakerShort", new SequentialCommandGroup(
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
));
NamedCommands.registerCommand("SpeakerLong", new SequentialCommandGroup(
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
m_ArmSubsystem.poseShoulder(77.5);
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
m_intakeSubsystem.shooterSequence(0.95, 0.25, 0.45, 0.1, -0.8, 0.1, 1.5, 0.6);
}, m_intakeSubsystem),
new InstantCommand(() -> {
System.out.println("ARM ZERO");
m_ArmSubsystem.homeArm();
}, m_ArmSubsystem),
// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));
NamedCommands.registerCommand("GroundIntake", new SequentialCommandGroup(
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.armActive), m_LedSubsystem),
// Run the arm
// new InstantCommand(() -> {
// System.out.println("GroundIntake");
// m_ArmSubsystem.poseShoulder(80);
// }, m_ArmSubsystem),
// new WaitCommand(0.75),
new InstantCommand(() -> {
System.out.println("GroundIntake");
m_ArmSubsystem.groundIntakePose();
}, m_ArmSubsystem),
new WaitCommand(3),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseShoulder(21);
}, m_ArmSubsystem),
new WaitCommand(0.5),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseWrist(-33);
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
NamedCommands.registerCommand("HumanPlayerIntake", new SequentialCommandGroup(
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
NamedCommands.registerCommand("ClimbUp", new SequentialCommandGroup(    
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.climberActive), m_LedSubsystem),
// Run the cimber
new InstantCommand(() -> {
System.out.println("Climber Stuff");
m_ClimberSubsystem.ClimbCommand();
}, m_ClimberSubsystem),

new WaitCommand(4.5/2),

new InstantCommand(() -> {
System.out.println("Climber Stuff");
m_ClimberSubsystem.stopMotors();
}, m_ClimberSubsystem),

new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));
NamedCommands.registerCommand("ClimbDown", new SequentialCommandGroup(    
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.climberActive), m_LedSubsystem),
// Run the cimber
new InstantCommand(() -> {
System.out.println("Climber Stuff");
m_ClimberSubsystem.RetractCommand();
}, m_ClimberSubsystem),

new WaitCommand(4.5/2),

new InstantCommand(() -> {
System.out.println("Climber Stuff");
m_ClimberSubsystem.stopMotors();
}, m_ClimberSubsystem),

new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));
NamedCommands.registerCommand("HomeArm", new SequentialCommandGroup(
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.armActive), m_LedSubsystem),
// Run the arm
new InstantCommand(() -> {
System.out.println("STOP INTAKE CHECK");
m_intakeSubsystem.stopMotors();
}, m_intakeSubsystem),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseShoulder(60);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseWrist(-44);
}, m_ArmSubsystem),
new WaitCommand(1),
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
}



/*
* Use this method to define your button->command mappings. Buttons can be created by
* instantiating a {@link GenericHID} or one of its subclasses ({@link
* edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
* edu.wpi.first.wpilibj2.command.button.JoystickButton}.
*/

private void configureButtonBindings() {
    
new Trigger(m_BeamBreak::isBeamBroken).whileTrue(
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem));
    
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
));

Trigger eliteScore = new JoystickButton(driver2, XboxController.Button.kLeftStick.value);
eliteScore.onTrue(new SequentialCommandGroup(
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
m_ArmSubsystem.poseShoulder(77.5);
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
m_intakeSubsystem.shooterSequence(0.95, 0.25, 0.45, 0.1, -0.8, 0.1, 1.5, 0.6);
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
new WaitCommand(1.5),
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
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseShoulder(60);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseWrist(-44);
}, m_ArmSubsystem),
new WaitCommand(1),
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
// new InstantCommand(() -> {
// System.out.println("GroundIntake");
// m_ArmSubsystem.poseShoulder(80);
// }, m_ArmSubsystem),
// new WaitCommand(0.75),
new InstantCommand(() -> {
System.out.println("GroundIntake");
m_ArmSubsystem.groundIntakePose();
}, m_ArmSubsystem),
new WaitCommand(3),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseShoulder(21);
}, m_ArmSubsystem),
new WaitCommand(0.5),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseWrist(-33);
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



Trigger climber = new JoystickButton(driver2, XboxController.Button.kB.value);
climber.onTrue(new SequentialCommandGroup(    
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.climberActive), m_LedSubsystem),
// Run the cimber
new InstantCommand(() -> {
System.out.println("Climber Stuff");
m_ClimberSubsystem.ClimbCommand();
}, m_ClimberSubsystem),

new WaitCommand(4.5/2),

new InstantCommand(() -> {
System.out.println("Climber Stuff");
m_ClimberSubsystem.stopMotors();
}, m_ClimberSubsystem),

new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));

Trigger climber2 = new JoystickButton(driver2, XboxController.Button.kRightStick.value);
climber2.onTrue(new SequentialCommandGroup(    
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.climberActive), m_LedSubsystem),
// Run the cimber
new InstantCommand(() -> {
System.out.println("Climber Stuff");
m_ClimberSubsystem.RetractCommand();
}, m_ClimberSubsystem),

new WaitCommand(4.5/2),

new InstantCommand(() -> {
System.out.println("Climber Stuff");
m_ClimberSubsystem.stopMotors();
}, m_ClimberSubsystem),

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
// return new shootLeave(m_ArmSubsystem, m_intakeSubsystem, m_LedSubsystem);
return autoChooser.getSelected();
}

}
