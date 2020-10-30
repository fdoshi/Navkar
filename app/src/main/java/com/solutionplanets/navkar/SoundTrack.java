package com.solutionplanets.navkar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.solutionplanets.navkar.R.drawable.ic_pause;
import static com.solutionplanets.navkar.R.drawable.ic_play;

public class SoundTrack extends AppCompatActivity {
    private MediaPlayer player;
    private ImageButton playBtn1;
    private SeekBar seekBar1;
    private TextView time1;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_track);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        playBtn1 = findViewById(R.id.play1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
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
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    public void playPause1 (View view){
        if(player == null){
            player = MediaPlayer.create(SoundTrack.this, R.raw.navkarmantra);
            playBtn1.setImageResource(ic_pause);

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    player.reset();
                    seekBar1.setProgress(0);
                    playBtn1.setImageResource(ic_play);
                    time1.setText("00:00");
                    releasePlayer();
                }
            });
            player.start();

        }else if(player != null){
            player.pause();
            playBtn1.setImageResource(ic_play);
           //releasePlayer();
        }

    }

    public void releasePlayer(){
        if(player != null){
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }
}