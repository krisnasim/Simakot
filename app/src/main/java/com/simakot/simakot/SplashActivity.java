package com.simakot.simakot;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    private static int SPLASH_TIME_OUT = 2500;
    static final Integer CAMERA = 0x1;
    private boolean isCameraGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        askForPermission(Manifest.permission.CAMERA, CAMERA);

//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                //get out from here
//                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_TIME_OUT);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            switch (requestCode) {
                //Camera
                case 1:
                    isCameraGranted = true;
                    Log.d("cameraGranted!", "camera is granted!");
                    callTimer();
                    break;
            }
            //Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_DENIED){
            switch (requestCode) {
                //Camera
                case 1:
                    showMessageOKCancel("Kami perlu akses kamera anda untuk dapat mengambil gambar dengan aplikasi Verificare. Mohon mengaktifkan agar aplikasi bisa berjalan dengan baik.", this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("testCamera", "Asking for camera permission");
                            askForPermission(Manifest.permission.CAMERA,CAMERA);
                        }
                    }, this);
                    break;
            }
        } else{
            //Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    private static void showMessageOKCancel(String message, Activity activity, DialogInterface.OnClickListener okListener,
                                            DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(activity).setMessage(message).setPositiveButton("OK", okListener).create().show();
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            //Log.d("askPermission", "permission is not granted for "+permission);
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
        } else {
            //Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();

            //check for request code that has been accepted. check for those who are not accepted yet
//            if(requestCode == LOCATION) {
//                isLocationGranted = true;
//                askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST);
//                Log.d("locationCheck", "location is granted!");
//                checkAllPermissions();
//            }
//
//            if(requestCode == WRITE_EXST) {
//                isMediaGranted = true;
//                askForPermission(Manifest.permission.CAMERA,CAMERA);
//                Log.d("locationCheck", "media external is granted!");
//                checkAllPermissions();
//            }

            if(requestCode == CAMERA) {
                isCameraGranted = true;
                Log.d("locationCheck", "camera is granted!");
                //callTimer();
            }
        }
    }

    private void callTimer() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                //get out from here
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
