package org.firstinspires.ftc.teamcode.huskyteers.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.checkerframework.checker.nullness.qual.NonNull;

@Config
public class Claw {
    private final Servo clawServo;

    public static double CLAW_OPEN_POSITION = 0;
    public static double CLAW_CLOSED_POSITION = 1;

    public Claw(HardwareMap hardwareMap) {
        clawServo = hardwareMap.get(Servo.class, "intake_servo");
        clawServo.setDirection(Servo.Direction.REVERSE);
    }

    public void closeClaw() {
        clawServo.setPosition(CLAW_CLOSED_POSITION);
    }

    public void openClaw() {
        clawServo.setPosition(CLAW_OPEN_POSITION);
    }

    public Action openClawAction() {
        return new OpenClawAction();
    }
    public class OpenClawAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            clawServo.setPosition(CLAW_OPEN_POSITION);

            return false;
        }
    }
}
