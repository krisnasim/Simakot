package com.simakot.simakot;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.nrp_login_input) EditText nrp_login_input;
    @BindView(R.id.pwd_input) EditText pwd_input;

    @OnClick(R.id.login_button)
    public void login() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/arial_italic.ttf");
        nrp_login_input.setTypeface(myFont);
        pwd_input.setTypeface(myFont);
        //adding changes so I can commit them teehee
    }
}
