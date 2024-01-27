package com.example.huskyteers;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

public class Paths {
    /**
     * This is relative to 0, 0, because it doesn't really matter which one it starts from so
     * overcomplicating it here won't help too much
     *
     * @param actionBuilder Starting from 0, 0, 0
     * @return Action to navigate to the team prop
     */
    public static TrajectoryActionBuilder pathToTeamProp(TrajectoryActionBuilder actionBuilder, TeamPropLocation teamPropLocation) {
        switch (teamPropLocation) {
            case LEFT:
                return actionBuilder
                        .setTangent(Math.toRadians(180))
                        .strafeTo(new Vector2d(28, 0))
                        .turnTo(Math.toRadians(90))
                        .waitSeconds(1)
                        .turnTo(Math.toRadians(0))
                        .strafeTo(new Vector2d(0, 0));
            // Ends at 28, -3
            case CENTER:
                return actionBuilder
                        .strafeTo(new Vector2d(33, 0))
                        .waitSeconds(1)
                        .strafeTo(new Vector2d(0, 0));
            // Ends at 30, 0
            case RIGHT:
                return actionBuilder
                        .setTangent(Math.toRadians(180))
                        .strafeTo(new Vector2d(28, 0))
                        .turnTo(Math.toRadians(-90))
                        .waitSeconds(1)
                        .turnTo(Math.toRadians(0))
                        .strafeTo(new Vector2d(0, 0));
            // Ends at 28, 3
        }
        return actionBuilder;
    }

    public static Action pathToParkingFromFrontstage(TrajectoryActionBuilder actionBuilder) {
        return actionBuilder
                .strafeTo(new Vector2d(-60, 60))
                .turn(Math.toRadians(90))
                .strafeTo(new Vector2d(-60, 12))
                .strafeTo(new Vector2d(60, 12))
                .build();
    }

    public static Vector2d getBackdropPlace(TeamPropLocation teamPropLocation) {
        switch (teamPropLocation) {
            case LEFT:
                return new Vector2d(50, 40);
            case CENTER:
                return new Vector2d(50, 35);
            case RIGHT:
                return new Vector2d(50, 30);
        }
        return new Vector2d(0, 0);
    }

    public static TrajectoryActionBuilder pathToBackdrop(TrajectoryActionBuilder actionBuilder, TeamPropLocation teamPropLocation) {
        return actionBuilder.splineTo(new Vector2d(30, 50), 0).splineTo(getBackdropPlace(teamPropLocation), 0);

    }

    public static TrajectoryActionBuilder pathToParkingFromBackstage(TrajectoryActionBuilder actionBuilder) {
        return actionBuilder.lineToX(40).turnTo(Math.toRadians(180)).splineToConstantHeading(new Vector2d(60, 60), 0);
    }
}
