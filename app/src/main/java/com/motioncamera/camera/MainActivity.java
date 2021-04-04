package com.motioncamera.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    android.hardware.Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout)findViewById(R.id.frame_layout);

        // Open the camera

        camera = android.hardware.Camera.open();
        showCamera = new ShowCamera(this,camera);

        frameLayout.addView(showCamera);
    }
}