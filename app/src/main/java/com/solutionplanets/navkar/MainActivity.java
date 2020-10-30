package com.solutionplanets.navkar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    /*public static final String KEY_DURATION = "duration";
    public static final String KEY_COUNT = "count";
    public static final String KEY_SPEED = "speed";*/

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference summaryRef = db.collection("JaapSummary");

    private TextView timer, counter;
    private NumberPicker speed;
    private Button start, playPause, stop, reset;
    private int seconds = 0;
    private int count = 0;
    private int secPerNavkar;
    private boolean running = false;

    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timer = findViewById(R.id.timerView);
        counter = findViewById(R.id.countView);
        speed = findViewById(R.id.numPicker);
        speed.setMinValue(5);
        speed.setMaxValue(15);
        speed.setValue(9);

        start = findViewById(R.id.startBtn);
        playPause = findViewById(R.id.pauseContinueBtn);
        stop = findViewById(R.id.stopBtn);
        reset = findViewById(R.id.resetBtn);

        if( savedInstanceState != null)
        {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            count = savedInstanceState.getInt("navkarCnt");
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setVisibility(View.INVISIBLE);
                playPause.setVisibility(View.VISIBLE);
                stop.setVisibility(View.VISIBLE);
                runTimer();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.summary:
                startActivity(new Intent(this, SummaryList.class));
                return true;
            case R.id.audio:
                startActivity(new Intent(this, SoundTrack.class));
                return true;
            case R.id.support:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.solutionplanets.com/")));
                return true;
            case R.id.logout:
                startActivity(new Intent(this, Login.class));
                return true;
            case R.id.about:
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.solutionplanets.com/")));
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    public  void onSaveInstanceState(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putInt("navkarCnt", count);
    }

    public void resetTimer(){
        running = false;
        seconds = 0 ;
        start.setVisibility(View.VISIBLE);
        playPause.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.INVISIBLE);
        speed.setEnabled(true);
        reset.setVisibility(View.INVISIBLE);
    }

    public void stopTimer(View v) {
        running = false;
        playPause.setText("Continue");
        reset.setVisibility(View.VISIBLE);
        showLogMsg();
    }

    public void togglePlayPause(View v) {
        running = !running;
        if(running){
            playPause.setText("Pause");
            running = true;

        }else {
            playPause.setText("Continue");
            running = false;
        }
    }

    private void runTimer() {
        secPerNavkar = speed.getValue();
        running = true;
        speed.setEnabled(false);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = (seconds / 3600);
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                int count = seconds / secPerNavkar;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timer.setText(time);
                String navkarCnt = String.format("%01d", count);
                counter.setText(navkarCnt);

                if (running) {
                    seconds++;
                    count++;
                }
                handler.postDelayed(this, 1000);
            }

        }) ;
    }

    public void saveJaapSummary(){
        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance().format(calendar.getTime());
        String duration = timer.getText().toString();
        String count = counter.getText().toString();

        summaryRef.add(new Summary(date, duration, count));
        finish();
    }

    public void showLogMsg(){
        final String jappTime = timer.getText().toString();
        final String mantraCnt = counter.getText().toString();;
        android.app.AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Do you want save Jaap summary?")
                .setMessage(" Your Jaap duration is  " + jappTime + " and Navkar count is " + mantraCnt)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //dbConn.insertLog(jappTime, mantraCnt, user.getuID());
                        saveJaapSummary();
                        resetTimer();
                        Toast.makeText(MainActivity.this, "Jaap data is saved successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog dialog = adb.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
    }
}