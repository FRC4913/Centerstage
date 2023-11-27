package org.firstinspires.ftc.teamcode.huskyteers.vision;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.huskyteers.HuskyBotConfig;
import org.firstinspires.ftc.vision.VisionPortal;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class HuskyVision {

    public AprilTagDetector AprilTagDetector;

    public VisionPortal visionPortal;
    public TensorflowDetection tensorflowdetection;
    public final static int WIDTH = 640;
    public final static int HEIGHT = 480;
    public final static int MAX_TIME = 4000;


    public HuskyVision(HardwareMap hwMap) {
        AprilTagDetector = new AprilTagDetector();
        tensorflowdetection = new TensorflowDetection();
        tensorflowdetection.initTfod();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(WIDTH, HEIGHT))
                .enableLiveView(true)
                .addProcessors(AprilTagDetector.aprilTag, tensorflowdetection.tfod)
                .build();


        // Manually set the camera gain and exposure.
        // ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        // exposureControl.setExposure((long)6, TimeUnit.MILLISECONDS);
        // GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        // gainControl.setGain(250);
    }

    /**
     * Let's arbitrarily assign numbers to each side it could be based on the location relative
     * to the robot's starting position.
     * <pre>
     *      1
     *    ______
     * 0 |     |  2
     *   |     |
     *    robot start
     *  </pre>
     *
     * @return Location of team prop, or -1 if not found
     */
    public int detectTeamPropLocation() {

        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < MAX_TIME) {
            List<Recognition> recognitions = tensorflowdetection.tfod.getRecognitions();
            Optional<Recognition> likelyRecognition = recognitions.stream()
                    .filter(recognition -> recognition.getLabel().equals("HuskyProp"))
                    .findAny();
            if (likelyRecognition.isPresent()) {
                Recognition recognition = likelyRecognition.get();

                return (int) (HuskyVision.WIDTH / (recognition.getLeft() + recognition.getRight()) / 2);
            }
            // TODO: Better recognition algorithm, currently classifying based on the 1/3 of the screen that it is found in.

            try {
                Thread.sleep(100); // Sleep for a short duration before retrying
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return -1; // Handle interrupted exception
            }
            // TODO: Add some code maybe to move the robot a little to improve chances of finding team prop instead of trying same thing 20 times
        }

        return -1; // Return -1 if the prop is not found within the time limit
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
