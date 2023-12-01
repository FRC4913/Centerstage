package org.firstinspires.ftc.teamcode.huskyteers.vision;

import org.firstinspires.ftc.vision.tfod.TfodProcessor;

public class TensorflowDetection {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_Blue_MODEL_ASSET = "HuskyPropBlue.tflite";
    private static final String TFOD_Red_MODEL_ASSET = "HuskyPropRed.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.

    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
            "HuskyProp",
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    public TfodProcessor tfodBlue;
    public TfodProcessor tfodRed;

    /**
     * The variable to store our instance of the vision portal.
     */


    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    public void initTfod() {


        // Create the TensorFlow processor by using a builder.
        tfodBlue = new TfodProcessor.Builder()
                .setModelAssetName(TFOD_Blue_MODEL_ASSET)
                .setModelLabels(LABELS)
                .build();

        tfodRed = new TfodProcessor.Builder()
                .setModelAssetName(TFOD_Red_MODEL_ASSET)
                .setModelLabels(LABELS)
                .build();
        // Create the vision portal by using a builder.


        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);


        // Set confidence threshold for TFOD recognitions, at any time.
        tfodBlue.setMinResultConfidence(0.7f);
        tfodRed.setMinResultConfidence(0.7f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */


}   // end method telemetryTfod()


