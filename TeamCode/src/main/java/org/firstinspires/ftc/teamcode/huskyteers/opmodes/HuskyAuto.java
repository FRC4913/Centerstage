package org.firstinspires.ftc.teamcode.huskyteers.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.example.huskyteers.FieldInfo;
import com.example.huskyteers.Paths;
import com.example.huskyteers.Position;
import com.example.huskyteers.TeamPropLocation;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

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

        huskyBot.outtake.outtakeServo.setDirection(Servo.Direction.REVERSE);

        huskyBot.outtake.outtakeMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        huskyBot.outtake.outtakeMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        huskyBot.outtake.outtakeMotor.setDirection(DcMotorEx.Direction.REVERSE);
        huskyBot.outtake.outtakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        waitForStart();
        if (isStopRequested()) return;

        huskyBot.claw.closeClaw();

        sleep(1000);

        // At initial location
        TeamPropLocation teamPropLocation = getPropLocation();

        //TODO: Change the team prop location with the actual one
        if (!FieldInfo.isRed(position)) {
            navigateToTeamPropLocation(teamPropLocation);
        } else {
            navigateToReversed(teamPropLocation);
        }
//        huskyBot.drive.pose = FieldInfo.getStartPose(position, RobotInfo.HEIGHT);
        // At initial location, in actual coordinates
//        if (FieldInfo.isBackstage(position)) {
//            navigateToBackdrop(teamPropLocation);
//            placePixelOnBackdrop();
//            navigateToParkingFromBackstage();
//        } else {
//            navigateToParkingFromFrontstage();
//        }
    }

    private void navigateToReversed(TeamPropLocation location) {
        Actions.runBlocking(Paths.reversedPath(huskyBot.drive.actionBuilder(new Pose2d(0, 0,
                        Math.toRadians(0))), location, huskyBot.claw.openClawAction(),
                huskyBot.outtake.extendArmAction(), huskyBot.outtake.dumpAction(),
                huskyBot.outtake.retractArmAction()).build());
    }

    public TrajectoryActionBuilder getActionBuilder() {
        if (!FieldInfo.isRed(position)) {
            return huskyBot.drive.actionBuilder(huskyBot.getDrivePoseEstimate());
        }
        return huskyBot.drive.reversedActionBuilder(huskyBot.getDrivePoseEstimate());
    }

    public void navigateToTeamPropLocation(TeamPropLocation location) {
        Actions.runBlocking(Paths.pathToTeamProp(huskyBot.drive.actionBuilder(new Pose2d(0, 0,
                        Math.toRadians(0))), location, huskyBot.claw.openClawAction(),
                huskyBot.outtake.extendArmAction(), huskyBot.outtake.dumpAction(),
                huskyBot.outtake.retractArmAction()).build());
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
