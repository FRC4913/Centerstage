package org.firstinspires.ftc.teamcode.huskyteers.opmodes;
import android.annotation.SuppressLint;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.huskyteers.HuskyBot;
import org.firstinspires.ftc.teamcode.huskyteers.utils.GamepadUtils;
import org.firstinspires.ftc.teamcode.huskyteers.utils.TelemetryUtils;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.concurrent.atomic.AtomicBoolean;

    @TeleOp(name = "Basic TeleOp", group = "Iterative Opmode")
    public class LoadDrone extends LinearOpMode {
        @SuppressLint("DefaultLocale")
        @Override
        public void runOpMode() {
            HuskyBot huskyBot = new HuskyBot(this);
            huskyBot.init();
            huskyBot.droneLauncher.loadDrone();

        }

    }

