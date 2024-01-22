package org.firstinspires.ftc.teamcode.huskyteers.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.example.huskyteers.FieldInfo;
import com.example.huskyteers.Paths;
import com.example.huskyteers.Position;
import com.example.huskyteers.RobotInfo;
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


    public TeamPropLocation getPropLocation() {
        // uses 0, 1, 2
        if (FieldInfo.isRed(position)) {
            return huskyBot.huskyVision.openCv.redPropLocation();
        } else {
            return huskyBot.huskyVision.openCv.bluePropLocation();
        }
    }

    @Override
    public void runOpMode() {
        huskyBot = new HuskyBot(this);
        huskyBot.init();


        waitForStart();
        if (isStopRequested()) return;
        sleep(1000);

        // At initial location
        TeamPropLocation teamPropLocation = getPropLocation();
        navigateToTeamPropLocation(teamPropLocation);
        huskyBot.drive.pose = FieldInfo.getStartPose(position, RobotInfo.HEIGHT);
        // At initial location, in actual coordinates
        if (FieldInfo.isBackstage(position)) {
            navigateToBackdrop(teamPropLocation);
            placePixelOnBackdrop();
            navigateToParkingFromBackstage();
        } else {
            navigateToParkingFromFrontstage();
        }
    }

    public TrajectoryActionBuilder getActionBuilder() {
        if (!FieldInfo.isRed(position)) {
            return huskyBot.drive.actionBuilder(huskyBot.getDrivePoseEstimate());
        }
        return huskyBot.drive.reversedActionBuilder(huskyBot.getDrivePoseEstimate());
    }

    public void navigateToTeamPropLocation(TeamPropLocation location) {
        Actions.runBlocking(Paths.pathToTeamProp(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)), location));
    }

    private void navigateToBackdrop(TeamPropLocation teamPropLocation) {
        // Assigned to Ethan
        Actions.runBlocking(Paths.pathToBackdrop(getActionBuilder(), teamPropLocation).build());
    }

    private void placePixelOnBackdrop() {
        // Motor thingy, unassigned
    }

    private void navigateToParkingFromBackstage() {
        Actions.runBlocking(Paths.pathToParkingFromBackstage(getActionBuilder()).build());
    }

    private void navigateToParkingFromFrontstage() {
        Actions.runBlocking(Paths.pathToParkingFromFrontstage(getActionBuilder()));
    }


}
