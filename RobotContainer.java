package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
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
    public final ArmSubsystem m_armSubsystem = new ArmSubsystem( 25,  27,  24,  26, 24, 26);
    public final ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();
    public final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
    public final LEDsSubsystem m_ledsSubsystem = new LEDsSubsystem();
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

        // Arm Control

        Trigger xboxButton1 = new JoystickButton(driver, XboxController.Button.kA.value);
    xboxButton1.onTrue(new InstantCommand(() -> {
        System.out.println("Pose0 Pressed");
        m_armSubsystem.setArmPosition( 0, 0);
    }));

    Trigger xboxButton2 = new JoystickButton(driver, XboxController.Button.kB.value);
    xboxButton2.onTrue(new InstantCommand(() -> {
        System.out.println("Pose45 Pressed");
        m_armSubsystem.setArmPosition(45, 0);
    }));

        // Toaster (INTAKE+ SHOOTER) Control
  Trigger xboxButton21 = new JoystickButton(driver2, XboxController.Button.kA.value);
    xboxButton21.onTrue(new InstantCommand(() -> {
        System.out.println("Intake");
        m_intakeSubsystem.driveMotors(0,0);
    }));

    Trigger xboxButton22 = new JoystickButton(driver2, XboxController.Button.kB.value);
    xboxButton22.whileTrue(new InstantCommand(() -> {
        System.out.println("Shooting");
        m_intakeSubsystem.driveMotors(1,1);
    }));


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
