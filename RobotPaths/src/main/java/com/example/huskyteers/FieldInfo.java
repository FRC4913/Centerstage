package com.example.huskyteers;

import com.acmerobotics.roadrunner.Pose2d;

public class FieldInfo {
    public static double TILE_SIZE = 24;

    public static double tiles(double num) {
        return TILE_SIZE * num;
    }

    public static Pose2d getStartPose(Position position, double thickness) {
        switch (position) {
            case RED_LEFT:
                return new Pose2d(tiles(-1.5), tiles(-3) + thickness / 2, Math.toRadians(-90));
            case RED_RIGHT:
                return new Pose2d(tiles(0.5), -tiles(3) + thickness / 2, Math.toRadians(-90));
            case BLUE_LEFT:
                return new Pose2d(tiles(0.5), tiles(3) - thickness / 2, Math.toRadians(90));
            case BLUE_RIGHT:
                return new Pose2d(tiles(-1.5), tiles(3) - thickness / 2, Math.toRadians(90));
            default:
                return new Pose2d(0, 0, 0);
        }
    }

    public static boolean isRed(Position position) {
        return position == Position.RED_RIGHT || position == Position.RED_LEFT;
    }

    public static boolean isBackstage(Position position) {
        return position == Position.BLUE_LEFT || position == Position.RED_RIGHT;
    }
}
