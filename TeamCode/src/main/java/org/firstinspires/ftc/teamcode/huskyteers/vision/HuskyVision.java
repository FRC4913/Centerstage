package org.firstinspires.ftc.teamcode.huskyteers.vision;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.huskyteers.HuskyBotConfig;
import org.firstinspires.ftc.teamcode.huskyteers.OpenCv;
import org.firstinspires.ftc.vision.VisionPortal;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class HuskyVision {

    public AprilTagDetector AprilTagDetector;
    public OpenCv visionProcessor;
    public VisionPortal visionPortal;

    public TensorflowDetection tensorflowdetection;
    public final static int WIDTH = 640;
    public final static int HEIGHT = 480;
    public final static int MAX_TIME = 4000;


    public HuskyVision(HardwareMap hwMap) {
        AprilTagDetector = new AprilTagDetector();


        visionProcessor = new OpenCv();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(WIDTH, HEIGHT))
                .enableLiveView(true)
                .addProcessors(AprilTagDetector.aprilTag, visionProcessor)
                .build();


        // Manually set the camera gain and exposure.
        // ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        // exposureControl.setExposure((long)6, TimeUnit.MILLISECONDS);
        // GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        // gainControl.setGain(250);
    }

    public int detectTeamPropLocation() {
        return visionProcessor.getPropLocation();
    }

    public void setExposure() {
        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
        }

        ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        exposureControl.setMode(ExposureControl.Mode.Manual);
        exposureControl.setExposure(HuskyBotConfig.DURATION, TimeUnit.MILLISECONDS);
        GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        gainControl.setGain(HuskyBotConfig.GAIN);
    }
}
