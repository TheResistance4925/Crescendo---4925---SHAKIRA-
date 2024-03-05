// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BeamBreak;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.Constants;

public class ControlLEDs extends Command {

    private final BeamBreak beamBreakSubsystem;
    private final LEDSubsystem ledSubsystem;

    public ControlLEDs(BeamBreak beamBreakSubsystem, LEDSubsystem ledSubsystem) {
        this.beamBreakSubsystem = beamBreakSubsystem;
        this.ledSubsystem = ledSubsystem;
        addRequirements(beamBreakSubsystem, ledSubsystem);
    }

    @Override
    public void execute() {
        if (beamBreakSubsystem.isBeamBroken()) {
            // If the beam is broken, set the LED color to orange
            ledSubsystem.customColor(Constants.LEDs.hasNote);
        } else {
            // If the beam is not broken, set the LED color based on alliance color
            ledSubsystem.baseColor();
        }
    }

    @Override
    public void end(boolean interrupted) {
        // Ensure that the LEDs are turned off when the command ends
        ledSubsystem.baseColor(); // Set to alliance color or default color
    }
}
