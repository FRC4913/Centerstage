package org.firstinspires.ftc.teamcode.huskyteers.hardware;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
public class Intake {

    private final DcMotorEx intakeMotor;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake_motor");
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        // Set other motor properties if needed
    }

    public void runIntake(double power) {
        intakeMotor.setPower(power);
    }

    public void stopIntake() {
        intakeMotor.setPower(0);
    }

    public void reverseIntake(double power) {
        intakeMotor.setPower(-power);
    }


    // Add other methods as needed
}
