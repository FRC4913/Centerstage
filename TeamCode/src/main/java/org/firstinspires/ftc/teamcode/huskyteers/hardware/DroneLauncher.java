package org.firstinspires.ftc.teamcode.huskyteers.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class DroneLauncher {
    public static double LAUNCHER_POWER = 0.5;
    public static double LAUNCHER_DISTANCE = 10;

    public final CRServo servo;

    public DroneLauncher(HardwareMap hardwareMap) {
        servo = hardwareMap.get(CRServo.class, "drone_launcher");
    }

    public Action shootDrone() {
        double start = servo.getController().getServoPosition(servo.getPortNumber());
        return packet -> {
            servo.setPower(0.5);
            return servo.getController().getServoPosition(servo.getPortNumber()) > start + LAUNCHER_DISTANCE;
        };
    }
}
