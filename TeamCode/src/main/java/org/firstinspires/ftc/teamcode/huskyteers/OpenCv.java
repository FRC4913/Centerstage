package org.firstinspires.ftc.teamcode.huskyteers;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class OpenCv implements VisionProcessor {
    private int mostRedPart = 0;

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
        Scalar upperRed = new Scalar(0, 255, 255);

        int partWidth = frame.width() / 3;
        Mat part1 = hsvFrame.submat(new Rect(0, 0, partWidth, frame.height()));
        Mat part2 = hsvFrame.submat(new Rect(partWidth, 0, partWidth, frame.height()));
        Mat part3 = hsvFrame.submat(new Rect(2 * partWidth, 0, partWidth, frame.height()));

        Mat mask1 = new Mat();
        Mat mask2 = new Mat();
        Mat mask3 = new Mat();

        Core.inRange(part1, lowerRed, upperRed, mask1);
        Core.inRange(part2, lowerRed, upperRed, mask2);
        Core.inRange(part3, lowerRed, upperRed, mask3);

        double redSaturation1 = Core.sumElems(mask1).val[0];
        double redSaturation2 = Core.sumElems(mask2).val[0];
        double redSaturation3 = Core.sumElems(mask3).val[0];

        this.mostRedPart = (redSaturation1 > redSaturation2) ?
                ((redSaturation1 > redSaturation3) ? 1 : 3) :
                ((redSaturation2 > redSaturation3) ? 2 : 3);

        hsvFrame.release();
        part1.release();
        part2.release();
        part3.release();
        mask1.release();
        mask2.release();
        mask3.release();

        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Paint textPaint = new Paint();
        textPaint.setColor(android.graphics.Color.WHITE);
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

        if (this.mostRedPart > 0) {
            int labelXPosition = (this.mostRedPart - 1) * partWidth + 10;
            canvas.drawText("Most Red: Part " + this.mostRedPart, labelXPosition, 30, textPaint);
        }
    }
}
