package org.firstinspires.ftc.teamcode.huskyteers.vision;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.huskyteers.HuskyBot;

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

            telemetry.addData("HuskyProp", huskyBot.huskyVision.openCv.redPropLocation());
            telemetry.update();
        }
        // endregion
    }
}
