package org.firstinspires.ftc.teamcode.huskyteers.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.huskyteers.HuskyBot;
import org.firstinspires.ftc.teamcode.huskyteers.utils.FieldInfo;
import org.firstinspires.ftc.teamcode.huskyteers.utils.Position;

@Config
public class HuskyAuto extends LinearOpMode {
    HuskyBot huskyBot;

    final Position position;


    public HuskyAuto(Position p) {
        position = p;
    }

    public void navigateToTeamPropLocation(int location) {
            telemetry.addData("Going to location:", location);

            switch (location) {
                case 0:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(28)
                            .turnTo(Math.toRadians(90))
                            .lineToY(3)
                            .waitSeconds(1)
                            .lineToY(-3)
                            .build());
                    break;
                case 1:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .strafeTo(new Vector2d(30, 0))
                            .build());
                    break;
                case 2:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(28)
                            .turnTo(Math.toRadians(-90))
                            .lineToY(-2)
                            .waitSeconds(1)
                            .lineToY(3)
                            .build());
                    break;
                default:
                    break;
            }
        }
    public void navigateToInitialLocation(int location) {
        telemetry.addData("Going to location:", location);

        switch (location) {
            case 0:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(28, 0, 90))
                        .turnTo(Math.toRadians(0))
                        .strafeTo(new Vector2d(0, 0))
                        .build());
                break;
            case 1:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(30, 0, 0))
                        .strafeTo(new Vector2d(0, 0))
                        .build());
                break;
            case 2:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(28, 1, -90))
                        .turnTo(Math.toRadians(0))
                        .strafeTo(new Vector2d(0, 0))
                        .build());
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

    public void parkInBackstageCloseBack() {
        // TODO: Only supports when next to backstage. Decide on a strategy for the other side.
        if (position.equals(Position.BLUE_LEFT_STAGE)) {
            Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                    .strafeTo(new Vector2d(0, 24 * 2))
                    .build());
        } else {
            Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                    .strafeTo(new Vector2d(0, -24 * 2))
                    .build());
        }
    }
    public void parkInBackstageFarFront(int location) {
        if (position == Position.RED_LEFT_STAGE) {
            switch (location){
                case 0:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(28)
                            .lineToY(-150)
                            .build());
                case 1:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .strafeToLinearHeading(new Vector2d(-3,36),Math.toRadians(90))
                            .waitSeconds(5)
                            //Todo: finish these as a spline
                            .lineToX(31)
                            .lineToY(-180)
                            .build());
                case 2:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0,0,0))
                            .lineToX(28)
                            .lineToY(-150)
                            .build());
            }
        }
        else if(position == Position.BLUE_RIGHT_STAGE) {
            switch (location){
                case 0:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(28)
                            .lineToY(150)
                            .build());
                case 1:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(-3)
                            .lineToY(-36)
                            .lineToX(31)
                            .lineToY(180)
                            .build());
                case 2:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0,0,0))
                            .lineToX(28)
                            .lineToY(150)
                            .build());

            }
        }

    }


    public int getPropLocation() {
        // uses 0, 1, 2
        if(position.equals(Position.RED_LEFT_STAGE) || position.equals(Position.RED_RIGHT_STAGE)) {
            return huskyBot.huskyVision.openCv.redPropLocation();
        } else {
            return huskyBot.huskyVision.openCv.bluePropLocation();
        }
    }

    @Override
    public void runOpMode() {
        huskyBot = new HuskyBot(this);
        huskyBot.init();

        Pose2d initialPose = FieldInfo.getStartPose(position, 18);
        huskyBot.drive.pose = initialPose;

        waitForStart();
        if (isStopRequested()) return;

        int teamPropLocation = getPropLocation();

        // Put down purple pixel
        navigateToTeamPropLocation(teamPropLocation);
        if (position == Position.BLUE_LEFT_STAGE || position == Position.RED_RIGHT_STAGE) {
            navigateToInitialLocation(teamPropLocation);
            parkInBackstageCloseBack();
        }
        if(position == Position.BLUE_RIGHT_STAGE || position==Position.RED_LEFT_STAGE){
            navigateToTeamPropLocation(teamPropLocation);
            parkInBackstageFarFront(teamPropLocation);
        }


        // TODO: Pick up yellow pixel
        // Put yellow pixel on backdrop
//                huskyBot.alignWithAprilTag(locationToAprilTag(teamPropLocation));
//                huskyBot.moveClawToBackdropPosition();
//                huskyBot.openClaw();
//                parkInBackstage();
    }
}
