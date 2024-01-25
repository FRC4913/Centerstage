package org.firstinspires.ftc.teamcode.huskyteers.opmodes;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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
    private static final long HIGH_POINT = 2000; //Todo: figure this out
    private static final long LOW_POINT = 300; //Todo: figure this out
    private long stateEntryTime = 0;
    private static final long MOVING_TIMEOUT = 5000; //Todo: adjust this
    private static final long DUMPING_TIMEOUT = 3000; //Todo: adjust this

    private ElapsedTime finiteTimer = new ElapsedTime();

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

        huskyBot.outtake.outtakeMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        huskyBot.outtake.outtakeMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        huskyBot.outtake.outtakeMotor.setDirection(DcMotorEx.Direction.REVERSE);
        huskyBot.outtake.outtakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

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


        gamepad1Utils.addRisingEdge("b", d -> {
            huskyBot.outtake.outtakeServo.setPosition(0);
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

            switch (currentOuttakeState) {
                case IDLE:
                    if (gamepad1.dpad_up) {
                        huskyBot.outtake.outtakeMotor.setPower(1);
                        huskyBot.outtake.outtakeMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                        huskyBot.outtake.outtakeMotor.setTargetPosition(2500);

                        finiteTimer.reset();
                        currentOuttakeState = OuttakeState.MOVING_UP;
                    } else if (gamepad1.dpad_down) {
                        huskyBot.outtake.outtakeMotor.setPower(1);
                        huskyBot.outtake.outtakeMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                        huskyBot.outtake.outtakeMotor.setTargetPosition(50);

                        finiteTimer.reset();
                        currentOuttakeState = OuttakeState.MOVING_DOWN;
                    }

                    break;
                case MOVING_UP:
                    if(!huskyBot.outtake.outtakeMotor.isBusy() || finiteTimer.seconds() > 5) {
                        gamepad1.rumble(1000);

                        huskyBot.outtake.outtakeMotor.setPower(0);
                        huskyBot.outtake.outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                        currentOuttakeState = OuttakeState.IDLE;
                        break;
                    }

                    telemetry.addData("Outtake Status: ", "MOVING UP");
                    break;
                case MOVING_DOWN:
                    if(!huskyBot.outtake.outtakeMotor.isBusy() || finiteTimer.seconds() > 5) {

                        gamepad1.rumble(1000);

                        huskyBot.outtake.outtakeMotor.setPower(0);
                        huskyBot.outtake.outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                        currentOuttakeState = OuttakeState.IDLE;
                        break;
                    }

                    telemetry.addData("Outtake Status: ", "MOVING DOWN");
                    break;
            }


            telemetry.addData("Outtake Pos: ", huskyBot.outtake.outtakeMotor.getCurrentPosition());

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
             *  - LEFT TRIGGER to decrease speed.
             */
            else {
                if (usingFieldCentric.get()) {
                    telemetry.addLine("Currently using field centric");
                    huskyBot.fieldCentricDriveRobot(
                            currentGamepad1.left_stick_y,
                            -currentGamepad1.left_stick_x,
                            -currentGamepad1.right_stick_x,
                            (0.85 - 0.5 * currentGamepad1.left_trigger));
                } else {
                    telemetry.addLine("Currently using tank drive");
                    huskyBot.driveRobot(
                            currentGamepad1.left_stick_y,
                            -currentGamepad1.left_stick_x,
                            -currentGamepad1.right_stick_x,
                            (0.85 - 0.5 * currentGamepad1.left_trigger));
                }
            }
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

