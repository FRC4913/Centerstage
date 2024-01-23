package org.firstinspires.ftc.teamcode.huskyteers.hardware;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.huskyteers.utils.TelemetryUtils;

@Config
public class Outtake {
    public final Servo outtakeServo;
    public final DcMotorEx outtakeMotor;


    public Outtake(HardwareMap hardwareMap) {
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtake_motor");
        outtakeServo = hardwareMap.get(Servo.class, "outtake_servo");
    }
}
