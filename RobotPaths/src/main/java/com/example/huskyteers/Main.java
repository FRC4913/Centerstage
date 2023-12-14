package com.example.huskyteers;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Main {
    public static MeepMeep meepMeep = new MeepMeep(600);

    public static RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
            // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
            .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), RobotInfo.WIDTH)
            .setDimensions(RobotInfo.WIDTH, RobotInfo.HEIGHT)
            .build();

    public static void previewTeamPropPath(TeamPropLocation location) {
        myBot.runAction(Paths.pathToTeamProp(myBot.getDrive().actionBuilder(new Pose2d(0, 0, 0)), location));
    }

    public static void main(String[] args) {
        previewTeamPropPath(TeamPropLocation.LEFT);
//        myBot.runAction(myBot.getDrive().actionBuilder(
//                        FieldInfo.getStartPose(Position.RED_LEFT, RobotInfo.WIDTH))
//                .strafeTo(new Vector2d(-36, -72 + 9 + 30))
//                .strafeToSplineHeading(new Vector2d(-36 - 18, -72 + 9 + 30), 0)
//                .waitSeconds(5)
//                .setTangent(Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-36 - 18, -72 + 9 + 30 + 13), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(-36 - 18 + 78, -72 + 9 + 30 + 13 + 8), 0)
//                .splineToConstantHeading(new Vector2d(-36 - 18 + 78 + 36, -72 + 9 + 30 + 13 + 8), 0)
//                .build());

// region Red Left Stage
        /* RED_LEFT_STAGE. LOCATION 0.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(-36, -72 + 9, Math.toRadians(90)))
            .strafeTo(new Vector2d(-36, -72+9 + 28))
            .strafeToLinearHeading(new Vector2d(-36 + 3, -72+9+28), 0)
            .strafeTo(new Vector2d(-36, -72+9+28))
            .waitSeconds(5)
            .setTangent(Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(-36, -72+9+30 + 13), Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(-36 + 60, -72+9+30+13 + 8), 0)
            .splineToConstantHeading(new Vector2d(-36+60 + 36, -72+9+30+13+8), 0)
            .build());
         */

        /* RED_LEFT_STAGE. LOCATION 1.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(-36, -72 + 9, Math.toRadians(90)))
            .strafeTo(new Vector2d(-36, -72+9 + 30))
            .strafeToLinearHeading(new Vector2d(-36 - 18, -72+9+30), 0)
            .waitSeconds(5)
            .setTangent(Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(-36-18, -72+9+30 + 13), Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(-36-18 + 78, -72+9+30+13 + 8), 0)
            .splineToConstantHeading(new Vector2d(-36-18+78 + 36, -72+9+30+13+8), 0)
            .build());
         */

        /* RED_LEFT_STAGE. LOCATION 2.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(-36, -72 + 9, Math.toRadians(90)))
            .strafeTo(new Vector2d(-36, -72+9 + 28))
            .strafeToLinearHeading(new Vector2d(-36 + 3, -72+9+28), 0)
            .strafeToLinearHeading(new Vector2d(-36 - 18, -72+9+28), 0)
            .waitSeconds(5)
            .setTangent(Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(-36-18, -72+9+30 + 13), Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(-36-18 + 78, -72+9+30+13 + 8), 0)
            .splineToConstantHeading(new Vector2d(-36-18+78 + 36, -72+9+30+13+8), 0)
            .build());
         */
// endregion

// region Blue Right Stage
        /* BLUE_RIGHT_STAGE. LOCATION 0.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(-36, 72 - 9, Math.toRadians(-90)))
            .strafeTo(new Vector2d(-36, 72-9 - 28))
            .strafeToLinearHeading(new Vector2d(-36 + 3, 72-9-28), 0)
            .strafeToLinearHeading(new Vector2d(-36 - 18, 72-9-28), 0)
            .waitSeconds(5)
            .setTangent(Math.toRadians(-90))
            .splineToConstantHeading(new Vector2d(-36-18, 72-9-30 - 13), Math.toRadians(-90))
            .splineToConstantHeading(new Vector2d(-36-18 + 78, 72-9-30-13 - 8), 0)
            .splineToConstantHeading(new Vector2d(-36-18+78 + 36, 72-9-30-13-8), 0)
            .build());
         */

        /* BLUE_RIGHT_STAGE. LOCATION 1.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(-36, 72 - 9, Math.toRadians(-90)))
            .strafeTo(new Vector2d(-36, 72-9 - 30))
            .strafeToLinearHeading(new Vector2d(-36 - 18, 72-9-30), 0)
            .waitSeconds(5)
            .setTangent(Math.toRadians(270))
            .splineToConstantHeading(new Vector2d(-36-18, 72-9-30 - 13), Math.toRadians(-90))
            .splineToConstantHeading(new Vector2d(-36-18 + 78, 72-9-30-13 - 8), 0)
            .splineToConstantHeading(new Vector2d(-36-18+78 + 36, 72-9-30-13-8), 0)
            .build());
         */

        /* BLUE_RIGHT_STAGE. LOCATION 2.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(-36, 72 - 9, Math.toRadians(-90)))
            .strafeTo(new Vector2d(-36, 72-9 - 28))
            .strafeToLinearHeading(new Vector2d(-36 - 3, 72-9-28), Math.toRadians(180))
            .strafeTo(new Vector2d(-36, 72-9-28))
            .waitSeconds(5)
            .setTangent(Math.toRadians(-90))
            .splineToConstantHeading(new Vector2d(-36, 72-9-30 - 13), Math.toRadians(-90))
            .splineToConstantHeading(new Vector2d(-36 + 60, 72-9-30-13 - 8), 0)
            .splineToConstantHeading(new Vector2d(-36+60 + 36, 72-9-30-13-8), 0)
            .build());
         */
// endregion

// region Red Right Stage
        /* RED_RIGHT_STAGE. LOCATION 0.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(12, -72 + 9, Math.toRadians(90)))
            .strafeTo(new Vector2d(12, -72+9 + 28))
            .strafeToLinearHeading(new Vector2d(12 - 3, -72+9+28), Math.toRadians(180))
            .setReversed(true)
            .splineToConstantHeading(new Vector2d(12 + 48, -72+9), 0)
            .build());
         */

        /* RED_RIGHT_STAGE. LOCATION 1.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(12, -72 + 9, Math.toRadians(90)))
            .strafeTo(new Vector2d(12, -72+9 + 30))
            .setReversed(true)
            .splineToConstantHeading(new Vector2d(12 + 48, -72+9), 0)
            .build());
         */

        /* RED_RIGHT_STAGE. LOCATION 2.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(12, -72 + 9, Math.toRadians(90)))
            .strafeTo(new Vector2d(12, -72+9 + 28))
            .strafeToSplineHeading(new Vector2d(12 + 3, -72+9+28), Math.toRadians(0))
            .setReversed(true)
            .splineToConstantHeading(new Vector2d(12 + 48, -72+9), 0)
            .build());
         */
// endregion

//region Blue Left Stage
        /* BLUE_LEFT_STAGE. LOCATION 0.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(12, 72 - 9, Math.toRadians(-90)))
            .strafeTo(new Vector2d(12, 72-9 - 28))
            .strafeToSplineHeading(new Vector2d(12 + 3, 72-9-28), Math.toRadians(0))
            .setReversed(true)
            .splineToConstantHeading(new Vector2d(12 + 48, 72-9), 0)
            .build());
         */

        /* BLUE_LEFT_STAGE. LOCATION 1.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(12, 72 - 9, Math.toRadians(-90)))
            .strafeTo(new Vector2d(12, 72-9 - 30))
            .setReversed(true)
            .splineToConstantHeading(new Vector2d(12 + 48, 72-9), 0)
            .build());
         */

        /* BLUE_LEFT_STAGE. LOCATION 2.
        myBot.runAction(myBot.getDrive().actionBuilder(
            new Pose2d(12, 72 - 9, Math.toRadians(-90)))
            .strafeTo(new Vector2d(12, 72-9 - 28))
            .strafeToSplineHeading(new Vector2d(12 - 3, 72-9-28), Math.toRadians(180))
            .setReversed(true)
            .splineToConstantHeading(new Vector2d(12 + 48, 72-9), 0)
            .build());
         */
// endregion

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}