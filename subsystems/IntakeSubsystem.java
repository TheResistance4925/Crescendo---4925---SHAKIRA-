// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public class IntakeSubsystem extends SubsystemBase {
  private final CANSparkMax intake;
  private final CANSparkMax shooterLeft;
  private final CANSparkMax shooterRight;

  private final SimpleMotorFeedforward intakeFeedforward = new SimpleMotorFeedforward(0.1, 0.05, 0.02);
  private final SimpleMotorFeedforward shooterFeedforward = new SimpleMotorFeedforward(0.1, 0.05, 0.02);

  public IntakeSubsystem() {
      intake = new CANSparkMax(28, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
      shooterLeft = new CANSparkMax(29, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
      shooterRight = new CANSparkMax(30, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
  }

  public void driveMotors(double intakeSetpoint, double shooterSetpoint) {
      intake.setVoltage(intakeSetpoint + intakeFeedforward.calculate(intakeSetpoint));
      shooterLeft.setVoltage(shooterSetpoint + shooterFeedforward.calculate(shooterSetpoint));
      shooterRight.setVoltage(shooterSetpoint + shooterFeedforward.calculate(shooterSetpoint));
  }

  public void stopMotors() {
      intake.stopMotor();
      shooterLeft.stopMotor();
      shooterRight.stopMotor();
  }
}