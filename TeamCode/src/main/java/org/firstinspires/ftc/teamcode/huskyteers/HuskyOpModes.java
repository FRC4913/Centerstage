package org.firstinspires.ftc.teamcode.huskyteers;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.teamcode.huskyteers.utils.Position;

public class HuskyOpModes {
    final static String TELEOP = "Husky Tele Op Mode";

    @OpModeRegistrar
    public static void register(OpModeManager manager) {
        for (Position p : Position.values()) {
            manager.register(metaForAutoClass(HuskyAuto.class, splitCamelCase(p.toString())), new HuskyAuto(p));
        }

        manager.register(metaForTeleOpClass(HuskyTeleOpMode.class), HuskyTeleOpMode.class);
    }

    static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    private static OpModeMeta metaForAutoClass(Class<? extends OpMode> cls, String info) {
        return new OpModeMeta.Builder()
                .setName(splitCamelCase(cls.getSimpleName()) + ": " + info)
                .setGroup("Huskyteers")
                .setFlavor(OpModeMeta.Flavor.AUTONOMOUS)
                .setTransitionTarget(TELEOP)
                .build();
    }

    private static OpModeMeta metaForTeleOpClass(Class<? extends OpMode> cls) {
        return new OpModeMeta.Builder()
                .setName(splitCamelCase(cls.getSimpleName()))
                .setGroup("Huskyteers")
                .setFlavor(OpModeMeta.Flavor.TELEOP)
                .build();
    }
}
