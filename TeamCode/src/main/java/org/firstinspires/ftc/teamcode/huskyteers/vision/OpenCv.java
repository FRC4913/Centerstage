package org.firstinspires.ftc.teamcode.huskyteers.vision;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Optional;

public class OpenCv implements VisionProcessor {
    private int redPropLocation;
    private int bluePropLocation;
    private double val1;
    private double val2;
    private double val3;

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        // no need
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Mat hsvFrame = new Mat();
        Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_RGB2HSV);

        // this is for the red scala in HSV
        Scalar lowerRed = new Scalar(0, 100, 20);
        Scalar upperRed = new Scalar(10, 255, 255);
//        Scalar lowerBlue = new Scalar(128, 255, 255);
//        Scalar upperBlue = new Scalar(90, 50, 70);
        //TODO: I changed the hsv values, so we need to test the new value.
        Scalar lowerBlue = new Scalar(100, 50, 50);    // Lower bound for blue
        Scalar upperBlue = new Scalar(130, 255, 255);
        int partWidth = frame.width() / 3;
        Mat part1 = hsvFrame.submat(new Rect(0, 0, partWidth, frame.height()));
        Mat part2 = hsvFrame.submat(new Rect(partWidth, 0, partWidth, frame.height()));
        Mat part3 = hsvFrame.submat(new Rect(2 * partWidth, 0, partWidth, frame.height()));

        Mat redMask1 = new Mat();
        Mat redMask2 = new Mat();
        Mat redMask3 = new Mat();

        Core.inRange(part1, lowerRed, upperRed, redMask1);
        Core.inRange(part2, lowerRed, upperRed, redMask2);
        Core.inRange(part3, lowerRed, upperRed, redMask3);

        double redSaturation1 = Core.sumElems(redMask1).val[0];
        double redSaturation2 = Core.sumElems(redMask2).val[0];
        double redSaturation3 = Core.sumElems(redMask3).val[0];

        Mat blueMask1 = new Mat();
        Mat blueMask2 = new Mat();
        Mat blueMask3 = new Mat();

        Core.inRange(part1, lowerBlue, upperBlue, blueMask1);
        Core.inRange(part2, lowerBlue, upperBlue, blueMask2);
        Core.inRange(part3, lowerBlue, upperBlue, blueMask3);

            double blueSaturation1 = Core.sumElems(blueMask1).val[0];
            double blueSaturation2 = Core.sumElems(blueMask2).val[0];
            double blueSaturation3 = Core.sumElems(blueMask3).val[0];
        val1 = blueSaturation1;
        val2 = blueSaturation2;
        val3 = blueSaturation3;


        this.redPropLocation = (redSaturation1 > redSaturation2) ?
                ((redSaturation1 > redSaturation3) ? 1 : 3) :
                ((redSaturation2 > redSaturation3) ? 2 : 3);

        this.bluePropLocation = (blueSaturation1 > blueSaturation2) ?
                ((blueSaturation1 > blueSaturation3) ? 1 : 3) :
                ((blueSaturation2 > blueSaturation3) ? 2 : 3);


        hsvFrame.release();
        part1.release();
        part2.release();
        part3.release();
        redMask1.release();
        redMask2.release();
        redMask3.release();
        blueMask1.release();
        blueMask2.release();
        blueMask3.release();

        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Paint textPaint = new Paint();
        textPaint.setColor(android.graphics.Color.RED);
        textPaint.setTextSize(30);

        Paint rectPaint = new Paint();
        rectPaint.setColor(android.graphics.Color.GREEN);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(5);

        int partWidth = onscreenWidth / 3;
        for (int i = 0; i < 3; i++) {
            int left = i * partWidth;
            canvas.drawRect(left, 0, left + partWidth, onscreenHeight, rectPaint);
        }

        if (this.redPropLocation > 0) {
            int labelXPosition = (this.redPropLocation - 1) * partWidth + 10;
            canvas.drawText("Most Red: Part " + this.redPropLocation, labelXPosition, 30, textPaint);
        }
        if (this.bluePropLocation > 0) {
            int labelXPosition = (this.bluePropLocation - 1) * partWidth + 10;
            canvas.drawText("Most Blue: Part " + this.bluePropLocation, labelXPosition, 60, textPaint);
            canvas.drawText("val1: " + val1 + "val2: " + val2 + "val3: " + val3, labelXPosition, 90, textPaint);
        }
    }

    /**
     * Let's arbitrarily assign numbers to each side it could be based on the location relative
     * to the robot's starting position.
     *
     *      2
     *    ______
     * 1 |     |  3
     *   |     |
     *    robot start
     *
     * NOTE: In this implementation, there is no such a thing "prop not found"
     *    - The method will always return a location that has the most red/blue color on it.
     *
     * @return Location of team prop
     */
    public Optional<Integer> redPropLocation(){
        return Optional.ofNullable(redPropLocation);
    }
    public Optional<Integer> bluePropLocation(){
        return Optional.ofNullable(bluePropLocation);
    }
}
