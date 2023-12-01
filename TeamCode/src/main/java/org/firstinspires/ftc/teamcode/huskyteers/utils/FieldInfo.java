package org.firstinspires.ftc.teamcode.huskyteers.utils;

import com.acmerobotics.roadrunner.Pose2d;

public class FieldInfo {
    public static Pose2d getStartPose(Position position, double thickness) {
        switch (position) {
            case RED_LEFT_STAGE:
                return new Pose2d(-36, -72 + thickness / 2, 90);
            case RED_RIGHT_STAGE:
                return new Pose2d(36, -72 + thickness / 2, 90);
            case BLUE_LEFT_STAGE:
                return new Pose2d(36, 72 - thickness / 2, 270);
            case BLUE_RIGHT_STAGE:
                return new Pose2d(-36, 72 - thickness / 2, 270);
        }
        return null;
    }
}
