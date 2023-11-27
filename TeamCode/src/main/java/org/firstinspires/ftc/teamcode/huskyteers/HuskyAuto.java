package org.firstinspires.ftc.teamcode.huskyteers;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.huskyteers.vision.HuskyVision;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

import java.util.List;
import java.util.Optional;

@Config
@Autonomous(name = "Husky Auto", group = "Auto")
public class HuskyAuto extends LinearOpMode {
    public static int MAX_TRIES = 20;
    HuskyBot huskyBot;

    /**
     * Let's arbitrarily assign numbers to each side it could be based on the location relative
     * to the robot's starting position.
     * <pre>
     *      1
     *    ______
     * 0 |     |  2
     *   |     |
     *    robot start
     *  </pre>
     *
     * @return Location of team prop, or -1 if not found
     */
    public int detectTeamPropLocation() {
        // TODO: Change this to maybe be in seconds, instead of number of tries which could be different every time.
        int tries = 0;
        do {
            List<Recognition> recognitions = huskyBot.huskyVision.tensorflowdetection.tfod.getRecognitions();
            Optional<Recognition> likelyRecognition = recognitions.stream().filter(recognition -> recognition.getLabel().equals("HuskyProp")).findAny();
            if (likelyRecognition.isPresent()) {
                Recognition recognition = likelyRecognition.get();
                // TODO: Better recognition algorithm, currently classifying based on the 1/3 of the screen that it is found in.
                return (int) (HuskyVision.WIDTH / (recognition.getLeft() + recognition.getRight()) / 2);



            }
            // TODO: Add some code maybe to move the robot a little to improve chances of finding team prop instead of trying same thing 20 times
        } while (++tries <= MAX_TRIES);
        return -1;
    }

    public void navigateToTeamPropLocation(int location) {
        telemetry.addData("Going to location:", location);
        Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).lineToX(32).build());
//        Actions.runBlocking(huskyBot.drive.actionBuilder(new Pose2d(0, 0, 0)).strafeTo(new Vector2d(24, 0)).build());

//        switch (location) {
//            case 0:
//                huskyBot.drive.actionBuilder(huskyBot.drive.pose).lineToX(-48).build().run(new TelemetryPacket());
//                break;
//            case 1:
//                huskyBot.drive.actionBuilder(huskyBot.drive.pose).lineToX(-48).build().run(new TelemetryPacket());
//                break;
//            case 2:
//                huskyBot.drive.actionBuilder(huskyBot.drive.pose).lineToX(-48).build().run(new TelemetryPacket());
//                break;
//            default:
//                break;
//      }
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

        int teamPropLocation = detectTeamPropLocation();
        if (teamPropLocation != -1 || true) {
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
