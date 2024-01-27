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

    // POWER CONSTANTS
    public static double OUTTAKE_RUN_POWER = 1;
    public static double OUTTAKE_STOP_POWER = 0;


    // DUMP (SERVO) POSITION CONSTANTS
    public static double DUMP_POSITION = 0.53;
    public static double DUMP_REST_POSITION = 0;
    public static double DUMP_INTAKE_POSITION = 0.087;

    // ARM (MOTOR) POSITION CONSTANTS
    public static int OUTTAKE_MOTOR_EXTENDED = 2850;
    public static int OUTTTAKE_MOTOR_RETRACTED = 50;

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

    public void armToRest(){
        outtakeMotor.setPower(OUTTAKE_RUN_POWER);
        outtakeMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        outtakeMotor.setTargetPosition(OUTTTAKE_MOTOR_RETRACTED);
    }

    public void armToExtended(){
        outtakeMotor.setPower(OUTTAKE_RUN_POWER);
        outtakeMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        outtakeMotor.setTargetPosition(OUTTAKE_MOTOR_EXTENDED);
    }

    public void armStop(){
        outtakeMotor.setPower(OUTTAKE_STOP_POWER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}