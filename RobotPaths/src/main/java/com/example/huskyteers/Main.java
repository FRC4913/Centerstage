package com.example.huskyteers;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.ColorScheme;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Main {
    public static MeepMeep meepMeep = new MeepMeep(600);

    public static RoadRunnerBotEntity createRobot(ColorScheme colorScheme) {
        return new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), RobotInfo.WIDTH)
                .setDimensions(RobotInfo.WIDTH, RobotInfo.HEIGHT)
                .setColorScheme(colorScheme)
                .build();
    }

    public static void main(String[] args) {
        TeamPropLocation teamPropLocation = TeamPropLocation.LEFT;
        RoadRunnerBotEntity backstageBot = createRobot(new ColorSchemeBlueDark());
        backstageBot.runAction(
                Paths.pathToTeamProp(
                        backstageBot.getDrive().actionBuilder(new Pose2d(0, 0, Math.toRadians(-180))),
                        teamPropLocation).build()
//                        .stopAndAdd(Paths.pathToBackdrop(
//                                backstageBot.getDrive().actionBuilder(FieldInfo.getStartPose(Position.BLUE_LEFT, RobotInfo.HEIGHT)),
//                                teamPropLocation
//                        ).build())
//                        .stopAndAdd(
//                                Paths.pathToParkingFromBackstage(
//                                        backstageBot.getDrive().actionBuilder(new Pose2d(Paths.getBackdropPlace(teamPropLocation), 0))
//                                ).build()
//                        ).build()
        );


//        RoadRunnerBotEntity frontstageBot = createRobot(new ColorSchemeBlueLight());
//        frontstageBot.runAction(
//                Paths.pathToBackdrop(
//                        frontstageBot.getDrive().actionBuilder(FieldInfo.getStartPose(Position.BLUE_LEFT, RobotInfo.HEIGHT)),
//                        teamPropLocation
//                ).stopAndAdd(
//                        Paths.pathToParkingFromBackstage(
//                                frontstageBot.getDrive().actionBuilder(new Pose2d(Paths.getBackdropPlace(teamPropLocation), 0))
//                        ).build()
//                ).build()
//        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(backstageBot)
//                .addEntity(frontstageBot)
                .start();

    }
}