package org.firstinspires.ftc.teamcode.huskyteers.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class DroneLauncher {
    public static double LAUNCHER_LOADED_POSITION;
    public static double LAUNCHER_LAUNCH_POSITION;

    public final Servo drone_servo;

    public DroneLauncher(HardwareMap hardwareMap) {
        drone_servo = hardwareMap.get(Servo.class, "drone_launcher");
    }

    public void shootDrone() {
            drone_servo.setPosition(LAUNCHER_LAUNCH_POSITION);
    }

}
