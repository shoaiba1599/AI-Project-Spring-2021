package com.motioncamera.camera;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    android.hardware.Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;
    static int count;
    MyCounter timer = null;
    byte[] imageInBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);

        // Open the camera

        camera = android.hardware.Camera.open();
        showCamera = new ShowCamera(this, camera);

        frameLayout.addView(showCamera);

    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.e("called", "picture taken");
            camera.stopPreview();
            if (null == imageInBytes) {
                imageInBytes = data;
            } else {
                Bitmap img1 = BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);
                Bitmap img2 = BitmapFactory.decodeByteArray(data, 0, data.length);
                int width = img1.getWidth();
                int height = img1.getHeight();
                int width2 = img2.getWidth();
                int height2 = img2.getHeight();
                long diff = 0;
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        diff += pixelDiff(img1.getColor(x, y).toArgb(), img2.getColor(x, y).toArgb());
                    }
                }
                //Check
                long maxDiff = 3L * 255 * width * height;
                double percent = 100.0 * diff / maxDiff;
                ;

                // boolean diff = Arrays.equals(imageInBytes,data);
                if (percent > 10) {
                    try {
                        Thread.sleep(1000);
                        Toast.makeText(MainActivity.this, "Detect Motion", Toast.LENGTH_SHORT).show();
                        imageInBytes = data;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    };


    public void stop(View v) {
        timer.cancel();
    }

    private static int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = rgb1 & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = rgb2 & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }

   /* private File getOutputMediaFile() {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        } else {
            File folder_gui = new File(Environment.getExternalStorageState() + File.separator + "GUI");
            if (!folder_gui.exists()) {
                folder_gui.mkdirs();
            }
            File outputFile = new File(folder_gui, "temp.jpg");
            return outputFile;
        }
    }*/

    public void captureImage(View v) {
        if (camera != null) {
            try {

                camera.takePicture(null, null, mPictureCallback);

            } catch (Exception e) {
                showCamera = new ShowCamera(MainActivity.this, camera);

                frameLayout.addView(showCamera);
            }
            timer = new MyCounter(1000, 1000);
            timer.start();
        }
    }


    public class MyCounter extends CountDownTimer {

        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            Log.e("Finish", "Timer Completed.");

            timer.start();
        }

        @Override
        public void onTick(long millisUntilFinished) {

            try {

                camera.startPreview();
                camera.takePicture(null, null, mPictureCallback);

            } catch (Exception e) {
                showCamera = new ShowCamera(MainActivity.this, camera);

                frameLayout.addView(showCamera);
            }

        }
    }
}
