package frc.robot;

import java.io.File;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.swervedrive.GyroBack;
import frc.robot.commands.swervedrive.drivebase.DriveToTarget;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDriveAdv;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;
import com.pathplanner.lib.auto.NamedCommands;

import frc.robot.subsystems.*;
import frc.robot.commands.*;
import frc.robot.Constants;



/**
* This class is where the bulk of the robot should be declared. Since Command-based is a
* "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
* periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
* subsystems, commands, and button mappings) should be declared here.
*/

public class RobotContainer {

/* --------------------------------------------------- Subsystem Declaration ---------------------------------------------------- */

public final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
public final BeamBreak m_BeamBreak = new BeamBreak();
public final LEDSubsystem m_LedSubsystem = new LEDSubsystem();
public final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();

/* --------------------------------------------------- Controller Setup ---------------------------------------------------- */

XboxController driverXbox = new XboxController(0);
XboxController driver2 = new XboxController(1);
XboxController tempController = new XboxController(3);


SendableChooser<Command> m_chooser = new SendableChooser<>();

/* --------------------------------------------------- SWERVE STUFF ---------------------------------------------------- */

 private final SwerveSubsystem m_drivetrain = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve/falcon"));
 /*---------------------------------------------------Drive Modes----------------------------------------------------*/
 
 /*CREDIT TO LAKERBOTS 8046, yoinked this swerve code  + LL stuff. :) thanks guys if you see this
 
  AbsoluteDrive closedAbsoluteDrive = new AbsoluteDrive(m_drivetrain,
                                                        // Applies deadbands and inverts controls because joysticks
                                                        // are back-right positive while robot
                                                          // controls are front-left positive
                                                        () -> MathUtil.applyDeadband(driverXbox.getLeftY(),
                                                                                     OperatorConstants.LEFT_Y_DEADBAND),
                                                        () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
                                                                                     OperatorConstants.LEFT_X_DEADBAND),
                                                        () -> -driverXbox.getRightX(),
                                                        () -> -driverXbox.getRightY());

  AbsoluteFieldDrive closedFieldAbsoluteDrive = new AbsoluteFieldDrive(m_drivetrain,
                                                                       () ->
                                                                           MathUtil.applyDeadband(driverXbox.getLeftY(),
                                                                                                  OperatorConstants.LEFT_Y_DEADBAND),
                                                                       () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
                                                                                                    OperatorConstants.LEFT_X_DEADBAND),
                                                                       () -> driverXbox.getRawAxis(5));//2
                                                                       */

  AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(m_drivetrain,
                                                                    () -> MathUtil.applyDeadband(driverXbox.getRawAxis(1),
                                                                                              OperatorConstants.LEFT_Y_DEADBAND),
                                                                    () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
                                                                                                OperatorConstants.LEFT_X_DEADBAND),
                                                                    () -> MathUtil.applyDeadband(driverXbox.getRightX(),
                                                                                                OperatorConstants.RIGHT_X_DEADBAND), 
                                                                    tempController::getYButtonPressed, 
                                                                    tempController::getAButtonPressed, 
                                                                    tempController::getXButtonPressed, 
                                                                    tempController::getBButtonPressed);
  
  AbsoluteDriveAdv closedAbsoluteDriveAdvtele = new AbsoluteDriveAdv(m_drivetrain,
                                                                    () -> MathUtil.applyDeadband(-driverXbox.getRawAxis(1),
                                                                                              OperatorConstants.LEFT_Y_DEADBAND),
                                                                    () -> MathUtil.applyDeadband(-driverXbox.getLeftX(),
                                                                                                OperatorConstants.LEFT_X_DEADBAND),
                                                                    () -> MathUtil.applyDeadband(-driverXbox.getRightX(),
                                                                                                OperatorConstants.RIGHT_X_DEADBAND), 
                                                                    tempController::getYButtonPressed, 
                                                                    tempController::getAButtonPressed, 
                                                                    tempController::getXButtonPressed, 
                                                                    tempController::getBButtonPressed);
