package com.simakot.simakot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuccessActivity extends AppCompatActivity {

    @BindView(R.id.success_image) ImageView success_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ButterKnife.bind(this);

        success_image.setImageResource(R.drawable.success_exception_button);
    }
}
