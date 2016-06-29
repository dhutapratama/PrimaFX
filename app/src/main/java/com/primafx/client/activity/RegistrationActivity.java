package com.primafx.client.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.primafx.client.R;
import com.primafx.client.model.ParalaxScrollView;

public class RegistrationActivity extends AppCompatActivity implements ParalaxScrollView.OnScrollChangedListener{
    private ParalaxScrollView mScrollView;
    private View imgContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    @Override
    public void onScrollChanged(int deltaX, int deltaY) {
        int scrollY = mScrollView.getScrollY();
        // Add parallax effect
        imgContainer.setTranslationY(scrollY * 0.5f);
    }
}
