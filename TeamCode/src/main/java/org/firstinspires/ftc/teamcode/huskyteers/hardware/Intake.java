package org.firstinspires.ftc.teamcode.huskyteers.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.checkerframework.checker.nullness.qual.NonNull;

@Config
public class Intake {
    private final Servo intakeServo;

    public static double INTAKE_OPEN_POSITION = 0;
    public static double INTAKE_CLOSED_POSITION = 1;

    public Intake(HardwareMap hardwareMap) {
        intakeServo = hardwareMap.get(Servo.class, "intake_servo");
        intakeServo.setDirection(Servo.Direction.REVERSE);
    }

    public void closeClaw() {
        intakeServo.setPosition(INTAKE_CLOSED_POSITION);
    }

    public void openClaw() {
        intakeServo.setPosition(INTAKE_OPEN_POSITION);
    }

    public Action openClawAction() {
        return new OpenClawAction();
    }
    public class OpenClawAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intakeServo.setPosition(INTAKE_OPEN_POSITION);

            return false;
        }
    }
}
