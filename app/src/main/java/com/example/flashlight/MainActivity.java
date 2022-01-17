package com.example.flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton toggleButton;

    boolean hasCameraFlash = false;
    boolean flashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.off);

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasCameraFlash){
                    if(flashOn){
                        flashOn = false;
                        toggleButton.setImageResource(R.drawable.power_off);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            flashLightOff();
                        }
                    }
                    else{
                        flashOn = true;
                        toggleButton.setImageResource(R.drawable.power_on);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            flashLightOn();
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "No flash available on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLightOn(){
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            assert cameraManager != null;
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
            Toast.makeText(MainActivity.this, "FlashLight is ON", Toast.LENGTH_SHORT).show();
        }
        catch(CameraAccessException e){
            Log.e("Camera Problem", "Cannot turn on camera flashlight");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLightOff(){
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            assert cameraManager != null;
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
            Toast.makeText(MainActivity.this, "FlashLight is OFF", Toast.LENGTH_SHORT).show();
        }
        catch(CameraAccessException e){
            Log.e("Camera Problem", "Cannot turn off camera flashlight");
        }
    }
}