package org.firstinspires.ftc.teamcode.huskyteers.vision;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.huskyteers.HuskyBot;
import org.firstinspires.ftc.teamcode.huskyteers.utils.GamepadUtils;
import org.firstinspires.ftc.teamcode.huskyteers.utils.TelemetryUtils;

import java.util.concurrent.atomic.AtomicBoolean;

@Config
@TeleOp(name = "VisionTester", group = "Huskyteers")
public class VisionTester extends LinearOpMode {
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {

        // region INITIALIZATION
        HuskyBot huskyBot = new HuskyBot(this);
        huskyBot.init();


        waitForStart();
        if (isStopRequested()) return;
        // endregion

        // region TELEOP MODE
        while (opModeIsActive() && !isStopRequested()) {

            telemetry.addData("HuskyProp", huskyBot.huskyVision.visionProcessor.redPropLocation());
            telemetry.update();
        }
        // endregion
    }
}
