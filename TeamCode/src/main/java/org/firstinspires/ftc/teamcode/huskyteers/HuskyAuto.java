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

//    public void navigateToTeamPropLocation(int location) {
//        telemetry.addData("Going to location:", location);
////        Pose2d startPose = FieldInfo.getStartPose(position)
////        Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).lineToX(32).build());
//
//
//        switch (location) {
//            case 0:
//                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(36, 24)).build());
//                break;
//            case 1:
//                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(36, 0)).build());
//                break;
//            case 2:
//                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(36, -24)).build());
//                break;
//            default:
//                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(36, 24)).build());
//        }
//    }
//    public void navigateBackToInitialLoc(int location) {
//        telemetry.addData("Going back to start location:", location);
////        Pose2d startPose = FieldInfo.getStartPose(position)
////        Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).lineToX(32).build());
//
//
//        switch (location) {
//            case 0:
//                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(36, 24, 0)).strafeTo(new Vector2d(0, 0)).build());
//                break;
//            case 1:
//                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(36, 0, 0)).strafeTo(new Vector2d(0, 0)).build());
//                break;
//            case 2:
//                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(36, -24, 0)).strafeTo(new Vector2d(0, 0)).build());
//                break;
//            default:
//                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(36, 24, 0)).strafeTo(new Vector2d(0, 0)).build());
//        }
//    }

    public void navigateToTeamPropLocation(int location) {
            telemetry.addData("Going to location:", location);
            telemetry.addData("Position X", huskyBot.drive.pose.position.x);
            telemetry.addData("Position Y", huskyBot.drive.pose.position.y);

            // If an adjustment needed for purple pixel's end position, change the first lineToY() methods in each runblock
            switch (location) {
                case 0:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(28).turnTo(Math.toRadians(90)).lineToY(3)
                            .waitSeconds(1)
                            .lineToY(-3).turnTo(Math.toRadians(0)).strafeTo(new Vector2d(0, 0)).build());
                    break;
                case 1:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .strafeTo(new Vector2d(30, 0))
                            .strafeTo(new Vector2d(0, 0))
                            .build());
                    break;
                case 2:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(28).turnTo(Math.toRadians(-90)).lineToY(-2)
                            .waitSeconds(1)
                            .lineToY(3).turnTo(Math.toRadians(0)).strafeTo(new Vector2d(0, 0)).build());
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
        // TODO: Only supports when next to backstage
        if (position.equals(Position.BLUE_LEFT_STAGE)) {

            Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(0, 24 * 2)).build());
        } else {
            Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(0, -24 * 2)).build());

        }
    }

    public int getPropLocation() {
        // uses 0, 1, 2
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (position.equals(Position.RED_LEFT_STAGE) || position.equals(Position.RED_RIGHT_STAGE)) {
            if (huskyBot.huskyVision.openCv.redPropLocation().isPresent()) {

            return huskyBot.huskyVision.openCv.redPropLocation().get() - 1;
            }
            return -1;
        } else {
            if (huskyBot.huskyVision.openCv.bluePropLocation().isPresent()) {
                return huskyBot.huskyVision.openCv.bluePropLocation().get() - 1;
            }
            return -1;
        }
    }

    @Override
    public void runOpMode() {
        huskyBot = new HuskyBot(this);
        huskyBot.init();

        waitForStart();
        if (isStopRequested()) return;

        int teamPropLocation = getPropLocation();

        // Put down purple pixel
        navigateToTeamPropLocation(teamPropLocation);
        if (position == Position.BLUE_LEFT_STAGE || position == Position.RED_RIGHT_STAGE) {
            parkInBackstage();
        }


        // TODO: Pick up yellow pixel
        // Put yellow pixel on backdrop
//                huskyBot.alignWithAprilTag(locationToAprilTag(teamPropLocation));
//                huskyBot.moveClawToBackdropPosition();
//                huskyBot.openClaw();
//                parkInBackstage();
    }
}