/* 
  TeleopDrive simClosedFieldRel = new TeleopDrive(m_drivetrain,
                                                  () -> MathUtil.applyDeadband(driverXbox.getLeftY(),
                                                                               OperatorConstants.LEFT_Y_DEADBAND),
                                                  () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
                                                                               OperatorConstants.LEFT_X_DEADBAND),
                                                  () -> driverXbox.getRawAxis(2), () -> true);
  TeleopDrive closedFieldRel = new TeleopDrive(
      m_drivetrain,
      () -> MathUtil.applyDeadband(driverController.getRawAxis(1), OperatorConstants.LEFT_Y_DEADBAND),
      () -> MathUtil.applyDeadband(driverController.getRawAxis(0), OperatorConstants.LEFT_X_DEADBAND),
      () -> -driverController.getRawAxis(2), () -> true);

  //custom
  TeleopDrive DriveTest = new TeleopDrive(
      m_drivetrain,
      () -> MathUtil.applyDeadband(driverXbox.getRawAxis(1), OperatorConstants.LEFT_Y_DEADBAND),
      () -> MathUtil.applyDeadband(driverXbox.getRawAxis(0), OperatorConstants.LEFT_X_DEADBAND),
      () -> -driverXbox.getRawAxis(4), () -> true);
*/
  //m_drivetrain.setDefaultCommand(!RobotBase.isSimulation() ? closedAbsoluteDrive : closedFieldAbsoluteDrive);





public RobotContainer() {
 


/* ---------------------------------------------------PathPlanner Named Command Registartion---------------------------------------------------- */



NamedCommands.registerCommand("StopIntake",
new InstantCommand(() -> {
System.out.println("STOP INTAKE CHECK");
m_intakeSubsystem.stopMotors();
}, m_intakeSubsystem)
);

NamedCommands.registerCommand("SpeakerIntakeSequence", 
new InstantCommand(() -> {
System.out.println("Shooter Sequence Initiated");
m_intakeSubsystem.shooterSequence(3, 0.95, 0.25, 0.45, 0.1, -0.9, 0.1, 0.5, 0.6);
}, m_intakeSubsystem)
);

NamedCommands.registerCommand("SubwooferSpeakerShoudlerPose",
new InstantCommand(() -> {
System.out.println("Shoulder pose 80");
m_ArmSubsystem.poseShoulder(0, 80);
}, m_ArmSubsystem)
);

NamedCommands.registerCommand("SubwooferSpeakerWristPose",
new InstantCommand(() -> {
System.out.println("Wrist pose -44");
m_ArmSubsystem.poseWrist(0.75, -44);
}, m_ArmSubsystem)
);

NamedCommands.registerCommand("ArmLEDs",
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.armActive), m_LedSubsystem)
);

NamedCommands.registerCommand("IntakeLEDs",
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.intakeActive), m_LedSubsystem)
);

NamedCommands.registerCommand("ShooterLEDs",
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.shooterActive), m_LedSubsystem)
);

NamedCommands.registerCommand("BaseLEDs",
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
);

//LED JANK (PROB LESS TEMP THAN DESIRED)
ControlLEDs controlLEDsCommand = new ControlLEDs(m_BeamBreak, m_LedSubsystem);
scheduleCommand(controlLEDsCommand);
//CONFIGUREs BUTTONS
configureButtonBindings();
//SETS DRIVETRAIN DEFAULT COMMAND
m_drivetrain.setDefaultCommand(closedAbsoluteDriveAdv);
}



/* ---------------------------------------------------Button Binding Setup (ALL ON DRIVER2 / OPERATOR)---------------------------------------------------- */



private void configureButtonBindings() {

/* 
NOT DONE COMMANDS - EASIEST TO HARDEST - 

angled subwoofer? 

LL integration? --> VISON  
TAGS 
ML peice targeting 
*/

/* -==-==-==-X-==-==-==- Scores using a note from central Subwoofer -==-==-==-X-==-==-==-  */
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
System.out.println("Shoulder pose 80");
m_ArmSubsystem.poseShoulder(0, 80);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseWrist(0.75, -44);
}, m_ArmSubsystem),
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.shooterActive), m_LedSubsystem),
// Run the Shooter
new InstantCommand(() -> {
System.out.println("Shooter button pressed");
m_intakeSubsystem.shooterSequence(3, 0.95, 0.25, 0.45, 0.1, -0.9, 0.1, 0.5, 0.6);
}, m_intakeSubsystem),
new InstantCommand(() -> {
System.out.println("Shoulder ZERO");
m_ArmSubsystem.poseShoulder(0, 5);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("Wrist pose ZERO");
m_ArmSubsystem.poseWrist(0, 0);
}, m_ArmSubsystem),
// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));


