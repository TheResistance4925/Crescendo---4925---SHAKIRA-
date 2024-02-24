// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import frc.robot.subsystems.IntakeSubsystem;

public class ToasterCommand extends Command {
  private final IntakeSubsystem intakeSubsystem;
  private final double intakeSetpoint;
  private final double shooterSetpoint;

  public ToasterCommand(IntakeSubsystem intakeSubsystem, double intakeSetpoint, double shooterSetpoint) {
      this.intakeSubsystem = intakeSubsystem;
      this.intakeSetpoint = intakeSetpoint;
      this.shooterSetpoint = shooterSetpoint;

      addRequirements(intakeSubsystem);
  }

  @Override
  public void initialize() {
      // Optional: Add any initialization code here
  }

  @Override
  public void execute() {
      intakeSubsystem.driveMotors(intakeSetpoint, shooterSetpoint);
  }

  @Override
  public void end(boolean interrupted) {
      intakeSubsystem.stopMotors();
  }

  @Override
  public boolean isFinished() {
      return false; // Adjust as needed, or keep the command running continuously
  }
}
