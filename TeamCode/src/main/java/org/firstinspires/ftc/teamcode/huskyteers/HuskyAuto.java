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
//        Pose2d startPose = FieldInfo.getStartPose(position)
//        Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).lineToX(32).build());


        switch (location) {
            case 0:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(36, 24)).build());
                break;
            case 1:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(36, 0)).build());
                break;
            case 2:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(36, -24)).build());
                break;
            default:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(36, 24)).build());
        }
    }
    public void navigateBackToInitialLoc(int location) {
        telemetry.addData("Going back to start location:", location);
//        Pose2d startPose = FieldInfo.getStartPose(position)
//        Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).lineToX(32).build());


        switch (location) {
            case 0:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(36, 24, 0)).strafeTo(new Vector2d(0, 0)).build());
                break;
            case 1:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(36, 0, 0)).strafeTo(new Vector2d(0, 0)).build());
                break;
            case 2:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(36, -24, 0)).strafeTo(new Vector2d(0, 0)).build());
                break;
            default:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(36, 24, 0)).strafeTo(new Vector2d(0, 0)).build());
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
        // TODO: Only supports when next to backstage
        Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(0, 24 * 1.5)));
    }

    @Override
    public void runOpMode() {
        huskyBot = new HuskyBot(this);
        huskyBot.init();

        waitForStart();
        if (isStopRequested()) return;

        int teamPropLocation = huskyBot.huskyVision.detectTeamPropLocation();

        if (teamPropLocation != -1 || true) {
            // Put down purple pixel
            navigateToTeamPropLocation(teamPropLocation);
            if (position.equals(Position.BLUE_LEFT_STAGE) || position.equals(Position.RED_RIGHT_STAGE)){
                navigateBackToInitialLoc(teamPropLocation);
            }


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
