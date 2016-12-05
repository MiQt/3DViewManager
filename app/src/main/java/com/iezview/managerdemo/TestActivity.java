package com.iezview.managerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_my_view);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(300, 300);
        List<View> views = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            views.add(new MyView(this));
        }
        for (int i = 0; i < views.size(); i++) {
            addContentView(views.get(i), params);
            if (i < 6) {
                views.get(i).setRotationX(30 * i);
            } else {
                views.get(i).setRotationY(30 * (i - 6));
            }
        }
    }
}
