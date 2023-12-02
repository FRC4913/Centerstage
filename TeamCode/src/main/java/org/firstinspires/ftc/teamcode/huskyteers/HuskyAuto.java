package org.firstinspires.ftc.teamcode.huskyteers;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.huskyteers.utils.Position;

@Config
public class HuskyAuto extends LinearOpMode {
    HuskyBot huskyBot;
    public static double ROBOT_THICKNESS = 17;
    final Position position;

    public HuskyAuto(Position p) {
        position = p;
    }

    public void navigateToTeamPropLocation(int location) {
        telemetry.addData("Going to location:", location);
        telemetry.addData("Position X", huskyBot.drive.pose.position.x);
        telemetry.addData("Position Y", huskyBot.drive.pose.position.y);

        // If an adjustment needed for purple pixel's end position, change the first lineToY() methods in each runblock
        //
        switch (location) {
            case 1:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                        .lineToX(28).turnTo(Math.toRadians(90)).lineToY(3)
                        .waitSeconds(1)
                        .lineToY(0).turnTo(Math.toRadians(0)).lineToX(0).build());
                break;
            case 2:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                        .strafeTo(new Vector2d(30, 0)).build());
                break;
            case 3:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                        .lineToX(28).turnTo(Math.toRadians(-90)).lineToY(-2)
                        .waitSeconds(1)
                        .lineToY(0).turnTo(Math.toRadians(0)).lineToX(0).build());
                break;
            default:
                break;
        }

        //TODO: YELLOW PIXEL (CHECK FOR WHICH SIDE ARE WE)
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

        int teamPropLocation = huskyBot.huskyVision.visionProcessor.getPropLocation();

            // Put down purple pixel
            navigateToTeamPropLocation(teamPropLocation);


            // TODO: Pick up yellow pixel
            // Put yellow pixel on backdrop
//                huskyBot.alignWithAprilTag(locationToAprilTag(teamPropLocation));
//                huskyBot.moveClawToBackdropPosition();
//                huskyBot.openClaw();
//                parkInBackstage();
    }
}
