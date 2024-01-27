package org.firstinspires.ftc.teamcode.huskyteers.opmodes;
import android.annotation.SuppressLint;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.huskyteers.HuskyBot;

@Config
@TeleOp(name = "LoadDrone", group = "Huskyteers")
public class LoadDrone extends LinearOpMode {
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {
        HuskyBot huskyBot = new HuskyBot(this);
        huskyBot.init();

        waitForStart();

        if (opModeIsActive()) {
            huskyBot.droneLauncher.loadDrone();

            sleep(3000);


        }
    }
}
