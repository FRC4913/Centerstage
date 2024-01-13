package org.firstinspires.ftc.teamcode.huskyteers.hardware;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@Config
public class Outtake {
    public final Servo outtakeServo;
    private final int MAX_ENCODER_POSITION = 1000; // Todo: Find the actual value
    private final int MIN_ENCODER_POSITION = 0; // Todo: Find the actual value
    private final DcMotorEx outtakeMotor;
    public Outtake(HardwareMap hardwareMap) {
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtake_motor");
        outtakeServo = hardwareMap.get(Servo.class, "outtake_servo");
        // Set other motor properties if needed@
    }
    public void OuttakeUp(double power) {
        outtakeMotor.setPower(power);
    }

    public void stopOuttake() {
        outtakeMotor.setPower(0);
    }

    public void OuttakeDown(double power) {
        outtakeMotor.setPower(-power);
    }
    public void dump(double targetPosition, double returnPosition, double increment, long delay) {
        // Safety check to ensure positions are within the valid range (0.0 to 1.0)
        targetPosition = Math.min(Math.max(targetPosition, 0.0), 1.0);
        returnPosition = Math.min(Math.max(returnPosition, 0.0), 1.0);

        // Gradually move the servo to the target position
        moveToPosition(targetPosition, increment, delay);

        // Optional delay here if you need to wait for the dumping action to complete
        // Thread.sleep([some delay in milliseconds]);

        // Gradually return the servo to its original position
        moveToPosition(returnPosition, increment, delay);
    }
    public void setPosition(int targetPosition, double power) {
        outtakeMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        outtakeMotor.setTargetPosition(targetPosition);
        outtakeMotor.setPower(power);

        while (outtakeMotor.isBusy()) {
            // Optionally, add some code here to provide feedback or to handle other tasks
            // while waiting for the motor to reach the position.
        }

        // Stop the motor once the position is reached
        outtakeMotor.setPower(0);
        outtakeMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    private void moveToPosition(double position, double increment, long delay) {
        double currentPosition = outtakeServo.getPosition();
        while (Math.abs(currentPosition - position) > increment) {
            if (currentPosition < position) {
                currentPosition += increment;
            } else {
                currentPosition -= increment;
            }

            currentPosition = Math.min(Math.max(currentPosition, 0.0), 1.0); // Ensure currentPosition stays within range
            outtakeServo.setPosition(currentPosition);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        outtakeServo.setPosition(position);
    }
    public void setMotorPowerWithLimit(double power) {
        int currentPos = outtakeMotor.getCurrentPosition();
        if ((power > 0 && currentPos < MAX_ENCODER_POSITION) || (power < 0 && currentPos > MIN_ENCODER_POSITION)) {
            outtakeMotor.setPower(power);
        } else {
            outtakeMotor.setPower(0);
        }
    }
    public int getOuttakeMotorPosition() {
        return outtakeMotor.getCurrentPosition();
    }
}
