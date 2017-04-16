package com.example.asiagibson.whatsthatsound;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by asiagibson on 4/16/17.
 */

public class Recorder extends Activity {

    private static final String LOG_TAG = "recording";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private static String mFileName = null;
    private MediaRecorder mRecorder = null;


    public Button playButton;
    public Button recButton;
    public Button stopButton;
    private boolean permissionToRecordAccepted = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        playButton = (Button) findViewById(R.id.play_bttn);
        recButton = (Button) findViewById(R.id.record_bttn);
        stopButton = (Button) findViewById(R.id.stop_bttn);

        recButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecording();
            }
        });

    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

}
