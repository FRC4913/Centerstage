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
    public static Action pathToTeamProp(TrajectoryActionBuilder actionBuilder, TeamPropLocation teamPropLocation) {
        switch (teamPropLocation) {
            case LEFT:
                return actionBuilder
                        .lineToX(28)
                        .turnTo(Math.toRadians(90))
                        .lineToY(3)
                        .waitSeconds(1)
                        .lineToY(-3)
                        .build();
            // Ends at 28, -3
            case CENTER:
                return actionBuilder
                        .strafeTo(new Vector2d(30, 0))
                        .build();
            // Ends at 30, 0
            case RIGHT:
                return actionBuilder
                        .lineToX(28)
                        .turnTo(Math.toRadians(-90))
                        .lineToY(-3)
                        .waitSeconds(1)
                        .lineToY(3)
                        .build();
            // Ends at 28, 3
        }
        return actionBuilder.build();
    }

    public static Action reversePathToTeamProp(TrajectoryActionBuilder actionBuilder, TeamPropLocation teamPropLocation) {
        switch (teamPropLocation) {
            case LEFT:
            case RIGHT:
                return actionBuilder.turnTo(Math.toRadians(0))
                        .strafeTo(new Vector2d(0, 0)).build();
            case CENTER:
                return actionBuilder.strafeTo(new Vector2d(0, 0))
                        .build();
        }
        return actionBuilder.build();
    }


    public static Vector2d commonPoint = new Vector2d(-34, -8);

    public static Action pathToCommonPoint(TrajectoryActionBuilder actionBuilder, TeamPropLocation teamPropLocation) {
        switch (teamPropLocation) {
            case LEFT:
            case RIGHT:
                // Start at 28, -3
                return actionBuilder.strafeToSplineHeading(commonPoint, 0).build();
            case CENTER:
                // Start at 30, 0
                return actionBuilder.strafeTo(new Vector2d(FieldInfo.tiles(-1.5), FieldInfo.tiles(-1.75))).turnTo(Math.toRadians(180)).splineTo(new Vector2d(-58, -24), Math.toRadians(90)).splineTo(commonPoint, 0).build();
        }
        return actionBuilder.build();
    }

    public static Action pathToBackstageFar(TrajectoryActionBuilder actionBuilder) {
        return actionBuilder.strafeTo(new Vector2d(58.5, -12.2)).build();
    }
}
