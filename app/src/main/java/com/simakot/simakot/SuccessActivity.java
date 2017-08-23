package com.simakot.simakot;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuccessActivity extends AppCompatActivity {

    @BindView(R.id.success_image) ImageView success_image;

    private static int SUCCESS_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ButterKnife.bind(this);

        callTimer();
        //success_image.setImageResource(R.drawable.success_exception_button);
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
                Intent intent = new Intent(SuccessActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SUCCESS_TIME_OUT);
    }
}
