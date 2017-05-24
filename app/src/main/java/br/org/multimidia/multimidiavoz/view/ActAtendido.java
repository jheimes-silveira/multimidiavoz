package br.org.multimidia.multimidiavoz.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.audio.AudioQuality;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.video.VideoQuality;

import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Contato;

public class ActAtendido extends AppCompatActivity implements
        View.OnClickListener, Session.Callback, SurfaceHolder.Callback{

    private Contato contato;
    private TextView tvNome;
    private TextView tvNumero;
    private FloatingActionButton btnDesligar;
    private SurfaceView mSurfaceView;
    private Session mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_atendido);
        initComponentes();
        initSession();
    }

    private void initSession() {
        mSession = SessionBuilder.getInstance()
                .setCallback(this)
                .setSurfaceView(mSurfaceView)
                .setPreviewOrientation(90)
                .setContext(getApplicationContext())
                .setAudioEncoder(SessionBuilder.AUDIO_NONE)
                .setAudioQuality(new AudioQuality(16000, 32000))
                .setVideoEncoder(SessionBuilder.VIDEO_H264)
                .setVideoQuality(new VideoQuality(320,240,20,500000))
                .build();
        mSurfaceView.getHolder().addCallback(this);
        mSession.setDestination(contato.getId().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initComponentes() {
        contato = (Contato) getIntent().getExtras().get("contato");
        initViews();
        onBtnDesligar();
        tvNome.setText(contato.getNome());
        tvNumero.setText(contato.getNumero());
    }

    private void onBtnDesligar() {
        btnDesligar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSession.stop();
                finish();
            }
        });
    }

    private void initViews() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        tvNome = (TextView) findViewById(R.id.tvNome);
        tvNumero = (TextView) findViewById(R.id.tvNumero);
        btnDesligar = (FloatingActionButton) findViewById(R.id.btnDesligar);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSession.release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSession.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSession.stop();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBitrateUpdate(long bitrate) {

    }

    @Override
    public void onSessionError(int reason, int streamType, Exception e) {

    }

    @Override
    public void onPreviewStarted() {

    }

    @Override
    public void onSessionConfigured() {
        mSession.start();
    }

    @Override
    public void onSessionStarted() {

    }

    @Override
    public void onSessionStopped() {

    }
}
