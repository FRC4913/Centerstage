package org.firstinspires.ftc.teamcode.huskyteers.opmodes;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

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
    private static final long HIGH_POINT = 400; //Todo: figure this out
    private static final long LOW_POINT = 100; //Todo: figure this out
    private long stateEntryTime = 0;
    private static final long MOVING_TIMEOUT = 5000; //Todo: adjust this
    private static final long DUMPING_TIMEOUT = 3000; //Todo: adjust this
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
        huskyBot.outtake.outtakeServo.setDirection(Servo.Direction.REVERSE);
        GamepadUtils gamepad1Utils = new GamepadUtils();
        GamepadUtils gamepad2Utils = new GamepadUtils();
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

//        AtomicBoolean usingFieldCentric = new AtomicBoolean(true);
//        gamepad1Utils.addRisingEdge("a", d -> {
//            usingFieldCentric.set(!usingFieldCentric.get());
//            gamepad1.runRumbleEffect(new Gamepad.RumbleEffect.Builder().addStep(1, 1, 200).build());
//        });
        // region DRONE LAUNCHER

        gamepad1Utils.addRisingEdge("a", d -> {
            huskyBot.outtake.outtakeServo.setPosition(0);
        });

        gamepad1Utils.addRisingEdge("b", d -> {
            huskyBot.outtake.outtakeServo.setPosition(0.3);
        });

        gamepad1Utils.addRisingEdge("y", d -> {
            huskyBot.outtake.outtakeServo.setPosition(0.6);
        });
        gamepad1Utils.addRisingEdge("x", d -> {
            huskyBot.outtake.outtakeServo.setPosition(1);
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

            telemetry.addData("Servo Pos: ", huskyBot.outtake.outtakeServo.getPosition());
            telemetry.addData("Servo Direc: ", huskyBot.outtake.outtakeServo.getDirection());


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

            if (gamepad1.dpad_up) {
                // Start moving up
                huskyBot.outtake.setMotorPowerWithLimit(0.5); // Example power for moving up
                stateEntryTime = System.currentTimeMillis(); // Start the timer for timeout
                currentOuttakeState = OuttakeState.MOVING_UP;
            } else if (gamepad1.dpad_down) {
                // Start moving down
                huskyBot.outtake.setMotorPowerWithLimit(-0.5); // Exampxle power for moving down
                stateEntryTime = System.currentTimeMillis(); // Start the timer for timeout
                currentOuttakeState = OuttakeState.MOVING_DOWN;
            } else if (gamepad1.dpad_left) {
                huskyBot.outtake.stopOuttake();
            }

            telemetry.addData("Motor Position: ", huskyBot.outtake.outtakeMotor.getCurrentPosition());
//
//            switch (currentOuttakeState) {
//                case IDLE:
//                    if (gamepad1.dpad_up) {
//                        // Start moving up
//                        huskyBot.outtake.setMotorPowerWithLimit(0.5); // Example power for moving up
//                        stateEntryTime = System.currentTimeMillis(); // Start the timer for timeout
//                        currentOuttakeState = OuttakeState.MOVING_UP;
//                    } else if (gamepad1.dpad_down) {
//                        // Start moving down
//                        huskyBot.outtake.setMotorPowerWithLimit(-0.5); // Example power for moving down
//                        stateEntryTime = System.currentTimeMillis(); // Start the timer for timeout
//                        currentOuttakeState = OuttakeState.MOVING_DOWN;
//                    } else if (gamepad1.x) {
//                        // Start dumping
//                        huskyBot.outtake.dump(1.0, 0.5, 0.1, 50); // Example parameters for dumping
//                        stateEntryTime = System.currentTimeMillis(); // Start the timer for dumping
//                        currentOuttakeState = OuttakeState.DUMPING;
//                    } else if (gamepad1.b) {
//                        // Manual stop button pressed in IDLE state
//                        // Implement any necessary logic here if needed
//                        // For example, reset certain values or stop any residual motor movement
//                    }
//                    break;
//
//                case MOVING_UP:
//                    if (huskyBot.outtake.getOuttakeMotorPosition() >= HIGH_POINT ||
//                            System.currentTimeMillis() - stateEntryTime > MOVING_TIMEOUT ||
//                            gamepad1.b) { // Check for manual stop
//                        huskyBot.outtake.stopOuttake();
//                        currentOuttakeState = OuttakeState.IDLE;
//                    }
//                    break;
//                case MOVING_DOWN:
//                    if (huskyBot.outtake.getOuttakeMotorPosition() <= LOW_POINT ||
//                            System.currentTimeMillis() - stateEntryTime > MOVING_TIMEOUT ||
//                            gamepad1.b) { // Check for manual stop
//                        huskyBot.outtake.stopOuttake();
//                        currentOuttakeState = OuttakeState.IDLE;
//                    }
//                    break;
//                case DUMPING:
//                    if (System.currentTimeMillis() - stateEntryTime > DUMPING_TIMEOUT || gamepad1.b) {
//                        // Dumping completed or manual stop
//                        currentOuttakeState = OuttakeState.IDLE;
//                    }
//                    break;
//            }



            // region DRIVE CONTROL

            // Press START to reset robot heading.
//            if (currentGamepad1.start) {
//                huskyBot.setCurrentHeadingAsForward();
//            }
//
//            /*
//             * If not LEFT BUMPER, use:
//             *  - A to toggle between field-centric and robot-centric drive.
//             *  - LEFT STICK for movement.
//             *  - RIGHT STICK for rotation.
//             *  - LEFT TRIGGER to increase speed.
//             */
//            else {
//                if (usingFieldCentric.get()) {
//                    telemetry.addLine("Currently using field centric");
//                    huskyBot.fieldCentricDriveRobot(
//                            currentGamepad1.left_stick_y,
//                            -currentGamepad1.left_stick_x,
//                            -currentGamepad1.right_stick_x,
//                            (0.35 + 0.5 * currentGamepad1.left_trigger));
//                } else {
//                    telemetry.addLine("Currently using tank drive");
//                    huskyBot.driveRobot(
//                            currentGamepad1.left_stick_y,
//                            -currentGamepad1.left_stick_x,
//                            -currentGamepad1.right_stick_x,
//                            (0.35 + 0.5 * currentGamepad1.left_trigger));
//                }
//            }
//
//            if(currentGamepad1.y) {
//                huskyBot.intake.runIntake(10);
//            } else if(currentGamepad1.b) {
//                huskyBot.intake.reverseIntake(10);
//            }
//            else if(!currentGamepad1.y&&!currentGamepad1.b){
//                huskyBot.intake.stopIntake();
//            }
            // endregion

            // region TELEMETRY
            TelemetryUtils.Gamepad(currentGamepad1);
//            TelemetryUtils.DrivePos2d(huskyBot);
            telemetry.update();
            // endregion
        }
        // endregion
    }
}
