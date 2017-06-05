package br.org.multimidia.multimidiavoz.view;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import br.org.multimidia.multimidiavoz.BO.ConversaBO;
import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.domain.Conversa;
import br.org.multimidia.multimidiavoz.enuns.Action;
import br.org.multimidia.multimidiavoz.utils.Constant;
import br.org.multimidia.multimidiavoz.utils.DialogLigarContato;
import br.org.multimidia.multimidiavoz.utils.MobileApp;
import br.org.multimidia.multimidiavoz.utils.Router;
import br.org.multimidia.multimidiavoz.utils.Utils;

public class ActPrincipal extends AppCompatActivity {

    private Context context;

    public SipManager manager = null;
    public SipProfile me = null;
    public SipAudioCall call = null;
    public IncomingCallReceiver callReceiver;
    private ConversaBO conversas;
    private Conversa conversa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_principal);
        context = ActPrincipal.this;
        if (MobileApp.getApplication().getContatoLogado(context) == null) {
           finish();
        }
        setTitle(MobileApp.getApplication().getContatoLogado(context).getNome() +"" +
                " - " + MobileApp.getApplication().getContatoLogado(context).getId());
        Bundle args = getIntent().getExtras();
        if (args != null && args.containsKey(Action.MESSAGE.toString())) {
            Utils.showToast(context, args.get(Action.MESSAGE.toString()).toString());
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        // Set up the ViewPager with the sections adapter.
        toolbar.inflateMenu(R.menu.menu_act_principal);
        Utils.setFragmentReplacePage(getSupportFragmentManager(), new FgtConversas());
        try {
            conversas = new ConversaBO(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initServerSip();
    }

    private void initServerSip() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.SipDemo.INCOMING_CALL");
        callReceiver = new IncomingCallReceiver();
        this.registerReceiver(callReceiver, filter);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initializeManager();
    }

    public void initializeManager() {
        if(manager == null) {
            manager = SipManager.newInstance(this);
        }

        initializeLocalProfile();
    }
    /**
     * Logs you into your SIP provider, registering this device as the location to
     * send SIP calls to for your SIP address.
     */
    public void initializeLocalProfile() {
        if (manager == null) {
            return;
        }

        if (me != null) {
            closeLocalProfile();
        }
        Contato contato = MobileApp.getApplication().getContatoLogado(context);
        String username = "softphone-" + contato.getId();
        String domain = Constant.IP;
        String password = "1234";

        try {
            SipProfile.Builder builder = new SipProfile.Builder(username, domain);
            builder.setPassword(password);
            me = builder.build();

            Intent i = new Intent();
            i.setAction("android.SipDemo.INCOMING_CALL");
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, Intent.FILL_IN_DATA);
            manager.open(me, pi, null);


            manager.setRegistrationListener(me.getUriString(), new SipRegistrationListener() {
                public void onRegistering(String localProfileUri) {
                    updateStatus("Registering with SIP Server...");
                }

                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    updateStatus("Ready");
                    setTitle(MobileApp.getApplication().getContatoLogado(context).getNome() +"" +
                            " - " + MobileApp.getApplication().getContatoLogado(context).getId()+"" +
                            " - conectado");
                }

                public void onRegistrationFailed(String localProfileUri, int errorCode,
                                                 String errorMessage) {
                    updateStatus("Registration failed.  Please check settings.");
                    setTitle(MobileApp.getApplication().getContatoLogado(context).getNome() +"" +
                            " - " + MobileApp.getApplication().getContatoLogado(context).getId()+"" +
                            " - desconectado");
                }
            });
        } catch (ParseException pe) {
            updateStatus("Connection Error.");
        } catch (SipException se) {
            updateStatus("Connection error.");
        }
    }

    /**
     * Closes out your local profile, freeing associated objects into memory
     * and unregistering your device from the server.
     */
    public void closeLocalProfile() {
        if (manager == null) {
            return;
        }
        try {
            if (me != null) {
                manager.close(me.getUriString());
            }
        } catch (Exception ee) {
            Log.d("WalkieTalkieActivity/onDestroy", "Failed to close local profile.", ee);
        }
    }

    /**
     * Make an outgoing call.
     */
    public void initCall(Contato contato) {
        /**
         * Make an outgoing call.
         */
        conversa = new Conversa();
        conversa.setUsuarioDestino(contato.getNome());
        conversa.setDtaInicio(new Date());
        ActAtendido actAtendido = new ActAtendido();
        Bundle args = new Bundle();
        args.putString("nome", contato.getNome());
        actAtendido.setArguments(args);
        Utils.setFragmentReplacePage(getSupportFragmentManager(), actAtendido);
            String sipAddress = String.valueOf(contato.getId());
            updateStatus(contato.getNome());
            try {
                SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                    // Much of the client's interaction with the SIP Stack will
                    // happen via listeners.  Even making an outgoing call, don't
                    // forget to set up a listener to set things up once the call is established.
                    @Override
                    public void onCallEstablished(SipAudioCall call) {
                        call.startAudio();
                        call.setSpeakerMode(true);
                        if(call.isMuted()) {
                            call.toggleMute();
                        }

                        updateStatus(call);
                    }

                    @Override
                    public void onCallEnded(SipAudioCall call) {
                        updateStatus("Ready.");
                    }
                };
                String path = "sip:" + sipAddress + "@" + Constant.IP;
                call = manager.makeAudioCall(me.getUriString(), path, listener, 30);

               // call.startAudio();

            }
            catch (Exception e) {
                Log.i("WalkieTalkieActivity/InitiateCall", "Error when trying to close manager.", e);
                if (me != null) {
                    try {
                        manager.close(me.getUriString());
                    } catch (Exception ee) {
                        Log.i("WalkieTalkieActivity/InitiateCall",
                                "Error when trying to close manager.", ee);
                        ee.printStackTrace();
                    }
                }
                if (call != null) {
                    call.close();
                }
            }
    }

    /**
     * Updates the status box at the top of the UI with a messege of your choice.
     * @param status The String to display in the status box.
     */
    public void updateStatus(final String status) {
        // Be a good citizen.  Make sure UI changes fire on the UI thread.
        this.runOnUiThread(new Runnable() {
            public void run() {
                Utils.showToast(context, status);
            }
        });
    }

    /**
     * Updates the status box with the SIP address of the current call.
     * @param call The current, active call.
     */
    public void updateStatus(SipAudioCall call) {
        String useName = call.getPeerProfile().getDisplayName();
        if(useName == null) {
            useName = call.getPeerProfile().getUserName();
        }
        updateStatus(useName + "@" + call.getPeerProfile().getSipDomain());
    }
    /////////////////////###### aplicação Page #######/////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if(id == R.id.btn_deslogar) {
            MobileApp.getApplication().logout(context);
            Router.onCreateActivity(context, ActLogin.class);
            finish();
        } else if (id == R.id.btn_pesquisar_contatos) {
            abrirDialogContato();
//            Router.onCreateActivity(context, ActPesquisaContato.class);
        }
        return true;
    }

    private void abrirDialogContato() {
        DialogLigarContato dialog = new DialogLigarContato(context);
        dialog.show(new DialogLigarContato.CallBack() {
            @Override
            public void call(String profile) {
                Contato c = new Contato();
                c.setId(Integer.valueOf(profile));
                c.setNome(profile);
                c.setNumero(profile);
                initCall(c);
            }
        });
    }

    public void onEncerrarChamada() {
        if (conversa == null) {
            conversa = new Conversa();
            conversa.setDtaInicio(new Date());
        }
        conversa.setDtaFinal(new Date());
        conversas.create(conversa);
        callReceiver.onEncerrarLigacao();
        Utils.setFragmentReplacePage(getSupportFragmentManager(), new FgtConversas());
    }

    public void onAtenderChamada(String nome) {
        conversa = new Conversa();
        conversa.setUsuarioDestino(nome);
        conversa.setDtaInicio(new Date());
        callReceiver.onAtenderLigacao();
        ActAtendido actAtendido = new ActAtendido();
        Bundle args = new Bundle();
        args.putSerializable("nome", nome);
        actAtendido.setArguments(args);
        Utils.setFragmentReplacePage(getSupportFragmentManager(), actAtendido);
    }
}
