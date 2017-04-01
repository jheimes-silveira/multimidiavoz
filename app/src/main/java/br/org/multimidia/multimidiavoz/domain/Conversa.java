package br.org.multimidia.multimidiavoz.domain;

import java.util.Date;

/**
 * Created by jheimes on 24/03/17.
 */

public class Conversa {

    private Date dtaInicio;
    private Date dtaFinal;
    private String usuarioDestino;
    private String numeroDestino;

    public Date getDtaInicio() {
        return dtaInicio;
    }

    public void setDtaInicio(Date dtaInicio) {
        this.dtaInicio = dtaInicio;
    }

    public Date getDtaFinal() {
        return dtaFinal;
    }

    public void setDtaFinal(Date dtaFinal) {
        this.dtaFinal = dtaFinal;
    }

    public String getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(String usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public String getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(String numeroDestino) {
        this.numeroDestino = numeroDestino;
    }
}
