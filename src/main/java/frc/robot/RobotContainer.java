package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */

public class RobotContainer {

    //private static RobotContainer m_robotContainer = new RobotContainer();
    //subsystems
  
    public final ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();
    public final MotorTestSubsystem m_motorTestSubsystem = new MotorTestSubsystem();
    public final ShoulderSubsystem m_shoulderSubsystem = new ShoulderSubsystem();
    public final WristSubsystem m_wristSubsystem = new WristSubsystem();
    /* Controllers */
    private final XboxController driver = new XboxController(0);
    private final XboxController driver2 = new XboxController(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    /* MOTOR TEST CONTROLS 

    private final JoystickButton shoulderUp = new JoystickButton(driver2, XboxController.Button.kA.value);
    private final JoystickButton shoulderDown = new JoystickButton(driver2, XboxController.Button.kA.value);
    private final JoystickButton wristUp = new JoystickButton(driver2, XboxController.Button.kA.value);
    private final JoystickButton wristDown = new JoystickButton(driver2, XboxController.Button.kA.value);
    private final JoystickButton intakePositive = new JoystickButton(driver2, XboxController.Button.kA.value);
    private final JoystickButton intakeNegative = new JoystickButton(driver2, XboxController.Button.kA.value);
    
    */


    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    /*Arm Control */
    // private final JoystickButton Pose0 = new JoystickButton(driver, XboxController.Button.kA.value);
    // private final JoystickButton Pose45 = new JoystickButton(driver, XboxController.Button.kX.value);
   
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        
        // Climber Controls

        Trigger xboxButton5 = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
        xboxButton5.onTrue(new InstantCommand (() -> m_climberSubsystem.raiseClaw()));
                   Trigger xboxButton6 = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
        xboxButton6.onTrue(new InstantCommand (() -> m_climberSubsystem.lowerClaw()));

        // Swerve Gyro Code

        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));



// ////////////////////////
// ///MOTOR TEST COMMANDS///
// /////////////////////////


///SHOULDER///

   
    Trigger xboxButton11 = new JoystickButton(driver2, XboxController.Button.kB.value);
    xboxButton11.whileTrue(new InstantCommand(() -> {
        System.out.print("SHOULDER DOWN?");
        m_shoulderSubsystem.testShoulder( -0.5, 0.5);
    }));

    Trigger xboxButton1 = new JoystickButton(driver2, XboxController.Button.kA.value);
    xboxButton1.whileTrue(new InstantCommand(() -> {
        System.out.print("SHOULDER UP?");
        m_shoulderSubsystem.testShoulder( 0.5, 0.5);
    }));

///WRIST///

    Trigger xboxButton2 = new JoystickButton(driver2, XboxController.Button.kX.value);
    xboxButton2.whileTrue(new InstantCommand(() -> {
        System.out.print("WRIST UP?");
        m_wristSubsystem.testWrist( 0.5, 0.5);
    }));

    Trigger xboxButton22 = new JoystickButton(driver2, XboxController.Button.kY.value);
    xboxButton22.whileTrue(new InstantCommand(() -> {
        System.out.print("WRIST DOWN?");
        m_wristSubsystem.testWrist( -0.5, 0.5);
    }));
    
///INTAKE///

    Trigger xboxButton3 = new JoystickButton(driver2, XboxController.Button.kLeftBumper.value);
    xboxButton3.whileTrue(new InstantCommand(() -> {
        System.out.print("INTAKING INITIATED");
        m_motorTestSubsystem.testIntake( -0.5, 0.6, 2);
    }));
    
    Trigger xboxButton33 = new JoystickButton(driver2, XboxController.Button.kRightBumper.value);
    xboxButton33.whileTrue(new InstantCommand(() -> {
        System.out.print("INTAKE REGURGITATING");
        m_motorTestSubsystem.testIntake( 0.5, -0.6, 2);
    }));

// ///////////////////////////////
// /// MOTOR TEST COMMANDS DONE///
// ///////////////////////////////

    
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
