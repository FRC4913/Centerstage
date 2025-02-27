package org.firstinspires.ftc.teamcode.huskyteers.vision;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.teamcode.huskyteers.HuskyBotConfig;
import org.firstinspires.ftc.teamcode.huskyteers.TelemetryUtils;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.concurrent.TimeUnit;

public class HuskyVision {

    public AprilTagDetector AprilTagDetector;

    public VisionPortal visionPortal;
    public TensorflowDetection tensorflowdetection;

    public HuskyVision(HardwareMap hwMap) {
        AprilTagDetector = new AprilTagDetector();
        tensorflowdetection =  new TensorflowDetection();
        tensorflowdetection.initTfod();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .enableLiveView(true)
                .addProcessors(AprilTagDetector.aprilTag,tensorflowdetection.tfod)
                .build();


        // Manually set the camera gain and exposure.
        // ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        // exposureControl.setExposure((long)6, TimeUnit.MILLISECONDS);
        // GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        // gainControl.setGain(250);
    }
    public void setExposure(){
        while (visionPortal.getCameraState()!= VisionPortal.CameraState.STREAMING) {}


        ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        exposureControl.setMode(ExposureControl.Mode.Manual);
        exposureControl.setExposure(HuskyBotConfig.DURATION, TimeUnit.MILLISECONDS);
        GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        gainControl.setGain(HuskyBotConfig.GAIN);
    }
}
