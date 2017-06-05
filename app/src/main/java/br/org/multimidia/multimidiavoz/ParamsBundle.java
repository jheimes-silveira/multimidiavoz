package br.org.multimidia.multimidiavoz;

import java.io.Serializable;

import br.org.multimidia.multimidiavoz.view.ActPrincipal;

/**
 * Created by jheimesilveira on 01/06/17.
 */

public class ParamsBundle implements Serializable {

    private ActPrincipal context;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ActPrincipal getContext() {
        return context;
    }

    public void setContext(ActPrincipal context) {
        this.context = context;
    }
}
