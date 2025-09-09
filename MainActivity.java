package com.holox.firestickdemo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

public class MainActivity extends AppCompatActivity {
    private ExoPlayer player;
    private Button playBtn;
    private Button infoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize PlayerView
        PlayerView playerView = findViewById(R.id.player_view);
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4");
        player.setMediaItem(mediaItem);
        player.prepare();

        // Initialize Buttons
        playBtn = findViewById(R.id.btnPlay);
        infoBtn = findViewById(R.id.btnInfo);

        if (playBtn != null) {
            playBtn.setOnClickListener(v -> {
                player.play();
                Toast.makeText(this, R.string.play_clicked, Toast.LENGTH_SHORT).show();
            });
            playBtn.setOnFocusChangeListener((v, hasFocus) -> {
                v.setBackgroundColor(getResources().getColor(hasFocus ? R.color.focused_button : R.color.default_button));
            });
        }

        if (infoBtn != null) {
            infoBtn.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.info_title)
                        .setMessage(R.string.info_message)
                        .setPositiveButton("OK", null)
                        .show();
            });
            infoBtn.setOnFocusChangeListener((v, hasFocus) -> {
                v.setBackgroundColor(getResources().getColor(hasFocus ? R.color.focused_button : R.color.default_button));
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}