// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Timer;
public class IntakeSubsystem extends SubsystemBase {
    

 
   
    private final CANSparkMax shooter1;
    private final CANSparkMax shooter2;
    private final CANSparkMax intake1;
  
    
    public IntakeSubsystem() {
        // Initialize NEO motors with device IDs
     
     
        shooter1 = new CANSparkMax(29, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
        shooter2 = new CANSparkMax(30, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
        intake1 = new CANSparkMax(28, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
        
        shooter1.setSmartCurrentLimit(60);
        shooter2.setSmartCurrentLimit(60);
        intake1.setSmartCurrentLimit(60);
        //shooter1.setIdleMode();
        //shooter2.setIdleMode();
       // intake1.setIdleMode();

        shooter1.burnFlash();
        shooter2.burnFlash();
        intake1.burnFlash();

        // Set default command if needed
        //setDefaultCommand(new YourDefaultCommand(this));
    }

   // Method to set speed for intake
   public void runIntake(double intakeSpeed, double shooterSpeed, double runTime) {
   
    System.out.print("ACTIVE INTAKE");
    shooter1.set(shooterSpeed);
    shooter2.set(shooterSpeed);
    intake1.set(-intakeSpeed);
    Timer.delay(runTime);
  }
 
public void shooterSequence
(double primeIntakeSpeed, double primeShooterSpeed, double primeTime, 
double revIntakeSpeed, double revShooterSpeed, 
double revIntakeTime, double revShooterTime, 
double fireTime) {

    System.out.print("FIRE IN THE HOLEEEE!");

    //Positions the note in the intake
    shooter1.set(-primeShooterSpeed);
    shooter2.set(-primeShooterSpeed);
    intake1.set(-primeIntakeSpeed);
    //Time that this process takes
    Timer.delay(primeTime);  
    //Stops the motors to hold the notes pose
    shooter1.stopMotor();
    shooter2.stopMotor();
    intake1.stopMotor();
    //A breif push to insure the note position
    intake1.set(revIntakeSpeed);
    Timer.delay(revIntakeTime);
    intake1.stopMotor();
    Timer.delay(0.25);
    //Revs the shooters to full speed
    shooter1.set(-revShooterSpeed);
    shooter2.set(-revShooterSpeed);
    Timer.delay(revShooterTime);
    intake1.set(-1);
    Timer.delay(fireTime);
    stopMotors();

  }



    // Method to stop all motors
    public void stopMotors() {

        shooter1.stopMotor();
        shooter2.stopMotor();
        runIntake(0.1, 0, 0);
        
    }

    // Additional methods for other testing functionalities can be added

    // Ensure motors are stopped when the robot is disabled
    @Override
    public void periodic() {
       
    }
}