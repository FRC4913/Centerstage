package org.firstinspires.ftc.teamcode.huskyteers;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.huskyteers.utils.Position;

@Config
public class HuskyAuto extends LinearOpMode {
    public static int MAX_TRIES = 20;
    HuskyBot huskyBot;

    final Position position;

    public HuskyAuto(Position p) {
        position = p;
    }


    public void navigateToTeamPropLocation(int location) {
        telemetry.addData("Going to location:", location);
        Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).lineToX(32).build());


        switch (location) {
            case 0:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(32, 0, 0)).strafeTo(new Vector2d(32, -30)).build());
                break;
            case 1:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(32, 0, 0)).build());
                break;
            case 2:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(32, 0, 0)).strafeTo(new Vector2d(32, 30)).build());
                break;
            default:
                break;
        }
    }

    public int locationToAprilTag(int location) {
        if (location == 0) {
            return 432;
            // TODO: Replace with actual values, depending on alliance too.
        }
        return -1;
    }

    public void parkInBackstage() {
        // TODO: Implement navigating to backstage
        throw new UnsupportedOperationException();
    }

    @Override
    public void runOpMode() {
        huskyBot = new HuskyBot(this);
        huskyBot.init();

        waitForStart();
        if (isStopRequested()) return;

        int teamPropLocation = huskyBot.huskyVision.detectTeamPropLocation();
        if (teamPropLocation != -1) {
            // Put down purple pixel
            navigateToTeamPropLocation(teamPropLocation);
//                huskyBot.moveClawToBottom();
//                huskyBot.openClaw();
            // TODO: Pick up yellow pixel
            // Put yellow pixel on backdrop
//                huskyBot.alignWithAprilTag(locationToAprilTag(teamPropLocation));
//                huskyBot.moveClawToBackdropPosition();
//                huskyBot.openClaw();
//                parkInBackstage();
        }
    }
}
