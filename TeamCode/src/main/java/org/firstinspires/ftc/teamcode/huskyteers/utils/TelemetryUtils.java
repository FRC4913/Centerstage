package org.firstinspires.ftc.teamcode.huskyteers.utils;

import android.annotation.SuppressLint;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.huskyteers.HuskyBot;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class TelemetryUtils {
    public static Telemetry telemetry;

    public static void PoseVelocity2d(PoseVelocity2d pw) {
        telemetry.addData("Drive: ", pw.component1().y);
        telemetry.addData("Strafe: ", pw.component1().x);
        telemetry.addData("Turn: ", pw.component2());
    }

    public static void DrivePos2d(HuskyBot huskyBot) {
        Pose2d pose = huskyBot.getDrivePoseEstimate();
        telemetry.addData("Robot X: ", pose.component1().x);
        telemetry.addData("Robot Y: ", pose.component1().y);
    }

    @SuppressLint("DefaultLocale")
    public static void AprilTagDetection(AprilTagDetection detection) {
        if (detection.metadata != null) {
            telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
        } else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
            telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
        }
    }

    @SuppressLint("DefaultLocale")
    public static void Gamepad(Gamepad gamepad) {
        telemetry.addLine(String.format("Left Stick: %.2f %.2f", gamepad.left_stick_x, gamepad.left_stick_y));
        telemetry.addLine(String.format("Right Stick: %.2f %.2f", gamepad.right_stick_x, gamepad.right_stick_y));
    }

}
