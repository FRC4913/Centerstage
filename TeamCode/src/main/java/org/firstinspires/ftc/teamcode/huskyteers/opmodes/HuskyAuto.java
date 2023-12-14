package org.firstinspires.ftc.teamcode.huskyteers.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.example.huskyteers.FieldInfo;
import com.example.huskyteers.Paths;
import com.example.huskyteers.Position;
import com.example.huskyteers.TeamPropLocation;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.huskyteers.HuskyBot;

@Config
public class HuskyAuto extends LinearOpMode {
    HuskyBot huskyBot;

    final Position position;


    public HuskyAuto(Position p) {
        position = p;
    }

    public void navigateToTeamPropLocation(TeamPropLocation location) {
        telemetry.addData("Going to location:", location);

        Actions.runBlocking(Paths.pathToTeamProp(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)), location));
        Actions.runBlocking(Paths.reversePathToTeamProp(huskyBot.drive.actionBuilder(huskyBot.drive.pose), location));
    }

    public void navigateToInitialLocation(TeamPropLocation location) {
        telemetry.addData("Going to location:", location);

        switch (location) {
            case LEFT:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(28, 0, 90))
                        .turnTo(Math.toRadians(0))
                        .strafeTo(new Vector2d(0, 0))
                        .build());
                break;
            case CENTER:
                Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(30, 0, 0))
                        .strafeTo(new Vector2d(0, 0))
                        .build());
                break;
            case RIGHT:
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
        if (position.equals(Position.BLUE_LEFT)) {
            Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                    .strafeTo(new Vector2d(0, 24 * 2))
                    .build());
        } else {
            Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                    .strafeTo(new Vector2d(0, -24 * 2))
                    .build());
        }
    }

    public void parkInBackstageFarFront(TeamPropLocation location) {
        if (position == Position.RED_LEFT) {
            switch (location) {
                case LEFT:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(28)
                            .lineToY(-150)
                            .build());
                case CENTER:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .strafeToLinearHeading(new Vector2d(-3, 36), Math.toRadians(90))
                            .waitSeconds(5)
                            //Todo: finish these as a spline
                            .lineToX(31)
                            .lineToY(-180)
                            .build());
                case RIGHT:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(28)
                            .lineToY(-150)
                            .build());
            }
        } else if (position == Position.BLUE_RIGHT) {
            switch (location) {
                case LEFT:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(28)
                            .lineToY(150)
                            .build());
                case CENTER:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(-3)
                            .lineToY(-36)
                            .lineToX(31)
                            .lineToY(180)
                            .build());
                case RIGHT:
                    Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(28)
                            .lineToY(150)
                            .build());

            }
        }

    }


    public TeamPropLocation getPropLocation() {
        // uses 0, 1, 2
        if (position.equals(Position.RED_LEFT) || position.equals(Position.RED_RIGHT)) {
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

        TeamPropLocation teamPropLocation = getPropLocation();

        // Put down purple pixel
        navigateToTeamPropLocation(teamPropLocation);
        if (position == Position.BLUE_LEFT || position == Position.RED_RIGHT) {
            navigateToInitialLocation(teamPropLocation);
            parkInBackstageCloseBack();
        }
        if (position == Position.BLUE_RIGHT || position == Position.RED_LEFT) {
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