/* -==-==-==-X-==-==-==- Scores using a note while on AMP (CAN BE USED TO EJECT BAD FEEDS + JAMS TOO!) -==-==-==-X-==-==-==-  */
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
m_ArmSubsystem.poseShoulder(0, 20);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("Wrist pose 0");
m_ArmSubsystem.poseWrist(0, -10);
}, m_ArmSubsystem),
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.shooterActive), m_LedSubsystem),
new InstantCommand(() -> {
System.out.println("Intake button activated");
m_intakeSubsystem.runIntake(1, -0.9, -0.9, 0.5);
}, m_intakeSubsystem),
new InstantCommand(() -> {
System.out.println("Shoulder ZERO");
m_ArmSubsystem.poseShoulder(0.5, 5);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("Wrist pose ZERO");
m_ArmSubsystem.poseWrist(0, 0);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("STOP INTAKE CHECK");
m_intakeSubsystem.stopMotors();
}, m_intakeSubsystem),
// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));


/* -==-==-==-X-==-==-==- Enables the intake WITHOUT moving the arm -==-==-==-X-==-==-==-  */
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
m_intakeSubsystem.runIntake(0, 0.6, 0.2, 0);
}, m_intakeSubsystem),
// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));


/* -==-==-==-X-==-==-==- Moves the Arm into the homed position **HARD STOPS THE INTAKE** -==-==-==-X-==-==-==-  */
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
System.out.println("Wrist pose ZERO");
m_ArmSubsystem.poseWrist(0, 0);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("Shoulder ZERO");
m_ArmSubsystem.poseShoulder(0.25, 5);
}, m_ArmSubsystem),
// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));


/* -==-==-==-X-==-==-==- Poses the arm infront of the Bot (BATTERY SIDE) to intake a peice while in motion -==-==-==-X-==-==-==-  */
Trigger groundIntakePose = new JoystickButton(driver2, XboxController.Button.kX.value);
groundIntakePose.onTrue(new SequentialCommandGroup(
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.armActive), m_LedSubsystem),
// Run the arm
new InstantCommand(() -> {
System.out.println("Shoulder pose 40");
m_ArmSubsystem.poseShoulder(0, 60);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("Wrist pose -35");
m_ArmSubsystem.poseWrist(1, -35);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("Shoulder pose 24");
m_ArmSubsystem.poseShoulder(2, 20);
}, m_ArmSubsystem),
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.intakeActive), m_LedSubsystem),
new InstantCommand(() -> {
System.out.println("Intake button activated");
m_intakeSubsystem.runIntake(1, 0.9, 0.2, 0);
}, m_intakeSubsystem),
// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));


/* -==-==-==-X-==-==-==- Primes the arm pose to climb **DOES NOT CLIMB, for that HOME (Y) the arm after engaged with the chain** -==-==-==-X-==-==-==-  */
Trigger climb = new JoystickButton(driver2, XboxController.Button.kB.value);
climb.onTrue(new SequentialCommandGroup(    
// Change LED color to newBlinkinValue
new InstantCommand(() -> m_LedSubsystem.customColor(Constants.LEDs.armActive), m_LedSubsystem),
// Run the arm
new InstantCommand(() -> {
System.out.println("STOP INTAKE CHECK");
m_intakeSubsystem.stopMotors();
}, m_intakeSubsystem),
new InstantCommand(() -> {
System.out.println("Shoulder CLIMB PREP");
m_ArmSubsystem.poseShoulder(0, 120);
}, m_ArmSubsystem),
new InstantCommand(() -> {
System.out.println("Wrist pose TUCKED");
m_ArmSubsystem.poseWrist(0, 0);
}, m_ArmSubsystem),
// Revert LED color back to initialBlinkinValue
new InstantCommand(() -> m_LedSubsystem.baseColor(), m_LedSubsystem)
));



}

/* --------------------------------------------------- Everything Thats not a Button Binding Here ---------------------------------------------------- */


//LED JANK
private void scheduleCommand(Command command) {
// Schedule command to run periodically
command.schedule();
}

/**
* Use this to pass the autonomous command to the main {@link Robot} class.
*
* @return the command to run in autonomous
*/

/* --------------------------------------------------- AutoChooser Selection Command ---------------------------------------------------- */

public Command getAutonomousCommand()
{
  return m_chooser.getSelected();
}

/* --------------------------------------------------- Swerve OLD Content ---------------------------------------------------- */

// public void setDriveMode()
//   {
//     // m_drivetrain.setDefaultCommand();
    
//   }

/* --------------------------------------------------- Reduces Bullying ---------------------------------------------------- */

  public void setMotorBrake(boolean brake)
  {
    m_drivetrain.setMotorBrake(brake);
  }

}
