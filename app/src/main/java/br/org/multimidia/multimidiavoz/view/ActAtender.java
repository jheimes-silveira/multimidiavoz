package br.org.multimidia.multimidiavoz.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspServer;

import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.utils.LocalStorage;

public class ActAtender extends AppCompatActivity {

    private SurfaceView mSurfaceView;
    private LocalStorage localStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_act_atender);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponentes();
    }

    private void initComponentes() {
        initViews();
        initServer();
    }

    private void initServer() {

        // Sets the port of the RTSP server to 1234
        localStorage.setItem(RtspServer.KEY_PORT, String.valueOf(1234));

        // Configures the SessionBuilder
        SessionBuilder.getInstance()
                .setSurfaceView(mSurfaceView)
                .setPreviewOrientation(90)
                .setContext(getApplicationContext())
                .setAudioEncoder(SessionBuilder.AUDIO_NONE)
                .setVideoEncoder(SessionBuilder.VIDEO_H264);

        // Starts the RTSP server
        this.startService(new Intent(this,RtspServer.class));

    }

    private void initViews() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
    }
}
