package org.firstinspires.ftc.teamcode.huskyteers;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.huskyteers.utils.Position;

@Autonomous(name = "POV: Our Auto Doesn't Work", group="Huskyteers", preselectTeleOp = "Husky TeleOp Mode")
@Config
public class EmergencyAuto extends LinearOpMode {
    HuskyBot huskyBot;

    @Override
    public void runOpMode() {
        huskyBot = new HuskyBot(this);
        huskyBot.init();

        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(24, 0)).build());    }
}

