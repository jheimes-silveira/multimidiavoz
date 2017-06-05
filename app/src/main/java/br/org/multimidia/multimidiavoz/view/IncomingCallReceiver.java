/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.org.multimidia.multimidiavoz.view;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipProfile;
import android.os.Bundle;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import br.org.multimidia.multimidiavoz.BO.ConversaBO;
import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.domain.Conversa;
import br.org.multimidia.multimidiavoz.utils.Callback;
import br.org.multimidia.multimidiavoz.utils.Utils;

/**
 * Listens for incoming SIP calls, intercepts and hands them off to WalkieTalkieActivity.
 */
public class IncomingCallReceiver extends BroadcastReceiver {

    private Callback callbackAtender;
    private Callback callbackEncerrar;
    private SipAudioCall incomingCall;
    /**
     * Processes the incoming call, answers it, and hands it over to the
     * WalkieTalkieActivity.
     * @param context The context under which the receiver is running.
     * @param intent The intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        incomingCall = null;
        try {

            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    try {
                        call.answerCall(30);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            final ActPrincipal actPrincipal = (ActPrincipal) context;

            incomingCall = actPrincipal.manager.takeAudioCall(intent, listener);


            String useName = incomingCall.getPeerProfile().getDisplayName();
            if(useName == null) {
                useName = incomingCall.getPeerProfile().getUserName();
            }
            ActAtender fgt = new ActAtender();
            Bundle args = new Bundle();
            args.putString("nome", useName);
            fgt.setArguments(args);
            Utils.setFragmentReplacePage(actPrincipal.getSupportFragmentManager(), fgt);

            callbackAtender = new Callback() {
                @Override
                public void call(Object object) {

                    try {
                        incomingCall.answerCall(30);
                    } catch (SipException e) {
                        e.printStackTrace();
                    }
                    incomingCall.startAudio();
                    incomingCall.setSpeakerMode(true);
                    if(incomingCall.isMuted()) {
                        incomingCall.toggleMute();
                    }

                    actPrincipal.call = incomingCall;

                    actPrincipal.updateStatus(incomingCall);
                }
            };
            callbackEncerrar = new Callback() {
                @Override
                public void call(Object object) {
                    try {
                        incomingCall.endCall();
                    } catch (SipException e) {
                        e.printStackTrace();
                    }
                }
            };

        } catch (Exception e) {
            if (incomingCall != null) {
                incomingCall.close();
            }
        }
    }

    public void onAtenderLigacao() {
        if (callbackAtender == null) {
            return;
        }
        callbackAtender.call(null);
    }
    public void onEncerrarLigacao() {
        if (callbackEncerrar == null) {
            return;
        }
        callbackEncerrar.call(null);
    }
}
