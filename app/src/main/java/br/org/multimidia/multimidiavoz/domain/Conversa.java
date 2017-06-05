package br.org.multimidia.multimidiavoz.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by jheimes on 24/03/17.
 */
@DatabaseTable
public class Conversa {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private Date dtaInicio;
    @DatabaseField
    private Date dtaFinal;
    @DatabaseField
    private String usuarioDestino;
    @DatabaseField
    private String numeroDestino;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
