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
                        .setTangent(Math.toRadians(180))
                        .strafeTo(new Vector2d(28, 0))
                        .turnTo(Math.toRadians(90))
                        .waitSeconds(1)
                        .turnTo(Math.toRadians(0))
                        .strafeTo(new Vector2d(0, 0))
                        .build();
            // Ends at 28, -3
            case CENTER:
                return actionBuilder
                        .strafeTo(new Vector2d(30, 0))
                        .waitSeconds(1)
                        .strafeTo(new Vector2d(0, 0))
                        .build();
            // Ends at 30, 0
            case RIGHT:
                return actionBuilder
                        .setTangent(Math.toRadians(180))
                        .strafeTo(new Vector2d(28, 0))
                        .turnTo(Math.toRadians(-90))
                        .waitSeconds(1)
                        .turnTo(Math.toRadians(0))
                        .strafeTo(new Vector2d(0, 0))
                        .build();
            // Ends at 28, 3
        }
        return actionBuilder.build();
    }

    public static Action pathToParkingFromFrontstage(TrajectoryActionBuilder actionBuilder) {
        // Assigned to francis
        return actionBuilder.build();
    }

    public static Action pathToBackdrop(TrajectoryActionBuilder actionBuilder, TeamPropLocation teamPropLocation) {
        // Assigned to Ethan
        return actionBuilder.build();
    }

    public static Action pathToParkingFromBackstage(TrajectoryActionBuilder actionBuilder) {
        // Assigned to Ethan
        return actionBuilder.build();
    }
}
