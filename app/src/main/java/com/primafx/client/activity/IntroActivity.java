package com.primafx.client.activity;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.primafx.client.*;
import com.primafx.client.database.DatabaseSQL;
import com.primafx.client.model.IntroSlide;

public class IntroActivity extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(IntroSlide.newInstance(R.layout.intro_1));
        addSlide(IntroSlide.newInstance(R.layout.intro_2));
        addSlide(IntroSlide.newInstance(R.layout.intro_3));
        addSlide(IntroSlide.newInstance(R.layout.intro_4));

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        //addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(getResources().getColor(R.color.colorPrimary));
        setSeparatorColor(getResources().getColor(R.color.colorAccent));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        DatabaseSQL.updateSecurityData(this, DatabaseSQL.FIELD_FIRST_TIME, "false");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        DatabaseSQL.updateSecurityData(this, DatabaseSQL.FIELD_FIRST_TIME, "false");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged() {
        // Do something when the slide changes.
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }

}