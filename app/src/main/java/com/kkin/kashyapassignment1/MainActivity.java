package com.kkin.kashyapassignment1;

import android.app.Activity;
import android.os.Looper;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.Locale;

import com.kkin.kashyapassignment1.databinding.ActivityMainBinding;


public class MainActivity extends Activity implements View.OnClickListener {
    // View Binding
    private ActivityMainBinding activityMainBinding;

    // Handler to run Timer on the main thread
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Variables to track initial time and elapsed time
    private long initialTime = 0L;
    private long elapsedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewBinding and set the layout
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        // Set click listeners for buttons
        activityMainBinding.buttonStart.setOnClickListener(this);
        activityMainBinding.buttonStop.setOnClickListener(this);
        activityMainBinding.buttonReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Handle click events for the buttons
        if (v.equals(activityMainBinding.buttonStart)) {
            startStopwatch();
        } else if (v.equals(activityMainBinding.buttonStop)) {
            stopStopwatch();
        } else if (v.equals(activityMainBinding.buttonReset)) {
            resetStopwatch();
        }
    }

    // To update the timer in every 10 milliseconds
    private final Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            setTextViewTimer();
            // Repeat task after 10 milliseconds
            handler.postDelayed(this, 10);
        }
    };

    // Set Text for Text View Timer
    private void setTextViewTimer() {
        // Get the current time in milliseconds since the initialTime.
        long currTimeMillis = System.currentTimeMillis() - initialTime;

        // Convert milliseconds into seconds
        int seconds = (int) (currTimeMillis / 1000);

        // Convert seconds into minutes
        int minutes = seconds / 60;
        seconds = seconds % 60;

        // Get the milliseconds after calculating seconds
        int millis = (int) (currTimeMillis % 1000);

        // For showing milliseconds in two digit
        millis = millis / 10;
        activityMainBinding.tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, millis));
    }

    // Method to reset the stopwatch
    private void resetStopwatch() {
        // Reset timeElapsed to zero
        elapsedTime = 0L;
        // Set Text for Text View Timer to default value
        activityMainBinding.tvTimer.setText(R.string.default_time);
        // Set Button Visibility
        activityMainBinding.buttonStart.setVisibility(View.VISIBLE);
        activityMainBinding.buttonReset.setVisibility(View.GONE);
        activityMainBinding.buttonStop.setVisibility(View.GONE);
    }

    // Method to start the stopwatch
    private void startStopwatch() {
        // Calculate the initial time from current time millis
        initialTime = System.currentTimeMillis() - elapsedTime;

        // Start updating the timer
        handler.post(updateTimer);

        // Set Button Visibility
        activityMainBinding.buttonStart.setVisibility(View.GONE);
        activityMainBinding.buttonReset.setVisibility(View.GONE);
        activityMainBinding.buttonStop.setVisibility(View.VISIBLE);
    }

    // Method to stop the stopwatch
    private void stopStopwatch() {
        // Stop updating the timer
        handler.removeCallbacks(updateTimer);

        // Assign value in elapsed time
        elapsedTime = System.currentTimeMillis() - initialTime;

        // Set Button Visibility
        activityMainBinding.buttonReset.setVisibility(View.VISIBLE);
        activityMainBinding.buttonStart.setVisibility(View.VISIBLE);
        activityMainBinding.buttonStop.setVisibility(View.GONE);
    }
}
