package com.example.myfood.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfood.Activity.Camera;
import com.example.myfood.R;

public class Scan extends Fragment {
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_scan, container, false);

        progressBar=view.findViewById(R.id.scan_progresBar);

        Intent camera_intent = new Intent(getContext(), Camera.class);
        startActivity(camera_intent);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {


            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "הפריטים נוספו בהצלחה", Toast.LENGTH_SHORT).show();


            }
        }, 3000);




        return view;

    }
}
