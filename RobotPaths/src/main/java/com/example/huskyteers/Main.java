package com.example.huskyteers;

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

    public static void main(String[] args) {
        myBot.runAction(myBot.getDrive().actionBuilder(FieldInfo.getStartPose(Position.RED_RIGHT, RobotInfo.HEIGHT))
                .lineToY(-30)
                .turn(Math.toRadians(-90))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}