package org.firstinspires.ftc.teamcode.huskyteers.hardware;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
@Config
public class Outtake {
    public final CRServo outtakeServo;
    private final DcMotorEx outtakeMotor;
    public Outtake(HardwareMap hardwareMap) {
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtake_motor");
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeServo = hardwareMap.get(CRServo.class, "outtak_servo");
        // Set other motor properties if needed
    }
}
