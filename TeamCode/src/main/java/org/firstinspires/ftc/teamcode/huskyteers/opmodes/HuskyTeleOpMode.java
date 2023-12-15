package org.firstinspires.ftc.teamcode.huskyteers.opmodes;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.huskyteers.HuskyBot;
import org.firstinspires.ftc.teamcode.huskyteers.utils.GamepadUtils;
import org.firstinspires.ftc.teamcode.huskyteers.utils.TelemetryUtils;

import java.util.concurrent.atomic.AtomicBoolean;

@Config
@TeleOp(name = "Husky TeleOp Mode", group = "Huskyteers")
public class HuskyTeleOpMode extends LinearOpMode {
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {

        // region INITIALIZATION
        HuskyBot huskyBot = new HuskyBot(this);
        huskyBot.init();

        GamepadUtils gamepad1Utils = new GamepadUtils();
        GamepadUtils gamepad2Utils = new GamepadUtils();
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        AtomicBoolean usingFieldCentric = new AtomicBoolean(true);
        gamepad1Utils.addRisingEdge("a", d -> {
            usingFieldCentric.set(!usingFieldCentric.get());
            gamepad1.runRumbleEffect(new Gamepad.RumbleEffect.Builder().addStep(1, 1, 200).build());
        });
        // region DRONE LAUNCHER
        gamepad2Utils.addRisingEdge("dpad_up", d -> {
            huskyBot.droneLauncher.shootDrone();
        });
        // endregion

        waitForStart();
        if (isStopRequested()) return;
        // endregion

        // region TELEOP MODE
        while (opModeIsActive() && !isStopRequested()) {

            // region GAMEPAD CONTROL
            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);
            gamepad1Utils.processUpdates(currentGamepad1);
            gamepad2Utils.processUpdates(currentGamepad2);
            // endregion

            // region DRIVE CONTROL

            // Press START to reset robot heading.
            if (currentGamepad1.start) {
                huskyBot.setCurrentHeadingAsForward();
            }

            // Hold LEFT BUMPER to align with an AprilTag.
            if (currentGamepad1.left_bumper &&
                    huskyBot.huskyVision.AprilTagDetector.getAprilTagById(583).isPresent()) {
                PoseVelocity2d pw = huskyBot.alignWithAprilTag(583);
                TelemetryUtils.PoseVelocity2d(pw);
                huskyBot.driveRobot(
                        pw.component1().y,
                        pw.component1().x,
                        pw.component2(),
                        1.0);
            }
            /*
             * If not LEFT BUMPER, use:
             *  - A to toggle between field-centric and robot-centric drive.
             *  - LEFT STICK for movement.
             *  - RIGHT STICK for rotation.
             *  - LEFT TRIGGER to increase speed.
             */
            else {
                if (usingFieldCentric.get()) {
                    telemetry.addLine("Currently using field centric");
                    huskyBot.fieldCentricDriveRobot(
                            currentGamepad1.left_stick_y,
                            -currentGamepad1.left_stick_x,
                            -currentGamepad1.right_stick_x,
                            (0.35 + 0.5 * currentGamepad1.left_trigger));
                } else {
                    telemetry.addLine("Currently using tank drive");
                    huskyBot.driveRobot(
                            currentGamepad1.left_stick_y,
                            -currentGamepad1.left_stick_x,
                            -currentGamepad1.right_stick_x,
                            (0.35 + 0.5 * currentGamepad1.left_trigger));
                }
            }
            if(currentGamepad1.y) {
                huskyBot.intake.runIntake(1);
            } else if(currentGamepad1.b) {
                huskyBot.intake.reverseIntake(1);
            } else {
                huskyBot.intake.stopIntake();
            }
            // endregion

            // region TELEMETRY
            TelemetryUtils.Gamepad(currentGamepad1);
            TelemetryUtils.DrivePos2d(huskyBot);
            telemetry.update();
            // endregion
        }
        // endregion
    }
}
