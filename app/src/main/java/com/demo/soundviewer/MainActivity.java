package com.demo.soundviewer;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

public class MainActivity extends AppCompatActivity {

    private TextView msg;
    private VideoView videoView;
    private Visualizer visualizer;
    private BarVisualizer barVisualizer;

    ActivityResultLauncher<String> pickVideo = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    //msg.setText( result.toString() );
                    videoView.setVideoURI(result);
                    videoView.start();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.msg = this.findViewById(R.id.msg);
        this.videoView = this.findViewById(R.id.video_view);
        this.barVisualizer = this.findViewById(R.id.visualizer_bar);
        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                /*
                if( visualizer == null ) {
                    msg.setText(String.format("onPrepared %s", mp.getAudioSessionId()));
                    onVisualizeSound( mp.getAudioSessionId() );
                }
                */
                onVisualizeSound(mp.getAudioSessionId());
            }
        });
        this.findViewById(R.id.button_select).setOnClickListener( v -> {
            onSelectHandle();
        } );
    }

    private void onSelectHandle() {
        pickVideo.launch( "video/*" );
    }

    private void onVisualizeSound( int audioSessionId ){
        /*
        this.visualizer = new Visualizer(audioSessionId);
        this.visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                msg.setText("onWaveFormDataCapture");
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                msg.setText(String.format( "onFftDataCapture count:%s",fft.length ) );
            }
        },Visualizer.getMaxCaptureRate() / 2, false, true);
        visualizer.setEnabled(true);
         */
        this.barVisualizer.setAudioSessionId(audioSessionId);
    }
}