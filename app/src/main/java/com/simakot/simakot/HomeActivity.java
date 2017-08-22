package com.simakot.simakot;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.full_name_label) TextView full_name_label;
    @BindView(R.id.nrp_num_label) TextView nrp_num_label;
    @BindView(R.id.target_label) TextView target_label;
    @BindView(R.id.sisa_target_label) TextView sisa_target_label;
    @BindView(R.id.penindakan_label) TextView penindakan_label;
    @BindView(R.id.target_number_label) TextView target_number_label;
    @BindView(R.id.sisa_target_number_label) TextView sisa_target_number_label;
    @BindView(R.id.penindakan_number_label) TextView penindakan_number_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/swis.ttf");
        full_name_label.setTypeface(myFont);
        nrp_num_label.setTypeface(myFont);
        target_label.setTypeface(myFont);
        sisa_target_label.setTypeface(myFont);
        penindakan_label.setTypeface(myFont);
        target_number_label.setTypeface(myFont);
        sisa_target_number_label.setTypeface(myFont);
        penindakan_number_label.setTypeface(myFont);
    }
}
