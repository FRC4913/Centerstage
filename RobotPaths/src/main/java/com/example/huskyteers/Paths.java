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
            case CENTER:
                return actionBuilder
                        .strafeTo(new Vector2d(30, 0))
                        .build();
            case RIGHT:
                return actionBuilder
                        .lineToX(28)
                        .turnTo(Math.toRadians(-90))
                        .lineToY(-2)
                        .waitSeconds(1)
                        .lineToY(3)
                        .build();
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
}
