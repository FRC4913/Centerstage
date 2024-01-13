package org.firstinspires.ftc.teamcode.huskyteers.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SleepAction;
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

        Actions.runBlocking(Paths.pathToTeamProp(huskyBot.drive.actionBuilder(new Pose2d(0, 0, Math.toRadians(180))), location));
    }

    public void navigateToInitialLocation(TeamPropLocation location) {
        telemetry.addData("Going to location:", location);
        Actions.runBlocking(Paths.reversePathToTeamProp(huskyBot.drive.actionBuilder(huskyBot.getDrivePoseEstimate()), location));
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

    public void parkInBackstageFar() {
        if (FieldInfo.isRed(position)) {
            Actions.runBlocking(Paths.pathToBackstageFar(huskyBot.drive.actionBuilder(huskyBot.getDrivePoseEstimate())));
        } else {
            Actions.runBlocking(Paths.pathToBackstageFar(huskyBot.drive.reversedActionBuilder(huskyBot.getDrivePoseEstimate())));
        }

    }

    public void navigateToCommonPoint(TeamPropLocation teamPropLocation) {
        if (FieldInfo.isRed(position)) {
            Actions.runBlocking(Paths.pathToCommonPoint(huskyBot.drive.actionBuilder(huskyBot.getDrivePoseEstimate()), teamPropLocation));
        } else {
            Actions.runBlocking(Paths.pathToCommonPoint(huskyBot.drive.reversedActionBuilder(huskyBot.getDrivePoseEstimate()), teamPropLocation));
        }
    }


    public TeamPropLocation getPropLocation() {
        // uses 0, 1, 2
        if (FieldInfo.isRed(position)) {
            return huskyBot.huskyVision.openCv.redPropLocation();
        } else {
            return huskyBot.huskyVision.openCv.bluePropLocation();
        }
    }

    public void waitSeconds(double seconds) {
        Actions.runBlocking(new SleepAction(seconds));
    }

    @Override
    public void runOpMode() {
        huskyBot = new HuskyBot(this);
        huskyBot.init();


        waitForStart();
        if (isStopRequested()) return;
        sleep(1000);

        TeamPropLocation teamPropLocation = getPropLocation();
        System.out.println(teamPropLocation.toString());

        // Put down purple pixel
        navigateToTeamPropLocation(teamPropLocation);
        if (position == Position.BLUE_LEFT || position == Position.RED_RIGHT) {
            navigateToInitialLocation(teamPropLocation);
            parkInBackstageCloseBack();
        }
        if (position == Position.BLUE_RIGHT || position == Position.RED_LEFT) {
            Pose2d startPose = FieldInfo.getStartPose(position, 18);
            Pose2d updatedPose = huskyBot.drive.pose;

            if (position == Position.RED_LEFT) {
                huskyBot.drive.pose = new Pose2d(startPose.position.x + updatedPose.position.y, startPose.position.y + updatedPose.position.x, startPose.heading.toDouble() + updatedPose.heading.toDouble());
            } else {
                huskyBot.drive.pose = new Pose2d(startPose.position.x + updatedPose.position.y, startPose.position.y - updatedPose.position.x, startPose.heading.toDouble() - updatedPose.heading.toDouble());
            }
            navigateToCommonPoint(teamPropLocation);
            waitSeconds(13);
            parkInBackstageFar();
        }
    }
}
