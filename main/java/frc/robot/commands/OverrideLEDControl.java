// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LEDSubsystem;

public class OverrideLEDControl extends Command {
    private final LEDSubsystem ledSubsystem;
    private final double blinkinValue;

    public OverrideLEDControl(LEDSubsystem ledSubsystem, double blinkinValue) {
        this.ledSubsystem = ledSubsystem;
        this.blinkinValue = blinkinValue;
        addRequirements(ledSubsystem);
    }

    @Override
    public void initialize() {
        // Set the LEDs to the specified color when the command starts
        ledSubsystem.customColor(blinkinValue);
    }

    @Override
    public void end(boolean interrupted) {
        // Return LED control back to the beambreak control when the command ends
        ledSubsystem.baseColor(); // You may adjust this to return control to the beambreak status
    }
}