package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.lib.PIDGains;
import frc.lib.util.COTSTalonFXSwerveConstants;
import frc.lib.util.SwerveModuleConstants;
import swervelib.math.Matter;
import swervelib.parser.PIDFConfig;

public final class Constants {

 public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32 lbs * kg per pound //TUNE TO THE 4925 BOT//
  public static final Matter CHASSIS    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
  public static final double LOOP_TIME  = 0.13; //s, 20ms + 110ms sprk max velocity lag

  public static final class Auton
  {

    public static final PIDFConfig TranslationPID     = new PIDFConfig(0.7, 0, 0);
    public static final PIDFConfig angleAutoPID = new PIDFConfig(0.4, 0, 0.01);

    public static final double MAX_ACCELERATION = 2;
  }

  public static final class Drivebase
  {

    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static class OperatorConstants
  {

    // Joystick Deadband
    public static final double LEFT_X_DEADBAND = 0.1; //used to be .01
    public static final double LEFT_Y_DEADBAND = 0.1; //used to be .01
    public static final double RIGHT_X_DEADBAND = 0.1; //used to be .01
    public static final double TURN_CONSTANT = 24;//used to be 6  - 3/1/24
  }

    // Feedforward constants
    public static final class Arm {
        
        public static final double SHOULDER_GEAR_RATIO = 240.0;
        public static final double WRIST_GEAR_RATIO = 60.0;
        public static final double NEO_STALL_TORQUE = 2.6; // Stall torque of NEO in Nm
        public static final double NEO_FREE_SPEED = 5676.0 * 2 * Math.PI / 60; // Free speed of NEO in rad/s
    
        public static final double ENCODER_COUNTS_PER_REVOLUTION = 1;//8192; //For the dutycycle absolute encoders



        public static final double LENGTH_SEGMENT_1 = 0.83;  // Length of segment 1 in meters
        public static final double LENGTH_SEGMENT_2 = 0.38;  // Length of segment 2 in meters
        public static final double WEIGHT_END_SEGMENT_1 = 25;  // Weight at the end of segment 1 in kg
        public static final double WEIGHT_END_SEGMENT_2 = 25;  // Weight at the end of segment 2 in kg
        public static final double GRAVITY_CONSTANT = 9.81; // Gravity constant in m/s^2

        // PID constants for shoulder joint
        public static final class SHOULDER_PID {
            public static final double kP = 0.1;
            public static final double kI = 0.0;
            public static final double kD = 0.0;
            public static final double TOLERANCE = 1.0; // Tolerance for angle error in degrees
        }

        // PID constants for wrist joint
        public static final class WRIST_PID {
            public static final double kP = 0.1;
            public static final double kI = 0.0;
            public static final double kD = 0.0;
            public static final double TOLERANCE = 1.0; // Tolerance for angle error in degrees
        }
    }
 public static final class LEDs {
        /* COLORS FOR BLINKIN CONTROL */
        public static final double armActive = -0.99;
        public static final double intakeActive = -0.89 ;
        public static final double shooterActive = -0.87;
        public static final double redTeam = -0.85;
        public static final double blueTeam = -0.83;
        public static final double noTeam = 0.91;
        public static final double hasNote = 0.57;

      //  public static final double = n; //EXAMPLE LINE

  }



}