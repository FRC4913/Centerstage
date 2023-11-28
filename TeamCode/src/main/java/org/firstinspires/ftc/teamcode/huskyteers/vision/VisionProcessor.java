package org.firstinspires.ftc.teamcode.huskyteers.vision;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.opencv.core.Mat;

public class VisionProcessor implements org.firstinspires.ftc.vision.VisionProcessor {

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        canvas.drawLine(onscreenWidth / 3, 0, onscreenWidth / 3, onscreenHeight, paint);
        canvas.drawLine(2 * onscreenWidth / 3, 0, 2 * onscreenWidth / 3, onscreenHeight, paint);
    }
}
