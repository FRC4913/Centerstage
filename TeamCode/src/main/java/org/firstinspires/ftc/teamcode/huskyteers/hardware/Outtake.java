package org.firstinspires.ftc.teamcode.huskyteers.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.checkerframework.checker.nullness.qual.NonNull;

@Config
public class Outtake {
    public final Servo outtakeServo;
    public final DcMotorEx outtakeMotor;

    // POWER CONSTANTS
    public static double OUTTAKE_RUN_POWER = 1;
    public static double OUTTAKE_STOP_POWER = 0;
    private double SERVO_POS = 0;


    // DUMP (SERVO) POSITION CONSTANTS
    public static double DUMP_POSITION = 0.53;
    public static double DUMP_REST_POSITION = 0;
    public static double DUMP_INTAKE_POSITION = 0.087;

    // ARM (MOTOR) POSITION CONSTANTS
    public static int OUTTAKE_MOTOR_EXTENDED = 2400;
    public static int OUTTTAKE_MOTOR_RETRACTED = 0;

    public Outtake(HardwareMap hardwareMap) {
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtake_motor");
        outtakeServo = hardwareMap.get(Servo.class, "outtake_servo");

    }

    public void dumpToRest() {
        outtakeServo.setPosition(DUMP_REST_POSITION);
    }

    public void dump() {
        outtakeServo.setPosition(DUMP_POSITION);
    }

    public void dumpToIntake() {
        outtakeServo.setPosition(DUMP_INTAKE_POSITION);
    }

    public void armToRest() {
        outtakeMotor.setPower(OUTTAKE_RUN_POWER);
        outtakeMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        outtakeMotor.setTargetPosition(OUTTTAKE_MOTOR_RETRACTED);
    }

    public void armToExtended() {
        outtakeMotor.setPower(OUTTAKE_RUN_POWER);
        outtakeMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        outtakeMotor.setTargetPosition(OUTTAKE_MOTOR_EXTENDED);
    }

    public void armStop() {
        outtakeMotor.setPower(OUTTAKE_STOP_POWER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public Action extendArmAction() {
        return new ExtendArmAction();
    }

    public Action retractArmAction() {
        return new RetractArmAction();
    }

    public Action dumpAction() {
        return new DumpAction();
    }

    public class ExtendArmAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                outtakeMotor.setPower(OUTTAKE_RUN_POWER);
                outtakeMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                outtakeMotor.setTargetPosition(OUTTAKE_MOTOR_EXTENDED);
                initialized = true;
            }

            double pos = outtakeMotor.getCurrentPosition();
            if (pos < OUTTAKE_MOTOR_EXTENDED - 100) {
                return true;
            } else {
                outtakeMotor.setPower(OUTTAKE_STOP_POWER);
                outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                return false;
            }
        }
    }

    public class RetractArmAction implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                outtakeMotor.setPower(OUTTAKE_RUN_POWER);
                outtakeMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                outtakeMotor.setTargetPosition(OUTTTAKE_MOTOR_RETRACTED);
                initialized = true;
            }

            double pos = outtakeMotor.getCurrentPosition();
            if (pos > OUTTTAKE_MOTOR_RETRACTED + 50) {
                return true;
            } else {
                outtakeMotor.setPower(OUTTAKE_STOP_POWER);
                outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                return false;
            }
        }
    }

    public class DumpAction implements Action {
        private double inc = 0.05;
        private int count = 0;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            for (int i = 0; i <= 15; i++) {
                outtakeServo.setPosition(SERVO_POS);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {
                }

                SERVO_POS += inc;
                count++;
            }

            outtakeServo.setPosition(0);

            return false;
        }
    }
}