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
    private long dumpingStartTime = 0;

// When transitioning to DUMPING state
    private static final long DUMPING_DURATION = 2000; //Todo: figure this out
    private static final long HIGH_POINT = 800; //Todo: figure this out
    private static final long LOW_POINT = 100; //Todo: figure this out
    private enum OuttakeState {
        IDLE,
        MOVING_UP,
        MOVING_DOWN,
        DUMPING
    }

    private OuttakeState currentOuttakeState = OuttakeState.IDLE;

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
// FSM for Outtake
//            switch (currentOuttakeState) {
//                case IDLE:
//                    if (gamepad1.dpad_up) {
//                        currentOuttakeState = OuttakeState.MOVING_UP;
//                        huskyBot.outtake.OuttakeUp(0.5); // Example power value
//                    } else if (gamepad1.dpad_down) {
//                        currentOuttakeState = OuttakeState.MOVING_DOWN;
//                        huskyBot.outtake.OuttakeDown(0.5); // Example power value
//                    } else if (gamepad1.x) {
//                        currentOuttakeState = OuttakeState.DUMPING;
//                        huskyBot.outtake.dump(1.0, 0.5, 0.1, 50); // Example parameters
//                    }
//                    break;
//                case MOVING_UP:
//                    // Check if reached target position or other condition
//                    if (huskyBot.outtake.getOuttakeMotorPosition() > HIGH_POINT) {
//                        huskyBot.outtake.stopOuttake();
//                        currentOuttakeState = OuttakeState.IDLE;
//                    }
//                    break;
//                case MOVING_DOWN:
//                    // Check if reached target position or other condition
//                    if (huskyBot.outtake.getOuttakeMotorPosition() < LOW_POINT) {
//                        huskyBot.outtake.stopOuttake();
//                        currentOuttakeState = OuttakeState.IDLE;
//                    }
//                    break;
//                case DUMPING:
//                    // Check if dumping is completed
//                    if (System.currentTimeMillis() - dumpingStartTime > DUMPING_DURATION) {
//                        currentOuttakeState = OuttakeState.IDLE;
//                    }
//                    break;
//            }
            switch (currentOuttakeState) {
                case IDLE:
                    if (gamepad1.dpad_up) {
                        currentOuttakeState = OuttakeState.MOVING_UP;
                        huskyBot.outtake.setMotorPowerWithLimit(0.5);
                    } else if (gamepad1.dpad_down) {
                        currentOuttakeState = OuttakeState.MOVING_DOWN;
                        huskyBot.outtake.setMotorPowerWithLimit(-0.5);
                    } else if (gamepad1.x) {
                        currentOuttakeState = OuttakeState.DUMPING;
                        huskyBot.outtake.dump(1.0, 0.5, 0.1, 50);//Todo: Find Optimal Value
                        dumpingStartTime = System.currentTimeMillis();
                    }
                    break;
                case MOVING_UP:
                    // Transition logic for MOVING_UP
                    if (huskyBot.outtake.getOuttakeMotorPosition() >= HIGH_POINT) {
                        huskyBot.outtake.stopOuttake();
                        currentOuttakeState = OuttakeState.IDLE;
                    }
                    break;
                case MOVING_DOWN:
                    // Transition logic for MOVING_DOWN
                    if (huskyBot.outtake.getOuttakeMotorPosition() <= LOW_POINT) {
                        huskyBot.outtake.stopOuttake();
                        currentOuttakeState = OuttakeState.IDLE;
                    }
                    break;
                case DUMPING:
                    if (System.currentTimeMillis() - dumpingStartTime > DUMPING_DURATION) {
                        currentOuttakeState = OuttakeState.IDLE;
                    }
                    break;
            }

            // region DRIVE CONTROL

            // Press START to reset robot heading.
            if (currentGamepad1.start) {
                huskyBot.setCurrentHeadingAsForward();
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
                huskyBot.intake.runIntake(10);
            } else if(currentGamepad1.b) {
                huskyBot.intake.reverseIntake(10);
            }
            else if(!currentGamepad1.y&&!currentGamepad1.b){
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
